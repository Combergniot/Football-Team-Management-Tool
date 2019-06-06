package com.combergniot.fm.repositiories;

import com.combergniot.fm.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByTeamIdentifier(String teamIdentifier);
}
