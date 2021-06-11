package az.code.unisubribtion.controllers;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.exceptions.EmailAlreadyExists;
import az.code.unisubribtion.exceptions.UsernameAlreadyExists;
import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.services.SubscriptionService;
import az.code.unisubribtion.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class MainController {

    SubscriptionService subService;
    UserService service;

    public MainController(SubscriptionService subService, UserService service) {
        this.subService = subService;
        this.service = service;
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<String> handleNotFound(EmailAlreadyExists e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<String> handleNotFound(UsernameAlreadyExists e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/subscriptions/")
    public ResponseEntity<List<Subscription>> getSubscriptionsByUserId(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(subService.getSubscriptionsByUserId(userId, pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<Subscription>> getSubscriptionsByUserIdAndGroupId(
            @RequestParam Long userId,
            @RequestParam Long groupId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(subService.getSubscriptionsByGroupId(userId, groupId, pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody SubscriptionUser user) {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }
}
