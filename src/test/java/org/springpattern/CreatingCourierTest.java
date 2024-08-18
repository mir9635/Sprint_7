package org.springpattern;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class CreatingCourierTest {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String CREATING_COURIER = "/api/v1/courier";

    private static Set<String> usedLogins = new HashSet<>();
    private static Random random = new Random();

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    private String generateUniqueLogin() {
        String login;
        do {
            login = "user" + random.nextInt(10000);
        } while (usedLogins.contains(login));
        usedLogins.add(login);
        return login;
    }

    @Test
    @Description("Проверка, что курьера можно создать")
    @DisplayName("Успешное создание курьера")
    public void testCreateCourier() {
        String login = generateUniqueLogin();
        String password = "1234";

        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"" + login + "\", \"password\": \"" + password + "\", \"firstName\": \"saske\" }")
                .when()
                .post(CREATING_COURIER)
                .then()
                .statusCode(anyOf(is(201)))
                .body("ok", equalTo(true));
    }

    @Test
    @Description("Проверка, что при отсутствии логина возвращается ошибка")
    @DisplayName("Ошибка при создании курьера без логина")
    public void testCreateCourierWithoutLogin() {
        String password = "1234";

        given()
                .contentType(ContentType.JSON)
                .body("{ \"password\": \"" + password + "\", \"firstName\": \"saske\" }")
                .when()
                .post(CREATING_COURIER)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Проверка, что при отсутствии пароля возвращается ошибка")
    @DisplayName("Ошибка при создании курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        String login = generateUniqueLogin();

        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"" + login + "\", \"firstName\": \"saske\" }")
                .when()
                .post(CREATING_COURIER)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Проверка, что при попытке создания курьера с уже существующим логином возвращается ошибка")
    @DisplayName("Ошибка при создании курьера с дубликатом логина")
    public void testCreateCourierWithDuplicateLogin() {
        String login = generateUniqueLogin();
        String password = "1234";

        // Сначала создаем курьера
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"" + login + "\", \"password\": \"" + password + "\", \"firstName\": \"saske\" }")
                .when()
                .post(CREATING_COURIER)
                .then()
                .statusCode(anyOf(is(201)));

        // Повторно пытаемся создать курьера с тем же логином
        given()
                .contentType(ContentType.JSON)

                .body("{ \"login\": \"" + login + "\", \"password\": \"" + password + "\", \"firstName\": \"saske\" }")
                .when()
                .post(CREATING_COURIER)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Description("Проверка, что при отсутствии всех данных возвращается ошибка")
    @DisplayName("Создание курьера без данных")
    public void testCreateCourierWithoutAnyData() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post(CREATING_COURIER)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
