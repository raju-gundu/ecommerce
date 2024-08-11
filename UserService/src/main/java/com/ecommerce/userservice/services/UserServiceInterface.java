package com.ecommerce.userservice.services;

import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserServiceInterface {
    User createUser(String name,String email,String password) throws JsonProcessingException;
    Token loginUser(String email, String password) throws UserNotFoundException;
    void logout(String token);
    User validateToken(String token);
}
