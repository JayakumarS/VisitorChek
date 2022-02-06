package com.paragon.visitorchek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paragon.visitorchek.model.VisitRequest;
import com.paragon.visitorchek.service.VisitRequestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Visitor Chek", description="Visit Request API operation")
@RestController
public class VisitRequestController {

	@Autowired
	VisitRequestService visitRequestService;

	@ApiOperation(value = "Retrieves all the Visit Request")
	@GetMapping("/visitRequest")
	private List<VisitRequest> getAllVisitRequest() {
		return visitRequestService.getAllVisitRequest();
	}

	@ApiOperation(value = "Retrieves specific Visit Request")
	@GetMapping("/visitRequest/{visitRequestId}")
	private VisitRequest getVisitRequestById(@PathVariable("visitRequestId") long visitRequestId) {
		return visitRequestService.getVisitRequestById(visitRequestId);
	}

	@ApiOperation(value = "Delete specific Visit Request")
	@DeleteMapping("/visitRequest/{visitRequestId}")
	private void deleteProductOrder(@PathVariable("visitRequestId") long visitRequestId) {
		visitRequestService.delete(visitRequestId);
	}

	@ApiOperation(value = "Create a Visit Request")
	@PostMapping("/visitRequest")
	private VisitRequest saveProductOrder(@RequestBody VisitRequest visitRequest) {
		return visitRequestService.save(visitRequest);
	}

	@ApiOperation(value = "Update the existing Visit Request")
	@PutMapping("/visitRequest")
	private VisitRequest updateProductOrder(@RequestBody VisitRequest visitRequest) {
		visitRequestService.update(visitRequest);
		return visitRequest;
	}
}
