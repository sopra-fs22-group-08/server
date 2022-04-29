package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.Duel;
import ch.uzh.ifi.hase.soprafs22.entity.Invitation;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DuelGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DuelPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.InvitationGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.InvitationPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MultipleChoiceCardPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;

/**
 * @brief DTOMapper
 *        This class is responsible for generating classes that will
 *        automatically
 *        transform/map the internal representation
 *        of an entity (e.g., the User) to the external/API representation
 *        (e.g.,
 *        UserGetDTO for getting, UserPostDTO for creating)
 *        and vice versa.
 *        Additional mappers can be defined for new entities.
 *        Always created one mapper for getting information (GET) and one mapper
 *        for
 *        creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "email", target = "email")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "creationdate", target = "creationdate")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "token", target = "token")
    UserGetDTO convertEntityToUserGetDTO(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "status", target = "status")
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);

    @Mapping(source = "deckname", target = "deckname")
    Deck convertDeckPostDTOtoEntity(DeckPostDTO deckPostDTO);

    @Mapping(source = "question", target = "question")
    @Mapping(source = "answer", target = "answer")
    @Mapping(source = "options", target = "options")
    MultipleChoiceCard convertMultipleChoiceCardPostDTOtoEntity(MultipleChoiceCardPostDTO multipleChoiceCardPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "question", target = "question")
    @Mapping(source = "answer", target = "answer")
    @Mapping(source = "options", target = "options")
    MultipleChoiceCardGetDTO convertEntityToMultipleChoiceCardGetDTO(MultipleChoiceCard multipleChoiceCard);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "deckname", target = "deckname")
    @Mapping(source = "creationdate", target = "creationdate")
    DeckGetDTO convertEntityToDeckGetDTO(Deck deck);

    @Mapping(source = "deckId", target = "deckId")
    @Mapping(source = "playerOneId", target = "playerOneId")
    @Mapping(source = "playerTwoId", target = "playerTwoId")
    Duel convertDuelPostDtoToEntity(DuelPostDTO duelPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "deckId", target = "deckId")
    @Mapping(source = "playerOneId", target = "playerOneId")
    @Mapping(source = "playerTwoId", target = "playerTwoId")
    @Mapping(source = "playerOneScore", target = "playerOneScore")
    @Mapping(source = "playerTwoScore", target = "playerTwoScore")
    @Mapping(source = "playerOneStatus", target = "playerOneStatus")
    @Mapping(source = "playerTwoStatus", target = "playerTwoStatus")
    DuelGetDTO convertEntityToDuelGetDTO(Duel duel);

    @Mapping(source = "receiverId", target = "receiverId")
    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "duelId", target = "duelId")
    @Mapping(source = "deckId", target = "deckId")
    @Mapping(source = "deckname", target = "deckname")
    @Mapping(source = "senderUsername", target = "senderUsername")
    @Mapping(source = "receiverUsername", target = "receiverUsername")
    Invitation convertInvitationPostDTOToEntity(InvitationPostDTO invitationPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "receiverId", target = "receiverId")
    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "deckId", target = "deckId")
    @Mapping(source = "deckname", target = "deckname")
    @Mapping(source = "duelId", target = "duelId")
    @Mapping(source = "senderUsername", target = "senderUsername")
    @Mapping(source = "receiverUsername", target = "receiverUsername")
    InvitationGetDTO convertEntityToInvitationGetDTO(Invitation invitation);
}