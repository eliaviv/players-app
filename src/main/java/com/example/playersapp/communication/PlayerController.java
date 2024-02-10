/*
 * @author Eli Aviv
 */
package com.example.playersapp.communication;

import com.example.playersapp.communication.responses.GetAllPlayersResponse;
import com.example.playersapp.communication.responses.GetPlayerResponse;
import com.example.playersapp.communication.responses.RestResponse;
import com.example.playersapp.communication.responses.Result;
import com.example.playersapp.components.Logger;
import com.example.playersapp.data.model.Player;
import com.example.playersapp.service.PlayerService;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class PlayerController {

    private final PlayerService playerService;
    private final Logger logger;

    @Autowired
    public PlayerController(PlayerService playerService, Logger logger) {
        this.playerService = playerService;
        this.logger = logger;
    }

    @GetMapping("/players")
    public ResponseEntity<? extends RestResponse> getPlayers() {
        logger.log("Received get players request");

        final List<Player> allPlayers;
        try {
            allPlayers = playerService.getPlayers();
        }
        catch (Exception e) {
            final var result = new Result("ERROR", "Failed to get all players. Error: " + e.getMessage());
            return new ResponseEntity<>(new RestResponse(result) ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final var result = new Result("OK", "Get all players finished successfully");

        final var getAllPlayersResponse = new GetAllPlayersResponse();
        getAllPlayersResponse.setResult(result);
        getAllPlayersResponse.setPlayers(allPlayers);

        return new ResponseEntity<>(getAllPlayersResponse ,HttpStatus.OK);
    }

    // TODO: Infinite time - Add more validations

    @GetMapping("/players/{playerId}")
    @Validated
    public ResponseEntity<? extends RestResponse> getPlayer(@PathVariable @Size(min = 1, max = 9) String playerId) {
        logger.log("Received get player request of: " + playerId);

        final Player player;
        try {
            player = playerService.getPlayer(playerId);
        }
        catch (Exception e) {
            final var result = new Result("ERROR", "Failed to get player. Error: " + e.getMessage());
            return new ResponseEntity<>(new RestResponse(result) ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final var result = new Result("OK", "Get player finished successfully");

        final GetPlayerResponse getPlayerResponse = new GetPlayerResponse();
        getPlayerResponse.setResult(result);
        getPlayerResponse.setPlayer(player);

        return new ResponseEntity<>(getPlayerResponse ,HttpStatus.OK);
    }
}