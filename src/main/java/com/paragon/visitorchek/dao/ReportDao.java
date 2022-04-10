package com.paragon.visitorchek.dao;

import java.util.List;

import com.paragon.visitorchek.report.Report;

public interface ReportDao {

	List<Report> findvistorsbyVisitDate(String visitDate);
	
	List<Report> findvistorsbyVisitDateRange(String startDate, String endDate);
	
	List<Report> findvistorsbyDatesOrName(String startDate, String endDate, String name);
	
	List<Report> findvistorsbyName(String name);
	
}
