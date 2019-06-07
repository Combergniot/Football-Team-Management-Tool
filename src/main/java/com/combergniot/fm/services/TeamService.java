package com.combergniot.fm.services;

import com.combergniot.fm.exceptions.TeamIdException;
import com.combergniot.fm.model.TeamSquad;
import com.combergniot.fm.model.Team;
import com.combergniot.fm.repositiories.TeamSquadRepository;
import com.combergniot.fm.repositiories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamSquadRepository teamSquadRepository;

    public Iterable<Team> getAll() {
        return teamRepository.findAll();
    }

    public Team saveOrUpdate(Team team) {
        try {
            team.setTeamIdentifier(team.getTeamIdentifier().toUpperCase());
            if (team.getId() == null) {
                TeamSquad teamSquad = new TeamSquad();
                team.setTeamSquad(teamSquad);
                teamSquad.setTeam(team);
                teamSquad.setTeamIdentifier(team.getTeamIdentifier().toUpperCase());
            }

            if (team.getId() != null) {
                team.setTeamSquad(teamSquadRepository.findByTeamIdentifier(
                        team.getTeamIdentifier().toUpperCase()));
            }
            return teamRepository.save(team);
        } catch (Exception e) {
            throw new TeamIdException("Team identifier '" + team.getTeamIdentifier() + "' already exists");
        }

    }

    public Optional<Team> findById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team;
    }

    public Team findByTeamIdentifier(String teamIdentifier) {
        Team team = teamRepository.findByTeamIdentifier(teamIdentifier);
        if (team == null) {
            throw new TeamIdException("Team identifier '" + teamIdentifier + "' does not exist");
        }
        return team;
    }


    public void deleteTeamByIdentifier(String teamIdentifier) {
        Team team = teamRepository.findByTeamIdentifier(teamIdentifier.toUpperCase());

        if (team == null) {
            throw new TeamIdException("Cannot Team with identifier '" + teamIdentifier + "'. This team does not exist");
        }
        teamRepository.delete(team);
    }
}

