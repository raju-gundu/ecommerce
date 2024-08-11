package com.ecommerce.userservice.services;

import com.ecommerce.userservice.dtos.SendEmailEventDto;
import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.TokenRepository;
import com.ecommerce.userservice.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;
    private KafkaTemplate<String,String> kafkaTemplate;
    private ObjectMapper objectMapper;


    @Autowired
    public UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,
                       TokenRepository tokenRepository,KafkaTemplate<String,String> kafkaTemplate,
                       ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }




    @Override
    public User createUser(String name,String email,String password)  {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        SendEmailEventDto sendEmailEventDto = new SendEmailEventDto();
        sendEmailEventDto.setSubject("Amazon Web Service Billing of $69");
        sendEmailEventDto.setBody("Hello Sony Yash\nYour bill of $69 is due.\n Please pay the bill as soon as possible else there will be strict action taken.\n Regards,\n Raju Gundu");
        sendEmailEventDto.setTo(email);

        try {
            kafkaTemplate.send("sendEmail",objectMapper.writeValueAsString(sendEmailEventDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return savedUser;
    }

    @Override
    public Token loginUser(String email, String password) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Please enter valid email");
        }
        User realUser = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password, realUser.getHashedPassword())) {
            throw new UserNotFoundException("Please enter valid password");
        }
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);

        // Convert LocalDate to Date
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setUser(realUser);
        token.setExpiryAt(expiryDate);
        token.setToken(RandomStringUtils.randomAlphanumeric(128));
        return tokenRepository.save(token);


    }

    @Override
    public void logout(String token) {
        Optional<Token> optionalToken = Optional.ofNullable(tokenRepository.findByTokenAndDeletedEquals(token, false));
        if(optionalToken.isEmpty()){
            return;
        }
        Token tkn = optionalToken.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);
        return;
    }

    @Override
    public User validateToken(String token) {
        Optional<Token> optionalToken = Optional.ofNullable(tokenRepository.findByTokenAndDeletedEqualsAndExpiryAtGreaterThan(token, false, new Date()));
        if(optionalToken.isEmpty()){
            return null;
        }

        return optionalToken.get().getUser();
    }

}
