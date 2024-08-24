package api.client;

import api.courier.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_LOG_IN = "/api/v1/courier/login";

    @Step("Авторизация курьера")
    public Response getIdAndLogIn(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(courier)
                .when()
                .post(COURIER_LOG_IN);
    }

//    @Step("Получение ответа для некорректного логина")
//    public Response getErrorLoggingInWithoutLogin(String password) {
//        String jsonBody = String.format("{\"login\": \"\", \"password\": \"%s\"}", password);
//        return given()
//                .baseUri(BASE_URL)
//                .contentType("application/json")
//                .body(jsonBody)
//                .when()
//                .post(COURIER_LOG_IN);
//    }
//
//    @Step("Получение ответа для некорректного пароля")
//    public Response getErrorResponseWithoutPassword(String login) {
//        String jsonBody = String.format("{\"login\": \"%s\", \"password\": \"\"}", login);
//        return given()
//                .baseUri(BASE_URL)
//                .contentType("application/json")
//                .body(jsonBody)
//                .when()
//                .post(COURIER_LOG_IN);
//    }

    @Step("Получение ответа для некорректного логина или пароля")
    public Response getErrorResponse(String login, String password) {
        // Проверка на пустые параметры
        if ((login == null || login.isEmpty()) && (password == null || password.isEmpty())) {
            throw new IllegalArgumentException("Логин и пароль не могут быть одновременно пустыми.");
        } else if (login != null && !login.isEmpty() && password != null && !password.isEmpty()) {
            throw new IllegalArgumentException("Логин и пароль не должны передаваться одновременно.");
        }

        String jsonBody = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password);

        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(COURIER_LOG_IN);
    }


    @Step("Получение ответа для несуществующих учетных данных")
    public Response getErrorResponseForNonExistentCredentials(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(courier)
                .when()
                .post(COURIER_LOG_IN);
    }
}
