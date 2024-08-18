package org.springpattern;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_LOG_IN = "/api/v1/courier/login";
    private static final String COURIER_CREATION_ENDPOINT = "/api/v1/courier";


    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Description("Успешный логин курьера с созданием учетной записи")
    @DisplayName("Успешный логин курьера")
    public void successfulLoginTest() {
        String login = "ninjaa3";
        String password = "1234";
        String firstName = "Saske";


        createCourier(login, password, firstName);

        // Авторизация курьера и получение ID
        Integer retrievedCourierId = loginCourier(login, password);

        // Удаление курьера
        deleteCourier(retrievedCourierId);
    }

    @Test
    @Description("Попытка авторизации без указания логина")
    @DisplayName("Авторизация без логина")
    public void loggingWithoutLogin() {
        String jsonBody = "{\"login\": \"\", \"password\": \"1234\"}";

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(COURIER_LOG_IN)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Попытка авторизации без указания пароля")
    @DisplayName("Авторизация без пароля")
    public void loginWithoutPassword() {
        String jsonBody = "{\"login\": \"ninja11\", \"password\": \"\"}";

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(COURIER_LOG_IN)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Попытка авторизации с несуществующими учетными данными (логин/пароль)")
    @DisplayName("Авторизация с несуществующими данными")
    public void loginWithNonExistentCredentialsTest() {
        String jsonBody = "{\"login\": \"wrongUser\", \"password\": \"wrongPass\"}";

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(COURIER_LOG_IN)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    private void createCourier(String login, String password, String firstName) {
        String requestBody = String.format("{\"login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}", login, password, firstName);

         given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(COURIER_CREATION_ENDPOINT)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    private Integer loginCourier(String login, String password) {
        String body = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(COURIER_LOG_IN)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .path("id");
    }

    private void deleteCourier(Integer id) {
        if (id != null) {
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete(COURIER_CREATION_ENDPOINT + "/" + id)
                    .then()
                    .statusCode(200)
                    .body("ok", equalTo(true));
        }
    }
}
