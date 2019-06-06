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
        Player player1 = playerService.save(backlog_id, player);
        return new ResponseEntity<Player>(player1, HttpStatus.CREATED);
    }

}
