package org.example.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.models.Courier;
import org.example.models.Credentials;
import org.example.models.CredentialsWithoutLogin;
import org.example.models.CredentialsWithoutPassword;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String CREATE_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";
    private static final String DELETE_PATH = "/api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(courier)
                .when()
                .post(CREATE_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Логин курьера")
    public ValidatableResponse loginCourier(Credentials credentials) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(credentials)
                .when()
                .post(LOGIN_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Логин курьера без логина")
    public ValidatableResponse loginCourierWithoutLogin(CredentialsWithoutLogin credentialsWithoutLogin) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(credentialsWithoutLogin)
                .when()
                .post(LOGIN_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Логин курьера без пароля")
    public ValidatableResponse loginCourierWithoutPassword(CredentialsWithoutPassword credentialsWithoutPassword) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(credentialsWithoutPassword)
                .when()
                .post(LOGIN_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .when()
                .delete(DELETE_PATH + id)
                .then()
                .log().all(); // логируем респонс
    }
}
