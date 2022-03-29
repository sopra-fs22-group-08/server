package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import lombok.Getter;
import lombok.Setter;

public class UserGetDTO {

    @Getter
    @Setter
    private Long id;

    @Setter
    @Getter
    private String name;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private UserStatus status;
}