import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;
import models.AuthorizationInfo;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Тестирование регистрации пользователей.
 */
public class UserRegisterTest {
    private final UsersApiClient usersApiClient = new UsersApiClient();

    @After
    public void afterTest() {
        usersApiClient.deleteCreatedUsers();
    }

    @Test
    @DisplayName("It should be possible to create a unique user.")
    public void shouldRegisterUniqueUserTest() {
        usersApiClient
                .register(User.getRandomUser())
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should not be possible to create a user that already exists.")
    public void shouldNotRegisterExistedUserTest() {
        User user = User.getRandomUser();

        boolean isUserRegistered =
                usersApiClient
                        .register(user)
                        .then().statusCode(HttpStatus.SC_OK)
                        .and().extract().body().path("success");

        if (!isUserRegistered){
            Assert.fail("Failed to create user for verification.");
            return;
        }

        usersApiClient
                .register(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("It should not be possible to create a user without filling in a required field.")
    public void shouldNotRegisterUserWithoutNecessaryFieldsTest() {
        User user = User.getRandomUser();

        user.setEmail(null);

        usersApiClient
                .register(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().assertThat().body("success", equalTo(false));
    }


    @Test
    @DisplayName("It should be possible to get information about the user.")
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
