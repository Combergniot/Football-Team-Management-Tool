package com.combergniot.fm.services;

import com.combergniot.fm.exceptions.TeamNotFoundException;
import com.combergniot.fm.model.Backlog;
import com.combergniot.fm.model.Player;
import com.combergniot.fm.model.Team;
import com.combergniot.fm.repositiories.BacklogRepository;
import com.combergniot.fm.repositiories.PlayerRepository;
import com.combergniot.fm.repositiories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private TeamRepository teamRepository;


    public Iterable<Player> getAll() {
        return playerRepository.findAll();
    }

    public Player addPlayer(String teamIdentifier, Player player) {
        try {
            Backlog backlog = backlogRepository.findByTeamIdentifier(teamIdentifier);
            player.setBacklog(backlog);
            player.setTeamIdentifier(teamIdentifier);
            return playerRepository.save(player);
        } catch (Exception e) {
            throw new TeamNotFoundException("Team not found or someone already use player number");
        }
    }

    public Iterable<Player> findBacklogByTeamIdentifier(String teamIdentifier) {
        Team team = teamRepository.findByTeamIdentifier(teamIdentifier);
        if (team == null) {
            throw new TeamNotFoundException("Team with identifier: '" + teamIdentifier + " does not exist");
        }
        return playerRepository.findByTeamIdentifier(teamIdentifier);
    }

    public Iterable<Player> findByName(String name) {
        Iterable<Player> players = playerRepository.findByName(name);
        return players;
    }


}
