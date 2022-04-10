package com.paragon.visitorchek.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Purpose: POJO to hold report fields - used in display.
 * Db results are held in Report object, ReportService.massageReportData() transfers the results
 * to ReportDisplay object type.
 * otherNames - holds concatenated values from name2...name10. name1 is displayed as VisitorName.
 * Note: Name search searches all the name<n> columns, display of name results follows the above logic.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDisplay {
	private Integer rowNum;
	private String visitDate;
	private String visitStartime;
	private String visitEndtime;
	private String purpose;
	private String visitPlace;
	
	private String otherNames;
	private String visitorName;
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getVisitStartime() {
		return visitStartime;
	}
	public void setVisitStartime(String visitStartime) {
		this.visitStartime = visitStartime;
	}
	public String getVisitEndtime() {
		return visitEndtime;
	}
	public void setVisitEndtime(String visitEndtime) {
		this.visitEndtime = visitEndtime;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getVisitPlace() {
		return visitPlace;
	}
	public void setVisitPlace(String visitPlace) {
		this.visitPlace = visitPlace;
	}
	public String getOtherNames() {
		return otherNames;
	}
	public void setOtherNames(String otherNames) {
		this.otherNames = otherNames;
	}
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	
	
}
