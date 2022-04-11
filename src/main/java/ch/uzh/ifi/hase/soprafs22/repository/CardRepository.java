package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("multipleChoiceCardRepository")
public interface CardRepository extends JpaRepository<MultipleChoiceCard, Long> {
    List<MultipleChoiceCard> findCardByDeckId(Long deckId);

}
