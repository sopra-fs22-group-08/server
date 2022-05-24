package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.constant.Visibility;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckPutDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.InvitationGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardPutDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {
    @Test
    public void testCreateUser_fromUserPostDTO_toUser_success() {
        // create UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setFirstName("firstname");
        userPostDTO.setLastName("lastname");
        userPostDTO.setUsername("username");
        userPostDTO.setPassword("password");

        // MAP -> Create user
        User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // check content
        assertEquals(userPostDTO.getFirstName(), user.getFirstName());
        assertEquals(userPostDTO.getLastName(), user.getLastName());
        assertEquals(userPostDTO.getUsername(), user.getUsername());
        assertEquals(userPostDTO.getPassword(), user.getPassword());
    }

    @Test
    public void testGetUser_fromUser_toUserGetDTO_success() {
        // create User
        User user = new User();
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");

        // MAP -> Create UserGetDTO
        UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

        // check content
        assertEquals(user.getId(), userGetDTO.getId());
        assertEquals(user.getFirstName(), userGetDTO.getFirstName());
        assertEquals(user.getLastName(), userGetDTO.getLastName());
        assertEquals(user.getUsername(), userGetDTO.getUsername());
        assertEquals(user.getStatus(), userGetDTO.getStatus());
    }

    @Test
    public void getAndSetDTOs() {
        MultipleChoiceCardPutDTO putCard = new MultipleChoiceCardPutDTO();
        putCard.setAnswer("hello");
        putCard.setQuestion("there");
        putCard.setOptions(new ArrayList<String>() {
            {
                add("helloOne");
            }
        });
        putCard.getAnswer();
        putCard.getOptions();
        putCard.getQuestion();

        InvitationGetDTO getInvite = new InvitationGetDTO();
        getInvite.setId(1L);
        getInvite.setSenderId(1L);
        getInvite.setSenderUsername("one");
        getInvite.setReceiverId(2L);
        getInvite.setReceiverUsername("two");
        getInvite.setDeckId(1L);
        getInvite.setDeckname("testOne");
        getInvite.setDuelId(1L);

        getInvite.getId();
        getInvite.getSenderId();
        getInvite.getSenderUsername();
        getInvite.getReceiverId();
        getInvite.getReceiverUsername();
        getInvite.getDeckId();
        getInvite.getDeckname();
        getInvite.getDuelId();

        DeckPutDTO putDeck = new DeckPutDTO();
        putDeck.setDeckname("hello");
        putDeck.setVisibility(Visibility.PUBLIC);

        putDeck.getDeckname();
        putDeck.getVisibility();
    }

}