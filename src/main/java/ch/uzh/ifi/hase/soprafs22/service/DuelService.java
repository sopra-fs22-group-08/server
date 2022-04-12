package ch.uzh.ifi.hase.soprafs22.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        duelRepository.save(givenDuel);
        duelRepository.flush();
        log.debug("Created duel with players {} and {}", userRepository.findById(givenDuel.getPlayerOneId()),
                userRepository.findById(givenDuel.getPlayerTwoId()));
        return givenDuel;
    }
}