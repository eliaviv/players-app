/*
 * @author Eli Aviv
 */
package com.example.playersapp.service;

import com.example.playersapp.data.model.Player;
import com.example.playersapp.data.repository.PlayerRepository;
import com.example.playersapp.utils.CSVProcessor;
import com.example.playersapp.utils.TestUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Player Service Test")
@TestPropertySource(properties = { "players.server.initial_csv_path=player_small.csv" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @MockBean
    private PlayerRepository playerRepository;

    @Value("${players.server.initial_csv_path}")
    private String initialCsvPath;

    @Test
    @Order(1)
    @DisplayName("Test After Properties Set")
    void testAfterPropertiesSet() {
        // given
        final List<Player> players = CSVProcessor.parsePlayersCSV(initialCsvPath);

        // when (called automatically)
        // playerService.afterPropertiesSet();

        // then
        Mockito.verify(playerRepository).saveAll(players);
    }

    @Test
    @DisplayName("Test Service Get Players Successfully")
    void testServiceGetPlayersSuccessfully() {
        // given
        final List<Player> expectedPlayers = List.of(TestUtils.generateRandomPlayer(), TestUtils.generateRandomPlayer());

        // mock
        Mockito.when(playerRepository.findAll()).thenReturn(expectedPlayers);

        // when
        final List<Player> players = playerService.getPlayers();

        // then
        assertThat(players).isEqualTo(expectedPlayers);
    }

    @Test
    @DisplayName("Test Service Get Player Successfully")
    void testServiceGetPlayerSuccessfully() {
        // given
        final Player expectedPlayer = TestUtils.generateRandomPlayer();

        // mock
        Mockito.when(playerRepository.findById(expectedPlayer.getPlayerId())).thenReturn(Optional.of(expectedPlayer));

        // when
        final Player player = playerService.getPlayer(expectedPlayer.getPlayerId());

        // then
        assertThat(player).isEqualTo(expectedPlayer);
    }

    @Test
    @DisplayName("Test Service Get All Players DB Problems")
    void testServiceGetAllPlayersDBProblems() {
        // mock
        Mockito.when(playerRepository.findAll()).thenThrow(new RuntimeException("Error"));

        // when + then
        assertThrows(RuntimeException.class, () -> playerService.getPlayers());
    }

    @Test
    @DisplayName("Test Service Get Player DB Problems")
    void testServiceGetPlayerDBProblems() {
        // given
        final String playerId = "aaaaaaaaa";

        // mock
        Mockito.when(playerRepository.findById(playerId)).thenThrow(new RuntimeException("Error"));

        // when + then
        assertThrows(RuntimeException.class, () -> playerService.getPlayer(playerId));
    }
}