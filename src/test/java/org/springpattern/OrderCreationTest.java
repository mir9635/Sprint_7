
package org.springpattern;

import api.orders.Order;
import api.orders.OrdersClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderCreationTest {

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
        OrdersClient ordersClient = new OrdersClient();
        Response response = ordersClient.getCreateOrder(new Order("Иван", "Иванов", "Москва, 142", "4", "+7 800 355 35 35", 5, "2020-06-06", "Текст", colors));

        assertEquals(expectedStatusCode, response.getStatusCode());

        if (expectedStatusCode == 201) {
            response.then().body("track", notNullValue());
        }
    }
}
