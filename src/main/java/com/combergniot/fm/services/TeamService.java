package com.combergniot.fm.services;

import com.combergniot.fm.model.Team;
import com.combergniot.fm.repositiories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Iterable<Team> getAll() {
        return teamRepository.findAll();
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }


    public Optional<Team> findById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team;
    }

    public Optional<Team> findByTeamIdentifier(String teamIdentifier) {
        Team team = teamRepository.findByTeamIdentifier(teamIdentifier);
        return Optional.ofNullable(team);
    }
}
