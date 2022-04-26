package ch.uzh.ifi.hase.soprafs22.helpers;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utilities
 */
public class Utilities {
    /**
     * @brief Helper Method to convert userPostDTO into a JSON string such that the
     *        input
     *        can be processed
     *        Input will look like this: {"name": "Test User", "username":
     *        "testUsername"}
     *
     * @param object
     * @return string
     */
    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}