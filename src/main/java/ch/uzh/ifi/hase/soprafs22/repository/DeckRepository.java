package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("deckRepository")
public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> findDeckByUserId(Long userId);

    Deck findDeckByDeckname(String deckname);

}
