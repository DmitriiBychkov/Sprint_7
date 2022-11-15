package org.example.generators;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.models.Courier;

import java.sql.Timestamp;
import java.util.Random;

public class CourierGenerator {
    public static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    public static String login = RandomStringUtils.randomAlphabetic(5);
    public static String password = RandomStringUtils.randomAlphabetic(6);
    public static String firstName = RandomStringUtils.randomAlphabetic(7);

    public static Courier getRandomCourier() {
        return new Courier(login + Long.toString(timestamp.getTime()), password + new Random().nextInt(100), firstName + new Random().nextInt(10));
    }

    public static Courier getWithoutLogin() {
        return new Courier("", password + new Random().nextInt(100), firstName + new Random().nextInt(10));
    }

    public static Courier getWithoutPassword() {
        return new Courier(login + Long.toString(timestamp.getTime()), "", firstName + new Random().nextInt(10));
    }
}
