package ch.uzh.ifi.hase.soprafs22.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.hase.soprafs22.entity.Duel;

/**
 * DuelRepository
 */
@Repository("duelRepository")
public interface DuelRepository extends JpaRepository<Duel, Long> {

}