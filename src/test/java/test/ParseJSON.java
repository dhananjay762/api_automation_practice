package test;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ParseJSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String jsonBody = "{\r\n"
				+ "\r\n"
				+ "\"dashboard\": {\r\n"
				+ "\r\n"
				+ "\"purchaseAmount\": 910,\r\n"
				+ "\r\n"
				+ "\"website\": \"rahulshettyacademy.com\"\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "\"courses\": [\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Selenium Python\",\r\n"
				+ "\r\n"
				+ "\"price\": 50,\r\n"
				+ "\r\n"
				+ "\"copies\": 6\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Cypress\",\r\n"
				+ "\r\n"
				+ "\"price\": 40,\r\n"
				+ "\r\n"
				+ "\"copies\": 4\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"RPA\",\r\n"
				+ "\r\n"
				+ "\"price\": 45,\r\n"
				+ "\r\n"
				+ "\"copies\": 10\r\n"
				+ "\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "]\r\n"
				+ "\r\n"
				+ "}";
		
		JsonPath js = new JsonPath(jsonBody);
		
		//no of courses 
		int count = js.getInt("courses.size()");
		System.out.println("count --> "+count);
		
		//print purchase amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount --> "+purchaseAmount);
		
		//print title of first course
		String title = js.getString("courses[0].title");
		System.out.println("title --> "+title);
		
		//print all course titles
		for(int i=0; i<count; i++) {
			System.out.println("Course["+i+"] title --> "+js.get("courses["+i+"].title"));
		}
		
		//print no of copies sold by RPA course
		for(int i=0; i<count; i++) {
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
				System.out.println("Sold copies --> "+js.get("courses["+i+"].copies"));
				break;
			}
		}
		
		//verify if sum of all course prices match with the purchase amount 
		int sumAmount = 0;
		for(int i=0; i<count; i++) {
			sumAmount = sumAmount + js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
		}
		System.out.println("sumAmount --> "+sumAmount);
		Assert.assertEquals(sumAmount, purchaseAmount);

	}

}
