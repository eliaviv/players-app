package com.example.playersapp;

import com.example.playersapp.communication.responses.GetAllPlayersResponse;
import com.example.playersapp.communication.responses.GetPlayerResponse;
import com.example.playersapp.communication.responses.RestResponse;
import com.example.playersapp.data.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = { "players.server.initial_csv_path=player_small.csv" })
@DisplayName("Players App Application Tests")
class PlayersAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@BeforeAll
	void setUp() {
		objectMapper.findAndRegisterModules();
	}

	@Test
	@DisplayName("Test Get Players Full System Successfully")
	void testGetPlayersFullSystemSuccessfully() throws Exception {
		// when
		final var resultActions = mockMvc.perform(get("/api/players")
				.contentType("application/json")
				.characterEncoding("UTF-8"))
				.andReturn();

		// expected
		final int expectedSize = 99;
		final String expectedStatus = "OK";
		final String expectedDescription = "Get all players finished successfully";

		// then
		String content = resultActions.getResponse().getContentAsString();
		final var getAllPlayersResponse = objectMapper.readValue(content, GetAllPlayersResponse.class);

		assertThat(getAllPlayersResponse.getPlayers().size()).isEqualTo(expectedSize);
		assertThat(getAllPlayersResponse.getResult().getStatus()).isEqualTo(expectedStatus);
		assertThat(getAllPlayersResponse.getResult().getDescription()).isEqualTo(expectedDescription);
	}

	@Test
	@DisplayName("Test Get Player Full System Successfully")
	void testGetPlayerFullSystemSuccessfully() throws Exception {
		// when
		final var resultActions = mockMvc.perform(get("/api/players/aardsda01")
						.contentType("application/json")
						.characterEncoding("UTF-8"))
						.andReturn();

		// expected
		final String expectedStatus = "OK";
		final String expectedDescription = "Get player finished successfully";
		final String[] expectedAttributes = { "aardsda01" , "1981" , "12" , "27" , "USA" , "CO" ,"Denver" , "" ,
				"" , "" , "" , "" , "" , "David" , "Aardsma" , "David Allan" , "215" , "75" , "R" , "R" ,
				"2004-04-06" , "2015-08-23" , "aardd001" , "aardsda01" };
		final Player expectedPlayer = new Player(expectedAttributes);

		// then
		String content = resultActions.getResponse().getContentAsString();
		final var getPlayerResponse = objectMapper.readValue(content, GetPlayerResponse.class);

		assertThat(getPlayerResponse.getPlayer()).isEqualTo(expectedPlayer);
		assertThat(getPlayerResponse.getResult().getStatus()).isEqualTo(expectedStatus);
		assertThat(getPlayerResponse.getResult().getDescription()).isEqualTo(expectedDescription);
	}

	@Test
    @DisplayName("Test Get Player Size Not Valid")
    void testGetPlayerSizeNotValid() throws Exception {
        // when
        final var resultActions = mockMvc.perform(get("/api/players/aaaaaaaaaaaaaaaaaaa")
                        .contentType("application/json")
                        .characterEncoding("UTF-8"))
                .andReturn();

        // expected
        final String expectedStatus = "ERROR";
        final String expectedDescription = "getPlayer.playerId: size must be between 1 and 9";

        // then
        String content = resultActions.getResponse().getContentAsString();
        final var restResponse = objectMapper.readValue(content, RestResponse.class);

        assertThat(restResponse.getResult().getStatus()).isEqualTo(expectedStatus);
        assertThat(restResponse.getResult().getDescription()).isEqualTo(expectedDescription);
    }
}
