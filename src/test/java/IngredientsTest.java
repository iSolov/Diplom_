import client.BaseHttpClient;
import client.IngredientsApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

/**
 * Тестирование ингредиентов.
 */
public class IngredientsTest {
    private final IngredientsApiClient ingredientsApiClient = new IngredientsApiClient();

    @Test
    @DisplayName("Должна быть возможность получить список ингредиентов.")
    public void shouldGetIngredientsTest(){
        ingredientsApiClient
                .getIngredients()
                .then().assertThat().statusCode(HttpStatus.SC_OK);
    }
}
