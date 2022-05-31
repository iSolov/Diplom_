package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsApiClient extends BaseHttpClient {
    public Response getIngredients(){
        return given()
            .header("Content-type", HEADER_CONTENT_TYPE)
            .get("ingredients");
    }
}
