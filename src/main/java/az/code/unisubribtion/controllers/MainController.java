package az.code.unisubribtion.controllers;

import az.code.unisubribtion.dtos.GroupDTO;
import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.exceptions.EmailAlreadyExists;
import az.code.unisubribtion.exceptions.GroupIsNotEmpty;
import az.code.unisubribtion.exceptions.UsernameAlreadyExists;
import az.code.unisubribtion.models.*;
import az.code.unisubribtion.services.NotificationService;
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
    NotificationService notService;

    public MainController(SubscriptionService subService, UserService service, NotificationService notService) {
        this.subService = subService;
        this.service = service;
        this.notService = notService;
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

    @GetMapping("/subscriptions")
    public ResponseEntity<Paging<Subscription>> getSubscriptionsByUserId(
            @RequestParam Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(subService.getSubscriptionsByUserId(userId, pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }

    @GetMapping("/subscriptions/group")
    public ResponseEntity<Paging<Subscription>> getSubscriptionsByUserIdAndGroupId(
            @RequestParam Long userId,
            @RequestParam Long groupId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(subService.getSubscriptionsByGroupId(userId, groupId, pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }

    @GetMapping("/groups/dto")
    public ResponseEntity<List<GroupDTO>> getGroupDTOS(
            @RequestParam Long userId
    ) {
        return new ResponseEntity<>(subService.getAllGroupDTOs(userId), HttpStatus.OK);
    }

    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups(
            @RequestParam Long userId
    ) {
        return new ResponseEntity<>(subService.getAllGroups(userId), HttpStatus.OK);
    }

    @DeleteMapping("/groups")
    public ResponseEntity<Long> deleteGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId
    ) {
        return new ResponseEntity<>(subService.deleteGroup(userId, groupId), HttpStatus.OK);
    }

    @DeleteMapping("/groups/force")
    public ResponseEntity<Long> deleteForceGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId
    ) {
        return new ResponseEntity<>(subService.deleteForceGroup(userId, groupId), HttpStatus.OK);
    }

    @DeleteMapping("/subscriptions")
    public ResponseEntity<Long> deleteSub(
            @RequestParam Long userId,
            @RequestParam Long subId
    ) {
        return new ResponseEntity<>(subService.deleteSub(userId, subId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<Subscription> createSub(
            @RequestParam Long userId,
            @RequestBody Subscription sub
    ) {
        return new ResponseEntity<>(subService.createSubscription(userId, sub), HttpStatus.CREATED);
    }

    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(
            @RequestParam Long userId,
            @RequestBody Group group
    ) {
        return new ResponseEntity<>(subService.createGroup(userId, group), HttpStatus.CREATED);
    }

    @PutMapping("/subscriptions")
    public ResponseEntity<Subscription> updateSub(
            @RequestParam Long userId,
            @RequestParam Long subId,
            @RequestBody Subscription sub
    ) {
        return new ResponseEntity<>(subService.updateSubscription(userId, subId, sub), HttpStatus.ACCEPTED);
    }

    @PutMapping("/groups")
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

    @GetMapping("/notifications")
    public ResponseEntity<Paging<Notification>> getNotifications(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(notService.getAllNotifications(userId, pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }

    @GetMapping("subscription-stop")
    public ResponseEntity<Long> stopSubscription(
            @RequestParam Long userId,
            @RequestParam Long subscriptionId) {
        return new ResponseEntity<>(subService.stopSubscription(userId, subscriptionId), HttpStatus.OK);
    }


    @DeleteMapping("/notifications")
    public ResponseEntity<Long> deleteNotification(
            @RequestParam Long userId,
            @RequestParam Long notificationId
    ) {
        return new ResponseEntity<>(notService.deleteNotification(userId, notificationId), HttpStatus.OK);
    }

    @GetMapping("/users/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            session.removeAttribute("userId");
        }
    }

    @PostMapping("/users/auth")
    public UserDTO login(HttpServletRequest request, @RequestBody SimpleUser user) throws NoSuchAlgorithmException {
        UserDTO userDTO = service.authUser(user.getUsername(), user.getPassword());
        if (userDTO != null) {
            request.getSession().setAttribute("userId", userDTO);
            return userDTO;
        }
        return UserDTO.builder().username("sada").build();
    }
}
