package com.paragon.visitorchek.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paragon.visitorchek.dao.VisitorPassDao;
import com.paragon.visitorchek.model.VisitRequest;
import com.paragon.visitorchek.model.VisitorPass;

@Service
public class VisitorPassService {
	
	@Autowired
	VisitorPassDao visitorPassDao;

	public VisitorPass saveVisitorPass(VisitorPass visitorPass) {
		// TODO Auto-generated method stub
		visitorPass = visitorPassDao.saveVisitorPass(visitorPass);
		return visitorPass;
	}

	public List<VisitorPass> getAllVisitorPass(String id) {
		// TODO Auto-generated method stub
		List<VisitorPass> orders = new ArrayList<VisitorPass>();
		visitorPassDao.findAllVisitorPass(id).forEach(orders1 -> orders.add(orders1));
		return orders;
	}

}
