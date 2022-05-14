package com.paragon.visitorchek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paragon.visitorchek.model.VisitRequest;
import com.paragon.visitorchek.model.VisitorPass;
import com.paragon.visitorchek.service.VisitorPassService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Visitor Pass", description="Visit Pass API operation")
@RestController
public class VisitorPassController {
	
	@Autowired
	VisitorPassService visitorPassService;
	
	@ApiOperation(value="Visitor Pass Request")
	@PostMapping("/visitorPassScreen")
	private VisitorPass saveVisitorPass(@RequestBody VisitorPass visitorPass) {
		return visitorPassService.saveVisitorPass(visitorPass);
	}
	
	@ApiOperation(value = "Retrieves all the Visitor Pass Request")
	@GetMapping("/visitorPassScreenList")
	private List<VisitorPass> getAllVisitorPass(@RequestParam String hostid) {
		return visitorPassService.getAllVisitorPass(hostid);
	}

}
