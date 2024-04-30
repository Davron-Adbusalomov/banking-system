package com.example.banking_system.controller;

import com.example.banking_system.DTO.DepositDTO;
import com.example.banking_system.DTO.UserLoginDTO;
import com.example.banking_system.DTO.WithdrawDTO;
import com.example.banking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.login(userLoginDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositDTO depositDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deposit(depositDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> login(@RequestBody WithdrawDTO withdrawDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.withdraw(withdrawDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/checkBalance/{card_num}")
    public ResponseEntity<?> checkBalance(@PathVariable String card_num){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getBalance(card_num));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/fetchCards/{username}")
    private ResponseEntity<?> fetchCards(@PathVariable String username){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.fetchCardNums(username));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
