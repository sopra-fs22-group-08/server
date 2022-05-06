package ch.uzh.ifi.hase.soprafs22.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;

@Repository("multipleChoiceCardRepository")
public interface CardRepository extends JpaRepository<MultipleChoiceCard, Long> {
    List<MultipleChoiceCard> findCardByDeckId(Long deckId);

}