package com.veeva.vault.integration.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Study {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name__v")
	private String name;

	@JsonProperty("study_number__v")
	private String studyNumber;

	@JsonProperty("study_phase__v")
	private String studyPhase;

	@JsonProperty("product__v")
	private String productId;

	@JsonProperty("indication__v")
	private String indicationId;

	@JsonProperty("sponsor__v")
	private String sponsorId;

	@JsonProperty("study_type__v")
	private String studyType;

	@JsonProperty("start_date__v")
	private Date startDate;

	@JsonProperty("planned_completion_date__v")
	private Date plannedCompletionDate;

	@JsonProperty("status__v")
	private String status;

	// Additional fields
	private String productName;
	private String indicationName;
	private String sponsorName;
	private Integer totalSites;
	private Integer totalCountries;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudyNumber() {
		return studyNumber;
	}

	public void setStudyNumber(String studyNumber) {
		this.studyNumber = studyNumber;
	}

	public String getStudyPhase() {
		return studyPhase;
	}

	public void setStudyPhase(String studyPhase) {
		this.studyPhase = studyPhase;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getIndicationId() {
		return indicationId;
	}

	public void setIndicationId(String indicationId) {
		this.indicationId = indicationId;
	}

	public String getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getStudyType() {
		return studyType;
	}

	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getPlannedCompletionDate() {
		return plannedCompletionDate;
	}

	public void setPlannedCompletionDate(Date plannedCompletionDate) {
		this.plannedCompletionDate = plannedCompletionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getIndicationName() {
		return indicationName;
	}

	public void setIndicationName(String indicationName) {
		this.indicationName = indicationName;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public Integer getTotalSites() {
		return totalSites;
	}

	public void setTotalSites(Integer totalSites) {
		this.totalSites = totalSites;
	}

	public Integer getTotalCountries() {
		return totalCountries;
	}

	public void setTotalCountries(Integer totalCountries) {
		this.totalCountries = totalCountries;
	}
}