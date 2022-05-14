package com.paragon.visitorchek.dao;

import java.util.List;

import com.paragon.visitorchek.model.ApartmentRequest;
import com.paragon.visitorchek.model.CommercialSpaceRequest;
import com.paragon.visitorchek.model.GuardRequest;

public interface ApartmentDao {

	List<ApartmentRequest> getApartmentRequest(String talentId);

	void saveApartment(List<ApartmentRequest> list);

	boolean deleteApartment(int id);
	public boolean deleteCommercialSpace(int id);
	public void saveCommercialSpace(List<CommercialSpaceRequest> list);
	public List<CommercialSpaceRequest> getCommercialSpaceRequest(String talentId);

	List<GuardRequest> guardList(String talentId,String orgType);

	void saveGuard(List<GuardRequest> list);

	boolean deleteGuard(int id);
	
}
