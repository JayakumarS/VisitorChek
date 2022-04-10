package com.paragon.visitorchek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.paragon.visitorchek.service.BuildingAdminService;
import com.paragon.visitorchek.usermanagement.BuildingAdmin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Building Admin", description="Guard API operation")
@RestController
@RequestMapping("/guardRequest")
public class BuildingAdminController {

	@Autowired
	BuildingAdminService guardService;

	@ApiOperation(value = "Retrieves all the Guard Information")
	@GetMapping("/getAll")
	private List<BuildingAdmin> getAllGuardRequest() {
		return guardService.findAllGuards();
	}
	
	@ApiOperation(value = "Create a Guard entry")
	@RequestMapping(value = "/create")
	private BuildingAdmin saveGuardInfo(@RequestBody BuildingAdmin guardRequest) {
		// check if name is null, then capitalize the first char of userid and set.
		// this is a workaround until integration from talent search is complete.
		if (guardRequest.getName() == null || guardRequest.getName().trim().isEmpty()) {
			String name = guardRequest.getUserId();
			name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
            guardRequest.setName(name);
		}		
		return guardService.saveGuard(guardRequest);
	}

	@ApiOperation(value = "Update Guard entry")
	@RequestMapping(value = "/update")
	private BuildingAdmin updateGuardInfo(@RequestBody BuildingAdmin guardRequest) {
		return guardService.updateGuard(guardRequest);
	}
	
	//verifyTalentId
	@ApiOperation(value = "Validate Guard's talentId")
	@GetMapping("/validateTalentId/{talentId}")	
	private BuildingAdmin validateTalentId(@PathVariable("talentId") String talentId) {
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<String> result = restTemplate.getForEntity(talentId, String.class);
	    //LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>)result.getBody();
		return guardService.getGuardDetailsByTalentId(result.getBody());
	}
	

}
