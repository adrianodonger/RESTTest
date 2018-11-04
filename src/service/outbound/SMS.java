package service.outbound;

import java.sql.SQLException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import redis.clients.jedis.Jedis;
import service.PostgresConnection;
import service.RedisUtil;
import service.SMSJSONResponse;
import service.SMSObject;


@Path("/")
public class SMS {
	@POST
	@Path("/sms")
	@Produces(MediaType.APPLICATION_JSON)
	public SMSJSONResponse sendSMS(SMSObject sms) throws SQLException {
		SMSJSONResponse response = new SMSJSONResponse();
		
		//Redis Connection
		Jedis jedis = RedisUtil.getJedisConnection();
		
		//Basic verification tests.
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
			
			else if(!PostgresConnection.hasNumber(sms.getFromNumber())) {
				response.setMessage("");
				response.setError("From number not found in DB");
			}
			
			else if(jedis.get(sms.getFromNumber()) != null) {
				response.setError("sms from "+sms.getFromNumber() + " to " + 
						sms.getToNumber() + " is blocked by STOP Request");
				response.setMessage("");
			}
		
			else {
				response.setError("");
				response.setMessage("inbound sms OK");
			}
		
		
		//Checks for cache entries for From Number.
		String fromInCache = createCountForCache(sms.getFromNumber());
		
		int maxRequests = 50;
		int currentCount = 0;
		if(jedis.get(fromInCache)== null) {
			jedis.set(fromInCache, "1");
			jedis.expire(fromInCache, 86400);
		}
		else{
			currentCount = Integer.parseInt(jedis.get(fromInCache));
			if(currentCount==50) {
				response.setError("Limit reached for "+sms.getFromNumber());
				response.setMessage("");
			}
			else {
				jedis.set(fromInCache, Integer.toString(currentCount++));
			}
		}
		return response;
		
	}
	
	//Method to create a count Key name for each from number in cache.
	public static String createCountForCache(String Number) {
		return new String(Number+"_count");
	}
}
