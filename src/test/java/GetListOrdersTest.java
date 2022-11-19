import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.OrderClient;
import org.example.generators.OrderGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;

public class GetListOrdersTest {

    private OrderClient orderClient;
    private int track;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void clean() {
        orderClient.cancelOrder(track);
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void GetOrders() {
        ValidatableResponse response = orderClient.createOrder(OrderGenerator.getWithBlackAndGray());
        track = response.extract().path("track");
        ValidatableResponse responseList = orderClient.checkOrderList();
        ArrayList<String> trackInList = responseList.extract().path("orders.id");
        Assert.assertNotNull("Order list is not be null", trackInList);
        int statusCode = responseList.extract().statusCode();
        Assert.assertEquals("Wrong status create", SC_OK, statusCode);
    }
}