package com.paragon.visitorchek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paragon.visitorchek.report.ReportDisplay;
import com.paragon.visitorchek.service.ReportService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Vistor Report", description="Report data")
@RestController
@RequestMapping("/reportRequest")
public class ReportController {
	@Autowired
	ReportService reportService;

	@ApiOperation(value = "Retrieves all Visit Requests for a Visit Date")
	@GetMapping("/byDate/{visitDate}")
	private List<ReportDisplay> getAllVisitorRequestVisitDate(@PathVariable("visitDate") String visitDate) {
		return reportService.getAllVisitorRequestByVisitDate(visitDate);
	}
	
	@ApiOperation(value = "Retrieves all Visit Requests for report")
	@GetMapping(value = "/byParms/{startDate}/{endDate}/{name}")
	private List<ReportDisplay> getAllVisitorRequestForReport(@PathVariable("startDate") String startDate ,@PathVariable("endDate") String endDate , @PathVariable("name") String name) {
		return reportService.getAllVisitorRequestByDatesOrName(startDate, endDate, name);
    }
}
