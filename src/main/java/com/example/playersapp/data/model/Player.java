/*
 * @author Eli Aviv
 */
package com.example.playersapp.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Entity
@Data
public class Player {

    // TODO: Infinite time - Add validations on fields

    @Id @Column(name="playerID")
    private String playerId;

    @Column(name="birthYear")
    private Integer birthYear;

    @Column(name="birthMonth")
    private Integer birthMonth;

    @Column(name="birthDay")
    private Integer birthDay;

    @Column(name="birthCountry")
    private String birthCountry;

    @Column(name="birthState")
    private String birthState;

    @Column(name="birthCity")
    private String birthCity;

    @Column(name="deathYear")
    private Integer deathYear;

    @Column(name="deathMonth")
    private Integer deathMonth;

    @Column(name="deathDay")
    private Integer deathDay;

    @Column(name="deathCountry")
    private String deathCountry;

    @Column(name="deathState")
    private String deathState;

    @Column(name="deathCity")
    private String deathCity;

    @Column(name="nameFirst")
    private String nameFirst;

    @Column(name="nameLast")
    private String nameLast;

    @Column(name="nameGiven")
    private String nameGiven;

    @Column(name="weight")
    private Integer weight;

    @Column(name="height")
    private Integer height;

    @Column(name="bats")
    private Character bats;

    @Column(name="throws")
    private Character throwsValue;

    @Column(name="debut")
    private LocalDate debut;

    @Column(name="finalGame")
    private LocalDate finalGame;

    @Column(name="retroID")
    private String retroId;

    @Column(name="bbrefID")
    private String bbrefId;

    private static Map<String, String> SUPPORTED_DATE_FORMATS = Map.of(
            "([0-9]{4})-([0-9]{2})-([0-9]{2})", "yyyy-MM-dd",
            "([0-9]{2})/([0-9]{2})/([0-9]{4})", "dd/MM/yyyy"
    );

    public Player() {
    }

    public Player(String[] attributes) {
        this.playerId = parseString(attributes[0]);
        this.birthYear = parseInt(attributes[1]);
        this.birthMonth = parseInt(attributes[2]);
        this.birthDay = parseInt(attributes[3]);
        this.birthCountry = parseString(attributes[4]);
        this.birthState = parseString(attributes[5]);
        this.birthCity = parseString(attributes[6]);
        this.deathYear = parseInt(attributes[7]);
        this.deathMonth = parseInt(attributes[8]);
        this.deathDay = parseInt(attributes[9]);
        this.deathCountry = parseString(attributes[10]);
        this.deathState = parseString(attributes[11]);
        this.deathCity = parseString(attributes[12]);
        this.nameFirst = parseString(attributes[13]);
        this.nameLast = parseString(attributes[14]);
        this.nameGiven = parseString(attributes[15]);
        this.weight = parseInt(attributes[16]);
        this.height = parseInt(attributes[17]);
        this.bats = parseCharacter(attributes[18]);
        this.throwsValue = parseCharacter(attributes[19]);
        this.debut = parseDate(attributes[20]);
        this.finalGame = parseDate(attributes[21]);
        this.retroId = parseString(attributes[22]);
        this.bbrefId = parseString(attributes[23]);
    }

    private String parseString(String string) {
        return StringUtils.isBlank(string) ? null : string;
    }

    private Integer parseInt(String stringInt) {
        if (StringUtils.isBlank(stringInt)) {
            return null;
        }

        try {
            return Integer.parseInt(stringInt);
        }
        catch (Exception e) {
            String errorMessage = "Failed to parse number: " + stringInt + ". Error: " + e.getMessage();
            throw new RuntimeException(errorMessage);
        }
    }

    private Character parseCharacter(String stringChar) {
        if (StringUtils.isBlank(stringChar)) {
            return null;
        }

        if (stringChar.length() > 1) {
            String errorMessage = "Failed to parse character: " + stringChar + ". Error: Character size should be 1";
            throw new RuntimeException(errorMessage);
        }

        return stringChar.charAt(0);
    }

    private LocalDate parseDate(String stringDate) {
        if (StringUtils.isBlank(stringDate)) {
            return null;
        }

        for (Map.Entry<String, String> supportedDateFormat : SUPPORTED_DATE_FORMATS.entrySet()) {
            if (stringDate.matches(supportedDateFormat.getKey())) {
                return  LocalDate.parse(stringDate, DateTimeFormatter.ofPattern(supportedDateFormat.getValue()));
            }
        }

        throw new RuntimeException("Time format is not supported: " + stringDate);
    }

    public static void verifyNames(String[] columnNames) {
        final Field[] classFields = Player.class.getDeclaredFields();

        for (int i = 0; i < columnNames.length; i++) {
            final var columnAnnotation = classFields[i].getAnnotation(Column.class);
            if (columnAnnotation == null) {
                String errorMessage = "Field without @Column annotation is not allowed.";
                throw new RuntimeException(errorMessage);
            }

            if (!columnAnnotation.name().equals(columnNames[i])) {
                String errorMessage = "Column name provided " + columnNames[i] + " is not matching the " +
                        "expected column name " + columnAnnotation.name() + ".";
                throw new RuntimeException(errorMessage);
            }
        }
    }
}