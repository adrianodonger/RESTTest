package service.inbound;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class TestingClient {
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		String uri = "http://localhost:8080/Auzmor/inbound/sms";
		JsonObject json = Json.createObjectBuilder().add("from","8084323442").add("to","23491074314").add("text","Hello this is a testing ground").build();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(uri);
		JsonArray response = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class);
	}

}
