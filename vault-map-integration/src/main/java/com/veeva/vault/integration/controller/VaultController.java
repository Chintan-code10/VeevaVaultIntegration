package com.veeva.vault.integration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wonderdrugs")
public class VaultController {

	private static final Logger logger = LoggerFactory.getLogger(VaultController.class);

	@GetMapping("welcome")
	public String session(Model model) {
		logger.info("In controller");
		try {
			return "home";

		} catch (Exception e) {
			model.addAttribute("error", "Unable to load study map. Please try again later.");
			return "error";
		}
	}

}
