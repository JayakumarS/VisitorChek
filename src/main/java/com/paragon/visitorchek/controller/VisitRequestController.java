package com.paragon.visitorchek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paragon.visitorchek.model.SideBar;
import com.paragon.visitorchek.model.VisitRequest;
import com.paragon.visitorchek.service.SideBarService;
import com.paragon.visitorchek.service.VisitRequestService;
import com.paragon.visitorchek.usermanagement.User;
import com.paragon.visitorchek.usermanagement.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Visitor Chek", description="Visit Request API operation")
@RestController
public class VisitRequestController {

	@Autowired
	SideBarService sideBarService;
	
	@Autowired
	VisitRequestService visitRequestService;
	
	@Autowired
	UserRepository userRepository;

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
	
	@ApiOperation(value = "Retrieves all the visitRequest")
	@GetMapping("/myList/{hostid}")
	private List<VisitRequest> getAllMyList(@PathVariable("hostid") String hostid) {
		return visitRequestService.getAllMyList(hostid);
	}
	
	@ApiOperation(value = "Retrieves all the Visit Request for host id")
	@GetMapping("/hostRequest/{hostid}")
	private List<VisitRequest> getAllVisitorRequestUsingHostId(@PathVariable("hostid") String hostid) {
		return visitRequestService.getAllVisitorRequestUsingHostId(hostid);
	}
	
	 @ApiOperation(value = "Retrieves all the Visit Request for id")
	@GetMapping("/getNotesValue/{id}")
	private List<VisitRequest> getNotesValue(@PathVariable("id") Integer id) {
		return visitRequestService.getNotesValue(id);
	}
	
	@ApiOperation(value = "Retrieves all the Visit Request for visitorId")
	@GetMapping("/visitRequestByVisitorId/{visitorid}")
	private List<VisitRequest> getAllVisitorRequestVisitorId(@PathVariable("visitorid") String visitorid) {
		return visitRequestService.getAllVisitorRequestVisitorId(visitorid);
	}
	
	@ApiOperation(value = "Retrieves all the Visit Request for visitorId")
	@GetMapping("/getGuard/{visitorid}")
	private SideBar getGuard(@PathVariable("visitorid") String visitorid) throws Exception {
		return sideBarService.getGuard(visitorid);
	}

	@ApiOperation(value = "Retrieves all Visit Requests for a Visit Date")
	@GetMapping("/visitRequestByDate/{visitDate}")
	private List<VisitRequest> getAllVisitorRequestVisitDate(@PathVariable("visitDate") String visitDate) {
		return visitRequestService.getAllVisitorRequestByVisitDate(visitDate);
	}
	
	@ApiOperation(value = "Create a Host Visit Request")
	@PostMapping("/hostvisitRequest")
	private VisitRequest savebyHost(@RequestBody VisitRequest visitRequest) {
		return visitRequestService.savebyHost(visitRequest);
	}
	@ApiOperation(value = "Updating visitor request status")
	@GetMapping("/visitRequestByVisitorId/{status}/{hostid}/{id}")
	private Boolean updateRequestStatus(@PathVariable("status") String status ,@PathVariable("hostid") String hostid , @PathVariable("id") int id) {
		return visitRequestService.updateRequestStatus(status,hostid ,id);
	}
	
	@ApiOperation(value = "Update the existing Visit Request")
	@PutMapping("/visitRequest")
	private VisitRequest updateProductOrder(@RequestBody VisitRequest visitRequest) {
		visitRequestService.update(visitRequest);
		return visitRequest;
	}
	@ApiOperation(value = "GET User details")
	@GetMapping("/users")
	private Iterable<User> getAllUser() {
		return userRepository.findAll();
	}
	@ApiOperation(value = "Create a Notes")
	@PostMapping("/notes")
	private VisitRequest saveNotes(@RequestBody VisitRequest visitRequest) {
		return visitRequestService.saveNotes(visitRequest);
	}
	
	@ApiOperation(value = "Save ListIn")
	@PostMapping("/saveIn")
	private VisitRequest saveListIn(@RequestBody VisitRequest visitRequest) {
		return visitRequestService.saveListIn(visitRequest);
	}
	
	@ApiOperation(value = "save ListOut")
	@PostMapping("/saveOut")
	private VisitRequest saveListOut(@RequestBody VisitRequest visitRequest) {
		return visitRequestService.saveListOut(visitRequest);
	}
	
	@ApiOperation(value = "save Token")
	@PostMapping("/saveToken")
	private VisitRequest saveToken(@RequestBody VisitRequest visitRequest) {
		return visitRequestService.saveToken(visitRequest);
	}
	
	@ApiOperation(value = "Email Accept")
	@GetMapping("/emailAccept")
	private Boolean saveAccept(@RequestParam String id ,@RequestParam String emailId , @RequestParam String res) {
		return visitRequestService.saveAccept(emailId,id,res);
	}
	
}
