package com.veeva.vault.integration.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wonderdrugs")
public class VaultController {

	private static final Logger logger = LoggerFactory.getLogger(VaultController.class);

	@GetMapping("welcome")
	public String session(Model model) {
		logger.info("In controller");
		try {
			return "HOME";

		} catch (Exception e) {
			model.addAttribute("error", "Unable to load study map. Please try again later.");
			return "error";
		}
	}

	@GetMapping("")
	public Object showStudyMap(Model model, @RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
		if (sessionId == null || sessionId.isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session");
		}
		try {
//			List<Map<String, Object>> availableStudies = mapDataService.getAvailableStudies(sessionId);
//			model.addAttribute("availableStudies", availableStudies);
			logger.info("Displaying study map page with available studies");
			return "study-map";
		} catch (Exception e) {
			logger.error("Error loading study map page", e);
			model.addAttribute("error", "Unable to load study map. Please try again later.");
			return "error";
		}
	}
}
