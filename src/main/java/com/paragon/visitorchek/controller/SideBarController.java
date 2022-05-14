package com.paragon.visitorchek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.paragon.visitorchek.model.SideBar;
import com.paragon.visitorchek.service.SideBarService;
import com.paragon.visitorchek.usermanagement.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Visitor Chek", description="Sidebar API operation")
@RestController
public class SideBarController {
	
	
	@Autowired
	SideBarService sideBarService;
	
	@Autowired
	UserRepository userRepository;
	
//	@ApiOperation(value = "Get Guard Value")
//	@GetMapping("/getGuard/{visitorId}")
//	private SideBar getGuard(@PathVariable("visitorId") String visitorId) throws Exception {
//		return sideBarService.getGuard(visitorId);
//	}

}
