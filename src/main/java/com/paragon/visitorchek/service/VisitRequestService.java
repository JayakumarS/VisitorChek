package com.paragon.visitorchek.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paragon.visitorchek.dao.VisitRequestDao;
import com.paragon.visitorchek.model.VisitRequest;

@Service
public class VisitRequestService {

	@Autowired
	VisitRequestDao visitRequestDao;

	public List<VisitRequest> getAllVisitRequest() {
		List<VisitRequest> orders = new ArrayList<VisitRequest>();
		visitRequestDao.findAll().forEach(orders1 -> orders.add(orders1));
		return orders;
	}

	public VisitRequest getVisitRequestById(long id) {
		return visitRequestDao.findById(id);
	}

	@Transactional
	public VisitRequest save(VisitRequest visitRequest) {
		System.out.println("Before"+visitRequest.toString());
		visitRequest = visitRequestDao.save(visitRequest);
		System.out.println("After"+visitRequest.toString());
		
		return visitRequest;
	}

	@Transactional
	public void delete(long id) {
		visitRequestDao.deleteById(id);
	}

	public VisitRequest update(VisitRequest visitRequest) {
		return visitRequestDao.update(visitRequest);
	}
}
