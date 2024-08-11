package com.ecommerce.userservice.repositories;

import com.ecommerce.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);
    Token findByTokenAndDeletedEquals(String token,boolean isDeleted);
    Token findByTokenAndDeletedEqualsAndExpiryAtGreaterThan(String token, boolean isDeleted, Date expiryGreaterThan);
}
