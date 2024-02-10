/*
 * @author Eli Aviv
 */
package com.example.playersapp.utils;

import com.example.playersapp.data.model.Player;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVProcessor {

    // TODO: Infinite time - Consider chunk reading and multi-threaded parsing
    // TODO: Infinite time - Could become a component and generalize for various purposes with callbacks

    public static List<Player> parsePlayersCSV(String playersFilePath) {
        final ArrayList<Player> players = new ArrayList<>();

        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(playersFilePath)) {
            final var csvReader = new CSVReader(new InputStreamReader(in));
            String[] columns = csvReader.readNext();

            Player.verifyNames(columns);

            String[] row;
            while ((row = csvReader.readNext()) != null) {
                players.add(new Player(row));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return players;
    }
}