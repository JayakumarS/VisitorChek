package com.paragon.visitorchek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paragon.visitorchek.dao.SideBarDao;
import com.paragon.visitorchek.model.SideBar;
import com.paragon.visitorchek.model.VisitRequest;

@Service
public class SideBarService {
	
	@Autowired
	SideBarDao sideBarDao;

	public SideBar getGuard(String visitorid) throws Exception {
		// TODO Auto-generated method stub
		return sideBarDao.getGuardById(visitorid);
	}

	 
}
