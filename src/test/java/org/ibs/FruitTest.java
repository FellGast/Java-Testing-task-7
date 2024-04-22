package org.ibs;

import io.restassured.RestAssured;
import org.ibs.api.FoodPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.sessionId;
import static io.restassured.http.ContentType.*;
import static org.hamcrest.Matchers.*;

public class FruitTest {

    @BeforeEach

    public void setUp() {
        updateSessionId();
        // Базовый URL вашего API
        RestAssured.baseURI = "http://localhost:8080/";
    }
    public void updateSessionId() {
        // Получаем идентификатор сессии из переменной среды
        sessionId = System.getenv("SESSION_ID");
        if (sessionId == null) {
            // В случае, если переменная среды не установлена, выводим ошибку и завершаем выполнение
            System.err.println("Ошибка: Переменная среды SESSION_ID не установлена.");
            System.exit(1);
        }
        // Базовый URL вашего API
        RestAssured.baseURI = "http://localhost:8080/";
    }
    @Test
    void testFruit() {
        updateSessionId();
        given()
                .cookie("JSESSIONID", sessionId)
                .body("{\n" +
                        "  \"name\": \"Банан\",\n" +
                        "  \"type\": \"FRUIT\",\n" +
                        "  \"exotic\": true\n" +
                        "}")
                .when()
                .contentType(JSON)
                .log().all()
                .post("api/food")
                .then()
                .log().all()
                .statusCode(200);
        updateSessionId();
        given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .contentType(JSON)
                .get("api/food")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("name", hasItem("Банан"));
        updateSessionId();
        given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .contentType(JSON)
                .post("api/data/reset")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
        List<FoodPojo> foodList = given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .contentType(JSON)
                .log().all()
                .get("api/food")
                .then()
                .log().all()
                .extract()
                .body()
                .jsonPath()
                .getList("$", FoodPojo.class);

    }

}