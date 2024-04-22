package org.ibs;

import io.restassured.response.Response;
import org.ibs.api.FoodPojo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.*;
import static org.hamcrest.Matchers.*;

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

    @Test
    void testFruit() {
        given()
                .cookie("JSESSIONID", "59466C32B723995232CDACED53EF1F02")
                .body("{\n" +
                        "  \"name\": \"Банан\",\n" +
                        "  \"type\": \"FRUIT\",\n" +
                        "  \"exotic\": true\n" +
                        "}")
                .when()
                .contentType(JSON)
                .log().all()
                .post(URL + "api/food")
                .then()
                .log().all()
                .statusCode(200);
        given()
                .cookie("JSESSIONID", "59466C32B723995232CDACED53EF1F02")
                .when()
                .contentType(JSON)
                .get(URL + "api/food")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("name", hasItem("Банан"));
        given()
                .cookie("JSESSIONID", "59466C32B723995232CDACED53EF1F02")
                .when()
                .contentType(JSON)
                .post(URL + "api/data/reset")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
        List<FoodPojo> foodList = given()
                .cookie("JSESSIONID", "59466C32B723995232CDACED53EF1F02")
                .when()
                .contentType(JSON)
                .log().all()
                .get(URL + "api/food")
                .then()
                .log().all()
                .extract()
                .body()
                .jsonPath()
                .getList("$", FoodPojo.class);

    }

    @Test
    void requestTest() {
        List<FoodPojo> foodList = given()
                .cookie("JSESSIONID", "59466C32B723995232CDACED53EF1F02")
                .when()
                .contentType(JSON)
                .log().all()
                .get(URL + "api/food")
                .then()
                .log().all()
                .extract()
                .body()
                .jsonPath()
                .getList("$", FoodPojo.class);
    }

}