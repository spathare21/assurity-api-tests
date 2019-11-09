package com.assurity.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Feature("Categories Details")
class FirstTest {

    private static final Logger log = LoggerFactory.getLogger(FirstTest.class);

    @BeforeEach
    void setup(){
        RestAssured.baseURI ="https://api.tmsandbox.co.nz/v1/";
    }

    @Test
    @Story("Verify API Schema")
    void testAPISchema() {
        Response resp = given()
                .header("Accept", "/")
                .queryParam("catalogue",false)
                .when().log().all()
                .get("Categories/6327/Details.json");
        log.info(resp.getBody().asString());
        resp.then().assertThat().body(matchesJsonSchemaInClasspath("DetailsSchema.json"));

    }
    @Test
    @Story("Verify Name Carbon credits is")
    void testValidName() {
        Response resp = given()
                .header("Accept", "/")
                .queryParam("catalogue",false)
                .when().log().all()
                .get("Categories/6327/Details.json");
        log.info(resp.getBody().asString());
        resp.then().assertThat().statusCode(200);
        assertEquals(resp.getBody().jsonPath().get("Name").toString(),"Carbon credits","Verified Carbon credits found");
    }
    @Test
    @Story("Verify Description of Promotion Gallary ")
    void promotionGallaryDesctest() {
        Response resp = given()
                .header("Accept", "/")
                .queryParam("catalogue",false)
                .when().log().all()
                .get("Categories/6327/Details.json");
        log.info(resp.getBody().asString());
        resp.then().assertThat().statusCode(200);
        int i = ((ArrayList<String>) (resp.getBody().jsonPath().get("Promotions.Name"))).indexOf("Gallery");
        String desc = resp.getBody().jsonPath().get("Promotions[" + i + "].Description").toString();
        assertThat(desc,containsString("2x larger image"));
    }
    @Test
    @Story("Verify canRelist returned true")
    void canRelisttest() {
        Response resp = given()
                .header("Accept", "/")
                .queryParam("catalogue",false)
                .when().log().all()
                .get("Categories/6327/Details.json");
        log.info(resp.getBody().asString());
        resp.then().assertThat().statusCode(200);
        assertEquals(resp.getBody().jsonPath().get("CanRelist").toString(),"true");
    }

}



