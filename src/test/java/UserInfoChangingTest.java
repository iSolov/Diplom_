import static org.hamcrest.core.IsEqual.equalTo;

import client.BaseHttpClient;
import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import models.AuthInfo;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

        AuthInfo authInfo =
            usersApiClient
                .register(user)
                .as(AuthInfo.class);

        user.setEmail(User.getRandomEmail());

        authInfo.setUser(user);

        usersApiClient
            .patchAuthUserInfo(authInfo)
            .then().assertThat().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Должна быть возможность изменить имя пользователя с авторизацией.")
    public void shouldPatchAuthUsersNameTest() {
        User user = User.getRandomUser();

        AuthInfo authInfo =
            usersApiClient
                .register(user)
                .as(AuthInfo.class);

        user.setName(User.getRandomName());

        authInfo.setUser(user);

        usersApiClient
            .patchAuthUserInfo(authInfo)
            .then().assertThat().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Не должно быть возможности изменить Email пользователя без авторизации.")
    public void shouldNotPatchUsersEmailWithoutAuthTest() {
        User user = User.getRandomUser();

        usersApiClient
            .register(user)
            .as(AuthInfo.class);

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
            .as(AuthInfo.class);

        user.setName(User.getRandomName());

        usersApiClient
            .patchNotAuthUserInfo(user)
            .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
            .and().assertThat().body("success", equalTo(false));
    }
}
