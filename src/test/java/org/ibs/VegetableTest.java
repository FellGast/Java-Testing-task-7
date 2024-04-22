package org.ibs;

import org.ibs.api.FoodPojo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItem;

public class VegetableTest {
    private final static String URL = "http://localhost:8080/";

    @Test
    void testFruit() {
        given()
                .cookie("JSESSIONID", "59466C32B723995232CDACED53EF1F02")
                .body("{\n" +
                        "  \"name\": \"Картофель\",\n" +
                        "  \"type\": \"VEGETABLE\",\n" +
                        "  \"exotic\": false\n" +
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
                .body("name", hasItem("Картофель"));
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
}
