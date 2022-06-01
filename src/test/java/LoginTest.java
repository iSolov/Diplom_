import client.BaseHttpClient;
import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import models.AuthInfo;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class LoginTest {
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
    @DisplayName("Должна быть возможность авторизоваться под существующим пользователем.")
    public void shouldLoginTest() {
        User user = User.getRandomUser();

        boolean isUserRegistered =
                usersApiClient
                        .register(user)
                        .then().statusCode(HttpStatus.SC_OK)
                        .and().extract().body().path("success");

        if (!isUserRegistered) {
            Assert.fail("Не удалось создать пользователя для проверки.");
            return;
        }

        usersApiClient
                .login(user)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Не должно быть возможности авторизоваться с неверным логином и паролем.")
    public void shouldNotLoginWithWrongPasswordTest() {
        User user = User.getRandomUser();

        boolean isUserRegistered =
                usersApiClient
                        .register(user)
                        .then().statusCode(HttpStatus.SC_OK)
                        .and().extract().body().path("success");

        if (!isUserRegistered) {
            Assert.fail("Не удалось создать пользователя для проверки.");
            return;
        }

        user.setPassword(User.getRandomPassword());

        usersApiClient
                .login(user)
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false));
    }
}
