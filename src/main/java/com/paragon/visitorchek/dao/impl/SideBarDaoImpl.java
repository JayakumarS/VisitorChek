package com.paragon.visitorchek.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paragon.visitorchek.dao.SideBarDao;
import com.paragon.visitorchek.dao.VisitRequestDao;
import com.paragon.visitorchek.model.SideBar;
import com.paragon.visitorchek.model.VisitRequest;

@Repository
public class SideBarDaoImpl  implements SideBarDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	final String talentTokenUrl = "https://portal.talentchek.com/tc/hrms/master/employeeAdminMaster/visitorIdFromGcmToken?userId=";
	
	@Override
	public SideBar getGuardById(String visitorid) throws Exception {
		SideBar objbean= new SideBar();
 		try {

			Integer objbean11 = jdbcTemplate.queryForObject("select count(*) from guard_at_gate where talent_id = ?",
						new Object[] {visitorid},Integer.class);
			objbean.setValue(objbean11);
 		} catch (IncorrectResultSizeDataAccessException e) {
			e.printStackTrace();
		}
		return objbean;
	}

}
