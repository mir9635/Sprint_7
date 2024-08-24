package api.orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_LIST = "/api/v1/orders";

    @Step("Создание заказа")
    public Response getCreateOrder(Order order) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(order)
                .when()
                .post(ORDER_LIST);
    }

    @Step("Получение ответа что заказов не меньше, чем 1")
    public Response getListOrder(String link) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .when()
                .get(ORDER_LIST + link);
    }

    @Step("Получение ошибки по несуществующему ID")
    public Response getErrorIdNotExist(String id) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .when()
                .get(ORDER_LIST + id);
    }
}
