import client.BaseHttpClient;
import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import models.AuthorizationInfo;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Тестирование регистрации пользователей.
 */
public class UserRegisterTest {
    private final UsersApiClient usersApiClient = new UsersApiClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseHttpClient.API_HOST;
    }

    @After
    public void afterTest() {
        usersApiClient.deleteCreatedUsers();
    }


    @Test
    @DisplayName("Должна быть возможность создать уникального пользователя.")
    public void shouldRegisterUniqueUserTest() {
        usersApiClient
                .register(User.getRandomUser())
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Не должно быть возможности создать пользователя, который уже существует.")
    public void shouldNotRegisterExistedUserTest() {
        User user = User.getRandomUser();

        boolean isUserRegistered =
                usersApiClient
                        .register(user)
                        .then().statusCode(HttpStatus.SC_OK)
                        .and().extract().body().path("success");

        if (!isUserRegistered){
            Assert.fail("Не удалось создать пользователя для проверки.");
            return;
        }

        usersApiClient
                .register(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Не должно быть возможности создать пользователя без заполнения обязательного поля.")
    public void shouldNotRegisterUserWithoutNecessaryFieldsTest() {
        User user = User.getRandomUser();

        user.setEmail(null);

        usersApiClient
                .register(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().assertThat().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Должна быть возможность получить информацию о пользователе.")
    public void shouldGetUserTest() {
        User user = User.getRandomUser();

        AuthorizationInfo authorizationInfo =
            usersApiClient
                .register(user)
                .as(AuthorizationInfo.class);

        usersApiClient
            .getUser(authorizationInfo)
            .then().assertThat().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("success", equalTo(true));
    }
}
