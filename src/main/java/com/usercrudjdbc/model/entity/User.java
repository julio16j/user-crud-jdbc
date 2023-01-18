package com.usercrudjdbc.model.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	@Id
    private Long id;
    private String name;
    private String email;
    private String password;
}