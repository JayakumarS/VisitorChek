package com.paragon.visitorchek.dao;

import com.paragon.visitorchek.model.SideBar;
 
public interface SideBarDao {

	SideBar getGuardById(String visitorid) throws Exception;
 
}