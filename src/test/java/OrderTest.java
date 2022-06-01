import static org.hamcrest.core.IsEqual.equalTo;

import client.BaseHttpClient;
import client.IngredientsApiClient;
import client.OrdersApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import java.util.ArrayList;
import java.util.List;
import models.Ingredient;
import models.OrderParameters;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    private final OrdersApiClient ordersApiClient = new OrdersApiClient();
    private final IngredientsApiClient ingredientsApiClient = new IngredientsApiClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseHttpClient.API_HOST;
    }

    private List<Ingredient> getIngredients() {
        return ingredientsApiClient
            .getIngredients().then().extract().body().jsonPath()
            .getList("data", Ingredient.class);
    }

    @Test
    @DisplayName("Должна быть возможность авторизоваться под существующим пользователем.")
    public void shouldLoginTest() {
        List<Ingredient> ingredients = getIngredients();

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredients.get(0).getId());
        orderIngredients.add(ingredients.get(1).getId());
        orderIngredients.add(ingredients.get(2).getId());

        ordersApiClient
            .makeOrder(new OrderParameters(orderIngredients)).then().assertThat()
            .statusCode(HttpStatus.SC_OK).and().assertThat().body("success", equalTo(true));
    }
}
