package com.veeva.vault.integration.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.veeva.vault.integration.service.VaultService;

@Controller
@RequestMapping("/wonderdrugs")
public class VaultController {

	private static final Logger logger = LoggerFactory.getLogger(VaultController.class);

	@Autowired
	private VaultService vaultService;

	@GetMapping("")
	public Object showStudyMap(Model model, @RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
		System.out.println("Here");
		if (sessionId == null || sessionId.isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session");
		}

		try {
			List<Map<String, Object>> availableStudies = vaultService.getAvailableStudies(sessionId);
			model.addAttribute("availableStudies", availableStudies);
			logger.info("Displaying study map page with {} available studies", availableStudies.size());
			return "site-study";
		} catch (Exception e) {
			logger.error("Error loading study map page", e);
			model.addAttribute("error", "Unable to load study map. Please try again later.");
			return "error";
		}
	}

	@GetMapping("session")
	public String session(Model model) {
		try {
			return "home";

		} catch (Exception e) {
			logger.error("Error loading study map page", e);
			model.addAttribute("error", "Unable to load study map. Please try again later.");
			return "error";
		}
	}

	@GetMapping("/api/data/{studyId}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getStudyMapData(@PathVariable String studyId) {
		try {
			logger.info("API request for study map data: {}", studyId);

			Map<String, Object> mapData = vaultService.getStudyMapData(studyId);

			if (mapData.isEmpty()) {
				logger.warn("No map data found for study: {}", studyId);
				return ResponseEntity.notFound().build();
			}

			logger.info("Successfully returned map data for study: {}", studyId);
			return ResponseEntity.ok(mapData);

		} catch (Exception e) {
			logger.error("Error retrieving map data for study " + studyId, e);
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/health")
	@ResponseBody
	public ResponseEntity<Map<String, String>> healthCheck() {
		try {
			Map<String, String> health = Map.of("status", "UP", "timestamp", String.valueOf(System.currentTimeMillis()),
					"service", "Study Map Integration");

			return ResponseEntity.ok(health);

		} catch (Exception e) {
			logger.error("Health check failed", e);
			return ResponseEntity.internalServerError().body(Map.of("status", "DOWN", "error", e.getMessage()));
		}
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleException(Exception e) {
		logger.error("Unhandled exception in VaultController", e);

		Map<String, String> errorResponse = Map.of("error", "Internal server error", "message",
				e.getMessage() != null ? e.getMessage() : "Unknown error", "timestamp",
				String.valueOf(System.currentTimeMillis()));

		return ResponseEntity.internalServerError().body(errorResponse);
	}
}
