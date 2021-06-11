package az.code.unisubribtion.controllers;

import az.code.unisubribtion.dtos.GroupDTO;
import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.exceptions.EmailAlreadyExists;
import az.code.unisubribtion.exceptions.GroupIsNotEmpty;
import az.code.unisubribtion.exceptions.UsernameAlreadyExists;
import az.code.unisubribtion.models.*;
import az.code.unisubribtion.services.SubscriptionService;
import az.code.unisubribtion.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
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

    @ExceptionHandler(GroupIsNotEmpty.class)
    public ResponseEntity<String> handleGroupNotEmpty(GroupIsNotEmpty e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/subscriptions")//TODO: Checked!
    public ResponseEntity<Paging> getSubscriptionsByUserId(
            @RequestParam Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(subService.getSubscriptionsByUserId(userId, pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }

    @GetMapping("/subscriptions/group")//TODO: Checked!
    public ResponseEntity<Paging> getSubscriptionsByUserIdAndGroupId(
            @RequestParam Long userId,
            @RequestParam Long groupId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(subService.getSubscriptionsByGroupId(userId, groupId, pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }

    @GetMapping("/groups/dto")//TODO: Checked!
    public ResponseEntity<List<GroupDTO>> getGroupDTOS(
            @RequestParam Long userId
    ) {
        return new ResponseEntity<>(subService.getAllGroupDTOs(userId), HttpStatus.OK);
    }

    @GetMapping("/groups")//TODO: Checked!
    public ResponseEntity<List<Group>> getAllGroups(
            @RequestParam Long userId
    ){
        return new ResponseEntity<>(subService.getAllGroups(userId), HttpStatus.OK);
    }

    @DeleteMapping("/groups")//TODO: Checked!
    public ResponseEntity<Long> deleteGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId
    ){
        return new ResponseEntity<>(subService.deleteGroup(userId, groupId), HttpStatus.OK);
    }

    @DeleteMapping("/groups/force")//TODO: Checked!
    public ResponseEntity<Long> deleteForceGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId
    ){
        return new ResponseEntity<>(subService.deleteForceGroup(userId, groupId), HttpStatus.OK);
    }

    @DeleteMapping("/subscriptions")//TODO: Checked!
    public ResponseEntity<Long> deleteSub(
            @RequestParam Long userId,
            @RequestParam Long subId
    ) {
        return new ResponseEntity<>(subService.deleteSub(userId, subId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/subscriptions")//TODO: Checked!
    public ResponseEntity<Subscription> createSub(
            @RequestParam Long userId,
            @RequestBody Subscription sub
    ) {
        return new ResponseEntity<>(subService.createSubscription(userId, sub), HttpStatus.CREATED);
    }

    @PostMapping("/groups")//TODO: Checked!
    public ResponseEntity<Group> createGroup(
            @RequestParam Long userId,
            @RequestBody Group group
    ) {
        return new ResponseEntity<>(subService.createGroup(userId, group), HttpStatus.CREATED);
    }

    @PutMapping("/subscriptions")//TODO: Checked!
    public ResponseEntity<Subscription> updateSub(
            @RequestParam Long userId,
            @RequestParam Long subId,
            @RequestBody Subscription sub
    ) {
        return new ResponseEntity<>(subService.updateSubscription(userId, subId, sub), HttpStatus.ACCEPTED);
    }

    @PutMapping("/groups")//TODO: Checked!
    public ResponseEntity<Group> updateGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId,
            @RequestBody Group group
    ) {
        return new ResponseEntity<>(subService.updateGroup(userId, groupId, group), HttpStatus.ACCEPTED);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody SubscriptionUser user) throws NoSuchAlgorithmException {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            session.removeAttribute("userId");
            System.out.println("deleted");
        }
        System.out.println("sadas");
    }

    @PostMapping("/users/auth")
    public UserDTO login(HttpServletRequest request, @RequestBody SimpleUser user) throws NoSuchAlgorithmException {
        UserDTO userDTO = service.authUser(user.getUsername(), user.getPassword());
        if (userDTO != null) {
            request.getSession().setAttribute("userId", userDTO);
            System.out.println("OK");
            return userDTO;
        }
        return UserDTO.builder().username("sada").build();
    }
}
