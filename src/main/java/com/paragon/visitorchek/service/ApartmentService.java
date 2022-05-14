package com.paragon.visitorchek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paragon.visitorchek.dao.ApartmentDao; 
import com.paragon.visitorchek.model.ApartmentRequest;
import com.paragon.visitorchek.model.CommercialSpaceRequest;
import com.paragon.visitorchek.model.GuardRequest;

@Service
public class ApartmentService {
	
	@Autowired
	ApartmentDao apartmentDao;

	public List<ApartmentRequest> getApartmentRequest(String talentId) {
		// TODO Auto-generated method stub
		return apartmentDao.getApartmentRequest(talentId);
	}

	public void saveApartment(List<ApartmentRequest> list) {
	    apartmentDao.saveApartment(list);
	}

	public boolean deleteApartment(int id) {
		// TODO Auto-generated method stub
		return apartmentDao.deleteApartment(id);
	}
	
	public List<CommercialSpaceRequest> getCommercialSpaceRequest(String talentId) {
		// TODO Auto-generated method stub
		return apartmentDao.getCommercialSpaceRequest(talentId);
	}

	public void saveCommercialSpace(List<CommercialSpaceRequest> list) {
	    apartmentDao.saveCommercialSpace(list);
	}

	public boolean deleteCommercialSpace(int id) {
		// TODO Auto-generated method stub
		return apartmentDao.deleteCommercialSpace(id);
	}

	public List<GuardRequest> guardList(String talentId,String orgType) {
		// TODO Auto-generated method stub
		return apartmentDao.guardList(talentId,orgType);
	}

	public void saveGuard(List<GuardRequest> list) {
		 apartmentDao.saveGuard(list);
		
	}

	public boolean deleteGuard(int id) {
		apartmentDao.deleteGuard(id);
		return false;
	}

}
