package az.code.unisubribtion.controllers;

import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.services.SubscriptionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Flow;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionsController {

    SubscriptionService service;

    public SubscriptionsController(SubscriptionService service) {
        this.service = service;
    }

    //    @ExceptionHandler(StudentNotFound.class)
//    public ResponseEntity<String> handleNotFound(StudentNotFound e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(GradeNotFound.class)
//    public ResponseEntity<String> handleNotFound(GradeNotFound e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }

    @GetMapping("/subscriptions")
      public ResponseEntity<List<Subscription>> getStudents(
              @RequestParam(defaultValue = "0") Integer pageNo,
              @RequestParam(defaultValue = "10") Integer pageSize,
              @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(service.getAllSubscriptions(pageNo, pageSize, sortBy), HttpStatus.ACCEPTED);
    }
}
