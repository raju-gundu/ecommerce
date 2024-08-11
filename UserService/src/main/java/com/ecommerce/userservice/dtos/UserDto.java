package com.ecommerce.userservice.dtos;

import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

        private String name;
        private String email;
        private List<Role> roles;
        private boolean isEmailVerified;

        public static UserDto from(User user) {
            if (user == null) return null;

            UserDto userDto = new UserDto();
            userDto.email = user.getEmail();
            userDto.name = user.getName();
            userDto.roles = user.getRoles();
            userDto.isEmailVerified = user.isEmailVerified();

            return userDto;
        }
    }

