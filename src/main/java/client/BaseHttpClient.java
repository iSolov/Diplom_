package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

/**
 * Базовый HTTP клиент.
 */
public class BaseHttpClient {

    /**
     * Тип заголовка содержимого запроса.
     */
    public final String HEADER_CONTENT_TYPE = "application/json";

    /**
     * Хост API.
     */
    public static final String API_URL = "https://stellarburgers.nomoreparties.site/api";

    /**
     * Настройка выполнения запросов.
     */
    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(API_URL)
                .build();
    }
}
