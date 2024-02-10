/*
 * @author Eli Aviv
 */
package com.example.playersapp.service;

import com.example.playersapp.components.Logger;
import com.example.playersapp.data.model.Player;
import com.example.playersapp.data.repository.PlayerRepository;
import com.example.playersapp.utils.CSVProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService implements InitializingBean {

    @Value("${players.server.initial_csv_path}")
    private String initialCsvPath;

    private final PlayerRepository playerRepository;
    private final Logger logger;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, Logger logger) {
        this.playerRepository = playerRepository;
        this.logger = logger;
    }

    // TODO: Infinite time - Add valuable logs

    @Override
    @Transactional
    public void afterPropertiesSet() {
        final List<Player> players;
        try {
            players = CSVProcessor.parsePlayersCSV(initialCsvPath);
        }
        catch (Exception e) {
            String errorMessage = "Failed to parse players csv. Error: " + e.getMessage();
            logger.log(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        // TODO: Infinite time - Implement batch saving to increase performance

        try {
            playerRepository.saveAll(players);
        }
        catch (Exception e) {
            String errorMessage = "Failed to persist all players to DB. Error: " + e.getMessage();
            logger.log(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    public List<Player> getPlayers() {
        final List<Player> allPlayers;
        try {
            allPlayers = playerRepository.findAll();
        }
        catch (Exception e) {
            String errorMessage = "Failed to get all players from DB. Error: " + e.getMessage();
            logger.log(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        // TODO: Infinite time - Enormous amount could use pagination

        return allPlayers;
    }

    public Player getPlayer(String playerId) {
        final Player player;
        try {
            player = playerRepository.findById(playerId).orElseThrow();
        }
        catch (Exception e) {
            String errorMessage = "Failed to get player " + playerId + " from DB. Error: " + e.getMessage();
            logger.log(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        return player;
    }
}