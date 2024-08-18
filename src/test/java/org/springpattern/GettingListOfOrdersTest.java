package org.springpattern;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class GettingListOfOrdersTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_LIST = "/api/v1/orders";

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Description("Создание нового заказа и проверка успешного ответа с трек-номером")
    @DisplayName("Создание заказа и получение трек-номера")
    public void createOrderTest() {
        given()
                .contentType(ContentType.JSON)
                .body(
                        "{ \"firstName\": \"Иван\", " +
                                "\"lastName\": \"Иванов\", " +
                                "\"address\": \"Москва, 142\", " +
                                "\"metroStation\": \"4\", " +
                                "\"phone\": \"+7 800 355 35 35\", " +
                                "\"rentTime\": 5, " +
                                "\"deliveryDate\": \"2020-06-06\", " +
                                "\"comment\": \"Текст\", " +
                                "\"color\": [\"BLACK\"] }"
                )
                .when()
                .post(ORDER_LIST)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @Description("Получение списка заказов с проверкой, что заказов не меньше, чем 1")
    @DisplayName("Получение списка заказов и проверка наличия хотя бы одного заказа")
    public void getOrdersTest() {  given()
            .contentType(ContentType.JSON)
            .when()
            .get(ORDER_LIST+"?limit=10&page=0")
            .then()
            .statusCode(200)
            .body("orders", not(emptyArray()));
    }

    @Test
    @Description("Получение заказа по несуществующему ID и проверка статуса 404")
    @DisplayName("Получение заказа по несуществующему ID (404 ошибка)")
    public void getOrderByNonExistentIdTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(ORDER_LIST+"/9999")
                .then()
                .statusCode(404);
    }
}
