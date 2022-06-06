import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Тестирование авторизации.
 */
public class LoginTest {
    private final UsersApiClient usersApiClient = new UsersApiClient();

    @After
    public void afterTest() {
        usersApiClient.deleteCreatedUsers();
    }

    @Test
    @DisplayName("It should be possible to log in as an existing user.")
    public void shouldLoginTest() {
        User user = User.getRandomUser();

        boolean isUserRegistered =
                usersApiClient
                        .register(user)
                        .then().statusCode(HttpStatus.SC_OK)
                        .and().extract().body().path("success");

        if (!isUserRegistered) {
            Assert.fail("Failed to create user for verification.");
            return;
        }

        usersApiClient
                .login(user)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should not be possible to log in with an incorrect username and password.")
    public void shouldNotLoginWithWrongPasswordTest() {
        User user = User.getRandomUser();

        boolean isUserRegistered =
                usersApiClient
                        .register(user)
                        .then().statusCode(HttpStatus.SC_OK)
                        .and().extract().body().path("success");

        if (!isUserRegistered) {
            Assert.fail("Failed to create user for verification.");
            return;
        }

        user.setPassword(User.getRandomPassword());

        usersApiClient
                .login(user)
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false));
    }
}
