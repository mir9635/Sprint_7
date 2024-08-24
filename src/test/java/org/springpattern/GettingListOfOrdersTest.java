package org.springpattern;

import api.orders.Order;
import api.orders.OrdersClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import org.junit.Test;

public class GettingListOfOrdersTest extends BaseTest {

    @Test
    @Description("Создание нового заказа и проверка успешного ответа с трек-номером")
    @DisplayName("Создание заказа и получение трек-номера")
    public void createOrderTest() {

        OrdersClient ordersClient = new OrdersClient();
        Response createOrder = ordersClient.getCreateOrder(new Order("Иван", "Иванов", "Москва, 142", "4", "+7 800 355 35 35", 5, "2020-06-06", "Текст", new String[]{"BLACK"}));
        createOrder.then().statusCode(201).body("track", notNullValue());

    }

    @Test
    @Description("Получение списка заказов с проверкой, что заказов не меньше, чем 1")
    @DisplayName("Получение списка заказов и проверка наличия хотя бы одного заказа")
    public void getOrdersTest() {
        OrdersClient ordersClient = new OrdersClient();
        Response listOrder = ordersClient.getListOrder("?limit=10&page=0");
        listOrder.then().statusCode(200).body("orders", not(emptyArray()));
    }

    @Test
    @Description("Получение заказа по несуществующему ID и проверка статуса 404")
    @DisplayName("Получение заказа по несуществующему ID (404 ошибка)")
    public void getOrderByNonExistentIdTest() {
        OrdersClient ordersClient = new OrdersClient();
        Response errorIdNotExist = ordersClient.getErrorIdNotExist("/9999");
        errorIdNotExist.then().statusCode(404);
    }


}
