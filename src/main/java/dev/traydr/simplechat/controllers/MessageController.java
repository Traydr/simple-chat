package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.entities.Group;
import dev.traydr.simplechat.entities.Message;
import dev.traydr.simplechat.entities.MessageData;
import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.repositories.GroupRepository;
import dev.traydr.simplechat.repositories.MessageRepository;
import dev.traydr.simplechat.repositories.TokenRepository;
import jakarta.validation.Valid;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageRepository messageRepo;
    private final TokenRepository tokenRepo;
    private final GroupRepository groupRepo;

    public MessageController(MessageRepository messageRepo, TokenRepository tokenRepo, GroupRepository groupRepo) {
        this.messageRepo = messageRepo;
        this.tokenRepo = tokenRepo;
        this.groupRepo = groupRepo;
    }

    @GetMapping("")
    public List<Message> getMessages(@CookieValue("token") String token,
                                     @RequestParam("gid") long gid) {
        User user = tokenRepo.findByToken(token).getUser();
        Group group = groupRepo.findById(gid).orElseThrow();
        if (group.getJoinedUsers().contains(user)) {
            return messageRepo.findByGroup(group);
        } else {
            return null;
        }
    }

    @PostMapping("")
    public Message createMessage(@CookieValue("token") String token,
                                 @Valid @ModelAttribute MessageData message) {
        User user = tokenRepo.findByToken(token).getUser();
        Group group = groupRepo.findById(message.getGid()).orElseThrow();

        if (!group.getJoinedUsers().contains(user)) {
            return null;
        }

        Message msg = new Message(user, group, Jsoup.clean(message.getData(), Safelist.none()));
        messageRepo.saveAndFlush(msg);
        return msg;
    }

    @PutMapping("")
    public Message updateMessage(@CookieValue("token") String token,
                                 @RequestParam("mid") long mid,
                                 @Valid @ModelAttribute MessageData message) {
        User user = tokenRepo.findByToken(token).getUser();
        Message msg = messageRepo.findById(mid).orElseThrow();
        if (msg.getUser().equals(user)) {
            msg.setMessage(Jsoup.clean(message.getData(), Safelist.none()));
            messageRepo.saveAndFlush(msg);
            return msg;
        } else {
            return null;
        }
    }

    @DeleteMapping()
    public boolean deleteMessage(@CookieValue("token") String token, @RequestParam("mid") long mid) {
        User user = tokenRepo.findByToken(token).getUser();
        Message msg = messageRepo.findById(mid).orElseThrow();
        if (msg.getUser().equals(user)) {
            messageRepo.delete(msg);
            return true;
        } else {
            return false;
        }
    }
}
