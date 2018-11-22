package com.jackass.RestAPI.controller;

import com.jackass.RestAPI.entity.ConfirmationToken;
import com.jackass.RestAPI.entity.Group;
import com.jackass.RestAPI.entity.User;
import com.jackass.RestAPI.exception.AlreadyExistsException;
import com.jackass.RestAPI.exception.NotFoundException;
import com.jackass.RestAPI.mail.MailManager;
import com.jackass.RestAPI.repository.ConfirmationTokenRepository;
import com.jackass.RestAPI.repository.GroupRepository;
import com.jackass.RestAPI.repository.ProductRepository;
import com.jackass.RestAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private MailManager mailManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenRepository tokenRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping(method = RequestMethod.POST)
    public void register(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam String patronymic) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);

        User u = userRepository.getUserByEmail(user.getEmail());

        if (u != null) {
            throw new AlreadyExistsException("User with such email already registered.");
        }

        int userId = userRepository.save(user).getId();

        ConfirmationToken token = new ConfirmationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setId(userId);

        tokenRepository.save(token);

        mailManager.sendToken(user.getEmail(), token.getToken());
    }

    @RequestMapping(method = RequestMethod.POST, params = "token")
    public void confirm(@RequestParam String token) {
        ConfirmationToken tokenObj = tokenRepository.getConfirmationTokenByToken(token);
        tokenRepository.delete(tokenObj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> authenticate(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.getUserByEmailAndPassword(email, password);

        if (user == null) {
            throw new NotFoundException("Wrong email or password.");
        }

        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(method = RequestMethod.GET, params = "id")
    public ResponseEntity<User> getUser(@RequestParam int id) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            throw new NotFoundException("Wrong user ID.");
        }

        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(method = RequestMethod.POST, params = "patr")
    public void changePatr(@RequestParam int id,
                           @RequestParam String patr) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            throw new NotFoundException("Wrong user ID.");
        }

        user.setPatronymic(patr);
    }

    @RequestMapping(method = RequestMethod.DELETE, params = "patr")
    public void deletePatr(@RequestParam int id) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            throw new NotFoundException("Wrong user ID.");
        }

        user.setPatronymic(null);
    }

    @RequestMapping(method = RequestMethod.POST, params = "group")
    public void changeGroup(@RequestParam int id,
                           @RequestParam int group) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            throw new NotFoundException("Wrong user ID.");
        }

        Group groupObj = groupRepository.getGroupById(group);

        if (groupObj == null) {
            throw new NotFoundException("Wrong group ID.");
        }

        user.setGroup(groupObj);
    }

    @RequestMapping(method = RequestMethod.POST, params = "phone")
    public void changePhone(@RequestParam int id,
                            @RequestParam String phone) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            throw new NotFoundException("Wrong user ID.");
        }

        user.setPhoneNumber(phone);
    }

    @RequestMapping(method = RequestMethod.DELETE, params = "phone")
    public void deletePhone(@RequestParam int id) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            throw new NotFoundException("Wrong user ID.");
        }

        user.setPhoneNumber(null);
    }

}