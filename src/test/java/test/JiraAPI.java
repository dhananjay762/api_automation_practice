package test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraAPI {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://dhananjay762.atlassian.net/";
		String AUTH_KEY = "ZGhhbmFuamF5NzYyQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBFZFFIUDgzU194WmE1YmFac09OR2tRMFBfV2daTjAxa0JsUTJsMUppeTl0aFh0UlVJWTgtaDBvU3JYc0Z2LTQwOG1uaXBNaEdLV2tiMm1EZkNXMDVTeE9mYnRnclRBR0UyOVVrUUh6Wlg3MGV1cHlkNHhIb2RUckZzZ3FCM1ZSeWlnbHFLVEMyUU4yZlUyOVhUeVNITEl2UjZmamg2NmhLdS1zcWZrZWhQUTA9NzA3QzVBMDg=";
		
		//Create JIRA Ticket
		String projectKey = "SCRUM";
		String issueTitle = "Menu item is not working";
		Response response = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", "Basic "+AUTH_KEY)
				.body(Payload.createIssueAPI(projectKey, issueTitle))
		.when().post("rest/api/3/issue")
		.then().log().all().assertThat().statusCode(201).contentType("application/json").extract().response();
		
		JsonPath js = new JsonPath(response.asString());
		String issueId = js.getString("id");
		System.out.println("issue id --> "+issueId);
		
		//Upload screenshot
		given().pathParam("key", issueId).header("X-Atlassian-Token", "no-check").header("Authorization", "Basic "+AUTH_KEY)
				.multiPart("file", new File("C:\\Dhananjay\\eclipse-workspace\\api-automation\\src\\test\\resources\\fileupload\\sa-csm-600.png"))
			.when().post("rest/api/3/issue/{key}/attachments")
			.then().log().all().assertThat().statusCode(200);
	}

}
