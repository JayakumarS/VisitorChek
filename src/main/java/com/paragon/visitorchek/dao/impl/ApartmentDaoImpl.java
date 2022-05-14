package com.paragon.visitorchek.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paragon.visitorchek.dao.ApartmentDao;
import com.paragon.visitorchek.model.ApartmentRequest;
import com.paragon.visitorchek.model.CommercialSpaceRequest;
import com.paragon.visitorchek.model.GuardRequest; 

@Repository
public class ApartmentDaoImpl implements ApartmentDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<ApartmentRequest> getApartmentRequest(String talentId) {
		return jdbcTemplate.query("SELECT id, mobile_no mobileNumber, talent_id talentId,resident_name as residentPersonName, image, block blockNo, house_no houseNo, owner_tenant ownerTenant "
				+ "  FROM apartment_residents_new  where created_by=? and is_active =true ",new Object[] {talentId}, BeanPropertyRowMapper.newInstance(ApartmentRequest.class));
	}

	@Override
	public void saveApartment(List<ApartmentRequest> list) {
		for(ApartmentRequest request : list) {
			if(request.getId()!=null && !request.getId().isEmpty()) {
				  jdbcTemplate.update("UPDATE apartment_residents_new "
							+ "        SET    mobile_no=?,  block=?, house_no=?, owner_tenant=?,resident_name=?, "
							+ "            is_active=true, modified_by=?, modified_time=now(), image=?, talent_id=? where id=? ",
							new Object[] {request.getMobileNumber(),request.getBlockNo(), request.getHouseNo(), request.getOwnerTenant(),request.getResidentPersonName(),request.getLoginTalentId(),
									request.getImage(), request.getTalentId(),Integer.parseInt(request.getId())});
			}else {
		  jdbcTemplate.update("INSERT INTO apartment_residents_new(\r\n"
				+ "            mobile_no,  block, house_no, owner_tenant,resident_name, "
				+ "            is_active, created_by, created_time, image, talent_id)\r\n"
				+ "    VALUES (?, ?, ?, ?,?, true,  "
				+ "              ?, now(), ?, ?) ",
				new Object[] {request.getMobileNumber(),request.getBlockNo(), request.getHouseNo(), request.getOwnerTenant(),request.getResidentPersonName(),request.getLoginTalentId(),
						request.getImage(), request.getTalentId()});
			}
		}
	}

	@Override
	public boolean deleteApartment(int id) {
		jdbcTemplate.update("DELETE FROM apartment_residents_new WHERE ID=?",new Object[] {id});
		return true;
	}
	@Override
	public List<CommercialSpaceRequest> getCommercialSpaceRequest(String talentId) {
		return jdbcTemplate.query("SELECT id, contact_no mobileNumber, talent_id talentId, image, block blockNo, admin_talentId adminTalentId "
				+ "  FROM commercial_space_tenants_new  where created_by=? and is_active =true ",new Object[] {talentId}, BeanPropertyRowMapper.newInstance(CommercialSpaceRequest.class));
	}

	@Override
	public void saveCommercialSpace(List<CommercialSpaceRequest> list) {
		for(CommercialSpaceRequest request : list) {
			if(request.getId()!=null && !request.getId().isEmpty()) {
				  jdbcTemplate.update("UPDATE commercial_space_tenants_new "
							+ "        SET    contact_no=?,  block=?, admin_talentId=?, "
							+ "            is_active=true, modified_by=?, modified_time=now(), image=?, talent_id=? where id=? ",
							new Object[] {request.getMobileNumber(),request.getBlockNo(), request.getAdminTalentId(),request.getLoginTalentId(),
									request.getImage(), request.getTalentId(),Integer.parseInt(request.getId())});
			}else {
		  jdbcTemplate.update("INSERT INTO commercial_space_tenants_new(\r\n"
				+ "            contact_no,  block, admin_talentId,  "
				+ "            is_active, created_by, created_time, image, talent_id)\r\n"
				+ "    VALUES (?, ?, ?,  true,  "
				+ "              ?, now(), ?, ?) ",
				new Object[] {request.getMobileNumber(),request.getBlockNo(), request.getAdminTalentId(), request.getLoginTalentId(),
						request.getImage(), request.getTalentId()});
			}
		}
	}

	@Override
	public boolean deleteCommercialSpace(int id) {
		jdbcTemplate.update("DELETE FROM commercial_space_tenants_new WHERE ID=?",new Object[] {id});
		return true;
	}

	@Override
	public List<GuardRequest> guardList(String talentId ,String orgType) {
		return jdbcTemplate.query("SELECT id,organisation, guard mobileNumber,guard_name as guardName, talent_id talentId, image, gate_no gateNo,start_date startDate,end_date endDate"
				+ ",shift_time_start startTime,shift_time_end endTime "
				+ "  FROM guard_at_gate  where created_by=? and is_active =true ",new Object[] {talentId}, BeanPropertyRowMapper.newInstance(GuardRequest.class));
	}

	@Override
	public void saveGuard(List<GuardRequest> list) {

		for(GuardRequest request : list) {
			if(request.getId()!=null && !request.getId().isEmpty()) {
				  jdbcTemplate.update("UPDATE guard_at_gate "
							+ "        SET    organisation=?,  guard=?, gate_no=?,start_date=?,end_date=?,guard_name=?,resident_name,shift_time_start=?,shift_time_end=?, "
							+ "              modified_by=?, modified_time=now(), image=?, talent_id=? where id=? ",
							new Object[] {request.getOrganisation(),request.getMobileNumber(), request.getGateNo(),
									request.getStartDate(),request.getEndDate(),request.getGuardName(),request.getStartTime(), request.getEndTime(),
									request.getLoginTalentId(),
									request.getImage(), request.getTalentId(),Integer.parseInt(request.getId())});
			}else {
		  jdbcTemplate.update("INSERT INTO guard_at_gate(\r\n"
				+ "            organisation,guard_name,  guard, gate_no, start_date,end_date ,shift_time_start,shift_time_end,"
				+ "            created_by, created_time, image, talent_id)\r\n"
				+ "    VALUES (?, ?,?, ?,  TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'),TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'),TO_TIMESTAMP(?,'HH24:MI'),TO_TIMESTAMP(?,'HH24:MI'),  "
				
				+ "              ?, now(), ?, ?) ",
				new Object[] {request.getOrganisation(),request.getGuardName(),request.getMobileNumber(), request.getGateNo(),
						request.getStartDate(),request.getEndDate(),request.getStartTime(), request.getEndTime(),
						request.getLoginTalentId(),
						request.getImage(), request.getTalentId()});
			}
		}
	
	}

	
	@Override
	public boolean deleteGuard(int id) {
		jdbcTemplate.update("DELETE FROM guard_at_gate WHERE ID=?",new Object[] {id});
		return true;
	}
}
