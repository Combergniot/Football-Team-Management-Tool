package com.combergniot.fm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @NotBlank(message = "Team ID is required")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_id", referencedColumnName = "id")
//    @JsonIgnore
//    private Team team;

    @NotBlank(message = "Player name is required")
    private String name;

    @Column(nullable = false)
//    @NotBlank(message = "Player number is required")
    private int playerNumber;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_squad_id", updatable = false, nullable = false)
    @JsonIgnore
    private TeamSquad teamSquad;

    @Column(updatable = false)
    private String teamIdentifier;

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TeamSquad getTeamSquad() {
        return teamSquad;
    }

    public void setTeamSquad(TeamSquad teamSquad) {
        this.teamSquad = teamSquad;
    }

    public String getTeamIdentifier() {
        return teamIdentifier;
    }

    public void setTeamIdentifier(String teamIdentifier) {
        this.teamIdentifier = teamIdentifier;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }


    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playerNumber=" + playerNumber +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", teamSquad=" + teamSquad +
                ", teamIdentifier='" + teamIdentifier + '\'' +
                '}';
    }
}
