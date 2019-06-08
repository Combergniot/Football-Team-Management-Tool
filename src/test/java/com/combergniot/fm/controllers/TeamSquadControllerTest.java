package com.combergniot.fm.controllers;

import com.combergniot.fm.Main;
import com.combergniot.fm.model.Player;
import com.combergniot.fm.model.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamSquadControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Before
    public void initialize() {
        Team team = new Team();
        team.setName("Manchester United");
        team.setTeamIdentifier("MAN");

        restTemplate.postForEntity(getRootUrl() + "/team/", team, Team.class);
        Player player = new Player();
        player.setName("Pogba");
        player.setPlayerNumber(7);
        restTemplate.postForEntity(getRootUrl() + "/squad/MAN", player, Player.class);

        Player player2 = new Player();
        player2.setName("Zbyszko z Bogdanca");
        player2.setPlayerNumber(2);
        restTemplate.postForEntity(getRootUrl() + "/squad/MAN", player2, Player.class);
    }

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAddPlayerToSquad(){
        Player player = new Player();
        player.setName("Janko Muzykant");
        player.setPlayerNumber(11);

        ResponseEntity<Player> postResponse = restTemplate.postForEntity(getRootUrl() + "/squad/MAN", player, Player.class);
        assertNotNull(postResponse.getBody());
        Assert.assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        System.out.println(postResponse.getStatusCode() +": "+ postResponse.getBody().toString());
    }

    @Test
    public void testGetTeamSquad(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/squad/MAN",
                HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        assertNotNull(response);
    }
}