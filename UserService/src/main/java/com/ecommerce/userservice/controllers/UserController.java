package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.LoginRequestDTO;
import com.ecommerce.userservice.dtos.LogoutRequestDTO;
import com.ecommerce.userservice.dtos.SignUpRequestDTO;
import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.services.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequestDTO request) throws UserNotFoundException {
        //check if email and password in db
        // return user
        //else throw error
        return new ResponseEntity<>(userService.loginUser(request.getEmail(),request.getPassword()), HttpStatus.OK);

    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDTO request) throws UserNotFoundException {
        //store user in db
        return UserDto.from(userService.createUser(request.getName(),request.getEmail(),request.getPassword()));


    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO request) {
        //delete token if exists -> 200
        // if doesn't exists -> 404
        userService.logout(request.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public User validateToken(@PathVariable("token") @NonNull String token) {
        return userService.validateToken(token);
    }
}
