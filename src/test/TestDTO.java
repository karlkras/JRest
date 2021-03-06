package test;

import java.io.IOException;
import java.net.MalformedURLException;

import com.google.gson.JsonObject;

import jrest.HttpMethod;
import jrest.RequestEntity;

public class TestDTO {

	public TestDTO() throws MalformedURLException {
		// Create payload
		JsonObject body = new JsonObject();
		body.addProperty("id", 1);
		
		// Create request object
		RequestEntity<JsonObject> request = new RequestEntity<>(HttpMethod.POST, body);
		
		// Send request to server
		request.exchangeAsync("http://localhost/GetEmployee", Employee.class, (response)->{
			Employee employee = response.getBody();
			System.out.println("Employee data: ");
			System.out.println("\tid: " + employee.getId());
			System.out.println("\tname: " + employee.getName());
		});
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		new TestDTO();
	}
}

/**
 * DTO used to represent employee information sent from server.
 */
class Employee {
	private int id;
	
	private String name;
	
	public Employee() {
		//
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}