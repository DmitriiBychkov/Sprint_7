import io.restassured.response.ValidatableResponse;
import org.example.clients.CourierClient;
import org.example.generators.CourierGenerator;
import org.example.models.Courier;
import org.example.models.Credentials;
import org.example.models.CredentialsWithoutLogin;
import org.example.models.CredentialsWithoutPassword;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class LoginCourierTest {
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
    public void courierCanBeLoginAndReturnId() {
        ValidatableResponse responseCreate = courierClient.createCourier(courier);
        ValidatableResponse responseLogin = courierClient.loginCourier(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        int statusCodeLogin = responseLogin.extract().statusCode();
        Assert.assertEquals("Wrong status login", SC_OK, statusCodeLogin);
    }

    @Test
    public void wrongLoginReturnError() {
        ValidatableResponse responseCreate = courierClient.createCourier(courierWithoutPassword);
        ValidatableResponse responseLogin = courierClient.loginCourier(Credentials.from(courier));
        String actualMessage = responseLogin.extract().path("message");
        Assert.assertEquals("Wrong message - wrong login", "Учетная запись не найдена", actualMessage);
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("Wrong status - wrong login", SC_NOT_FOUND, statusCode);
    }

    @Test
    public void loginWithoutEnterLogin() {
        ValidatableResponse responseCreate = courierClient.createCourier(courier);
        ValidatableResponse responseLogin = courierClient.loginCourierWithoutLogin(CredentialsWithoutLogin.from(courier));
        String actualMessage = responseLogin.extract().path("message");
        Assert.assertEquals("Wrong message without enter login", "Недостаточно данных для входа", actualMessage);
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("Wrong status without enter login", SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void loginWithoutEnterPassword() {
        ValidatableResponse responseCreate = courierClient.createCourier(courier);
        ValidatableResponse responseLogin = courierClient.loginCourierWithoutPassword(CredentialsWithoutPassword.from(courier));
        String actualMessage = responseLogin.extract().path("message");
        Assert.assertEquals("Wrong message without enter password", "Недостаточно данных для входа", actualMessage);
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("Wrong status without enter password", SC_BAD_REQUEST, statusCode);
    }
}