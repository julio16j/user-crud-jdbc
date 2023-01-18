package com.usercrudjdbc.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.usercrudjdbc.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotBlank
    @Size(min = 5, max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    public User toEntity() {
        return new User(null, name, email, password);
    }
}
