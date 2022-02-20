package com.paragon.visitorchek.dao.impl;

import java.util.Date;
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
		return jdbcTemplate.query("SELECT id, talentid, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:SS') as visitStartTime, purpose, visit_place visitPlace from visit_request", BeanPropertyRowMapper.newInstance(VisitRequest.class));
	}

	
	@Override
	public List<VisitRequest> findvistorsbyHost(String hostid) {
		return jdbcTemplate.query("SELECT visit_request.id, talentid, host_id hostId,visitor_id visitorId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:SS') as visitStartTime, TO_CHAR(visit_endtime,'DD-MM-YYYY HH:SS') as visitEndtime,purpose, visit_place visitPlace,approve_by approveBy,"
				+ "				 cancelled_by cancelledBy ,image "
				+ "				  from visit_request,users where visit_request.host_id::int=users.id and host_id= ?", BeanPropertyRowMapper.newInstance(VisitRequest.class),hostid);
		}
	@Override
	public List<VisitRequest> findvistorsbyVisitor(String visitorid) {
		return jdbcTemplate.query(" SELECT visit_request.id, talentid, host_id hostId,visitor_id visitorId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:SS') as visitStartTime,TO_CHAR(visit_endtime,'DD-MM-YYYY HH:SS') as visitEndtime, purpose, visit_place visitPlace,approve_by approveBy,"
				+ "	  cancelled_by cancelledBy ,image\r\n"
				+ "				 from visit_request,users where visit_request.visitor_id::int=users.id and visitor_id= ?", BeanPropertyRowMapper.newInstance(VisitRequest.class),visitorid);
		}
	
	
	@Override
	public VisitRequest findById(long id) {
		try {
			VisitRequest visitRequest = jdbcTemplate.queryForObject("SELECT id, talentid,  TO_CHAR(visit_starttime,'DD-MM-YYYY HH:SS') as visitStartTime, purpose, visit_place FROM visit_request WHERE id=?",
					BeanPropertyRowMapper.newInstance(VisitRequest.class), id);

			return visitRequest;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}
	@Override
	public VisitRequest savebyHost(VisitRequest visitRequest, Date updateTime) {
		int result = 0;
		if(visitRequest.getType().equals("visitor")) {
		result = jdbcTemplate.update(
				"INSERT INTO visit_request("+
			         "    talentid, visitor_id, host_id, visit_starttime, visit_endtime, "+
			         "   visit_place, purpose, parking_required, vehical_no, no_of_people, "+
			         "   baggage, created_date, created_by,  "+
			          "      remarks)"+
			   " VALUES (?, ?, ?, TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), "+
			      "      ?, ?, ?, ?, ?, "+
			      "      ?, now(), ?, "+
			      "      ?)",
					new Object[] { visitRequest.getTalentId(), visitRequest.getVisitorId(), visitRequest.getHostId(),visitRequest.getVisitStarttime(),visitRequest.getVisitEndtime(),
							visitRequest.getVisitPlace(),visitRequest.getPurpose(),visitRequest.getParking(),visitRequest.getVehicalNo(),visitRequest.getNoofpeople()
						, visitRequest.getBaggage(), visitRequest.getId(),visitRequest.getRemarks() });
		}else {
			result = jdbcTemplate.update(
					"INSERT INTO visit_request("+
				         "    talentid, visitor_id, host_id, visit_starttime, visit_endtime, "+
				         "   visit_place, purpose, parking_required, vehical_no, no_of_people, "+
				         "   baggage, created_date, created_by,  "+
				          "      remarks,approve_by,approve_date)"+
				   " VALUES (?, ?, ?, TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), "+
				      "      ?, ?, ?, ?, ?, "+
				      "      ?, now(), ?, "+
				      "      ?,?,now())",
						new Object[] { visitRequest.getTalentId(), visitRequest.getVisitorId(), visitRequest.getHostId(),visitRequest.getVisitStarttime(),visitRequest.getVisitEndtime(),
								visitRequest.getVisitPlace(),visitRequest.getPurpose(),visitRequest.getParking(),visitRequest.getVehicalNo(),visitRequest.getNoofpeople()
							, visitRequest.getBaggage(), visitRequest.getId(),visitRequest.getRemarks(),visitRequest.getId() });
		}
		if (result != 0) {
			return visitRequest;
		} else {
			return null;
		}
	}

	@Override
	public VisitRequest save(VisitRequest visitRequest) {
		int result = 0;
		result = jdbcTemplate.update(
				"INSERT INTO visit_request (talentid,  visit_starttime ,purpose,visit_place) "
				+ " VALUES(?,?,TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'),?,?)",
				new Object[] { visitRequest.getTalentId(),  visitRequest.getVisitStarttime(),
						visitRequest.getPurpose(), visitRequest.getVisitPlace()});
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
		result = jdbcTemplate.update("UPDATE visit_request SET talentid=?,visit_starttime=to_date(?,'DD-MM-YYYY'),purpose=?,visit_place=? WHERE id=?",
				new Object[] { visitRequest.getTalentId(),  visitRequest.getVisitStarttime(),
						visitRequest.getPurpose(), visitRequest.getVisitPlace(),visitRequest.getId() });

		if (result != 0) {
			return visitRequest;
		} else {
			return null;
		}
	}


	@Override
	public Boolean update(String status,String hostId, int id) { 
		int result = 0;
		String query="";
		if(status.equals("A")){
			query =query+"UPDATE visit_request SET approve_by=? ,approve_date=now() WHERE id=?";
		}else {
			query =query+"UPDATE visit_request SET cancelled_by=? ,cancelled_date=now() WHERE id=?";
		}
		result = jdbcTemplate.update(query,
				new Object[] { hostId,id });
 
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

}
