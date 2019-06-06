package com.combergniot.fm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamIdentifier;

    //    OneToOne with the team
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnore
    private Team team;

    //    OneToMany players;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
      private List<Player> players = new ArrayList<>();

    public Backlog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamIdentifier() {
        return teamIdentifier;
    }

    public void setTeamIdentifier(String teamIdentifier) {
        this.teamIdentifier = teamIdentifier;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
