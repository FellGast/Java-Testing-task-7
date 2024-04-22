package org.ibs;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

public class RestTest {
    private final static String URL = "http://localhost:8080/";
    @Test
    void test2() {
        given()
                .when()
                .contentType(JSON)
                .log().all()
                .get(URL + "api/food")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("[0].name", equalTo("Апельсин"))
                .body("[1].name", equalTo("Капуста"))
                .body("[2].name", equalTo("Помидор"))
                .body("[3].name", equalTo("Яблоко"))
        ;


    }
}
