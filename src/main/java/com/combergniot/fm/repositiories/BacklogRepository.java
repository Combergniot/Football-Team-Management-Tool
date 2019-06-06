package com.combergniot.fm.repositiories;

import com.combergniot.fm.model.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByTeamIdentifier(String teamIdentifier);
}