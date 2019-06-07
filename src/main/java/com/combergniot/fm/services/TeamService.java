package com.combergniot.fm.services;

import com.combergniot.fm.exceptions.TeamIdException;
import com.combergniot.fm.model.Backlog;
import com.combergniot.fm.model.Team;
import com.combergniot.fm.repositiories.BacklogRepository;
import com.combergniot.fm.repositiories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Iterable<Team> getAll() {
        return teamRepository.findAll();
    }

    public Team saveOrUpdate(Team team) {
        try {
            team.setTeamIdentifier(team.getTeamIdentifier().toUpperCase());
            if (team.getId() == null) {
                Backlog backlog = new Backlog();
                team.setBacklog(backlog);
                backlog.setTeam(team);
                backlog.setTeamIdentifier(team.getTeamIdentifier().toUpperCase());
            }

            if (team.getId() != null) {
                team.setBacklog(backlogRepository.findByTeamIdentifier(
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

