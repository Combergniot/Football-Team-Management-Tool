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
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
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

        Team team2 = new Team();
        team2.setName("Juventus");
        team2.setTeamIdentifier("JUV");
        restTemplate.postForEntity(getRootUrl() + "/team/", team2, Team.class);

        Player player = new Player();
        player.setName("Pogba");
        player.setPlayerNumber(7);
        restTemplate.postForEntity(getRootUrl() + "/squad/MAN", player, Player.class);

        Player player2 = new Player();
        player2.setName("Zbyszko z Bogdanca");
        player2.setPlayerNumber(2);
        restTemplate.postForEntity(getRootUrl() + "/squad/MAN", player2, Player.class);

        Player player3 = new Player();
        player3.setName("Ronaldo");
        player3.setPlayerNumber(10);
        restTemplate.postForEntity(getRootUrl() + "/squad/JUV", player3, Player.class);
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
        System.out.println(response.getBody() + ": " + response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    public void testGetPlayer(){
        Player player = restTemplate.getForObject(getRootUrl() + "/squad/MAN/2", Player.class);
        Assert.assertEquals("Zbyszko z Bogdanca", player.getName());
        System.out.println(player.toString());
    }

    @Test
    public void testGetPlayerByName(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/squad/name=Zbyszko z Bogdanca",
                HttpMethod.GET, entity, String.class);
        Assert.assertNotNull(response);
        System.out.println(response.getBody() + ": " + response.getStatusCode());
    }

    @Test
    public void testGetAllPlayers(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/squad/all",
                HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody() + ": " + response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    public void testUpdatePlayerData(){
        Player player = restTemplate.getForObject(getRootUrl() + "/squad/MAN/1", Player.class);
        assertNotNull(player);
        System.out.println("Before update: " + player.toString());
        player.setName("Rivaldo");
        player.setPlayerNumber(8);
        restTemplate.patchForObject(getRootUrl() + "/squad/MAN/1",  player, Player.class);
        Player updatedPlayer = restTemplate.getForObject(getRootUrl() + "/squad/MAN/1", Player.class);
        Assert.assertEquals("Rivaldo", updatedPlayer.getName());
        Assert.assertEquals(8, updatedPlayer.getPlayerNumber());
        System.out.println("After update: " + updatedPlayer.toString());
    }

    @Test
    public void testDeletePlayer(){
        String teamIdentifier = "JUV/";
        Long playerId = 3L;

        Player player = restTemplate.getForObject(getRootUrl() + "/squad/" + teamIdentifier + playerId, Player.class);
        assertNotNull(player);
        System.out.println("Before delete: " + player.toString());
        restTemplate.delete(getRootUrl() + "/squad/" + teamIdentifier + playerId);

        try {
            player = restTemplate.getForObject(getRootUrl() + "/squad/" + teamIdentifier + playerId, Player.class);
            System.out.println("After delete: " + player.toString());
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}