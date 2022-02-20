package com.paragon.visitorchek.service;

import java.util.ArrayList;
import java.util.Date;
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

	public List<VisitRequest> getAllVisitorRequestUsingHostId(String hostid) {
		List<VisitRequest> orders = new ArrayList<VisitRequest>();
		visitRequestDao.findvistorsbyHost(hostid).forEach(orders1 -> orders.add(orders1));
		return orders;
	}
	public List<VisitRequest> getAllVisitorRequestVisitorId(String visitorId) {
		List<VisitRequest> orders = new ArrayList<VisitRequest>();
		visitRequestDao.findvistorsbyVisitor(visitorId).forEach(orders1 -> orders.add(orders1));
		return orders;
	}
	public VisitRequest getVisitRequestById(long id) {
		return visitRequestDao.findById(id);
	}

	@Transactional
	public VisitRequest save(VisitRequest visitRequest) {
	 	visitRequest = visitRequestDao.save(visitRequest);
	 	return visitRequest;
	}

	@Transactional
	public VisitRequest savebyHost(VisitRequest visitRequest) {
		Date dt = new Date();
	 	visitRequest = visitRequestDao.savebyHost(visitRequest,dt);
	 	return visitRequest;
	}

	@Transactional
	public void delete(long id) {
		visitRequestDao.deleteById(id);
	}

	public VisitRequest update(VisitRequest visitRequest) {
		return visitRequestDao.update(visitRequest);
	}

	public Boolean updateRequestStatus(String status, String hostId, int id) {
		return visitRequestDao.update(status, hostId, id);
	}
}
