package com.paragon.visitorchek.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paragon.visitorchek.dao.ReportDao;
import com.paragon.visitorchek.report.Report;
import com.paragon.visitorchek.report.ReportDisplay;


/**
 * Separate service class for Reports though the underlying table is visit_request. 
 *
 */
@Service
public class ReportService {

	@Autowired
	ReportDao reportDao;

	public List<ReportDisplay> getAllVisitorRequestByVisitDate(String visitDate) {
		List<Report> reportList = new ArrayList<Report>();
		reportDao.findvistorsbyVisitDate(visitDate).forEach(reportItem -> reportList.add(reportItem));
		List<ReportDisplay> finalReportList = massageReportData(reportList);
		return finalReportList;
	}
	
	public List<ReportDisplay> getAllVisitorRequestByDatesOrName(String startDate, String endDate, String name) {

	    String separator = "=";
	    int sepPos = startDate.indexOf(separator);
	    startDate = startDate.substring(sepPos+separator.length());
	    sepPos = endDate.indexOf(separator);
	    endDate = endDate.substring(sepPos+separator.length());
	    sepPos = name.indexOf(separator);
	    name = name.substring(sepPos+separator.length());
	    
		List<Report> reportList = new ArrayList<Report>();
		Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		//System.out.println("In service, startDate: " + startDate + ", endDate: " + endDate + ", name: " + name);
		// if all three are blank, use today's date as default and search.
		if ( StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate) && StringUtils.isBlank(name)) {
		    String strDate = formatter.format(date);  
		    reportList = reportDao.findvistorsbyVisitDate(strDate);
		} else if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate) && StringUtils.isBlank(name)) {
			// two dates have values, name is null/empty
		    reportList = reportDao.findvistorsbyVisitDateRange(startDate, endDate);
		} else if (StringUtils.isNotBlank(startDate) && StringUtils.isBlank(endDate) && StringUtils.isBlank(name)){
			// startDate has value
		    reportList = reportDao.findvistorsbyVisitDate(startDate);
		} else if (StringUtils.isBlank(startDate) && StringUtils.isNotBlank(endDate) && StringUtils.isBlank(name)){
			// endDate has value
		    reportList = reportDao.findvistorsbyVisitDate(endDate);
		} else if (StringUtils.isNotBlank(name)){
			// name has value
			name += "%";
			if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
				reportList = reportDao.findvistorsbyName(name);
			} else if (StringUtils.isNotBlank(startDate) && StringUtils.isBlank(endDate)) {
				endDate = startDate;
				reportList = reportDao.findvistorsbyDatesOrName(startDate, endDate, name);
			} else if (StringUtils.isBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				startDate = endDate;
				reportList = reportDao.findvistorsbyDatesOrName(startDate, endDate, name);
			} else {
				// both dates are not blank
				reportList = reportDao.findvistorsbyDatesOrName(startDate, endDate, name);
			}
		}
		List<ReportDisplay> finalReportList = massageReportData(reportList);
		return finalReportList;
	}
	
	private List<ReportDisplay> massageReportData(List<Report> reportList) {
		List<ReportDisplay> customReportList = new ArrayList<ReportDisplay>();
		// set primary name or searched name and other names
		Method method;
		String methodName;
		String startTime, endTime, visitDate;
		for (Report report : reportList) {
			ReportDisplay reportDisplay = new ReportDisplay();
			// get startTime, endTime and visitDate in proper format
			startTime = ""; endTime = ""; visitDate = "";
			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(report.getVisitStartime());
				startTime = new SimpleDateFormat("hh:mm a").format(date);
				visitDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(report.getVisitEndtime());
				endTime = new SimpleDateFormat("hh:mm a").format(date);
				
				reportDisplay.setVisitDate(visitDate);
				reportDisplay.setVisitStartime(startTime);
				reportDisplay.setVisitEndtime(endTime);
				reportDisplay.setHostName(report.getHostName());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			reportDisplay.setVisitorName(report.getName1());
			// concatenate name2 to name10 values in otherNames, show name1 as Visitor Name
			for (int i=2; i<=10; i++) {
				methodName = "getName" + i; // used in method.invoke call to call appropriate getName<int> method.
				try {
					method = report.getClass().getMethod(methodName);
					String name = (String)method.invoke(report);
					if (StringUtils.isNotBlank(name)) {
						if (StringUtils.isNotBlank(report.getOtherNames())) {
							report.setOtherNames(report.getOtherNames() + ", " + name);
						} else {
							report.setOtherNames(name);
						}
					}
					
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			reportDisplay.setOtherNames(report.getOtherNames());
			reportDisplay.setPurpose(report.getPurpose());
			reportDisplay.setRowNum(report.getRowNum());
			reportDisplay.setVisitPlace(report.getVisitPlace());
			customReportList.add(reportDisplay);
		}
		return customReportList;
	}
}
