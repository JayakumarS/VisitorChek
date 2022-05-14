package com.paragon.visitorchek.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.paragon.visitorchek.dao.VisitRequestDao;
import com.paragon.visitorchek.model.BaggageRequest;
import com.paragon.visitorchek.model.Email;
import com.paragon.visitorchek.model.VisitRequest;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

@Repository
public class VisitRequestDaoImpl implements VisitRequestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	final String talentTokenUrl = "https://portal.talentchek.com/tc/hrms/master/employeeAdminMaster/visitorIdFromGcmToken?userId=";

	@Override
	public List<VisitRequest> findAll() {
		return jdbcTemplate.query(
				"SELECT id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId,DATE(visit_starttime) as VisitStarttime, purpose_of_visit, visit_place visitPlace, mobile_number as mobileNumber, visitor_name visitorName, host_name as hostName, host_image as hostImage, image from visit_request",
				BeanPropertyRowMapper.newInstance(VisitRequest.class));
	}

	@Override
	public List<VisitRequest> findvistorsbyHost(String hostid) {
		return jdbcTemplate.query(
				"SELECT visit_request.id, visitor_talent_id visitorTalentId,host_address as hostAddress, host_talent_id hostTalentId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStartTime, TO_CHAR(visit_endtime,'DD-MM-YYYY HH:mi AM') as visitEndtime,purpose_of_visit purpose, visit_place visitPlace,approve_by approveBy,"
						+ "				 cancelled_by cancelledBy , mobile_number as mobileNumber, visitor_name as visitorName,host_name as hostName, host_image as hostImage, image,visibility  "
						+ "				  from visit_request where  host_talent_id= ?  and visit_starttime::date >= now()::date  order by created_date desc",
				BeanPropertyRowMapper.newInstance(VisitRequest.class), hostid);
	}

	@Override
	public List<VisitRequest> getNotesValue(Integer hostid) {
		return jdbcTemplate.query("select notes as hostNotes,visibility from visit_request where id=?",
				BeanPropertyRowMapper.newInstance(VisitRequest.class), hostid);
	}

	@Override
	public List<VisitRequest> findvistorsbyVisitor(String visitorid) {
		return jdbcTemplate.query(
				" SELECT visit_request.id, visitor_talent_id visitorTalentId,address as address, host_talent_id hostTalentId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStarttime, TO_CHAR(visit_endtime,'DD-MM-YYYY HH:mi AM') as visitEndtime, purpose_of_visit purpose, visit_place visitPlace,approve_by approveBy,"
						+ "	  cancelled_by cancelledBy , mobile_number as mobileNumber, visitor_name1 as visitorName, host_name as hostName, host_image as hostImage, image,visibility "
						+ "				 from visit_request where  visitor_talent_id= ? order by created_date desc",
				BeanPropertyRowMapper.newInstance(VisitRequest.class), visitorid);
	}

	@Override
	public VisitRequest findById(long id) {
		try {
			VisitRequest visitRequest = jdbcTemplate.queryForObject(
					"SELECT id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId,  TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStartTime, purpose_of_visit purpose, visit_place , mobile_number as mobileNumber, visitor_name visitorName, host_name as hostName, host_image as hostImage, image FROM visit_request WHERE id=?",
					BeanPropertyRowMapper.newInstance(VisitRequest.class), id);

			return visitRequest;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public VisitRequest savebyHost(VisitRequest visitRequest, Date updateTime) {
		int result = 0;
		String visitStartTime = "";
		String talentId = visitRequest.getVisitorTalentId();
			String mailId = visitRequest.getHostTalentId();
			String mailId1 = visitRequest.getVisitorTalentId();
		
			VisitRequest lastInstertedRow=new VisitRequest();
		List<String> baggage = visitRequest.getBaggage().stream().map(BaggageRequest::getId)
				.collect(Collectors.toList());

		VisitRequestDaoImpl ab = new VisitRequestDaoImpl();
		if (visitRequest.getHostName() == null) {
			visitRequest.setHostName(visitRequest.getUsername());
		}
		
		if (visitRequest.getVisitorName() == "") {
			visitRequest.setVisitorName(visitRequest.getUsername());
		}

		if (visitRequest.getType().equals("visitor")) {
			visitStartTime = jdbcTemplate.queryForObject("INSERT INTO visit_request("
					+ "    visitor_talent_id,address, host_talent_id,  visit_starttime, visit_endtime, "
					+ "   visit_place, purpose_of_visit, parking_required, vehical_no, no_of_people, "
					+ "   baggage, created_date, created_by,  " + "      remarks,visitor_name1, visitor_name2, \r\n"
					+ "            visitor_name3, visitor_name4, visitor_name5, visitor_name6, visitor_name7, \r\n"
					+ "            visitor_name8, visitor_name9, visitor_name10,image , mobile_number, visitor_name, host_name , host_image )"
					+ " VALUES (?,?, ?, TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), "
					+ "      ?, ?, ?, ?, ?, " + "      ?, now(), ?, " + "      ?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) returning  visit_starttime as visitStartTime",
					new Object[] { visitRequest.getVisitorTalentId(),visitRequest.getAddress(), visitRequest.getHostTalentId(),
							visitRequest.getVisitStarttime(), visitRequest.getVisitEndtime(),
							visitRequest.getVisitPlace(), visitRequest.getPurpose(), visitRequest.getParking(),
							visitRequest.getVehicalNo(), visitRequest.getNoofpeople(), StringUtils.join(baggage, ", "),
							visitRequest.getVisitorTalentId(), visitRequest.getRemarks(),
							visitRequest.getNameList().getName1(), visitRequest.getNameList().getName2(),
							visitRequest.getNameList().getName3(), visitRequest.getNameList().getName4(),
							visitRequest.getNameList().getName5(), visitRequest.getNameList().getName6(),
							visitRequest.getNameList().getName7(), visitRequest.getNameList().getName8(),
							visitRequest.getNameList().getName9(), visitRequest.getNameList().getName10(),
							visitRequest.getImage(), visitRequest.getMobileNumber(), visitRequest.getVisitorName(),
							visitRequest.getHostName(), visitRequest.getHostImage() },
					String.class);
			
			lastInstertedRow = jdbcTemplate.queryForObject(
					"SELECT id,visitor_talent_id as visitorTalentId from visit_request order by created_date desc limit 1 ",
					BeanPropertyRowMapper.newInstance(VisitRequest.class));
			
		} else {
			
			if(visitRequest.getHostName() ==null) {
				visitRequest.setHostName(visitRequest.getUsername());
			}

			visitStartTime = jdbcTemplate.queryForObject("INSERT INTO visit_request("
					+ "    visitor_talent_id,host_address, host_talent_id,  visit_starttime, visit_endtime, "
					+ "   visit_place, purpose_of_visit, parking_required, vehical_no, no_of_people, "
					+ "   baggage, created_date, created_by,  " + "      remarks,visitor_name1, visitor_name2, \r\n"
					+ "            visitor_name3, visitor_name4, visitor_name5, visitor_name6, visitor_name7, \r\n"
					+ "            visitor_name8, visitor_name9, visitor_name10,approve_by,approve_date,image, mobile_number, visitor_name, host_name , host_image )"
					+ " VALUES (?,?, ?, TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'), "
					+ "      ?, ?, ?, ?, ?, " + "      ?, now(), ?, " + "      ?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?) returning  visit_starttime as visitStartTime",
					new Object[] { visitRequest.getVisitorTalentId(),visitRequest.getHostAddress(), visitRequest.getHostTalentId(),
							visitRequest.getVisitStarttime(), visitRequest.getVisitEndtime(),
							visitRequest.getVisitPlace(), visitRequest.getPurpose(), visitRequest.getParking(),
							visitRequest.getVehicalNo(), visitRequest.getNoofpeople(), StringUtils.join(baggage, ", "),
							visitRequest.getHostTalentId(), visitRequest.getRemarks(),
							visitRequest.getNameList().getName1(), visitRequest.getNameList().getName2(),
							visitRequest.getNameList().getName3(), visitRequest.getNameList().getName4(),
							visitRequest.getNameList().getName5(), visitRequest.getNameList().getName6(),
							visitRequest.getNameList().getName7(), visitRequest.getNameList().getName8(),
							visitRequest.getNameList().getName9(), visitRequest.getNameList().getName10(),
							visitRequest.getHostTalentId(), visitRequest.getImage(), visitRequest.getMobileNumber(),
							visitRequest.getVisitorName(), visitRequest.getHostName(), visitRequest.getHostImage() },
					String.class);
			lastInstertedRow = jdbcTemplate.queryForObject(
					"SELECT id,visitor_talent_id as visitorTalentId from visit_request order by created_date desc limit 1 ",
					BeanPropertyRowMapper.newInstance(VisitRequest.class));
		}
		if (!visitStartTime.isEmpty()) {
			try {
				String message = "";
				HashMap dataMapforAppId = new HashMap();
				if (visitRequest.getType().equals("visitor")) {
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<HashMap> resultId = restTemplate
						.getForEntity(talentTokenUrl + visitRequest.getHostTalentId(), HashMap.class);
				dataMapforAppId = new HashMap(resultId.getBody());
				System.out.println(resultId.getBody());

				// String sDate1="2022-04-16 14:43:00";

				// SimpleDateFormat formatDate = ;

				

			
					message = visitRequest.getVisitorName() + " has requested an appointment to meet you on "
							+ new SimpleDateFormat("dd/MM/yyyy hh:mm a")
									.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(visitStartTime))
							+ " at " + visitRequest.getVisitPlace();
				} else {
					RestTemplate restTemplate = new RestTemplate();
					ResponseEntity<HashMap> resultId = restTemplate
							.getForEntity(talentTokenUrl + visitRequest.getVisitorTalentId(), HashMap.class);
					 dataMapforAppId = new HashMap(resultId.getBody());
					System.out.println(resultId.getBody());

					// String sDate1="2022-04-16 14:43:00";

					// SimpleDateFormat formatDate = ;

				

					message = visitRequest.getHostName() + " has requested an appointment to meet you on "
							+ new SimpleDateFormat("dd/MM/yyyy hh:mm a")
									.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(visitStartTime))
							+ " at " + visitRequest.getVisitPlace();
				}

				if (visitRequest.getHostTalentId().contains("@")|| visitRequest.getVisitorTalentId().contains("@")) {
					if(visitRequest.getHostTalentId().contains("@")) {
						customerMail(visitStartTime, talentId, message, mailId,lastInstertedRow.getId());
					}
					if( visitRequest.getVisitorTalentId().contains("@")) {
						customerMail(visitStartTime, talentId, message, mailId1,lastInstertedRow.getId());
					}
					
				} else {
					
					String TokenNew = dataMapforAppId.get("gcmToken").toString();

					ab.fcmnotification1(TokenNew, message);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return visitRequest;
		} else {
			return null;
		}
	}

	private String customerMail(String visitStartTime, String talentId, String message, String mailId,Long id)
			throws Exception {
		Email email = new Email();
		StringBuffer sb = new StringBuffer();
		String path = "";
		email.setFromEmailAddress("info@talentchek.com");
		String toMailAddress = mailId;
		String[] toEmailIds = toMailAddress.split(",");
		email.setToEmailAddress(toEmailIds);
		String logoImage = "http://183.82.246.243/assets/images/vc_logo_1.png";
		//String logoIdmage = "http://183.82.246.243/assets/images/vc_logo_1.png";
		//sb.append("<img style='width:86px;height:50px;' src="+logoImage+">");

 		sb.append("<!DOCTYPE html>\r\n");
		sb.append(
				"<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">");

		sb.append("<head>\r\n");
		sb.append("<meta charset=\"UTF-8\">");

		sb.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">");
		sb.append("<meta name=\"x-apple-disable-message-reformatting\">");

		sb.append("<style>");
		sb.append(" table, td, div, h1, p {font-family: Arial, sans-serif;}\r\n");
		
		sb.append("</style>");
		sb.append("</head>");

		sb.append("<body style=\"margin:0;padding:0;word-spacing:normal;\">");
		sb.append(
				"<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">");

		sb.append("<tr>");
		sb.append("<td align=\"center\" style=\"padding:0;\">");
		sb.append(
				"<table role=\"presentation\" style=\"width:602px;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0;text-align:left;\">");

		sb.append("<tr>");
		sb.append(
				"<td align=\"center\" style=\"padding:40px 0 30px 0;background: linear-gradient(to right,#ee7724,#d8363a,#dd3675,#b44593);\">");
		sb.append(
				"<img src=\"http://visitorchek.com/assets/images/1024%20%202.png\" alt=\"\" width=\"200\" style=\"height:auto;display:block;\" />");
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td style=\"padding:36px 14px 7px 23px;\">");
		sb.append(
				"<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">");

		sb.append("<tr>");
		sb.append("<td style=\"padding:0 0 36px 0;color:#153643; font-weight:600px;\">");
		sb.append("<h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Dear Sir/Madam</h1>");
		sb.append(message + ".");
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");

		sb.append("<td style=\"padding:0;\">");
		sb.append(
				"<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">");

		sb.append("<tr>");

		sb.append("<td>");
//		sb.append("<p style=\"margin:0;\"><a href=\"http://183.82.246.243/accept/3\" target=\"_blank\" style=\"background: #2bde28; margin-left: 100px;text-decoration: none; padding: 10px 45px; color: #ffffff; box-shadow: #D6D6E7 0 0 0 1.5px inset, rgba(45, 35, 66, 0.4) 0 2px 4px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;box-shadow: rgba(45, 35, 66, 0.4) 0 4px 8px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;\r\n"
//				+ "  transform: translateY(-2px); border-radius: 4px; display:inline-block; mso-padding-alt:0;text-underline-color:#ff3884\"><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%;mso-text-raise:20pt\">&nbsp;</i><![endif]--><span style=\"mso-text-raise:10pt;font-weight:bold;\">Accept</span><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%\">&nbsp;</i><![endif]--></a></p>");
//		sb.append("</td>");
		
		sb.append("<p style=\"margin:0;\"><a href=\"http://visitorchek.com/accept/"+id+"/"+mailId+"/A\" target=\"_blank\" style=\"background: #2bde28; margin-left: 100px;text-decoration: none; padding: 10px 45px; color: #ffffff; box-shadow: #D6D6E7 0 0 0 1.5px inset, rgba(45, 35, 66, 0.4) 0 2px 4px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;box-shadow: rgba(45, 35, 66, 0.4) 0 4px 8px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;\r\n"
				+ "  transform: translateY(-2px); border-radius: 4px; display:inline-block; mso-padding-alt:0;text-underline-color:#ff3884\"><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%;mso-text-raise:20pt\">&nbsp;</i><![endif]--><span style=\"mso-text-raise:10pt;font-weight:bold;\">Accept</span><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%\">&nbsp;</i><![endif]--></a></p>");
		sb.append("</td>");

		sb.append("<td style=\"width:20px;padding:0;font-size:0;line-height:0;\">&nbsp;</td>");

		sb.append("<td style=\"width:260px;padding:0;vertical-align:top;color:#153643;\">");
//		sb.append("\n\n<a href=\"http://183.82.246.243/accept\"><button><span>Accept</span></button></a>\n\n<br>");
		sb.append("<p style=\"margin:0;\"><a href=\"http://visitorchek.com/accept/"+id+"/"+mailId+"/R\" target=\"_blank\" style=\"background: #ff3526; text-decoration: none; padding: 10px 45px; color: #ffffff; box-shadow: #D6D6E7 0 0 0 1.5px inset, rgba(45, 35, 66, 0.4) 0 2px 4px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;box-shadow: rgba(45, 35, 66, 0.4) 0 4px 8px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;\r\n"
				+ "  transform: translateY(-2px); border-radius: 4px; display:inline-block; mso-padding-alt:0;text-underline-color:#ff3884\"><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%;mso-text-raise:20pt\">&nbsp;</i><![endif]--><span style=\"mso-text-raise:10pt;font-weight:bold;\">Reject<!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%\">&nbsp;</i><![endif]--></a></p>");
		sb.append("</td>");

		sb.append("</tr>");

		sb.append("</table>");
		
		sb.append("<tr>");
		sb.append("<td style=\"padding:0 0 36px 0;color:#153643; font-weight:600px;\">");
		sb.append("<h4  style=\"font-size:15px;margin:0 0 20px 0;margin-top:25px;font-family:Arial,sans-serif;\"><a href=\"https://portal.talentchek.com/tc/userprofile?tfid="+talentId+"\"target=\"_blank\">Please find the requestor's Talent Chek</a> </h4>");
		sb.append("\n<h4   style=\"font-size:15px;margin:0 0 20px 0;margin-top:25px;font-family:Arial,sans-serif;\">For better tracking and meeting tracker&nbsp;<a href=\"https://play.google.com/store/apps/details?id=com.paragondynamics.visitorchek\"target=\"_blank\">click here</a>&nbsp;to download and register </h4>\n\n<br>");
		sb.append("Best Regards," + "\n\n<br><br>");
		sb.append("Team VisitorChek");
		
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");

		sb.append("<td style=\"padding:30px;background: linear-gradient(to right,#ee7724,#d8363a,#dd3675,#b44593);\">");
		sb.append(
				"<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;font-size:9px;font-family:Arial,sans-serif;\">");

		sb.append("<tr>");

		sb.append("<td style=\"padding:0;width:50%;\" align=\"left\">");
		sb.append("<p style=\"margin:0;font-size:14px;line-height:16px;font-family:Arial,sans-serif;color:#ffffff;\">");
		sb.append("www.visitorchek.com");
		sb.append("</p>");
		sb.append("</td>");

		sb.append("<td style=\"padding:0;width:50%;\" align=\"right\">");
		sb.append("<table role=\"presentation\" style=\"border-collapse:collapse;border:0;border-spacing:0;\">");
		sb.append("<tr>");
		sb.append("<td style=\"padding:0 0 0 10px;width:38px;\">");
		sb.append(
				"<a href=\"https://www.instagram.com/talent_chek/?hl=en\" target=\"_blank\" style=\"color:#ffffff;\"><img src=\"https://cdn.exclaimer.com/Handbook%20Images/instagram-icon_32x32.png?_ga=2.38239331.1532690189.1651820489-1066359684.1651820489\" alt=\"Instagram\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>");
		sb.append("</td>");

		sb.append("<td style=\"padding:0 0 0 10px;width:38px;\">");
		sb.append(
				"<a href=\"https://www.facebook.com/Talent-Chek-107162598448547/\" target=\"_blank\" style=\"color:#ffffff;\"><img src=\"https://cdn.exclaimer.com/Handbook%20Images/facebook-icon_64x64.png?_ga=2.70652368.1532690189.1651820489-1066359684.1651820489\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");

		sb.append("</td>");

		sb.append("</tr>");
		sb.append("</table>");

		sb.append("</td>");
		sb.append("</tr>");

		sb.append("</table>");
		sb.append("</td>");

		sb.append("</tr>");
		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");

//		sb.append("Dear Sir" + "/" + "Madam <br><br>");
//		sb.append(message + ".");
//		sb.append("<br>");
//		sb.append("<br>");
////		sb.append("\n\n<a href=\"http://183.82.246.243/accept\">Click here to Accept</a>\n\n<br>");
//		sb.append("\n\n<a href=\"http://183.82.246.243/accept\"><button>Accept</button></a>\n\n<br>");
//		sb.append("<button type=\"button\" class=\"btn btn-success\">Reject</button>\r\n");
//
//		sb.append("<br>");
//		sb.append("Please find the Talent Chek credentials below.<br>");
//		sb.append("<br>");
//		sb.append("\n\n<a href=\"https://portal.talentchek.com/tc/login\">Click here to Register</a>\n\n<br>");
//		sb.append("<br>");
//
//		sb.append("Best Regards," + "\n\n<br>");
//		sb.append("IT Support" + "\n\n<br>");

		email.setBodyHtml(sb.toString());

		email.setSubject("Appointment to meet you on - " + new SimpleDateFormat("dd/MM/yyyy hh:mm a")
				.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(visitStartTime)));
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendMail(email, path);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

		return path;
	}

	public static void sendMail(Email email, String path) throws Exception {

		String host = "smtpout.secureserver.net";
		// Create properties for the Session
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get a session
		Session session = Session.getInstance(props);

		try {
			Transport bus = session.getTransport("smtp");

			bus.connect("smtpout.secureserver.net", "info@talentchek.com", "Paragon@01");
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(email.getFromEmailAddress()));

			int n = email.getToEmailAddress().length;
			InternetAddress[] address = new InternetAddress[n];
			for (int i = 0; i < n; i++) {
				address[i] = new InternetAddress(email.getToEmailAddress()[i]);

			}
			msg.setRecipients(Message.RecipientType.TO, address);

			if (email.getCcEmailAddress() != null) {
				int ccCount = email.getCcEmailAddress().length;
				InternetAddress[] ccAddress = new InternetAddress[ccCount];
				for (int i = 0; i < ccCount; i++) {
					ccAddress[i] = new InternetAddress(email.getCcEmailAddress()[i]);
				}
				msg.setRecipients(Message.RecipientType.CC, ccAddress);
			}

			msg.setSubject(email.getSubject());
			msg.setSentDate(new Date());
			msg.setContent(email.getBodyHtml(), "text/html");
			msg.saveChanges();
			bus.sendMessage(msg, msg.getAllRecipients());
			bus.close();

		} catch (MessagingException mex) {
			mex.printStackTrace();
			while (mex.getNextException() != null) {
				Exception ex = mex.getNextException();
				ex.printStackTrace();
				if (!(ex instanceof MessagingException))
					break;
				else
					mex = (MessagingException) ex;
			}
			throw mex;
		} finally {
			System.out.println("mail core smtp Successfully");
		}
	}

	@Override
	public VisitRequest save(VisitRequest visitRequest) {
		int result = 0;
		String talentId = visitRequest.getVisitorTalentId();
		String mailId = visitRequest.getHostTalentId();
		String visitStartTime = "";
		VisitRequestDaoImpl ab = new VisitRequestDaoImpl();
		VisitRequest lastInstertedRow = new VisitRequest();
		visitStartTime = jdbcTemplate.queryForObject(
				"INSERT INTO visit_request (visitor_talent_id, visit_starttime ,visit_endtime,  "
						+ "purpose_of_visit,visit_place,host_talent_id, created_by, created_date, mobile_number, visitor_name, image, host_name , host_image ) "
						+ " VALUES(?,TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'),TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI'),?,?,?,?,now(), ? , ? , ?, ?, ?) returning visit_starttime as visitStartTime",
				new Object[] { visitRequest.getVisitorTalentId(), visitRequest.getVisitStarttime(),
						visitRequest.getVisitEndtime(), visitRequest.getPurpose(), visitRequest.getVisitPlace(),
						visitRequest.getHostTalentId(), visitRequest.getVisitorTalentId(),
						visitRequest.getMobileNumber(), visitRequest.getVisitorName(), visitRequest.getImage(),
						visitRequest.getHostName(), visitRequest.getHostImage() },
				String.class);
		lastInstertedRow = jdbcTemplate.queryForObject(
				"SELECT id,visitor_talent_id as visitorTalentId from visit_request order by created_date desc limit 1 ",
				BeanPropertyRowMapper.newInstance(VisitRequest.class));
		if (!visitStartTime.isEmpty()) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<HashMap> resultId = restTemplate
						.getForEntity(talentTokenUrl + visitRequest.getHostTalentId(), HashMap.class);
				HashMap dataMapforAppId = new HashMap(resultId.getBody());
				System.out.println(resultId.getBody());

				// String sDate1="2022-04-16 14:43:00";

				// SimpleDateFormat formatDate = ;

				String message = visitRequest.getVisitorName() + " has requested an appointment to meet you on "
						+ new SimpleDateFormat("dd/MM/yyyy hh:mm a")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(visitStartTime))
						+ " at " + visitRequest.getVisitPlace();
				if (visitRequest.getHostTalentId().contains("@") || visitRequest.getVisitorTalentId().contains("@")) {

					customerMail(visitStartTime, talentId, message, mailId,lastInstertedRow.getId());
				} else {
					String TokenNew = dataMapforAppId.get("gcmToken").toString();

					ab.fcmnotification1(TokenNew, message);

				}

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
		String AUTH_KEY_FCM = "AAAAe3vDxjA:APA91bF8C_U4eN_ypDNGfm9caVP1xqR4PuXII-3kPGGrKUh-ymw1ahtwwVGjA5VjNjBIuCi6qhHJ3_ScBHteV9q8jMyPR_OkML-U7MYu9ai4qyLfRccLmNvXbzQ8m5FjfE3poFuCYUIk";
		String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
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
		result = jdbcTemplate.update(
				"UPDATE visit_request SET talentid=?,visit_starttime=to_date(?,'DD-MM-YYYY'),purpose=?,visit_place=? WHERE id=?",
				new Object[] { visitRequest.getVisitorTalentId(), visitRequest.getVisitEndtime(),
						visitRequest.getPurpose(), visitRequest.getVisitPlace(), visitRequest.getId() });

		if (result != 0) {
			return visitRequest;
		} else {
			return null;
		}
	}

	@Override
	public Boolean update(String status, String hostId, int id) {
		int result = 0;
		String query = "";
		if (status.equals("A")) {
			query = query + "UPDATE visit_request SET approve_by=? ,approve_date=now() WHERE id=?";
		} else {
			query = query + "UPDATE visit_request SET cancelled_by=? ,cancelled_date=now() WHERE id=?";
		}
		result = jdbcTemplate.update(query, new Object[] { hostId, id });

		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<VisitRequest> findvistorsbyVisitDate(String visitDate) {
		return jdbcTemplate.query(
				" SELECT visit_request.id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStarttime, TO_CHAR(visit_endtime,'DD-MM-YYYY HH:mi AM') as visitEndtime, purpose_of_visit purpose, visit_place visitPlace,approve_by approveBy,"
						+ "	  cancelled_by cancelledBy"
						+ "				 from visit_request where TO_CHAR(visit_starttime,'DD-MM-YYYY')= ?",
				BeanPropertyRowMapper.newInstance(VisitRequest.class), visitDate);
	}

	@Override
	public VisitRequest saveNotes(VisitRequest orders) {
		int result = 0;

		VisitRequestDaoImpl bean = new VisitRequestDaoImpl();

		String query = "";
		boolean visibility = false; //Only for me
		if (!orders.getVisibility().isEmpty()) {
			if (!orders.getVisibility().equals("false")) {
				visibility = true; // visible to Others
				
			}
		}

		query = query + "UPDATE visit_request SET notes=?,visibility=?  WHERE id=?";

		result = jdbcTemplate.update(query, new Object[] { orders.getHostNotes(), visibility, orders.getId() });

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

	@Override
	public List<VisitRequest> findAllList(String hostid) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(
				"SELECT  id, visitor_talent_id visitorTalentId, host_talent_id hostTalentId, TO_CHAR(visit_starttime,'DD-MM-YYYY HH:mi AM') as visitStartTime, \r\n"
						+ "TO_CHAR(visit_endtime,'DD-MM-YYYY HH:mi AM') as visitEndtime,purpose_of_visit purpose, visit_place visitPlace,approve_by approveBy,\r\n"
						+ "	cancelled_by cancelledBy , mobile_number as mobileNumber, visitor_name visitorName, host_name as hostName, host_image as hostImage, image,\r\n"
						+ "string_agg(case when vs.status ='I' then 'I' else '' end, '') as In , string_agg(case when vs.status ='O' then 'O' else '' end,'') as Out\r\n"
						+ "	from visit_request vr\r\n" + "left join visit_status vs\r\n"
						+ "on vs.request_id = vr.id\r\n" + "	 where  visit_starttime::date = now()::date\r\n"
						+ "	 group by \r\n" + "	 id,  visitorTalentId,  hostTalentId,  visitStartTime, \r\n"
						+ " visitEndtime, purpose,  visitPlace, approveBy,\r\n"
						+ "	 cancelledBy ,   mobileNumber,  visitorName,   hostName,  hostImage, image ",
				BeanPropertyRowMapper.newInstance(VisitRequest.class));
	}

	@Override
	public VisitRequest saveListIn(VisitRequest visitRequest) {
		// TODO Auto-generated method stub
		int result = 0;

		VisitRequestDaoImpl bean = new VisitRequestDaoImpl();

		String query = "insert into visit_status(request_id,status,status_time) values (?,'I',now())";

		result = jdbcTemplate.update(query, new Object[] { visitRequest.getId() });

		if (result != 0) {
			try {
				RestTemplate restTemplate = new RestTemplate();

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
	public VisitRequest saveListOut(VisitRequest visitRequest) {
		// TODO Auto-generated method stub
		int result = 0;

		VisitRequestDaoImpl bean = new VisitRequestDaoImpl();

		String query = "insert into visit_status(request_id,status,status_time) values (?,'O',now())";

//		String query="update visit_status set request_id =?,status='O',status_time=now() where request_id=?";

		result = jdbcTemplate.update(query, new Object[] { visitRequest.getId() });

		if (result != 0) {
			try {
				RestTemplate restTemplate = new RestTemplate();

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
	public VisitRequest saveToken(VisitRequest visitRequest) {
		// TODO Auto-generated method stub
		int result = 0;

		VisitRequestDaoImpl bean = new VisitRequestDaoImpl();
		
		String token = visitRequest.getVisitorTalentId();
		
		Integer count = jdbcTemplate.queryForObject("select count(*)  from gcm_token where token=?",
				BeanPropertyRowMapper.newInstance(Integer.class), token);
		
if(count==0) {
	//Insert
	String query = "insert into gcm_token values (?,?)";

	result = jdbcTemplate.update(query, new Object[] { token, visitRequest.getType() });
	
}else {
	//update
	String query ="UPDATE gcm_token SET token=? WHERE talent_id=?";


result = jdbcTemplate.update(query, new Object[] { token,  visitRequest.getType() });
	
}
		
		return null;
	}
	

@Override
public Boolean saveAccept(String email, String id,String res) {
	// TODO Auto-generated method stub
	int result;
	String query = "";
	if(res.equals("A")) {
		query ="UPDATE visit_request SET approve_by=? ,approve_date=now() WHERE id=?";
	}else {
		query ="UPDATE visit_request SET cancelled_by=? ,approve_date=now() WHERE id=?";
	}
	
	
	result = jdbcTemplate.update(query, new Object[] { email,Integer.parseInt(id) });
	if (result != 0) {
		return true;
	} else {
		return false;
	}
	
}

}
