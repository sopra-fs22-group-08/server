package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Duel;
import ch.uzh.ifi.hase.soprafs22.repository.DuelRepository;

/**
 * DuelServiceTest
 */
public class DuelServiceTest {

    @Mock
    private DuelRepository duelRepository;

    @InjectMocks
    private DuelService duelService;

    private Duel testDuel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // set the testduel up with all fields, as if from frontend
        testDuel = new Duel();
        testDuel.setId(1l);
        testDuel.setDeckId(1l);
        testDuel.setPlayerOneId(1l);
        testDuel.setPlayerTwoId(2l);
    }

    @Test
    public void updatePlayerOneStatus_success() {
        // set up testDuel, as if with createDuel()
        testDuel.setPlayerOneScore(0l);
        testDuel.setPlayerTwoScore(0l);
        testDuel.setPlayerOneStatus(PlayerStatus.ACCEPTED);
        testDuel.setPlayerTwoStatus(PlayerStatus.PENDING);

        // when
        Mockito.when(duelRepository.save(Mockito.any()))
                .thenReturn(testDuel);

        Mockito.when(duelRepository.findById(Mockito.anyLong()))
                .thenReturn(testDuel);

        // then
        Duel currentDuel = duelRepository.save(testDuel);

        duelService.updatePlayerStatus(1l, 2l, PlayerStatus.ACCEPTED);

        // general assertions
        String baseMsg = "does not match!";

        assertEquals(1, currentDuel.getId(), "Duel ID" + baseMsg);
        assertEquals(1, currentDuel.getDeckId(), "Deck ID" + baseMsg);
        assertEquals(1, currentDuel.getPlayerOneId(), "Player One ID" + baseMsg);
        assertEquals(2, currentDuel.getPlayerTwoId(), "Player Two ID" + baseMsg);
        assertEquals(0, currentDuel.getPlayerOneScore(), "Player One Score" + baseMsg);
        assertEquals(0, currentDuel.getPlayerTwoScore(), "Player Two Score" + baseMsg);

        // assert the change was successfull
        assertEquals(PlayerStatus.ACCEPTED, currentDuel.getPlayerOneStatus(),
                "Player One Status does not match!");
        assertEquals(PlayerStatus.ACCEPTED, currentDuel.getPlayerTwoStatus(),
                "Player One Status does not match!");
    }

}