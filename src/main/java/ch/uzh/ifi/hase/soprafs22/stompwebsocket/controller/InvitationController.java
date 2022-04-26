package ch.uzh.ifi.hase.soprafs22.stompwebsocket.controller;

import com.sun.istack.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * GreetingController
 */
@Controller
public class InvitationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

/*
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(@Payload HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
*/

    @MessageMapping("/invite")
    public void send(@NotNull SimpMessageHeaderAccessor sha,
                     @Payload String username) {
        String greeting = "Hello from " + sha.getUser().getName();
        simpMessagingTemplate
            .convertAndSendToUser(username, "/queue/invite/greetings", greeting);
    }
}