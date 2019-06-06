package com.combergniot.fm.controllers;

import com.combergniot.fm.model.Team;
import com.combergniot.fm.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/team")
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamService teamService;


    @GetMapping("/all")
    public Iterable<Team> getAll() {
        Iterable<Team> teams = teamService.getAll();
        return teams;
    }

    @GetMapping("/{teamIdentifier}")
    public Optional<Team> getTeamByTeamIdentifier(@PathVariable String teamIdentifier) {
        Optional<Team> team = teamService.findByTeamIdentifier(teamIdentifier);
        return team;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewTeam(@Valid @RequestBody Team team, BindingResult bindingResult){
        Team team1 = teamService.save(team);
        return new ResponseEntity<Team>(team1, HttpStatus.CREATED);
    }


}
