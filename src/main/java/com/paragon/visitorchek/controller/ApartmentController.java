package com.paragon.visitorchek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paragon.visitorchek.model.ApartmentRequest;
import com.paragon.visitorchek.model.CommercialSpaceRequest;
import com.paragon.visitorchek.model.GuardRequest;
import com.paragon.visitorchek.service.ApartmentService;
 
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Apartment", description="Apartment API operation")
@RestController
public class ApartmentController {
	
	@Autowired
	ApartmentService apartmentService;
	
	@ApiOperation(value = "Retrieves all the Apartment Request")
	@GetMapping("/apartmentList")
	private List<ApartmentRequest> getAllApartmentRequest(@RequestParam String talentId) {
		return apartmentService.getApartmentRequest(talentId);
	}
	
	@ApiOperation(value = "Save all the Apartment Request")
	@PostMapping("/saveApartment")
	private  List<ApartmentRequest> saveApartment(@RequestBody  List<ApartmentRequest> list) {
		  apartmentService.saveApartment(list);
		  return list;
	}
	@ApiOperation(value = "delete Apartment ")
	@GetMapping("/deleteApartment")
	private boolean deleteApartment(@RequestParam int id) {
		return apartmentService.deleteApartment(id);
	}
	
	@ApiOperation(value = "Retrieves all the Commericial Space")
	@GetMapping("/commercialSpaceList")
	private List<CommercialSpaceRequest> getCommercialSpaceRequest(@RequestParam String talentId) {
		return apartmentService.getCommercialSpaceRequest(talentId);
	}
	
	@ApiOperation(value = "Save all the Commericial Space")
	@PostMapping("/saveCommercialSpace")
	private  List<CommercialSpaceRequest> saveCommercialSpace(@RequestBody  List<CommercialSpaceRequest> list) {
		  apartmentService.saveCommercialSpace(list);
		  return list;
	}
	@ApiOperation(value = "delete Commericial Space ")
	@GetMapping("/deleteCommercialSpace")
	private boolean deleteCommercialSpace(@RequestParam int id) {
		return apartmentService.deleteCommercialSpace(id);
	}
	
	@ApiOperation(value = "Retrieves all the Guard")
	@GetMapping("/guardList")
	private List<GuardRequest> guardList(@RequestParam String talentId,String orgId) {
		return apartmentService.guardList(talentId, orgId);
		//return apartmentService.guardList(talentId,  orgId);
	}
	
	@ApiOperation(value = "Save all the Guard")
	@PostMapping("/saveGuard")
	private  List<GuardRequest> saveGuard(@RequestBody  List<GuardRequest> list) {
		  apartmentService.saveGuard(list);
		  return list;
	}
	@ApiOperation(value = "delete Guard")
	@GetMapping("/deleteGuard")
	private boolean deleteGuard(@RequestParam int id) {
		return apartmentService.deleteGuard(id);
	}

}
