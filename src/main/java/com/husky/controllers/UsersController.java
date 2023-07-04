package com.husky.controllers;

import com.husky.dtos.CreateUserDto;
import com.husky.entities.User;
import com.husky.repositories.UserRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final Logger logger = Logger.getLogger(UsersController.class.getName());
    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/health")
    public String health() {
        return "UP!!";
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        logger.info("Found user: " + (user.isPresent() ? user.get(): " No match found"));
        return user;
    }

    @PostMapping()
    public User createUser(@RequestBody CreateUserDto createUserDto) {
        User userRequest = new User();
        userRequest.setFirstname(createUserDto.firstname());
        userRequest.setLastname(createUserDto.lastname());
        return userRepository.save(userRequest);
    }
}
