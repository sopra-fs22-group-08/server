package ch.uzh.ifi.hase.soprafs22.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Duel;
import ch.uzh.ifi.hase.soprafs22.repository.DuelRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

/**
 * DuelService
 */
@Service
@Transactional
public class DuelService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final DuelRepository duelRepository;
    private final UserRepository userRepository;

    @Autowired
    public DuelService(DuelRepository duelRepository, UserRepository userRepository) {
        this.duelRepository = duelRepository;
        this.userRepository = userRepository;
    }

    public List<Duel> getUsers() {
        return this.duelRepository.findAll();
    }

    public Duel createDuel(Duel givenDuel) {
        givenDuel.setPlayerOneScore(0);
        givenDuel.setPlayerTwoScore(0);
        givenDuel.setPlayerOneStatus(PlayerStatus.ACCEPTED);
        givenDuel.setPlayerTwoStatus(PlayerStatus.PENDING);
        duelRepository.save(givenDuel);
        duelRepository.flush();
        log.debug("Created duel with players {} and {}", userRepository.findById(givenDuel.getPlayerOneId()),
                userRepository.findById(givenDuel.getPlayerTwoId()));
        return givenDuel;
    }

    public void deleteDuelById(long duelId) {
        try {
            duelRepository.deleteById(duelId);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Duel with ID " + duelId + " does NOT exist");
        }
    }

    public void updatePlayerStatus(long duelId, long playerId, PlayerStatus playerStatus) {
        Duel duel = duelRepository.findById(duelId);
        if (duel == null) {
            String baseErrorMessage = "The duel with ID " + duelId + " does not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage));
        } else {
            long playerOneId = duel.getPlayerOneId();
            if (playerOneId == playerId) {
                duel.setPlayerOneStatus(playerStatus);
            } else {
                duel.setPlayerTwoStatus(playerStatus);
            }
            duelRepository.save(duel);
            duelRepository.flush();
        }
    }

    public List<Duel> getDuels() {
        return duelRepository.findAll();
    }

    public void updatePlayerScore(long duelId, long playerId, long newScore) {
        Duel duel = duelRepository.findById(duelId);
        if (duel == null) {
            String baseErrorMessage = "The duel with ID " + duelId + " does not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage));
        } else {
            long playerOneId = duel.getPlayerOneId();
            if (playerOneId == playerId) {
                duel.setPlayerOneScore(newScore);
            } else {
                duel.setPlayerTwoScore(newScore);
            }
            duelRepository.save(duel);
            duelRepository.flush();
        }
    }

    public Duel getDuelById(long duelId) {
        Duel foundDuel = duelRepository.findById(duelId);
        if (foundDuel == null) {
            String baseErrorMessage = "A duel with ID" + duelId + " does NOT exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage));
        } else {
            return foundDuel;
        }
    }
}