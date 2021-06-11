package az.code.unisubribtion.controllers;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.exceptions.UsernameAlreadyExists;
import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.services.SubscriptionService;
import az.code.unisubribtion.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    //    @ExceptionHandler(StudentNotFound.class)
//    public ResponseEntity<String> handleNotFound(StudentNotFound e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<String> handleNotFound(UsernameAlreadyExists e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FOUND);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<Subscription>> getSubscription(
            @RequestParam Long id,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(subService.getSubscriptionsByUserId(id, pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody SubscriptionUser user) {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }
}
