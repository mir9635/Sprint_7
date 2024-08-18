
package org.springpattern;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/api/v1/orders";

    @Parameterized.Parameter(0)
    public String[] colors;

    @Parameterized.Parameter(1)
    public int expectedStatusCode;

    @Parameterized.Parameters(name = "{index}: createOrderTest(colors={0}, expectedStatusCode={1})")
    public static Object[][] orderData() {
        return new Object[][]{
                {new String[]{"BLACK"}, 201},
                {new String[]{"GREY"}, 201},
                {new String[]{"BLACK", "GREY"}, 201},
                {new String[]{}, 201}
        };
    }

    @Test
    @Description("Создание заказа с различными цветами и проверка кода статуса.")
    @DisplayName("Создание заказа с различными цветами")
    public void createOrderTest() {
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
