package client;

import io.restassured.response.Response;
import models.AuthorizationInfo;
import models.User;
import org.apache.http.HttpStatus;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

/**
 * API для работы с пользователями.
 */
public class UsersApiClient extends BaseHttpClient {
    /**
     * СОзданные пользователи для дальнейшей очистки.
     */
    private final ArrayList<User> createdUsers = new ArrayList<>();

    /**
     * Создание нового пользователя.
     */
    public Response register(User user) {
        createdUsers.add(user);

        return given()
                .spec(getRequestSpecification())
                .body(user.toJson())
                .when()
                .post("auth/register");
    }

    /**
     * Получение информации о пользователе..
     */
    public Response getUser(AuthorizationInfo authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .when()
                .get("auth/user");
    }

    /**
     * Внесение изменений в информацию о пользователе с использование авторизации.
     */
    public Response patchAuthUserInfo(AuthorizationInfo authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .body(authorizationInfo.getUser().toJson())
                .when()
                .patch("auth/user");
    }

    /**
     * Внесение изменений в информацию о пользователе без авторизации.
     */
    public Response patchNotAuthUserInfo(User user) {
        return given()
                .spec(getRequestSpecification())
                .body(user.toJson())
                .when()
                .patch("auth/user");
    }

    /**
     * Авторизация пользователя.
     */
    public Response login(User user) {
        return given()
                .spec(getRequestSpecification())
                .body(user.toJson())
                .when()
                .post("auth/login");
    }

    /**
     * Удаление пользователя.
     */
    public void deleteUser(User user) {
        Response response = login(user);
        if (response.statusCode() == HttpStatus.SC_OK) {
            AuthorizationInfo authorizationInfo = response.as(AuthorizationInfo.class);

            given()
                    .spec(getRequestSpecification())
                    .auth().oauth2(authorizationInfo.getAccessToken())
                    .when()
                    .delete("auth/user");
        }
    }

    /**
     * Удаляет всех созданных пользователей.
     */
    public void deleteCreatedUsers() {
        createdUsers.forEach(this::deleteUser);
    }
}
