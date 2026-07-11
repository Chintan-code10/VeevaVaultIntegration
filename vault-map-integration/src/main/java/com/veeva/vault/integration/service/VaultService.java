package com.veeva.vault.integration.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veeva.vault.integration.model.SiteStatusMapping;
import com.veeva.vault.integration.model.Study;
import com.veeva.vault.integration.model.StudySiteWithCountry;

@Service
public class VaultService {

	private static final Logger logger = LoggerFactory.getLogger(VaultService.class);

	@Autowired
	private VaultApiService vaultApiService;

	public Map<String, Object> getStudyMapData(String studyId) {
		try {
			logger.info("Generating map data for study: {}", studyId);

			List<StudySiteWithCountry> sites = vaultApiService.getStudySitesWithCountry(studyId);
			List<SiteStatusMapping> statusMappings = vaultApiService.getSiteStatusMappings();

			Map<String, Object> mapData = new HashMap<>();
			mapData.put("sites", sites);
			mapData.put("legend", createLegendData(statusMappings));

			logger.info("Successfully generated map data with {} sites", sites.size());

			return mapData;

		} catch (Exception e) {
			logger.error("Error generating map data for study " + studyId, e);
			return new HashMap<>();
		}
	}

	private List<Map<String, Object>> createLegendData(List<SiteStatusMapping> statusMappings) {
		return statusMappings.stream()
				.sorted(Comparator
						.comparing(mapping -> mapping.getDisplayOrder() != null ? mapping.getDisplayOrder() : 0))
				.map(mapping -> {
					Map<String, Object> legendItem = new HashMap<>();
					legendItem.put("status", mapping.getStudyStatus());
					legendItem.put("label",
							mapping.getStatusLabel() != null ? mapping.getStatusLabel() : mapping.getName());
					legendItem.put("color", mapping.getColorCode());
					return legendItem;
				}).collect(Collectors.toList());
	}

	public List<Map<String, Object>> getAvailableStudies(String sessionId) {
		try {

			vaultApiService.setSession(sessionId);
			List<Study> studies = vaultApiService.getAllStudies();

			return studies.stream().map(study -> {
				Map<String, Object> studyInfo = new HashMap<>();
				studyInfo.put("id", study.getId());
				studyInfo.put("name", study.getName());
				studyInfo.put("studyNumber", study.getStudyNumber());
				studyInfo.put("phase", study.getStudyPhase());
				studyInfo.put("status", study.getStatus());
				return studyInfo;
			}).collect(Collectors.toList());

		} catch (Exception e) {
			logger.error("Error retrieving available studies", e);
			return new ArrayList<>();
		}
	}
}
