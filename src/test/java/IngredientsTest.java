import client.IngredientsApiClient;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Test;

/**
 * Тестирование ингредиентов.
 */
public class IngredientsTest {
    private final IngredientsApiClient ingredientsApiClient = new IngredientsApiClient();

    @Test
    @DisplayName("It should be possible to get a list of ingredients.")
    public void shouldGetIngredientsTest(){
        ingredientsApiClient
                .getIngredients()
                .then().assertThat().statusCode(HttpStatus.SC_OK);
    }
}
