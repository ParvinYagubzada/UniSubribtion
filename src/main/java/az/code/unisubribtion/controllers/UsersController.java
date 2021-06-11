package az.code.unisubribtion.controllers;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UsersController {

    private final UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }

    @RequestMapping(name = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> createUser(@RequestBody SubscriptionUser user) {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }
}
