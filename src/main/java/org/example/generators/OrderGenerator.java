package org.example.generators;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.models.Order;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OrderGenerator {
    public static String firstName = RandomStringUtils.randomAlphabetic(4);
    public static String lastName = RandomStringUtils.randomAlphabetic(5);
    public static String newAddress = RandomStringUtils.randomAlphabetic(6) + ", " + new Random().nextInt(999) + "apt.";
    public static int metroStation = new Random().nextInt(100);
    public static String phone = "+7 " + RandomStringUtils.randomNumeric(10, 12);
    public static int rentTime = new Random().nextInt(24);
    public static String deliveryDate = new Random().nextInt(2025) + "-" + new Random().nextInt(12) + "-" + new Random().nextInt(31);
    public static String comment = RandomStringUtils.randomAlphabetic(30);

    public static Order getWithBlackOnly() {
        return new Order(firstName, lastName, newAddress, metroStation, phone, rentTime, deliveryDate, comment, Collections.singletonList("BLACK"));
    }

    public static Order getWithGrayOnly() {
        return new Order(firstName, lastName, newAddress, metroStation, phone, rentTime, deliveryDate, comment, Collections.singletonList("GRAY"));
    }

    public static Order getWithBlackAndGray() {
        return new Order(firstName, lastName, newAddress, metroStation, phone, rentTime, deliveryDate, comment, List.of("BLACK", "GREY"));
    }

    public static Order getWithoutColor() {
        return new Order(firstName, lastName, newAddress, metroStation, phone, rentTime, deliveryDate, comment, null);
    }
}
