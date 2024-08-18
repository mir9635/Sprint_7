package org.springpattern;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCreationTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/api/v1/orders";



    private static Object[][] orderData() {
        return new Object[][]{
                {new String[]{"BLACK"}, 201},
                {new String[]{"GREY"}, 201},
                {new String[]{"BLACK", "GREY"}, 201},
                {new String[]{}, 201}
        };
    }

    @ParameterizedTest
    @MethodSource("orderData")
    @Description("Создание заказа с различными цветами и проверка кода статуса.")
    @DisplayName("Создание заказа с различными цветами")
    public void createOrderTest(String[] colors, int expectedStatusCode) {
        String orderJson = String.format(
                "{ \"firstName\": \"Иван\", " +
                        "\"lastName\": \"Иванов\", " +
                        "\"address\": \"Москва, 142\", " +
                        "\"metroStation\": 4, " +
                        "\"phone\": \"+7 800 355 35 35\", " +
                        "\"rentTime\": 5, " +
                        "\"deliveryDate\": \"2020-06-06\", " +
                        "\"comment\": \"Текст\", " +
                        "\"color\": %s }",
                colors.length > 0 ? String.format("[\"%s\"]", String.join("\", \"", colors)) : "[]"
        );

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderJson)
                .when()
                .post(BASE_URL);


        assertEquals(expectedStatusCode, response.getStatusCode());


        if (expectedStatusCode == 201) {
            response.then().body("track", notNullValue());
        }
    }
}
