package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.Duel;
import ch.uzh.ifi.hase.soprafs22.entity.MultipleChoiceCard;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

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

    @Mapping(source = "deckId", target = "deckId")
    @Mapping(source = "playerOneId", target = "playerOneId")
    @Mapping(source = "playerTwoId", target = "playerTwoId")
    @Mapping(source = "playerOneScore", target = "playerOneScore")
    @Mapping(source = "playerTwoScore", target = "playerTwoScore")
    DuelGetDTO convertEntityToDuelGetDTO(Duel duel);
}