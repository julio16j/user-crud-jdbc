package com.usercrudjdbc.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usercrudjdbc.exception.BadRequestException;
import com.usercrudjdbc.exception.NotFoundException;
import com.usercrudjdbc.model.dto.UserDto;
import com.usercrudjdbc.model.entity.User;
import com.usercrudjdbc.repository.interfaces.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    public User create(UserDto user) {
    	User newUser = user.toEntity();
    	validateEmail(user.getEmail());
        return userRepository.save(newUser);
    }

	private void validateEmail(String email) {
		if (userRepository.existsByEmail(email)) {
    		throw new BadRequestException("Email already is used");
    	}
	}

    public User findById(Long id) {
    	Optional<User> userFounded = userRepository.findOne(id); 
        if (userFounded.isEmpty()) {
        	throw new NotFoundException("user");
        } return userFounded.get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public User update(Long id, UserDto userDto) {
    	User user = findById(id);
    	validateEmail(userDto.getEmail());
    	user.setEmail(userDto.getEmail());
    	user.setName(userDto.getName());
    	user.setPassword(userDto.getPassword());
        return userRepository.update(user);
    }
}