package com.paragon.visitorchek.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.paragon.visitorchek.dao.VisitRequestDao;
import com.paragon.visitorchek.model.BaggageRequest;
import com.paragon.visitorchek.model.VisitRequest;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

@Repository
public class VisitRequestDaoImpl implements VisitRequestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	final String talentTokenUrl = "https://portal.talentchek.com/tc/hrms/master/employeeAdminMaster/visitorIdFromGcmToken?userId=";
	
	@Override
	public List<VisitRequest> findAll() {
		return jdbcTemplate.query("SELECT id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId,DATE(visit_starttime) as VisitStarttime, purpose_of_visit, visit_place visitPlace, mobile_number as mobileNumber, visitor_name visitorName, host_name as hostName, host_image as hostImage, image from visit_request", BeanPropertyRowMapper.newInstance(VisitRequest.class));
	}

	
	@Override
	public List<VisitRequest> findvistorsbyHost(String hostid) {
		return jdbcTemplate.query("SELECT visit_request.id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStartTime, TO_CHAR(visit_endtime,'DD-MM-YYYY HH:mi AM') as visitEndtime,purpose_of_visit purpose, visit_place visitPlace,approve_by approveBy,"
				+ "				 cancelled_by cancelledBy , mobile_number as mobileNumber, visitor_name visitorName, host_name as hostName, host_image as hostImage, image  "
				+ "				  from visit_request where  host_talent_id= ?", BeanPropertyRowMapper.newInstance(VisitRequest.class),hostid);
		}
	
	
	@Override
	public List<VisitRequest> getNotesValue(Integer hostid) {
		return jdbcTemplate.query("select notes as hostNotes from visit_request where id=?", BeanPropertyRowMapper.newInstance(VisitRequest.class),hostid);
		}
	
	@Override
	public List<VisitRequest> findvistorsbyVisitor(String visitorid) {
		return jdbcTemplate.query(" SELECT visit_request.id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStarttime, TO_CHAR(visit_endtime,'DD-MM-YYYY HH:mi AM') as visitEndtime, purpose_of_visit purpose, visit_place visitPlace,approve_by approveBy,"
				+ "	  cancelled_by cancelledBy , mobile_number as mobileNumber, visitor_name visitorName, host_name as hostName, host_image as hostImage, image "
				+ "				 from visit_request where  visitor_talent_id= ?", BeanPropertyRowMapper.newInstance(VisitRequest.class),visitorid);
		}
	
	
	@Override
	public VisitRequest findById(long id) {
		try {
			VisitRequest visitRequest = jdbcTemplate.queryForObject("SELECT id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId,  TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStartTime, purpose_of_visit purpose, visit_place , mobile_number as mobileNumber, visitor_name visitorName, host_name as hostName, host_image as hostImage, image FROM visit_request WHERE id=?",
					BeanPropertyRowMapper.newInstance(VisitRequest.class), id);

			return visitRequest;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}
	@Override
	public VisitRequest savebyHost(VisitRequest visitRequest, Date updateTime) {
		int result = 0;
		
	 
	List<String> baggage=	visitRequest.getBaggage().stream()
	       .map(BaggageRequest::getId)
	       .collect(Collectors.toList());
	
	VisitRequestDaoImpl ab = new VisitRequestDaoImpl();

		if(visitRequest.getType().equals("visitor")) { 
		result = jdbcTemplate.update(
				"INSERT INTO visit_request("+
			         "    visitor_talent_id, host_talent_id,  visit_starttime, visit_endtime, "+
			         "   visit_place, purpose_of_visit, parking_required, vehical_no, no_of_people, "+
			         "   baggage, created_date, created_by,  "+
			          "      remarks,visitor_name1, visitor_name2, \r\n"
			          + "            visitor_name3, visitor_name4, visitor_name5, visitor_name6, visitor_name7, \r\n"
			          + "            visitor_name8, visitor_name9, visitor_name10,image , mobile_number, visitor_name, host_name , host_image )"+
			   " VALUES (?, ?, TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), "+
			      "      ?, ?, ?, ?, ?, "+
			      "      ?, now(), ?, "+
			      "      ?,"
			      + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[] { visitRequest.getVisitorTalentId(),visitRequest.getHostTalentId(),
							visitRequest.getVisitStarttime(),visitRequest.getVisitEndtime(),
							visitRequest.getVisitPlace(),visitRequest.getPurpose(),visitRequest.getParking(),visitRequest.getVehicalNo(),
							visitRequest.getNoofpeople()
						,StringUtils.join(baggage, ", ") , visitRequest.getVisitorTalentId(),visitRequest.getRemarks(),
						visitRequest.getNameList().getName1(),visitRequest.getNameList().getName2(),visitRequest.getNameList().getName3(),visitRequest.getNameList().getName4(),visitRequest.getNameList().getName5(),
						visitRequest.getNameList().getName6(),visitRequest.getNameList().getName7(),visitRequest.getNameList().getName8(),
						visitRequest.getNameList().getName9(),visitRequest.getNameList().getName10(),
						visitRequest.getImage(), visitRequest.getMobileNumber(), visitRequest.getVisitorName(), visitRequest.getHostName(), visitRequest.getHostImage()});
		}else {
			
			result = jdbcTemplate.update(
					"INSERT INTO visit_request("+
				         "    visitor_talent_id, host_talent_id,  visit_starttime, visit_endtime, "+
				         "   visit_place, purpose_of_visit, parking_required, vehical_no, no_of_people, "+
				         "   baggage, created_date, created_by,  "+
				          "      remarks,visitor_name1, visitor_name2, \r\n"
				          + "            visitor_name3, visitor_name4, visitor_name5, visitor_name6, visitor_name7, \r\n"
				          + "            visitor_name8, visitor_name9, visitor_name10,approve_by,approve_date,image, mobile_number, visitor_name, host_name , host_image )"+
				   " VALUES (?, ?, TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), "+
				      "      ?, ?, ?, ?, ?, "+
				      "      ?, now(), ?, "+
				      "      ?,"
				      + "?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?)",
						new Object[] { visitRequest.getVisitorTalentId(),visitRequest.getHostTalentId(),
								visitRequest.getVisitStarttime(),visitRequest.getVisitEndtime(),
								visitRequest.getVisitPlace(),visitRequest.getPurpose(),visitRequest.getParking(),visitRequest.getVehicalNo(),
								visitRequest.getNoofpeople()
							,StringUtils.join(baggage, ", ") , visitRequest.getHostTalentId(),visitRequest.getRemarks(),
							visitRequest.getNameList().getName1(),visitRequest.getNameList().getName2(),visitRequest.getNameList().getName3(),visitRequest.getNameList().getName4(),visitRequest.getNameList().getName5(),
							visitRequest.getNameList().getName6(),visitRequest.getNameList().getName7(),visitRequest.getNameList().getName8(),
							visitRequest.getNameList().getName9(),visitRequest.getNameList().getName10(), visitRequest.getHostTalentId(),visitRequest.getImage() , visitRequest.getMobileNumber(), visitRequest.getVisitorName(), visitRequest.getHostName(), visitRequest.getHostImage()});
			
			}
		if (result != 0) {
			try {
				RestTemplate restTemplate = new RestTemplate();   
				ResponseEntity<HashMap> resultId = restTemplate.getForEntity(talentTokenUrl+visitRequest.getVisitorTalentId(),  HashMap.class); 
				HashMap dataMapforAppId= new HashMap(resultId.getBody());
				System.out.println(resultId.getBody());
				String message = visitRequest.getHostName()+" has requested an appointment to meet you on "+visitRequest.getVisitStarttime()+" at "+visitRequest.getVisitPlace();
				String TokenNew =dataMapforAppId.get("gcmToken").toString();
				ab.fcmnotification1(TokenNew, message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return visitRequest;
		} else {
			return null;
		}
	}

	@Override
	public VisitRequest save(VisitRequest visitRequest) {
		int result = 0;
		
		VisitRequestDaoImpl ab = new VisitRequestDaoImpl();
		
		result = jdbcTemplate.update(
				"INSERT INTO visit_request (visitor_talent_id, visit_starttime ,visit_endtime,  "
				+ "purpose_of_visit,visit_place,host_talent_id, created_by, created_date, mobile_number, visitor_name, image, host_name , host_image ) "
				+ " VALUES(?,TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'),TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'),?,?,?,?,now(), ? , ? , ?, ?, ?)",
				new Object[] { visitRequest.getVisitorTalentId(),  visitRequest.getVisitStarttime(),visitRequest.getVisitEndtime(),
						visitRequest.getPurpose(), visitRequest.getVisitPlace(),visitRequest.getHostTalentId(), 
						visitRequest.getVisitorTalentId(),visitRequest.getMobileNumber(), visitRequest.getVisitorName(), 
						visitRequest.getImage(), visitRequest.getHostName(), visitRequest.getHostImage()});
		if (result != 0) {
			try {
				RestTemplate restTemplate = new RestTemplate();   
				ResponseEntity<HashMap> resultId = restTemplate.getForEntity(talentTokenUrl+visitRequest.getHostTalentId(),  HashMap.class); 
				HashMap dataMapforAppId= new HashMap(resultId.getBody());
				System.out.println(resultId.getBody());
				String message = visitRequest.getVisitorName()+" has requested an appointment to meet you on "+visitRequest.getVisitStarttime()+" at "+visitRequest.getVisitPlace();
				String TokenNew =dataMapforAppId.get("gcmToken").toString();
				ab.fcmnotification1(TokenNew, message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return visitRequest;
		} else {
			return null;
		}
	}
	
	
	public boolean fcmnotification1(String TokenId, String message) throws Exception {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		// FcmNotifyResultBean fcmresultBean = new FcmNotifyResultBean();
		// String AUTH_KEY_FCM = "AIzaSyDNQUMvsIZ2TNOE9a9Ycc42yhJ-GwJk8cs";
		String AUTH_KEY_FCM = "AAAAe3vDxjA:APA91bF8C_U4eN_ypDNGfm9caVP1xqR4PuXII-3kPGGrKUh-ymw1ahtwwVGjA5VjNjBIuCi6qhHJ3_ScBHteV9q8jMyPR_OkML-U7MYu9ai4qyLfRccLmNvXbzQ8m5FjfE3poFuCYUIk";
		String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
		// String userDeviceIdKey1 =
		// "f6HfxJMMvQQ:APA91bHLhiNEbC5nHQzXbzYNKZw9kOSIEEHE8d9q8D2M476vYwnxtaVk71S_9L0aZ2_buuhpLmaUfDMAByf68y4E_PIqDQgW2jGZrRRh9kT6T1GdmnDcq3qHhOTsmdbeXA2QQaDoGz53";
		// String Notifyicon = "http://throwbill.com/retailer/img/40x40.png";
		// userDeviceIdKey is the device id you will query from your database
		//TokenId = "e4Nx-bZ5TMG63bv0aD1GAk:APA91bFISUjW0EOnt0-12a8n9QFr5H_JAesZPbp7G-mSTaQ2u5cpb6Dpkhtnd3HvY_bJBE3A0SMVY8XhdHzrc7PRS7TSVZcCV6UH_M3sD_A6DouJxhNdDtOzh7mfHgfRy6JNiRvhcAlp";
		String authKey = AUTH_KEY_FCM; // You FCM AUTH key
		String FMCurl = API_URL_FCM;

		URL url = new URL(FMCurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + authKey);
		conn.setRequestProperty("Content-Type", "application/json");
		// for (String fcmNotifyBean1 : fcmNotifyBean.getTokens()) {
		String fcmtoken = TokenId;// "fwxIAN_3Syc:APA91bGypEH1qqYaA5_tpYRyAqgvsnjtVeKNP7RZD878Fh6mAXsofKN6g5SgAP-cL9DHIqh7EqMT4DHdF0aCLMG7sUB93LIoqIsxeiTcRqEOkdLQkJA78cIyw0Yqp4m12Ff0pzKcoOVj";
		JSONObject json = new JSONObject();
		json.put("to", fcmtoken.trim());
		JSONObject info = new JSONObject();
		info.put("title", "Requesting Appointment"); // Notification title
		info.put("body", message); // Notification body
		info.put("image", "http://213.42.28.72:8082/img/Cordelia.jpg");
		info.put("image-type", "circle");// Notification
		// body
		json.put("notification", info);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(json.toString());
		wr.flush();
		conn.getInputStream();
		System.out.println("json" + json);
		// }

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);

		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return isSuccess;
	}

	@Override
	public int deleteById(long id) {
		return jdbcTemplate.update("DELETE FROM visit_request WHERE id=?", id);
	}

	@Override
	public VisitRequest update(VisitRequest visitRequest) {
		int result = 0;
		result = jdbcTemplate.update("UPDATE visit_request SET talentid=?,visit_starttime=to_date(?,'DD-MM-YYYY'),purpose=?,visit_place=? WHERE id=?",
				new Object[] { visitRequest.getVisitorTalentId(),  visitRequest.getVisitEndtime(),
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

	@Override
	public List<VisitRequest> findvistorsbyVisitDate(String visitDate) {
		return jdbcTemplate.query(" SELECT visit_request.id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStarttime, TO_CHAR(visit_endtime,'DD-MM-YYYY HH:mi AM') as visitEndtime, purpose_of_visit purpose, visit_place visitPlace,approve_by approveBy,"
				+ "	  cancelled_by cancelledBy"
				+ "				 from visit_request where TO_CHAR(visit_starttime,'DD-MM-YYYY')= ?", BeanPropertyRowMapper.newInstance(VisitRequest.class),visitDate);
		}


	@Override
	public VisitRequest saveNotes(VisitRequest orders) {
		int result = 0;
		
		VisitRequestDaoImpl bean = new VisitRequestDaoImpl();
		
		String query="";
		
		query =query+"UPDATE visit_request SET notes=?  WHERE id=?";
		
		result = jdbcTemplate.update(query,
				new Object[] { orders.getHostNotes(),orders.getId() });
		
		if (result != 0) {
			try {
				RestTemplate restTemplate = new RestTemplate();   
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return orders;
		} else {
			return null;
		}
	}
	
}
