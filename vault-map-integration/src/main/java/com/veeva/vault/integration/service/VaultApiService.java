package com.veeva.vault.integration.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veeva.vault.integration.config.VaultConfig;
import com.veeva.vault.integration.model.SiteStatusMapping;
import com.veeva.vault.integration.model.Study;
import com.veeva.vault.integration.model.StudySiteWithCountry;

@Service
public class VaultApiService {

	private static final Logger logger = LoggerFactory.getLogger(VaultApiService.class);

	@Autowired
	private VaultConfig vaultConfig;

	@Autowired
	private RestTemplate vaultRestTemplate;

	private String sessionId;
	private ObjectMapper objectMapper = new ObjectMapper();

	public String validateSession() {
		return this.sessionId;
	}

	public void setSession(String sessionId) {
		this.sessionId = sessionId;
	}

	private HttpHeaders getAuthenticatedHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", sessionId);
		headers.set("Accept", "application/json");
		return headers;
	}

	public List<StudySiteWithCountry> getStudySitesWithCountry(String studyId) throws Exception {
		logger.info("Retrieved {} getStudySitesWithCountry using study_ID ", studyId);
		String vqlQuery = "q=SELECT " + "name__v, " + "state__v, " + "study_country__v, " + "study_country__vr.id, "
				+ "study_country__vr.name__v, " + "study_country__vr.country__v, "
				+ "study_country__vr.external_id__v, " + "study_country__vr.edc_id__v, "
				+ "study_country__vr.target_lag_site_selection_to_greenlight__v, " + "study_country__vr.cdx_id__v, "
				+ "study_country__vr.state_stage_id__sys, " + "study_country__vr.stage__sys, "
				+ "study_country__vr.global_id__sys, " + "study_country__vr.country_abbreviation__c, " + "study__v, "
				+ "study_name__v, " + "site_status__v, " + "country__v, " + "country__vr.name__v, "
				+ "country__vr.status__v, " + "country__vr.abbreviation__c, " + "country__vr.external_id__v, "
				+ "country__vr.cdx_id__v, " + "country__vr.default_days_to_greenlight_site__v, "
				+ "country__vr.status_color_z__c, " + "country__vr.country_cda__v, " + "country__vr.latitude__c, "
				+ "country__vr.longitude__c " + "FROM site__v " + "WHERE study__v = '" + studyId + "'";
		logger.info("Executing vqlQuery {}  ", vqlQuery);
		HttpHeaders headers = getAuthenticatedHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(vqlQuery, headers);

		ResponseEntity<String> response = vaultRestTemplate.exchange(vaultConfig.getFullApiUrl() + "/query",
				HttpMethod.POST, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			logger.info("Server Response {}  ", response.getBody());
			JsonNode root = objectMapper.readTree(response.getBody());
			JsonNode data = root.get("data");

			List<StudySiteWithCountry> siteList = new ArrayList<>();
			if (data != null && data.isArray()) {
				for (JsonNode node : data) {
					StudySiteWithCountry site = objectMapper.treeToValue(node, StudySiteWithCountry.class);
					siteList.add(site);
				}
			}
			logger.info("Response received siteList {}  ", siteList.size());
			return siteList;

		} else {
			throw new Exception("Failed to retrieve study site data");
		}
	}

	public List<SiteStatusMapping> getSiteStatusMappings() {
		try {

			String vqlQuery = "q=SELECT study_status__c,name__v,color_code__c,study_status__c FROM site_status_mapping__c";
			logger.info("Retriving site_status_mapping__c vqlQuery is {}", vqlQuery);
			HttpHeaders headers = getAuthenticatedHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> entity = new HttpEntity<>(vqlQuery, headers);

			ResponseEntity<String> response = vaultRestTemplate.exchange(vaultConfig.getFullApiUrl() + "/query",
					HttpMethod.POST, entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				JsonNode responseBody = objectMapper.readTree(response.getBody());
				JsonNode dataArray = responseBody.get("data");

				List<SiteStatusMapping> mappings = new ArrayList<>();
				if (dataArray != null && dataArray.isArray()) {
					for (JsonNode mappingNode : dataArray) {
						SiteStatusMapping mapping = objectMapper.treeToValue(mappingNode, SiteStatusMapping.class);
						mappings.add(mapping);
					}
				}

				logger.info("Retrieved {} site status mappings", mappings.size());
				return mappings;
			} else {
				logger.warn("Failed to retrieve site status mappings. Using default mappings.");
				return getDefaultSiteStatusMappings();
			}

		} catch (Exception e) {
			logger.error("Error retrieving site status mappings", e);
			return getDefaultSiteStatusMappings();
		}
	}

	private List<SiteStatusMapping> getDefaultSiteStatusMappings() {
		List<SiteStatusMapping> defaultMappings = new ArrayList<>();
		defaultMappings.add(new SiteStatusMapping("Planned", "#FFA500"));
		defaultMappings.add(new SiteStatusMapping("Initiated", "#2E8B57"));
		defaultMappings.add(new SiteStatusMapping("Active", "#008000"));
		defaultMappings.add(new SiteStatusMapping("Suspended", "#FF0000"));
		defaultMappings.add(new SiteStatusMapping("Closed", "#808080"));
		defaultMappings.add(new SiteStatusMapping("Completed", "#4169E1"));
		return defaultMappings;
	}

	public List<Study> getAllStudies() throws Exception {
		String vqlQuery = "q=SELECT id, name__v FROM study__v";

		HttpHeaders headers = getAuthenticatedHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> entity = new HttpEntity<>(vqlQuery, headers);

		ResponseEntity<String> response = vaultRestTemplate.exchange(vaultConfig.getFullApiUrl() + "/query",
				HttpMethod.POST, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			JsonNode root = objectMapper.readTree(response.getBody());
			JsonNode data = root.get("data");

			List<Study> studies = new ArrayList<>();
			if (data != null && data.isArray()) {
				for (JsonNode node : data) {
					Study study = objectMapper.treeToValue(node, Study.class);
					studies.add(study);
				}
			}
			return studies;
		} else {
			throw new Exception("Failed to retrieve studies");
		}
	}
}
