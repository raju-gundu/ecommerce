package com.ecommerce.userservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class SignUpRequestDTO {
    private String name;
    private String email;
    private String password;
}
