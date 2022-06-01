import static org.hamcrest.core.IsEqual.equalTo;

import client.BaseHttpClient;
import client.IngredientsApiClient;
import client.OrdersApiClient;
import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import java.util.ArrayList;
import java.util.List;

import models.AuthInfo;
import models.Ingredient;
import models.OrderParameters;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    private final OrdersApiClient ordersApiClient = new OrdersApiClient();
    private final IngredientsApiClient ingredientsApiClient = new IngredientsApiClient();
    private final UsersApiClient usersApiClient = new UsersApiClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseHttpClient.API_HOST;
    }

    @After
    public void afterTest() {
        usersApiClient.deleteCreatedUsers();
    }

    private List<Ingredient> getIngredients() {
        return ingredientsApiClient
            .getIngredients().then().extract().body().jsonPath()
            .getList("data", Ingredient.class);
    }

    private AuthInfo getRandomUserAuthInfo() {
        return usersApiClient
                .register(User.getRandomUser())
                .as(AuthInfo.class);
    }

    @Test
    @DisplayName("Должна быть возможность создать заказ для пользователя с авторизацией.")
    public void shouldMakeOrderTest() {
        List<Ingredient> ingredients = getIngredients();

        if (ingredients.size() == 0) {
            Assert.fail("Отсутствуют ингредиенты для возможности создать заказ.");
        }

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredients.get(0).getId());

        AuthInfo authInfo = getRandomUserAuthInfo();

        ordersApiClient
                .makeOrder(new OrderParameters(orderIngredients), authInfo)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Должна быть возможность создать заказ без авторизации.")
    public void shouldMakeOrderWithoutAuthTest() {
        List<Ingredient> ingredients = getIngredients();

        if (ingredients.size() == 0) {
            Assert.fail("Отсутствуют ингредиенты для возможности создать заказ.");
        }

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredients.get(0).getId());

        ordersApiClient
                .makeOrderWithoutAuth(new OrderParameters(orderIngredients))
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Должна быть ошибка при заказе без ингредиентов.")
    public void shouldGetErrorWhenMakeOrderWithoutIngredientsTest() {
        AuthInfo authInfo = getRandomUserAuthInfo();

        ordersApiClient
                .makeOrder(new OrderParameters(new ArrayList<>()), authInfo)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Должна быть ошибка при заказе без ингредиентов.")
    public void shouldGetErrorWhenMakeOrderWithWrongIngredientTest() {
        AuthInfo authInfo = getRandomUserAuthInfo();

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add("61c0c5a71d1f82001_______");

        ordersApiClient
                .makeOrder(new OrderParameters(orderIngredients), authInfo)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Должна быть возможность получения списка заказов пользователей с авторизацией.")
    public void shouldGetOrdersByUserWithAuthTest() {
        List<Ingredient> ingredients = getIngredients();

        if (ingredients.size() == 0) {
            Assert.fail("Отсутствуют ингредиенты для возможности создать заказ.");
        }

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredients.get(0).getId());

        AuthInfo authInfo = getRandomUserAuthInfo();

        ordersApiClient.makeOrder(new OrderParameters(orderIngredients), authInfo);

        ordersApiClient
                .getOrders(authInfo)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }
}
