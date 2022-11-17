package org.example.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String CREATE_ORDER_PATH = "/api/v1/orders";
    private static final String CANCEL_PATH = "/api/v1/orders/cancel?track=";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
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
    public ValidatableResponse checkOrderList() {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .when()
                .get(CREATE_ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .when()
                .put(CANCEL_PATH + track)
                .then()
                .log().all(); // логируем респонс
    }

//    @Step("Проверка трека")
//    public ValidatableResponse checkTrackInList(int track) {
//        return given()
//                .spec(getSpec())
//                .log().all()
//                .when()
//                .get(CREATE_ORDER_PATH)
//                .then().assertThat().body("orders.id", equalTo(track))
//                .log().all();
//    }
}
