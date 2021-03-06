package com.paragon.visitorchek.model;

import java.util.Date;
import java.util.List;

import com.paragon.visitorchek.util.ImageUtility;

public class VisitRequest {
	
	private long id;
	private List<BaggageRequest> baggage;
	private String mobileNumber;
	private VisitorNameRequest nameList;
	private int noofpeople;
	private String parking;
	private String purpose;
	private String remarks;
	private String visitorTalentId;
	private String hostTalentId;
	private String vehicalNo;
	private String visitEndtime;
	private String visitPlace;
	private String visitStarttime; 
	//private Date VisitStarttime;
	private String hostNotes;
	private String listStatus;
	private Integer requestId;
	private String address;
	private String hostAddress;
	private String token;
	
	
	


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
	private String image;
	private String type;
	private String hostImage;
	private String hostName;
	private String visitorName;
	private String email;
	private String username;

	
	private String In;
	private String Out;
	
	private String appointmentDate;

	private String visibility;
	
	private String nameOfVisitor;
	
	
	
	/*public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = ImageUtility.decompressImage(image);
	}*/
	
	
	public String getIn() {
		return In;
	}


	public String getAppointmentDate() {
		return appointmentDate;
	}


	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}


	public void setIn(String in) {
		In = in;
	}


	public String getOut() {
		return Out;
	}


	public void setOut(String out) {
		Out = out;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getHostNotes() {
		return hostNotes;
	}


	public void setHostNotes(String hostNotes) {
		this.hostNotes = hostNotes;
	}

 
	@Override
	public String toString() {
		
		return "Baggage"+ this.baggage +",Remarks"+this.remarks+",noofpeople"+this.noofpeople;
	}


	public String getHostImage() {
		return hostImage;
	}


	public void setHostImage(String hostImage) {
		this.hostImage = hostImage;
	}


	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public String getVisitorName() {
		return visitorName;
	}


	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public List<BaggageRequest> getBaggage() {
		return baggage;
	}


	public void setBaggage(List<BaggageRequest> baggage) {
		this.baggage = baggage;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public VisitorNameRequest getNameList() {
		return nameList;
	}


	public void setNameList(VisitorNameRequest nameList) {
		this.nameList = nameList;
	}


	public int getNoofpeople() {
		return noofpeople;
	}


	public void setNoofpeople(int noofpeople) {
		this.noofpeople = noofpeople;
	}


	public String getParking() {
		return parking;
	}


	public void setParking(String parking) {
		this.parking = parking;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getVisitorTalentId() {
		return visitorTalentId;
	}


	public void setVisitorTalentId(String visitorTalentId) {
		this.visitorTalentId = visitorTalentId;
	}


	public String getHostTalentId() {
		return hostTalentId;
	}


	public void setHostTalentId(String hostTalentId) {
		this.hostTalentId = hostTalentId;
	}


	public String getVehicalNo() {
		return vehicalNo;
	}


	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
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

//	public Integer getVisitStartTime() {
//		return visitStartTime;
//	}
//
//
//	public void setVisitStartTime(Integer visitStartTime) {
//		this.visitStartTime = visitStartTime;
//	}
	
	 
	 

	public Date getCreatedDate() {
		return createdDate;
	}


//	public String getVisitStarttime() {
//		return visitStarttime;
//	}
//
//
//	public void setVisitStarttime(String visitStarttime) {
//		this.visitStarttime = visitStarttime;
//	}


	


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


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getVisitStarttime() {
		return visitStarttime;
	}


	public void setVisitStarttime(String visitStarttime) {
		this.visitStarttime = visitStarttime;
	}


	public String getListStatus() {
		return listStatus;
	}


	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}


	public Integer getRequestId() {
		return requestId;
	}


	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}


	public String getVisibility() {
		return visibility;
	}


	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}


	public String getNameOfVisitor() {
		return nameOfVisitor;
	}


	public void setNameOfVisitor(String nameOfVisitor) {
		this.nameOfVisitor = nameOfVisitor;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getHostAddress() {
		return hostAddress;
	}


	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	
	
 
}
