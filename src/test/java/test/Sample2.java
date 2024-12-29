package test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Sample2 {

	@Test(dataProvider = "BooksData")
	public void dynamicJson(String aisle, String isbn) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		Response response = given().header("Content-Type", "application/json").body(Payload.AddBook(aisle, isbn))
							.when().post("Library/Addbook.php")
							.then().assertThat().statusCode(200).extract().response();
		
		JsonPath js = new JsonPath(response.asString());
		String id = js.get("ID");
		System.out.println("id --> "+id);

	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] {
			{"test213", "88991"},
			{"asfhsak", "88992"},
			{"asdwqer", "88993"},
		};
	}

}
