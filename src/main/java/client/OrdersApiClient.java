package client;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import models.OrderParameters;

public class OrdersApiClient extends BaseHttpClient {

    public Response makeOrder(OrderParameters orderParameters) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .body(orderParameters.toJson())
                .post("orders");
    }
}
