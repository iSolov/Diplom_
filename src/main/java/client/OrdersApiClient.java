package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.AuthorizationInfo;
import models.OrderParameters;

import static io.restassured.RestAssured.given;

/**
 * API для работы с заказами.
 */
public class OrdersApiClient extends BaseHttpClient {
    /**
     * Создает заказ.
     * @param orderParameters Параметры заказа.
     * @param authorizationInfo Информация об авторизации.
     */
    @Step("Create an order.")
    public Response makeOrder(OrderParameters orderParameters, AuthorizationInfo authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .body(orderParameters.toJson())
                .when()
                .post("orders");
    }

    /**
     * Создает заказ без выполнения авторизации.
     * @param orderParameters Параметры заказа.
     */
    @Step("Creating an order without authorization.")
    public Response makeOrderWithoutAuth(OrderParameters orderParameters) {
        return given()
                .spec(getRequestSpecification())
                .body(orderParameters.toJson())
                .when()
                .post("orders");
    }

    /**
     * Получает список заказов.
     * @param authorizationInfo Информация об авторизации.
     */
    @Step("Receiving user orders.")
    public Response getOrders(AuthorizationInfo authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .when()
                .get("orders");
    }
}
