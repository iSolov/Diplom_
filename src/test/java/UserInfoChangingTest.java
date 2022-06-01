import static org.hamcrest.core.IsEqual.equalTo;

import client.BaseHttpClient;
import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import models.AuthorizationInfo;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Тестирование изменения информации о пользователе.
 */
public class UserInfoChangingTest {
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
    @DisplayName("Должна быть возможность изменить Email пользователя с авторизацией.")
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
    @DisplayName("Должна быть возможность изменить имя пользователя с авторизацией.")
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
    @DisplayName("Должна быть возможность изменить пароль пользователя с авторизацией.")
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
    @DisplayName("Не должно быть возможности изменить Email пользователя без авторизации.")
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
    @DisplayName("Не должно быть возможности изменить имя пользователя без авторизации.")
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
    @DisplayName("Не должно быть возможности изменить пароль пользователя без авторизации.")
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