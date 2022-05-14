package com.paragon.visitorchek.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paragon.visitorchek.dao.VisitorPassDao;
import com.paragon.visitorchek.model.VisitRequest;
import com.paragon.visitorchek.model.VisitorPass;

@Repository
public class VisitorPassDaoImpl implements VisitorPassDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public VisitorPass saveVisitorPass(VisitorPass visitorPass) {
		// TODO Auto-generated method stub
		int result;
		String query = "";
		query = jdbcTemplate.queryForObject(
				"INSERT into visitor_pass(visitor_mobile_number,visitor_talent_id,visitor_name,visitor_in_time,visitor_org,created_time,created_by) VALUES (?,?,?,TO_TIMESTAMP(?,'HH24:MI'),?,now(),?)returning visitor_talent_id as visitorPassTalentId",
				new Object[] { visitorPass.getMobileNumber(),visitorPass.getVisitorPassTalentId(),visitorPass.getVisitorPassName(),visitorPass.getVisitorPassInTime(),visitorPass.getCompanyOrganization(),visitorPass.getVisitorTalentId() },
				String.class);
		return visitorPass;
	}

	@Override
	public List<VisitorPass> findAllVisitorPass(String id) {
		// TODO Auto-generated method stub
			return jdbcTemplate.query(
					"select visitor_pass_id as visitorPassId,visitor_mobile_number as mobileNumber,visitor_talent_id as visitorPassTalentId,visitor_name as visitorPassName,\r\n"
					+ "visitor_in_time as visitorPassInTime,image as hostName,visitor_org as companyOrganization from visitor_pass where created_by=?",
					BeanPropertyRowMapper.newInstance(VisitorPass.class), id);
		}
	
}
