import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;
import models.AuthorizationInfo;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Тестирование изменения информации о пользователе.
 */
public class UserInfoChangingTest {
    private final UsersApiClient usersApiClient = new UsersApiClient();

    @After
    public void afterTest() {
        usersApiClient.deleteCreatedUsers();
    }

    @Test
    @DisplayName("It should be possible to change the user's Email with authorization.")
    public void shouldPatchAuthUsersEmailTest() {
        User user = User.getRandomUser();

        AuthorizationInfo authorizationInfo =
            usersApiClient
                .register(user)
                .as(AuthorizationInfo.class);

        user.setEmail(User.getRandomEmail());

        authorizationInfo.setUser(user);

        usersApiClient
            .patchAuthUserInfo(authorizationInfo)
            .then().assertThat().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should be possible to change the username with authorization.")
    public void shouldPatchAuthUsersNameTest() {
        User user = User.getRandomUser();

        AuthorizationInfo authorizationInfo =
            usersApiClient
                .register(user)
                .as(AuthorizationInfo.class);

        user.setName(User.getRandomName());

        authorizationInfo.setUser(user);

        usersApiClient
            .patchAuthUserInfo(authorizationInfo)
            .then().assertThat().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should be possible to change the user's password with authorization.")
    public void shouldPatchAuthUsersPasswordTest() {
        User user = User.getRandomUser();

        AuthorizationInfo authorizationInfo =
            usersApiClient
                .register(user)
                .as(AuthorizationInfo.class);

        user.setPassword(User.getRandomPassword());

        authorizationInfo.setUser(user);

        usersApiClient
            .patchAuthUserInfo(authorizationInfo)
            .then().assertThat().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should not be possible to change the user's Email without authorization.")
    public void shouldNotPatchUsersEmailWithoutAuthTest() {
        User user = User.getRandomUser();

        usersApiClient
            .register(user)
            .as(AuthorizationInfo.class);

        user.setEmail(User.getRandomEmail());

        usersApiClient
            .patchNotAuthUserInfo(user)
            .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
            .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("It should not be possible to change the username without authorization.")
    public void shouldNotPatchUsersNameWithoutAuthTest() {
        User user = User.getRandomUser();

        usersApiClient
            .register(user)
            .as(AuthorizationInfo.class);

        user.setName(User.getRandomName());

        usersApiClient
            .patchNotAuthUserInfo(user)
            .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
            .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("It should not be possible to change a user's password without authorization.")
    public void shouldNotPatchUsersPasswordWithoutAuthTest() {
        User user = User.getRandomUser();

        usersApiClient
            .register(user)
            .as(AuthorizationInfo.class);

        user.setPassword(User.getRandomPassword());

        usersApiClient
            .patchNotAuthUserInfo(user)
            .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
            .and().assertThat().body("success", equalTo(false));
    }
}