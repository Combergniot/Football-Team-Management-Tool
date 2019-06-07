package com.combergniot.fm.controllers;

import com.combergniot.fm.model.Player;
import com.combergniot.fm.services.MapValidationErrorService;
import com.combergniot.fm.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPlayerToBacklog(@Valid @RequestBody Player player,
                                            BindingResult bindingResult, @PathVariable String backlog_id) {
        ResponseEntity<?> errorMap = mapValidationErrorService.validationService(bindingResult);
        if (errorMap != null) return errorMap;
        Player player1 = playerService.addPlayer(backlog_id, player);
        return new ResponseEntity<Player>(player1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<Player> getProjectBacklog(@PathVariable String backlog_id) {
        return playerService.findBacklogByTeamIdentifier(backlog_id);
    }

    @GetMapping("/all")
    public Iterable<Player> getAll() {
        Iterable<Player> players = playerService.getAll();
        return players;
    }

    @GetMapping("/name={name}")
    public Iterable<Player> getPlayerByName(@PathVariable String name) {
        Iterable<Player> players =
                playerService.findByName(name);
        return players;
    }



}
