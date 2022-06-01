package client;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import models.AuthInfo;
import models.OrderParameters;

public class OrdersApiClient extends BaseHttpClient {
    public Response makeOrder(OrderParameters orderParameters, AuthInfo authInfo) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .auth().oauth2(authInfo.getAccessToken())
                .body(orderParameters.toJson())
                .post("orders");
    }

    public Response makeOrderWithoutAuth(OrderParameters orderParameters) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .body(orderParameters.toJson())
                .post("orders");
    }
}
