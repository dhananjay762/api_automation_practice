package test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

public class Sample1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Post Call
//		String sampleJson = "{\r\n"
//				+ "  \"location\": {\r\n"
//				+ "    \"lat\": -38.383494,\r\n"
//				+ "    \"lng\": 33.427362\r\n"
//				+ "  },\r\n"
//				+ "  \"accuracy\": 50,\r\n"
//				+ "  \"name\": \"Frontline house\",\r\n"
//				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
//				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
//				+ "  \"types\": [\r\n"
//				+ "    \"shoe park\",\r\n"
//				+ "    \"shop\"\r\n"
//				+ "  ],\r\n"
//				+ "  \"website\": \"http://google.com\",\r\n"
//				+ "  \"language\": \"French-IN\"\r\n"
//				+ "}\r\n"
//				+ "";
		
		//content of the file to string
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Dhananjay\\eclipse-workspace\\api-automation\\src\\test\\resources\\json\\AddPlace.json"))))
				.when().post("/maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println("Response - "+response);
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println("placeID --> "+placeId);
		
		//Update
		String updatedAddress = "345 Churchgate Road, NJ, USA";
		String updateJson = "{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+updatedAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(updateJson)
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getPlaceResponse);
		System.out.println(js1.getString("address"));
		Assert.assertEquals(js1.getString("address"), updatedAddress);
		

	}

}
