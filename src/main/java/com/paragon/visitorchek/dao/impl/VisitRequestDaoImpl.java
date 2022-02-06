package com.paragon.visitorchek.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paragon.visitorchek.dao.VisitRequestDao;
import com.paragon.visitorchek.model.VisitRequest;

@Repository
public class VisitRequestDaoImpl implements VisitRequestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<VisitRequest> findAll() {
		return jdbcTemplate.query("SELECT id, talentid, name, to_char(visit_date,'DD-MM-YYYY HH:SS') as visit_date, purpose, visit_work_place, visit_home from visit_request", BeanPropertyRowMapper.newInstance(VisitRequest.class));
	}

	@Override
	public VisitRequest findById(long id) {
		try {
			VisitRequest visitRequest = jdbcTemplate.queryForObject("SELECT id, talentid, name, to_char(visit_date,'DD-MM-YYYY HH:SS') as visit_date, purpose, visit_work_place, visit_home FROM visit_request WHERE id=?",
					BeanPropertyRowMapper.newInstance(VisitRequest.class), id);

			return visitRequest;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public VisitRequest save(VisitRequest visitRequest) {
		int result = 0;
		result = jdbcTemplate.update(
				"INSERT INTO visit_request (talentid, name, visit_date,purpose,visit_work_place,visit_home) VALUES(?,?,TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'),?,?,?)",
				new Object[] { visitRequest.getTalentId(), visitRequest.getName(), visitRequest.getVisitDate(),
						visitRequest.getPurpose(), visitRequest.isVisitWorkplace(), visitRequest.isVisitHome() });
		if (result != 0) {
			return visitRequest;
		} else {
			return null;
		}
	}

	@Override
	public int deleteById(long id) {
		return jdbcTemplate.update("DELETE FROM visit_request WHERE id=?", id);
	}

	@Override
	public VisitRequest update(VisitRequest visitRequest) {
		int result = 0;
		result = jdbcTemplate.update("UPDATE visit_request SET talentid=?,name=?,visit_date=to_date(?,'DD-MM-YYYY'),purpose=?,visit_work_place=?,visit_home=? WHERE id=?",
				new Object[] { visitRequest.getTalentId(), visitRequest.getName(), visitRequest.getVisitDate(),
						visitRequest.getPurpose(), visitRequest.isVisitWorkplace(), visitRequest.isVisitHome(),visitRequest.getId() });

		if (result != 0) {
			return visitRequest;
		} else {
			return null;
		}
	}

}
