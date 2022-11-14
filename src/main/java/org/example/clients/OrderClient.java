package org.example.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.models.Order;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderClient extends Client {
    private static final String CREATE_ORDER_PATH = "/api/v1/orders";
    private static final String CANCEL_PATH = "/api/v1/orders/cancel?track=";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Получение списка заказов")
    public ValidatableResponse check() {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .when()
                .get(CREATE_ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancel(int track) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .when()
                .put(CANCEL_PATH + track)
                .then()
                .log().all(); // логируем респонс
    }

    @Test
    public ValidatableResponse checkTrackInList(int track) {
        return given()
                .spec(getSpec())
                .log().all()
                .when()
                .get(CREATE_ORDER_PATH)
                .then().assertThat().body("orders.id", equalTo(track))
                .log().all();
    }
}
