package com.veeva.vault.integration.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteStatusMapping {

	@JsonProperty("id")
	private String id;

	@JsonProperty("study_status__c")
	private List<String> studyStatus;

	@JsonProperty("name__v")
	private String name;

	@JsonProperty("status_label__v")
	private String statusLabel;

	@JsonProperty("color_code__c")
	private String colorCode;

	@JsonProperty("display_order__v")
	private Integer displayOrder;

	@JsonProperty("active__v")
	private Boolean active;

	@JsonProperty("description__v")
	private String description;

	public SiteStatusMapping() {
	}

	public SiteStatusMapping(String statusName, String colorCode) {
		this.name = statusName;
		this.colorCode = colorCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getStudyStatus() {
		return studyStatus;
	}

	public void setStudyStatus(List<String> studyStatus) {
		this.studyStatus = studyStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
