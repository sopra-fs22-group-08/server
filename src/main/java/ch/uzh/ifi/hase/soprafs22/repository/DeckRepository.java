package ch.uzh.ifi.hase.soprafs22.repository;

import java.util.List;

import ch.uzh.ifi.hase.soprafs22.constant.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;

@Repository("deckRepository")
public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> findDeckByUserId(Long userId);

    Deck findDeckByDeckname(String deckname);

    List<Deck> findDeckByVisibility(Visibility visibility);

    List<Deck> findDeckByDecknameContainingIgnoreCase(String deckname);

}