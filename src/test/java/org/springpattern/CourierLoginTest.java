package org.springpattern;

import api.client.AuthClient;
import api.courier.Courier;
import api.courier.CreatingCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;

public class CourierLoginTest extends BaseTest {

    @Test
    @Description("Успешный логин курьера с созданием учетной записи")
    @DisplayName("Успешный логин курьера")
    public void successfulLoginTest() {
        String login = "ninjaa33";
        String password = "1234";
        String firstName = "Saske";

        CreatingCourier courier = new CreatingCourier();
        Response createCourier = courier.createCourier(new Courier(login, password, firstName));
        createCourier.then().statusCode(201).body("ok", equalTo(true));

        AuthClient authClient = new AuthClient();
        Response idAndLogIn = authClient.getIdAndLogIn(new Courier(login, password));
        retrievedCourierId = idAndLogIn.then().statusCode(200).body("id", notNullValue()).extract().path("id");
    }

    @Test
    @Description("Попытка авторизации без указания логина")
    @DisplayName("Авторизация без логина")
    public void loggingWithoutLogin() {
        AuthClient authClient = new AuthClient();
        //Response loggingInWithoutLogin = authClient.getErrorLoggingInWithoutLogin("1234");
        Response loggingInWithoutLogin = authClient.getErrorResponse("", "1234");
        loggingInWithoutLogin.then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @Description("Попытка авторизации без указания пароля")
    @DisplayName("Авторизация без пароля")
    public void loginWithoutPassword() {
        AuthClient authClient = new AuthClient();
        // Response logWithoutPassword = authClient.getErrorResponseWithoutPassword("ninja32");
        Response logWithoutPassword = authClient.getErrorResponse("ninja32", "");
        logWithoutPassword.then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Попытка авторизации с несуществующими учетными данными (логин/пароль)")
    @DisplayName("Авторизация с несуществующими данными")
    public void loginWithNonExistentCredentialsTest() {
        AuthClient authClient = new AuthClient();
        Response responseForNonExistentCredentials = authClient.getErrorResponseForNonExistentCredentials(new Courier("wrongUser", "wrongPass"));
        responseForNonExistentCredentials.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }
}
