package com.combergniot.fm.services;

import com.combergniot.fm.model.Player;
import com.combergniot.fm.repositiories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Iterable<Player> getAll() {
        return playerRepository.findAll();
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> findByName(String name) {
        Optional<Player> player = playerRepository.findByName(name);
        return player;
    }
}
