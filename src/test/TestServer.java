package test;

import com.google.gson.JsonObject;
import jrest.HttpMethod;
import jrest.HttpStatus;
import jrest.MediaType;
import jrest.ResponseEntity;
import jrest.JRest;

public class TestServer {
	
	static String[] names = {
			"Frank",
			"Jeff",
			"Oliver",
			"Maxwell"
	};

	public static void main(String[] args) {
		/**
		 * Start server
		 */
		JRest server = JRest.create()
				.setServerName("Test Server")
				.setPort(80)
				.start();
		
		/**
		 * Open in a web browser! http://localhost/
		 */
		server.addEndpoint(HttpMethod.GET, "/", MediaType.TEXT_HTML, (request)->{
			return new ResponseEntity<String>(HttpStatus.OK, "<h1>Index! Welcome to JREST!</h1>");
		});

		/**
		 * Test Endpoint. Returns static String
		 */
		server.addEndpoint(HttpMethod.GET, "/testAPI", (request)->{
			return new ResponseEntity<String>(HttpStatus.OK, "Hello From Server!");
		});
		
		/**
		 * Test Post endpoint. Returns your posted data back to you.
		 */
		server.addEndpoint(HttpMethod.POST, "/GetEmployee", MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, JsonObject.class, (request)->{
			JsonObject payload = request.getBody();
			int id = payload.get("id").getAsInt();
			
			JsonObject response = new JsonObject();
			response.addProperty("id", id);
			response.addProperty("name", names[id-1]);
			
			return new ResponseEntity<JsonObject>(HttpStatus.OK, response);
		});
		
		/**
		 * Test Post endpoint. Returns your posted data back to you.
		 */
		server.addEndpoint(HttpMethod.GET, "/GetUsername", (request)->{
			int id = Integer.parseInt(request.getUrlParameters().get("id").toString());
			String name = names[id-1];
			return new ResponseEntity<String>(HttpStatus.OK, name);
		});
		
		/**
		 * Test JSON endpoint. Returns a JSON object.
		 */
		server.addEndpoint(HttpMethod.GET, "/testJson", MediaType.ALL, MediaType.APPLICATION_JSON, (request)->{
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("TestKey", "Hello World!");
			
			return new ResponseEntity<JsonObject>(HttpStatus.OK, jsonObject);
		});
		
		/**
		 * Test JSON endpoint. Returns a JSON object.
		 */
		server.addEndpoint(HttpMethod.POST, "/testForm", MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, (request)->{
			JsonObject jsonObject = new JsonObject();
			if ( request.getUrlParameters().containsKey("id") && "123".equals(request.getUrlParameters().get("id")))
				jsonObject.addProperty("Message", "Access Granted");
			else
				jsonObject.addProperty("Message", "Invalid Credentials");
			
			return new ResponseEntity<JsonObject>(HttpStatus.OK, jsonObject);
		});
	}
}