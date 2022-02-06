package com.paragon.visitorchek.model;

public class VisitRequest {

	private long id;
	private String talentId;
	private String name;
	private String visitDate;
	private String purpose;
	private boolean visitWorkplace;
	private boolean visitHome;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTalentId() {
		return talentId;
	}
	public void setTalentId(String talentId) {
		this.talentId = talentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public boolean isVisitWorkplace() {
		return visitWorkplace;
	}
	public void setVisitWorkplace(boolean visitWorkplace) {
		this.visitWorkplace = visitWorkplace;
	}
	public boolean isVisitHome() {
		return visitHome;
	}
	public void setVisitHome(boolean visitHome) {
		this.visitHome = visitHome;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	
	
	
	
	
}
