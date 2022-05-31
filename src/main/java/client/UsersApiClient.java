package client;

import io.restassured.response.Response;
import models.AuthInfo;
import models.User;
import org.apache.http.HttpStatus;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class UsersApiClient extends BaseHttpClient {
    private final ArrayList<User> createdUsers = new ArrayList<>();

    public Response register(User user) {
        createdUsers.add(user);

        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .body(user.toJson())
                .post("auth/register");
    }

    public Response getUser(AuthInfo authInfo) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .auth().oauth2(authInfo.accessToken)
                .get("auth/user");
    }

    public Response patchUserInfo(AuthInfo authInfo) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .auth().oauth2(authInfo.accessToken)
                .patch("auth/user");
    }

    public void delete(AuthInfo authInfo) {
        given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .delete("auth/user");
    }

    public Response login(User user) {
        return given()
                .header("Content-type", HEADER_CONTENT_TYPE)
                .body(user.toJson())
                .post("auth/login");
    }

    public void deleteUser(User user) {
        Response response = login(user);
        if (response.statusCode() == HttpStatus.SC_OK) {
            AuthInfo authInfo = response.as(AuthInfo.class);
            delete(authInfo);
        }
    }

    public void deleteCreatedUsers() {
        createdUsers.forEach(this::deleteUser);
    }
}
