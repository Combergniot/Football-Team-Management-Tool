package com.combergniot.fm.repositiories;

import com.combergniot.fm.model.TeamSquad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamSquadRepository extends CrudRepository<TeamSquad, Long> {

    TeamSquad findByTeamIdentifier(String teamIdentifier);
}
