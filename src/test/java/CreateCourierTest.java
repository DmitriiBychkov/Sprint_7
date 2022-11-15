import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.CourierClient;
import org.example.generators.CourierGenerator;
import org.example.models.Courier;
import org.example.models.Credentials;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class CreateCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private Courier courierWithoutLogin;
    private Courier courierWithoutPassword;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
        courierWithoutLogin = CourierGenerator.getWithoutLogin();
        courierWithoutPassword = CourierGenerator.getWithoutPassword();
    }

    @After
    public void cleanUp() {
        courierClient.deleteCourier(id);
    }

    @Test
    @DisplayName("Создание курьера возможно")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreate = courierClient.createCourier(courier);
        ValidatableResponse responseLogin = courierClient.loginCourier(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        boolean isCourierCreated = responseCreate.extract().path("ok");
        Assert.assertTrue("Courier isn`t created", isCourierCreated);
        int statusCode = responseCreate.extract().statusCode();
        Assert.assertEquals("Wrong status create", SC_CREATED, statusCode);
    }

    @Test
    @DisplayName("Создание дубля курьера невозможно")
    public void courierCanNotBeCreatedDouble() {
        ValidatableResponse responseCreate = courierClient.createCourier(courier);
        ValidatableResponse responseLogin = courierClient.loginCourier(Credentials.from(courier));
        ValidatableResponse responseCreateDouble = courierClient.createCourier(courier);
        id = responseLogin.extract().path("id");
        String actualMessage = responseCreateDouble.extract().path("message");
        Assert.assertEquals("Wrong message create double", "Этот логин уже используется. Попробуйте другой.", actualMessage);
        int statusCode = responseCreateDouble.extract().statusCode();
        Assert.assertEquals("Wrong status create double", SC_CONFLICT, statusCode);
    }

    @Test
    @DisplayName("Создание курьера без логина невозможно")
    public void courierCanNotBeCreatedWithoutLogin() {
        ValidatableResponse responseCreate = courierClient.createCourier(courierWithoutLogin);
        String actualMessage = responseCreate.extract().path("message");
        Assert.assertEquals("Wrong message created without login", "Недостаточно данных для создания учетной записи", actualMessage);
        int statusCode = responseCreate.extract().statusCode();
        Assert.assertEquals("Wrong status created without login", SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Создание курьера без пароля невозможно")
    public void courierCanNotBeCreatedWithoutPassword() {
        ValidatableResponse responseCreate = courierClient.createCourier(courierWithoutPassword);
        String actualMessage = responseCreate.extract().path("message");
        Assert.assertEquals("Wrong message created without password", "Недостаточно данных для создания учетной записи", actualMessage);
        int statusCode = responseCreate.extract().statusCode();
        Assert.assertEquals("Wrong status created without password", SC_BAD_REQUEST, statusCode);
    }
}