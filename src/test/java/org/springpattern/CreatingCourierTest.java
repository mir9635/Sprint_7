package org.springpattern;

import api.courier.Courier;
import api.courier.CreatingCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;


import static org.hamcrest.Matchers.*;


public class CreatingCourierTest extends BaseTest {

    @Test
    @Description("Проверка, что курьера можно создать")
    @DisplayName("Успешное создание курьера")
    public void testCreateCourier() {
        CreatingCourier create = new CreatingCourier();

        String login = create.generateUniqueLogin();
        String password = "1234";

        Response createCourier = create.createCourier(new Courier(login, password, "saske"));
        createCourier.then().statusCode(201).body("ok", equalTo(true));
    }

    @Test
    @Description("Проверка, что при отсутствии логина возвращается ошибка")
    @DisplayName("Ошибка при создании курьера без логина")
    public void testCreateCourierWithoutLogin() {
        String password = "1234";

        CreatingCourier create = new CreatingCourier();
        Response duringCourierCreationWithoutLogin = create.getErrorDuringCourierCreationWithoutLogin(new Courier("", password, "saske"));
        duringCourierCreationWithoutLogin.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Проверка, что при отсутствии пароля возвращается ошибка")
    @DisplayName("Ошибка при создании курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        CreatingCourier create = new CreatingCourier();

        String login = create.generateUniqueLogin();

        Response duringCourierCreationWithoutLogin = create.getErrorDuringCourierCreationWithoutPassword(new Courier(login, "", "saske"));
        duringCourierCreationWithoutLogin.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @Description("Проверка, что при попытке создания курьера с уже существующим логином возвращается ошибка")
    @DisplayName("Ошибка при создании курьера с дубликатом логина")
    public void testCreateCourierWithDuplicateLogin() {
        CreatingCourier create = new CreatingCourier();

        String login = create.generateUniqueLogin();
        String password = "1234";

        Response createFirstCourier = create.createCourier(new Courier(login, password, "saske"));
        createFirstCourier.then().statusCode(201).body("ok", equalTo(true));

        Response createSecondCourier = create.createCourier(new Courier(login, password, "saske"));
        createSecondCourier.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    @Description("Проверка, что при отсутствии всех данных возвращается ошибка")
    @DisplayName("Создание курьера без данных")
    public void testCreateCourierWithoutAnyData() {

        CreatingCourier create = new CreatingCourier();
        Response duringCourierCreationWithoutData = create.getErrorDuringCourierCreationWithoutData();
        duringCourierCreationWithoutData.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
