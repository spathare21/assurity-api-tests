package com.assurity.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


@Feature("Categories Details")
class FirstTest {

    @BeforeEach
    void setup(){
        RestAssured.baseURI ="https://api.tmsandbox.co.nz/v1/";
    }

    @Test
    @Story("Verify API Schema")
    void testAPISchema() {
        given()
                .header("Accept", "/")
                .when().log().all()
                .get("Categories/6327/Details.json?catalogue=false")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("DetailsSchema.json"));
    }

    @Test
    @Story("Verify Name")
    void testAPIDetails() {
        Response resp = given()
                .header("Accept", "/")
                .when().log().all()
                .get("Categories/6327/Details.json?catalogue=false");

        System.out.println(resp.getBody().jsonPath().get("Name").toString());
        System.out.println(resp.getBody().jsonPath().get("CanRelist").toString());
        System.out.println(resp.getBody().jsonPath().get("Promotions").toString());
    }

}



