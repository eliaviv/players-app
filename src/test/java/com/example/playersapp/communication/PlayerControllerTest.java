/*
 * @author Eli Aviv
 */
package com.example.playersapp.communication;

import com.example.playersapp.communication.responses.GetAllPlayersResponse;
import com.example.playersapp.communication.responses.GetPlayerResponse;
import com.example.playersapp.communication.responses.RestResponse;
import com.example.playersapp.communication.responses.Result;
import com.example.playersapp.data.model.Player;
import com.example.playersapp.service.PlayerService;
import com.example.playersapp.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Player Controller Test")
public class PlayerControllerTest {

    @Autowired
    private PlayerController playerController;

    @MockBean
    private PlayerService playerService;

    @Test
    @DisplayName("Test Controller Get Players Successfully")
    void testGetControllerPlayersSuccessfully() {
        // given
        final List<Player> players = List.of(TestUtils.generateRandomPlayer());

        // mock
        Mockito.when(playerService.getPlayers()).thenReturn(players);

        // expected
        final String expectedStatus = "OK";
        final String expectedDescription = "Get all players finished successfully";
        final var expectedGetAllPlayersResponse = new GetAllPlayersResponse();
        expectedGetAllPlayersResponse.setPlayers(players);
        expectedGetAllPlayersResponse.setResult(new Result(expectedStatus, expectedDescription));

        // when
        final var responseEntity = playerController.getPlayers();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedGetAllPlayersResponse);
    }

    @Test
    @DisplayName("Test Controller Get Player Successfully")
    void testControllerGetPlayerSuccessfully() {
        // given
        final Player player = TestUtils.generateRandomPlayer();

        // mock
        Mockito.when(playerService.getPlayer(player.getPlayerId())).thenReturn(player);

        // expected
        final String expectedStatus = "OK";
        final String expectedDescription = "Get player finished successfully";
        final var expectedGetPlayerResponse = new GetPlayerResponse();
        expectedGetPlayerResponse.setPlayer(player);
        expectedGetPlayerResponse.setResult(new Result(expectedStatus, expectedDescription));

        // when
        final var responseEntity = playerController.getPlayer(player.getPlayerId());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedGetPlayerResponse);
    }

    @Test
    @DisplayName("Test Controller Get Player Not Found")
    void testControllerGetPlayerNotFound() {
        // given
        final String playerId = "aaaaaaaaa";

        // mock
        Mockito.when(playerService.getPlayer(playerId)).thenThrow(new RuntimeException("Not Found"));

        // expected
        final String expectedStatus = "ERROR";
        final String expectedDescription = "Failed to get player. Error: Not Found";
        final var expectedRestResponse = new RestResponse();
        expectedRestResponse.setResult(new Result(expectedStatus, expectedDescription));

        // when
        final var responseEntity = playerController.getPlayer(playerId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isEqualTo(expectedRestResponse);
    }
}