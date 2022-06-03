package org.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import org.example.ExtentReportBase;



public class TestDeleteTicket_Ruchira {

    ResponseSpecification responseSpecification = null;
    
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

        ExtentReportBase.test = ExtentReportBase.reports.startTest("Test for Booking Tikcet 1", "Booking a ticket with correct details");

        JSONObject bodyParams = new JSONObject();

        bodyParams.put("uId", 20);
        bodyParams.put("tbSource", "Bengaluru");
        bodyParams.put("tbDestination", "Patna");
        bodyParams.put("trainNumber", 10111);

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

    @SuppressWarnings("unchecked")
    @Test
    public void postDeleteTicket() {

        ExtentReportBase.test = ExtentReportBase.reports.startTest("Test for Delete Ticket 1", "Deleting a ticket after it is booked");

        JSONObject bodyParams = new JSONObject();
        bodyParams.put("uId", 20);
        bodyParams.put("tbId",224 );
        bodyParams.put("trainNumber", 33336);

        ExtentReportBase.test.log(LogStatus.INFO, "Creating the payload", bodyParams.toJSONString());

        RestAssured.baseURI = Config.serverUrl;
        ExtentReportBase.test.log(LogStatus.INFO, "Specifying the base URL", RestAssured.baseURI);

        String message = given()
                .header("Content-Type", "application/json")
                .body(bodyParams.toJSONString())
                .when()
                .post("/deleteTicket")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .path("message");

        try {
            ExtentReportBase.test.log(LogStatus.INFO, "Actual Value", "Ticket deleted Successfully");
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

