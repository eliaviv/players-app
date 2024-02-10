/*
 * @author Eli Aviv
 */
package com.example.playersapp.utils;

import com.example.playersapp.data.model.Player;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtils {

    private static final Random random = new Random();

    public static Player generateRandomPlayer() {
        final Player player = new Player();
        player.setPlayerId(generateRandomString(9));
        player.setBirthYear(generateRandomYear());
        player.setBirthMonth(generateRandomMonth());
        player.setBirthDay(generateRandomDay());
        player.setBirthCountry(generateRandomString(9));
        player.setBirthState(generateRandomString(9));
        player.setBirthCity(generateRandomString(9));
        player.setDeathYear(generateRandomYear());
        player.setDeathMonth(generateRandomMonth());
        player.setDeathDay(generateRandomDay());
        player.setDeathCountry(generateRandomString(9));
        player.setDeathState(generateRandomString(9));
        player.setDeathCity(generateRandomString(9));
        player.setNameFirst(generateRandomString(9));
        player.setNameLast(generateRandomString(9));
        player.setNameGiven(generateRandomString(9));
        player.setWeight(generateRandomWeight());
        player.setHeight(generateRandomHeight());
        player.setBats(generateRandomChar());
        player.setThrowsValue(generateRandomChar());
        player.setDebut(generateRandomDate());
        player.setRetroId(generateRandomString(9));
        player.setBbrefId(generateRandomString(9));

        return player;
    }

    private static String generateRandomString(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }

    private static int generateRandomYear() {
        return randBetween(1900, 2024);
    }

    private static int generateRandomMonth() {
        return randBetween(1, 12);
    }

    private static int generateRandomDay() {
        return randBetween(1, 31);
    }

    private static int generateRandomWeight() {
        return randBetween(1, 1000);
    }

    private static int generateRandomHeight() {
        return randBetween(1, 1000);
    }

    private static char generateRandomChar() {
        return (char)(random.nextInt(26) + 'a');
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private static LocalDate generateRandomDate() {
        int hundredYears = 100 * 365;
        return LocalDate.ofEpochDay(ThreadLocalRandom
                .current().nextInt(-hundredYears, hundredYears));
    }
}