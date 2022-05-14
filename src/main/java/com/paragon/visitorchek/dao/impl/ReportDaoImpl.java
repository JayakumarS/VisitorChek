package com.paragon.visitorchek.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paragon.visitorchek.dao.ReportDao;
import com.paragon.visitorchek.report.Report;


/**
 * Separate daoimpl for reports even though the underlying table queried is: visit_request
 * Report specific queries are used here. Separation from VisitRequestDao makes it easier to address report issues/changes.
 *
 */
@Repository
public class ReportDaoImpl implements ReportDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String REPORT_SQL_BASE= "SELECT row_number() OVER() as rownum, visit_starttime as visitStartime,  visitor_name1 as name1, visitor_name2 as name2, visitor_name3 as name3, visitor_name4 as name4," + 
			"visitor_name5 as name5, visitor_name6 as name6, visitor_name7 as name7," + 
			" visitor_name8 as name8, visitor_name9 as name9, visitor_name10 as name10,host_name as hostName, visit_place as visitPlace, visit_endtime as visitEndtime, purpose_of_visit as purpose"  
			+ " FROM visit_request";
	private final String REPORT_SQL_NAMES_WHERE = "visitor_name1 ILIKE ? OR visitor_name2 ILIKE ? OR visitor_name3 ILIKE ? OR visitor_name4 ILIKE ? "
			+ " OR visitor_name5 ILIKE ? OR visitor_name6 ILIKE ? OR visitor_name7 ILIKE ? OR visitor_name8 ILIKE ? OR visitor_name9 ILIKE ? "
			+ "OR visitor_name10 ILIKE ? ";
	
	@Override
	public List<Report> findvistorsbyVisitDate(String visitDate) {
		return jdbcTemplate.query(REPORT_SQL_BASE + " WHERE TO_CHAR(visit_starttime,'YYYY-MM-DD')= ?", 
				BeanPropertyRowMapper.newInstance(Report.class),visitDate);
	}

	@Override
	public List<Report> findvistorsbyVisitDateRange(String startDate, String endDate) {
		return jdbcTemplate.query(REPORT_SQL_BASE + " WHERE TO_CHAR(visit_starttime,'YYYY-MM-DD') BETWEEN ? AND ?", 
				BeanPropertyRowMapper.newInstance(Report.class),startDate, endDate);
	}

	@Override
	public List<Report> findvistorsbyDatesOrName(String startDate, String endDate, String name) {
		String sqlWhereClause = " WHERE TO_CHAR(visit_starttime,'YYYY-MM-DD') BETWEEN ? AND ? AND " + REPORT_SQL_NAMES_WHERE;
		return jdbcTemplate.query(REPORT_SQL_BASE + sqlWhereClause, 
				BeanPropertyRowMapper.newInstance(Report.class),startDate, endDate, name, name, name, name, name, name, name, name, name, name);
	}

	@Override
	public List<Report> findvistorsbyName(String name) {
		String sqlWhereClause = " WHERE " + REPORT_SQL_NAMES_WHERE;
		return jdbcTemplate.query(REPORT_SQL_BASE + sqlWhereClause, 
				BeanPropertyRowMapper.newInstance(Report.class), name, name, name, name, name, name, name, name, name, name);
	}

}
