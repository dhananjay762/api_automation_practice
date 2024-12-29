package test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.RestAssured;

public class ECommerceAPITest {

	public static void main(String[] args) {
		
		//Set Base URI
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		RequestSpecification reqSpec;
		JsonPath js;
		
		//Login 
		LoginRequest loginReq = new LoginRequest();
		loginReq.setUserEmail("postmanapi@api.com");
		loginReq.setUserPassword("Postman@123");
		reqSpec = given().relaxedHTTPSValidation().log().all().contentType(ContentType.JSON).body(loginReq);
		LoginResponse loginResp = reqSpec.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		String authToken = loginResp.getToken();
		String userId = loginResp.getUserId();
		System.out.println("authToken --> "+authToken);
		
		//Add Product
		reqSpec = given().log().all().header("authorization", authToken)
					.param("productName", "Laptop")
					.param("productAddedBy", userId).param("productCategory", "Electronic")
					.param("productSubCategory", "Laptop").param("productPrice", "11500")
					.param("productDescription", "HP").param("productFor", "men")
					.multiPart("productImage",new File("C:\\Dhananjay\\eclipse-workspace\\api-automation\\images\\imageLaptop.jpg"));
		String addProdResp = reqSpec.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
		js = new JsonPath(addProdResp);
		String productId = js.get("productId");
		System.out.println("productId --> "+productId);
		
		//Create Order
		OrderDetails orderDetails1 = new OrderDetails();
		orderDetails1.setCountry("China");
		orderDetails1.setProductOrderedId(productId);
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		orderDetailsList.add(orderDetails1);
		Orders orders = new Orders();
		orders.setOrders(orderDetailsList);
		reqSpec = given().log().all().header("authorization", authToken).contentType(ContentType.JSON).body(orders);
		String createOrderResp = reqSpec.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
		System.out.println("createOrderResp --> "+createOrderResp);
		
		//Delete Product
		reqSpec = given().log().all().header("authorization", authToken).contentType(ContentType.JSON).pathParam("productId", productId);
		String deleteProdResp = reqSpec.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		js = new JsonPath(deleteProdResp);
		Assert.assertEquals("Product Deleted Successfully",js.get("message"));
				
		

	}

}
