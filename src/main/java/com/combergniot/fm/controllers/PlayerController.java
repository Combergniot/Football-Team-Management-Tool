package com.combergniot.fm.controllers;

import com.combergniot.fm.model.Player;
import com.combergniot.fm.model.Team;
import com.combergniot.fm.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/all")
    public Iterable<Player> getAll() {
        Iterable<Player> players = playerService.getAll();
        return players;
    }

    @GetMapping("/name={name}")
    public Optional<Player> getPlayerByName(@PathVariable String name) {
        Optional<Player> player =
                playerService.findByName(name);
        return player;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewPlayer(@Valid @RequestBody Player player, BindingResult bindingResult){
        Player player1 = playerService.save(player);
        return new ResponseEntity<Player>(player1, HttpStatus.CREATED);
    }
}
