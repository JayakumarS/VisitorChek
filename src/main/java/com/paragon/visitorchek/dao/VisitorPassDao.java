package com.paragon.visitorchek.dao;

import java.util.List;

import com.paragon.visitorchek.model.VisitorPass;

public interface VisitorPassDao {

	VisitorPass saveVisitorPass(VisitorPass visitorPass);

	List<VisitorPass> findAllVisitorPass(String id);

}
