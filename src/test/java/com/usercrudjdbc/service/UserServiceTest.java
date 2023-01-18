package com.usercrudjdbc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.usercrudjdbc.exception.BadRequestException;
import com.usercrudjdbc.exception.NotFoundException;
import com.usercrudjdbc.model.dto.UserDto;
import com.usercrudjdbc.model.entity.User;
import com.usercrudjdbc.repository.interfaces.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void create() {
		UserDto userDto = new UserDto("name", "email@email.com", "password");
		User user = userDto.toEntity();
		when(userRepository.save(user)).thenReturn(user);
		User returnedUser = userService.create(userDto);
		assertEquals(user, returnedUser);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void create_whenEmailIsAlreadyUsed_throwBadRequestException() {
		UserDto userDto = new UserDto("name", "email@email.com", "password");
		when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);
		assertThrows(BadRequestException.class, () -> userService.create(userDto));
	}

	@Test
	void findById() {
		User user = new User(1L, "name", "email@email.com", "password");
		when(userRepository.findOne(user.getId())).thenReturn(Optional.of(user));
		User returnedUser = userService.findById(user.getId());
		assertEquals(user, returnedUser);
		verify(userRepository, times(1)).findOne(user.getId());
	}

	@Test
	void findById_whenUserNotFound_throwNotFoundException() {
		Long id = 1L;
		when(userRepository.findOne(id)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> userService.findById(id));
	}

	@Test
	void findAll() {
		List<User> users = Arrays.asList(new User(1L, "name", "email@email.com", "password"));
		when(userRepository.findAll()).thenReturn(users);
		List<User> returnedUsers = userService.findAll();
		assertEquals(users, returnedUsers);
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void delete() {
		Long id = 1L;
		userService.delete(id);
		verify(userRepository, times(1)).delete(id);
	}

	@Test
	void update() {
		Long id = 1L;
		UserDto userDto = new UserDto("name", "email@email.com", "password");
		User user = new User(id, "name", "email@email.com", "password");
		when(userRepository.findOne(id)).thenReturn(Optional.of(user));
		when(userRepository.update(user)).thenReturn(user);
		User returnedUser = userService.update(id, userDto);
		assertEquals(user, returnedUser);
		verify(userRepository, times(1)).findOne(id);
		verify(userRepository, times(1)).update(user);
	}
	
	@Test
    void update_whenEmailIsAlreadyUsed_throwBadRequestException() {
        Long id = 1L;
        UserDto userDto = new UserDto("name", "email@email.com", "password");
        User user = new User(id, "name", "email@email.com", "password");
        when(userRepository.findOne(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);
        assertThrows(BadRequestException.class, () -> userService.update(id, userDto));
    }
}
