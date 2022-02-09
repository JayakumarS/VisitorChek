package com.paragon.visitorchek.model;

public class VisitRequest {

	private long id;
	private String talentId;
	private String name;
	private String visitDate;
	private String purpose;
	private boolean visitWorkplace;
	private boolean visitHome;
	private Long mobileNumber;
	private String imagePath;
	private String baggage;
	private String fromdate;
	private Integer noofpeople;
	private String parking; 
	private String remarks;
	private String toDate;
	private String type;
	private String vehical;
	
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
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getBaggage() {
		return baggage;
	}
	public void setBaggage(String baggage) {
		this.baggage = baggage;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public Integer getNoofpeople() {
		return noofpeople;
	}
	public void setNoofpeople(Integer noofpeople) {
		this.noofpeople = noofpeople;
	}
	public String getParking() {
		return parking;
	}
	public void setParking(String parking) {
		this.parking = parking;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVehical() {
		return vehical;
	}
	public void setVehical(String vehical) {
		this.vehical = vehical;
	}
	@Override
	public String toString() {
		
		return "Baggage"+ this.baggage +",Remarks"+this.remarks+",noofpeople"+this.noofpeople;
	}
}
