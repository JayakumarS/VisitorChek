package com.paragon.visitorchek.dao;

import java.util.List;

import com.paragon.visitorchek.model.VisitRequest;

public interface VisitRequestDao{

	List<VisitRequest> findAll();

	VisitRequest findById(long id);

	VisitRequest save(VisitRequest orders);

	int  deleteById(long id);

	VisitRequest update(VisitRequest visitRequest);
	
}
