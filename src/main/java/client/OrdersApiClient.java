package client;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import models.AuthorizationInfo;
import models.OrderParameters;

/**
 * API для работы с заказами.
 */
public class OrdersApiClient extends BaseHttpClient {
    /**
     * Создает заказ.
     * @param orderParameters Параметры заказа.
     * @param authorizationInfo Информация об авторизации.
     */
    public Response makeOrder(OrderParameters orderParameters, AuthorizationInfo authorizationInfo) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .auth().oauth2(authorizationInfo.getAccessToken())
                .body(orderParameters.toJson())
                .post("orders");
    }

    /**
     * Создает заказ без выполнения авторизации.
     * @param orderParameters Параметры заказа.
     */
    public Response makeOrderWithoutAuth(OrderParameters orderParameters) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .body(orderParameters.toJson())
                .post("orders");
    }

    /**
     * Получает список заказов.
     * @param authorizationInfo Информация об авторизации.
     */
    public Response getOrders(AuthorizationInfo authorizationInfo) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .auth().oauth2(authorizationInfo.getAccessToken())
                .get("orders");
    }
}
