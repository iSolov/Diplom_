package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API для работы с ингредиентами.
 */
public class IngredientsApiClient extends BaseHttpClient {
    /**
     * Получает ингредиенты.
     */
    public Response getIngredients() {
        return given()
                .spec(getRequestSpecification())
                .when()
                .get("ingredients");
    }
}
