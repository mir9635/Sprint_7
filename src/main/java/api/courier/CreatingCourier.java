package api.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CreatingCourier {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String CREATING_COURIER = "/api/v1/courier";

    private static Set<String> usedLogins = new HashSet<>();
    private static Random random = new Random();

    @Step("Генерация уникального логина")
    public String generateUniqueLogin() {
        String login;
        do {
            login = "user" + random.nextInt(10000);
        } while (usedLogins.contains(login));
        usedLogins.add(login);
        return login;
    }

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(courier)
                .when()
                .post(CREATING_COURIER);
    }

    @Step("Получение ответа при создании курьера без логина")
    public Response getErrorDuringCourierCreationWithoutLogin(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(courier)
                .when()
                .post(CREATING_COURIER);
    }

    @Step("Получение ответа при создании курьера без пароля")
    public Response getErrorDuringCourierCreationWithoutPassword(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(courier)
                .when()
                .post(CREATING_COURIER);
    }

    @Step("Получение ответа при создании курьера без данных")
    public Response getErrorDuringCourierCreationWithoutData() {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .when()
                .post(CREATING_COURIER);
    }
}
