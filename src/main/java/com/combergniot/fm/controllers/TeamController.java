package com.combergniot.fm.controllers;

import com.combergniot.fm.model.Team;
import com.combergniot.fm.services.MapValidationErrorService;
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

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @GetMapping("/all")
    public Iterable<Team> getAll() {
        Iterable<Team> teams = teamService.getAll();
        return teams;
    }

    @GetMapping("/id={id)")
    public Optional<Team> getTeamById(@PathVariable Long id){
        Optional<Team> team = teamService.findById(id);
        return team;
    }

    @GetMapping("/{teamIdentifier}")
    public Team getTeamByTeamIdentifier(@PathVariable String teamIdentifier) {
        Team team = teamService.findByTeamIdentifier(teamIdentifier);
        return team;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewTeam(@Valid @RequestBody Team team, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = mapValidationErrorService.validationService(bindingResult);
        if(errorMap !=null) return errorMap;
        Team team1 = teamService.saveOrUpdate(team);
        return new ResponseEntity<Team>(team, HttpStatus.CREATED);
    }

//    Removes the team and its players.
//    Consider archival data storage - maybe the better option is change the status of the team (active/inactive)?
    @DeleteMapping("/{teamIdentifier}")
    public ResponseEntity<?> deleteTeam(@PathVariable String teamIdentifier) {
        teamService.deleteTeamByIdentifier(teamIdentifier.toUpperCase());
        return new ResponseEntity<String>( "Team with identifier '" + teamIdentifier + "'  was deleted", HttpStatus.OK);
    }

}
