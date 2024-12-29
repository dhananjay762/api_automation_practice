package test;

import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;
import pojo.GetCourse;

public class Deserialization {

	public static void main(String[] args) {
		
		String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.formParam("grant_type", "client_credentials")
				.formParam("scope", "trust")
				.when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		System.out.println("accessToken -------> "+accessToken);
		
		System.out.println("----------------------------------------");
		GetCourse gc = given().queryParam("access_token", accessToken)
				.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		
		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getCourses().getWebAutomation().get(0).getCourseTitle());

	}

}
