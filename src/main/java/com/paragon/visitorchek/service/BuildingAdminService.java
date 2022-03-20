package com.paragon.visitorchek.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paragon.visitorchek.usermanagement.BuildingAdmin;
import com.paragon.visitorchek.usermanagement.BuildingAdminRepository;

@Service
public class BuildingAdminService {
	@Autowired
	private BuildingAdminRepository repository;

	public List<BuildingAdmin> findAllGuards() {
		List<BuildingAdmin> guardList = new ArrayList<BuildingAdmin>();
		guardList = repository.findAll();
		return guardList;		
	}

	public BuildingAdmin saveGuard(BuildingAdmin guard) {
		return repository.save(guard);
	}
	
	public BuildingAdmin getGuardById(Integer id) {
		return repository.findById(id).orElse(null);
	}
	
	public BuildingAdmin updateGuard(BuildingAdmin guard) {
		return repository.save(guard);
	}
}
