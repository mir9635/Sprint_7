package org.springpattern;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class BaseTest {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_CREATION_ENDPOINT = "/api/v1/courier";

    static Integer retrievedCourierId = null;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void cleanup() {
        if (retrievedCourierId != null) {
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete(COURIER_CREATION_ENDPOINT + "/" + retrievedCourierId)
                    .then()
                    .statusCode(200)
                    .body("ok", equalTo(true));
            retrievedCourierId = null;
        }
    }
}
