import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.OrderClient;
import org.example.generators.OrderGenerator;
import org.example.models.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private final Order order;
    private int track;


    @Before
    public void set() {
        orderClient = new OrderClient();
    }

    @After
    public void clean() {
        orderClient.cancelOrder(track);
    }

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {OrderGenerator.getWithBlackOnly()},
                {OrderGenerator.getWithGrayOnly()},
                {OrderGenerator.getWithBlackAndGray()},
                {OrderGenerator.getWithoutColor()}
        };
    }

    @Test
    @DisplayName("При успешном создании заказа создаётся трек")
    public void orderCanBeCreatedWithTrack() {
        ValidatableResponse response = orderClient.createOrder(order);
        track = response.extract().path("track");
        int actualTrack = response.extract().path("track");
        Assert.assertEquals("Order isn`t created", track, actualTrack);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Wrong status create", SC_CREATED, statusCode);
    }
}
