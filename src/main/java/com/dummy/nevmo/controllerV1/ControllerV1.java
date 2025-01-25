package com.dummy.nevmo.controllerV1;

import com.dummy.nevmo.exceptions.CustomException;
import com.dummy.nevmo.exceptions.NotFoundException;
import com.dummy.nevmo.entity.BankAccount;
import com.dummy.nevmo.entity.UserAccount;
import com.dummy.nevmo.repository.BankRepository;
import com.dummy.nevmo.repository.UserRepository;
import com.dummy.nevmo.service.BankServiceImpl;
import com.dummy.nevmo.service.TransactionServiceImpl;
import com.dummy.nevmo.service.TransferServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/nevmo/{id}")
public class ControllerV1 {

    static final Logger logger = LoggerFactory.getLogger(ControllerV1.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private TransferServiceImpl transferService;

    @Autowired
    private BankServiceImpl bankService;

    @Autowired
    private TransactionServiceImpl transactionService;

    @GetMapping("/profile")
    public ResponseEntity<UserAccount> getProfile(@PathVariable String id) {
        UserAccount user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserAccount> addUser(@PathVariable String id, @RequestBody UserAccount user) {
        if(!id.equalsIgnoreCase(user.getUserId())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if(userRepository.findById(user.getUserId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<String> getTransactions(@PathVariable String id) {
        UserAccount user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.badRequest().body("Invalid user");
        }
        return ResponseEntity.ok(transactionService.getAllTransactions(user.getUserId()).toString());
    }

    @PostMapping("/addBank")
    public ResponseEntity<String> addBank(@PathVariable String id, @RequestBody BankAccount bank) {
        UserAccount user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.badRequest().body("Invalid sender or receiver");
        }
        user.getBankAccounts().add(bank);
        bank.setUser(user);
        userRepository.save(user);
        return ResponseEntity.ok("Bank account added");
    }

    @RateLimiter(name = "externalService", fallbackMethod = "sendFallback")
    @PatchMapping("/sendMoney")
    public ResponseEntity<String> send(@PathVariable("id") String senderId, @RequestParam("recipient") String recipient, @RequestParam("amount") BigDecimal amount) {
        UserAccount sender = userRepository.findById(senderId).orElse(null);

        UserAccount receiver = userRepository.findById(recipient).orElse(null);

        if(sender == null || receiver == null) {
            return ResponseEntity.badRequest().body("Invalid sender or receiver");
        }
        if(transferService.transfer(sender, receiver, amount)) {
            return ResponseEntity.ok("Transfer successful");
        }
        return ResponseEntity.badRequest().body("Insufficient funds");
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<String> deposit(@PathVariable String id, @RequestParam("amount") BigDecimal amount) {
        UserAccount account = userRepository.findById(id).orElse(null);
        if(account == null) {
            return ResponseEntity.badRequest().body("Invalid account");
        }

        if(bankService.withdraw(amount, account)) {
            return ResponseEntity.ok("Deposit successful");
        }
        return ResponseEntity.badRequest().body("Deposit failed");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@PathVariable String id) {
        UserAccount user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.badRequest().body("Invalid user");
        }

        userRepository.delete(user);
        return ResponseEntity.accepted().body("User deleted");
    }

    @ExceptionHandler({InvalidFormatException.class, JsonProcessingException.class, CustomException.class})
    public ResponseEntity<String> parseException(HttpServletRequest req, Exception ex) {
        logger.info("{} could not serve request {}", req.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>("Unable to process request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(HttpServletRequest req, Exception ex) {
        logger.info("Data not found for {}. Exception {}", req.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>("Unable to process request", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(HttpServletRequest req, Exception ex) {
        logger.error("Error occurred for {}. Exception {}", req.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>("Unable to process request", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> sendFallback(String senderId,  Exception ex) {
        logger.trace("Rate limit exceeded for {}. Exception {}", senderId, ex.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
    }
}
