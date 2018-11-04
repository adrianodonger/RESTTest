package service.inbound;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import redis.clients.jedis.Jedis;
import service.PostgresConnection;
import service.RedisUtil;
import service.SMSJSONResponse;
import service.SMSObject;

@Path("/")
public class SMS {
	
	/*@XmlRootElement
	class SMSOb{
		public String from;
		public String to;
		public String text;
	}*/
	
	@POST
	@Path("/sms")
	@Produces(MediaType.APPLICATION_JSON)
	public SMSJSONResponse getSMS(SMSObject sms) throws SQLException {
		
		/*System.out.println("Entered the land of REST :P");
		System.out.println(sms);*/
		
		
		//Since these are static, can be placed in a common location.
		List<String> stopMessages = new ArrayList<>();
		stopMessages.add("STOP");
		stopMessages.add("STOP\n");
		stopMessages.add("STOP\r");
		stopMessages.add("STOP\r\n");
		
		//Validate the sms object received
		SMSJSONResponse response = new SMSJSONResponse();
		
		Jedis jedis = RedisUtil.getJedisConnection();
		
		if(sms.getFromNumber() == null ||
			sms.getToNumber() == null ||
			sms.getText() == null) {
			response.setMessage("");
			response.setError("One of the required parameters is missing");
		}
		
		else if(!sms.checkValidSMS()) {
			response.setMessage("");
			response.setError("one of the parameters is invalid");
		}
		
		else if(!PostgresConnection.hasNumber(sms.getToNumber())) {
			response.setMessage("");
			response.setError("To number not found in DB");
		}
		
		else {
			response.setError("");
			response.setMessage("inbound sms OK");
		}
		
		if(stopMessages.contains(sms.getText())) {
			jedis.set(sms.getFromNumber(), sms.getToNumber());
			jedis.expire(sms.getFromNumber(), 14400);
		}
		
		return response;
			
		
	}
	
	
	//Test Code
	/*@GET
	@Path("/xmltest")
	@Produces(MediaType.APPLICATION_XML)
	public SMSObject getSMS() {
		SMSObject sms = new SMSObject();
		sms.setFromNumber("0987120948");
		sms.setToNumber("9721843074");
		sms.setText("This is a test message that returns XML");
		return sms;
	}*/
	
	/*@GET
	@Path("/jsontest")
	@Produces(MediaType.APPLICATION_JSON)
	public SMSObject getSMSJSON() {
		SMSObject sms = new SMSObject();
		sms.setFromNumber("0987120948");
		sms.setToNumber("9721843074");
		sms.setText("This is a test message that returns XML");
		return sms;
	}*/
	
	
}
