package com.combergniot.fm.controllers;

import com.combergniot.fm.Main;
import com.combergniot.fm.model.Team;
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
public class TeamControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    //    Todo = use BeforeClass or BeforeAll adnotation
    @Before
    public void initialize() {
        Team team = new Team();
        team.setName("Manchester United");
        team.setTeamIdentifier("MAN");
        restTemplate.postForEntity(getRootUrl() + "/team/", team, Team.class);

        Team team1 = new Team();
        team1.setName("FC Porto");
        team1.setTeamIdentifier("FCP");
        restTemplate.postForEntity(getRootUrl() + "/team/", team1, Team.class);

        Team team2 = new Team();
        team2.setName("Conkordia Knurów");
        team2.setTeamIdentifier("KS Concordia");
        restTemplate.postForEntity(getRootUrl() + "/team/", team2, Team.class);
    }

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testCreateNewTeam() {
        Team team = new Team();
        team.setName("Manchester City");
        team.setTeamIdentifier("MC");
        ResponseEntity<Team> postResponse = restTemplate.postForEntity(getRootUrl() + "/team/", team, Team.class);
        System.out.println(postResponse.getStatusCode() +": " + postResponse.getBody().toString());
        assertEquals("Manchester City", postResponse.getBody().getName());
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    }

    @Test
    public void testGetTeamByTeamIdentifier() {
        Team team = restTemplate.getForObject(getRootUrl() + "/team/MAN", Team.class);
        System.out.println(team.toString());
        assertEquals("Manchester United", team.getName());
    }

    @Test
    public void testGetAll() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/team/all",
                HttpMethod.GET, entity, String.class);
        System.out.println(response.getStatusCode() +": " + response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteTeam() {
        String teamIdentifier = "KS CONCORDIA";
        Team team = restTemplate.getForObject(getRootUrl() + "/team/" + teamIdentifier, Team.class);
        assertNotNull(team);
        System.out.println("Before delete: " + team.getTeamIdentifier());
        restTemplate.delete(getRootUrl() + "/team/" + teamIdentifier);

        try {
            team = restTemplate.getForObject(getRootUrl() + "/team/" + teamIdentifier, Team.class);
            System.out.println("After delete: " + team.getTeamIdentifier());
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }

    }

//    TODO testUpdateTeam

}