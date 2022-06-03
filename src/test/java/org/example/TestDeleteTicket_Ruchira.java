package org.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.example.ExtentReportBase;



public class TestDeleteTicket_Ruchira {

    ResponseSpecification responseSpecification = null;
    
    String ticketId;
    @BeforeClass
    public void setResponseSpecification() {
        responseSpecification = RestAssured.expect();
        responseSpecification.contentType(ContentType.JSON);
        responseSpecification.statusCode(200);
        responseSpecification.time(Matchers.lessThan(5000L));
        responseSpecification.statusLine("HTTP/1.1 200 ");

        ExtentReportBase.createReport();
    }


    @BeforeTest
    public void startTest() {
    }

    @AfterClass
    public void flushReport() {
        ExtentReportBase.reports.flush();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void postBookTicket() {

        ExtentReportBase.test = ExtentReportBase.reports.startTest("Test for Booking Ticket 1", "Booking a ticket with correct details");

        JSONObject bodyParams = new JSONObject();

        bodyParams.put("uId", 56);
        bodyParams.put("tbSource", "Pune");
        bodyParams.put("tbDestination", "Malda");
        bodyParams.put("trainNumber", 12570);

        ExtentReportBase.test.log(LogStatus.INFO, "Creating the payload", bodyParams.toJSONString());

        RestAssured.baseURI = Config.serverUrl;
        ExtentReportBase.test.log(LogStatus.INFO, "Specifying the base URL", RestAssured.baseURI);

        String message = given()
                .header("Content-Type", "application/json")
                .body(bodyParams.toJSONString())
                .when()
                .post("/user/bookTicket")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .path("message");

        try {
            ExtentReportBase.test.log(LogStatus.INFO, "Actual Value", message);
            ExtentReportBase.test.log(LogStatus.INFO, "Expected Value", "Ticket booked");
            if(message.equals("Ticket booked")) {
                ExtentReportBase.test.log(LogStatus.PASS, "Expected value matched with the actual value");
            } else {
                ExtentReportBase.test.log(LogStatus.FAIL, "Expected value didn't match the actual value");
            }
        } catch (Exception e) {

        }
            
    }
    @Test
    public void getMyTicket() {
        ExtentReportBase.test = ExtentReportBase.reports.startTest("Fetch ticket", "Fetching the ticket");
        RestAssured.baseURI = Config.serverUrl;
        ArrayList message = given()
                .header("Content-Type","application/json")
                .when()
                .get("myTickets?uId=56")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .path("myTickets");
        String st=message.get(0).toString();
        ticketId=st.substring(6,9);
        try {
            ExtentReportBase.test.log(LogStatus.INFO, "Actual Value", String.valueOf(message.size()));
            ExtentReportBase.test.log(LogStatus.INFO, "Expected Value", "Size >=1 ");
            if(message.size()>=1) {
                ExtentReportBase.test.log(LogStatus.PASS, "Expected value matched with the actual value");
            } else {
                ExtentReportBase.test.log(LogStatus.FAIL, "Expected value didn't match the actual value");
            }
        } catch (Exception e) {

        }
         }
    @SuppressWarnings("unchecked")
    @Test
    public void postDeleteTicket() {

        ExtentReportBase.test = ExtentReportBase.reports.startTest("Test for Delete Ticket 1", "Deleting a ticket after it is booked");
        int tId=Integer.parseInt(ticketId);
        JSONObject bodyParams = new JSONObject();
        bodyParams.put("uId", 56);
        bodyParams.put("tbId",tId);
        bodyParams.put("trainNumber", 12570);
        ExtentReportBase.test.log(LogStatus.INFO, "Creating the payload", bodyParams.toJSONString());

        RestAssured.baseURI = Config.serverUrl;
        ExtentReportBase.test.log(LogStatus.INFO, "Specifying the base URL", RestAssured.baseURI);

        String message = given()
                .header("Content-Type", "application/json")
                .body(bodyParams.toJSONString())
                .when()
                .delete("deleteTicket")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .path("message");

        try {
            ExtentReportBase.test.log(LogStatus.INFO, "Actual Value", message);
            ExtentReportBase.test.log(LogStatus.INFO, "Expected Value", "Ticket deleted Successfully");
            if(message.equals("Ticket deleted Successfully")) {
                ExtentReportBase.test.log(LogStatus.PASS, "Expected value matched with the actual value");
            } else {
                ExtentReportBase.test.log(LogStatus.FAIL, "Expected value didn't match the actual value");
            }
        } catch (Exception e) {

        }
            
    }


}

