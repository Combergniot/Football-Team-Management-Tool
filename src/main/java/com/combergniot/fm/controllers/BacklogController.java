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

    @GetMapping("/{backlog_id}/{player_id}")
    public ResponseEntity<?> getPlayer(@PathVariable String backlog_id, @PathVariable Long player_id) {
        Player player = playerService.findPlayerByItsId(backlog_id, player_id);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

//    PathMapping, not Post or Put, requestBody required player_id
    @PatchMapping("/{backlog_id}/{player_id}")
    public ResponseEntity<?> updatePlayerData(@Valid @RequestBody Player player, BindingResult bindingResult,
                                                @PathVariable String backlog_id, @PathVariable Long player_id) {
        ResponseEntity<?> errorMap = mapValidationErrorService.validationService(bindingResult);
        if (errorMap != null) return errorMap;

        Player updatedPlayer = playerService.updatePlayerByItsId(player, backlog_id, player_id);
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{player_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable Long player_id){
        playerService.deletePlayerByItsId(backlog_id, player_id);
        return new ResponseEntity<>("Player with ID '" + player_id + "' was successfully deleted", HttpStatus.OK);
    }





}
