package com.combergniot.fm.repositiories;

import com.combergniot.fm.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    Iterable<Player> findByName(String name);

    Iterable<Player> findByTeamIdentifier(String teamIdentifier);

}
