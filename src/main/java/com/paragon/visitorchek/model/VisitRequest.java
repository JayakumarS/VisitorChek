package com.paragon.visitorchek.model;

import java.util.Date;

import com.paragon.visitorchek.util.ImageUtility;

public class VisitRequest {

	private long id;
	private String talentId;
	private String name;
	private String visitorId;
	private String hostId;
	private String imagePath;
	private long vistorMobileNumber;

    private long hostMobileNumber;
	private String purpose;
	private String visitStarttime;
	private String visitEndtime;
	private String visitPlace;
	private String parking; 
	private String vehicalNo;
	private int noofpeople;
	private String remarks;
	private String baggage;
	private Date createdDate;
	private String createdBy;
	private Date  updatedDate;
	private String updatedBy;
	private Date cancelledDate;
	private String cancelledBy;
	 private String approveBy;// pending by deafult for visitor,approved by default for host,reject,delete
	 private Date approveTime; 
	private String gaurdId;  
	 private Date 	vistorInTime;  
	private Date  vistorOutTime;
	private byte[] image;
	private String type;
	

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = ImageUtility.decompressImage(image);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

 
	public long getVistorMobileNumber() {
		return vistorMobileNumber;
	}

	public void setVistorMobileNumber(long vistorMobileNumber) {
		this.vistorMobileNumber = vistorMobileNumber;
	}

	public long getHostMobileNumber() {
		return hostMobileNumber;
	}

	public void setHostMobileNumber(long hostMobileNumber) {
		this.hostMobileNumber = hostMobileNumber;
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

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

 
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getVisitStarttime() {
		return visitStarttime;
	}

	public void setVisitStarttime(String visitStarttime) {
		this.visitStarttime = visitStarttime;
	}

	public String getVisitEndtime() {
		return visitEndtime;
	}

	public void setVisitEndtime(String visitEndtime) {
		this.visitEndtime = visitEndtime;
	}

	public String getVisitPlace() {
		return visitPlace;
	}

	public void setVisitPlace(String visitPlace) {
		this.visitPlace = visitPlace;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getVehicalNo() {
		return vehicalNo;
	}

	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}

	public int getNoofpeople() {
		return noofpeople;
	}

	public void setNoofpeople(int noofpeople) {
		this.noofpeople = noofpeople;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBaggage() {
		return baggage;
	}

	public void setBaggage(String baggage) {
		this.baggage = baggage;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public String getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	 
	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getGaurdId() {
		return gaurdId;
	}

	public void setGaurdId(String gaurdId) {
		this.gaurdId = gaurdId;
	}

	public Date getVistorInTime() {
		return vistorInTime;
	}

	public void setVistorInTime(Date vistorInTime) {
		this.vistorInTime = vistorInTime;
	}

	public Date getVistorOutTime() {
		return vistorOutTime;
	}

	public void setVistorOutTime(Date vistorOutTime) {
		this.vistorOutTime = vistorOutTime;
	}

	@Override
	public String toString() {
		
		return "Baggage"+ this.baggage +",Remarks"+this.remarks+",noofpeople"+this.noofpeople;
	}
}
