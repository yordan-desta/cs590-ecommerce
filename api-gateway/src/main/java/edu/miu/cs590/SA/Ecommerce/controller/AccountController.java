package edu.miu.cs590.SA.Ecommerce.controller;

import edu.miu.cs590.SA.Ecommerce.constant.RestEndpoints;
import edu.miu.cs590.SA.Ecommerce.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RestEndpoints.ACCOUNT_PREFIX)
public class AccountController {

    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    // Get all accounts
    @GetMapping
    public ResponseEntity<?> getAll(){
        return accountService.findAll();
    }

    // Get a single account by id
    @GetMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Object> account = accountService.findById(id);
        return ResponseEntity.ok(account);
    }

    // Register a user / Add an account
    @PostMapping(RestEndpoints.REGISTER)
    public ResponseEntity<?> save(@RequestBody Object accountBody){
        return accountService.save(accountBody);
    }

    // Update a user / an account
    @PutMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Object accountBody){
        Object account = accountService.update(id, accountBody);
        return ResponseEntity.ok(account);
    }

    // Authenticate a user
    @PostMapping(RestEndpoints.LOGIN)
    public ResponseEntity<?> authenticate(@RequestBody Object credentialsBody){
        return accountService.authenticate(credentialsBody);
    }

    // Get a single account by id
    @DeleteMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        accountService.deleteById(id);
        return ResponseEntity.ok("Account deleted successfully !");
    }

}
