package com.paragon.visitorchek.dao;

import java.util.Date;
import java.util.List;

import com.paragon.visitorchek.model.VisitRequest;

public interface VisitRequestDao{

	List<VisitRequest> findAll();

	VisitRequest findById(long id);

	VisitRequest save(VisitRequest orders);
	
	VisitRequest saveNotes(VisitRequest orders);
	
	VisitRequest savebyHost(VisitRequest hostrequest,Date updateTime);
	
	int  deleteById(long id);

	VisitRequest update(VisitRequest visitRequest);
	List<VisitRequest> findvistorsbyHost(String hostid);
	
	List<VisitRequest> getNotesValue(Integer hostid);
	List<VisitRequest> findvistorsbyVisitor(String hostid);

	Boolean update(String status,String hostId, int id);

	List<VisitRequest> findvistorsbyVisitDate(String visitDate);
}
