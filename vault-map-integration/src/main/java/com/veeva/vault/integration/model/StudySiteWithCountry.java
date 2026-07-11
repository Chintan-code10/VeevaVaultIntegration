package com.veeva.vault.integration.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudySiteWithCountry {

	@JsonProperty("name__v")
	private String name;

	@JsonProperty("state__v")
	private String state;

	@JsonProperty("study_country__v")
	private String studyCountryId;

	@JsonProperty("study__v")
	private String studyId;

	@JsonProperty("site_status__v")
	private List<String> siteStatus;

	@JsonProperty("study_name__v")
	private String studyName;

	@JsonProperty("country__v")
	private String countryId;

	@JsonProperty("study_country__vr.id")
	private String countryRefId;

	@JsonProperty("study_country__vr.name__v")
	private String countryName;

	@JsonProperty("study_country__vr.country__v")
	private String countryCode;

	@JsonProperty("study_country__vr.external_id__v")
	private String studyCountryExternalId;

	@JsonProperty("study_country__vr.edc_id__v")
	private String studyCountryEdcId;

	@JsonProperty("study_country__vr.target_lag_site_selection_to_greenlight__v")
	private String targetLagToGreenlight;

	@JsonProperty("study_country__vr.cdx_id__v")
	private String studyCountryCdxId;

	@JsonProperty("study_country__vr.state_stage_id__sys")
	private String stateStageId;

	@JsonProperty("study_country__vr.stage__sys")
	private String stage;

	@JsonProperty("study_country__vr.global_id__sys")
	private String globalId;

	@JsonProperty("study_country__vr.country_abbreviation__c")
	private String countryAbbreviation;

	// Country reference fields
	@JsonProperty("country__vr.name__v")
	private String countryFullName;

	@JsonProperty("country__vr.status__v")
	private List<String> countryStatus;

	@JsonProperty("country__vr.abbreviation__c")
	private String countryAbbreviationCode;

	@JsonProperty("country__vr.external_id__v")
	private String countryExternalId;

	@JsonProperty("country__vr.cdx_id__v")
	private String countryCdxId;

	@JsonProperty("country__vr.default_days_to_greenlight_site__v")
	private Integer defaultDaysToGreenlight;

	@JsonProperty("country__vr.status_color_z__c")
	private String statusColorZ;

	@JsonProperty("country__vr.country_cda__v")
	private String countryCda;

	@JsonProperty("country__vr.latitude__c")
	private Double latitude;

	@JsonProperty("country__vr.longitude__c")
	private Double longitude;

	private String statusColor;
	private Integer plannedEnrollment;
	private Integer actualEnrollment;

	public StudySiteWithCountry() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStudyCountryId() {
		return studyCountryId;
	}

	public void setStudyCountryId(String studyCountryId) {
		this.studyCountryId = studyCountryId;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public List<String> getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(List<String> siteStatus) {
		this.siteStatus = siteStatus;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryRefId() {
		return countryRefId;
	}

	public void setCountryRefId(String countryRefId) {
		this.countryRefId = countryRefId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}

	public Integer getPlannedEnrollment() {
		return plannedEnrollment;
	}

	public void setPlannedEnrollment(Integer plannedEnrollment) {
		this.plannedEnrollment = plannedEnrollment;
	}

	public Integer getActualEnrollment() {
		return actualEnrollment;
	}

	public void setActualEnrollment(Integer actualEnrollment) {
		this.actualEnrollment = actualEnrollment;
	}

	// --- New Getters/Setters for added fields ---

	public String getStudyCountryExternalId() {
		return studyCountryExternalId;
	}

	public void setStudyCountryExternalId(String studyCountryExternalId) {
		this.studyCountryExternalId = studyCountryExternalId;
	}

	public String getStudyCountryEdcId() {
		return studyCountryEdcId;
	}

	public void setStudyCountryEdcId(String studyCountryEdcId) {
		this.studyCountryEdcId = studyCountryEdcId;
	}

	public String getTargetLagToGreenlight() {
		return targetLagToGreenlight;
	}

	public void setTargetLagToGreenlight(String targetLagToGreenlight) {
		this.targetLagToGreenlight = targetLagToGreenlight;
	}

	public String getStudyCountryCdxId() {
		return studyCountryCdxId;
	}

	public void setStudyCountryCdxId(String studyCountryCdxId) {
		this.studyCountryCdxId = studyCountryCdxId;
	}

	public String getStateStageId() {
		return stateStageId;
	}

	public void setStateStageId(String stateStageId) {
		this.stateStageId = stateStageId;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}

	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}

	public String getCountryFullName() {
		return countryFullName;
	}

	public void setCountryFullName(String countryFullName) {
		this.countryFullName = countryFullName;
	}

	public List<String> getCountryStatus() {
		return countryStatus;
	}

	public void setCountryStatus(List<String> countryStatus) {
		this.countryStatus = countryStatus;
	}

	public String getCountryAbbreviationCode() {
		return countryAbbreviationCode;
	}

	public void setCountryAbbreviationCode(String countryAbbreviationCode) {
		this.countryAbbreviationCode = countryAbbreviationCode;
	}

	public String getCountryExternalId() {
		return countryExternalId;
	}

	public void setCountryExternalId(String countryExternalId) {
		this.countryExternalId = countryExternalId;
	}

	public String getCountryCdxId() {
		return countryCdxId;
	}

	public void setCountryCdxId(String countryCdxId) {
		this.countryCdxId = countryCdxId;
	}

	public Integer getDefaultDaysToGreenlight() {
		return defaultDaysToGreenlight;
	}

	public void setDefaultDaysToGreenlight(Integer defaultDaysToGreenlight) {
		this.defaultDaysToGreenlight = defaultDaysToGreenlight;
	}

	public String getStatusColorZ() {
		return statusColorZ;
	}

	public void setStatusColorZ(String statusColorZ) {
		this.statusColorZ = statusColorZ;
	}

	public String getCountryCda() {
		return countryCda;
	}

	public void setCountryCda(String countryCda) {
		this.countryCda = countryCda;
	}
}
