package com.eci.ieti;

import com.eci.ieti.controller.user.UsersController;
import com.eci.ieti.exception.UserNotFoundException;
import com.eci.ieti.repository.user.User;
import com.eci.ieti.repository.user.UserDto;
import com.eci.ieti.service.user.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class Lab01ApplicationTests {

	@Mock
	private UsersService usersService;

	@InjectMocks
	private UsersController usersController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateUser() {
		UserDto userDto = new UserDto();
		User user = new User(userDto);
		when(usersService.save(any(User.class))).thenReturn(user);

		ResponseEntity<User> response = usersController.createUser(userDto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testGetAllUsers() {
		List<User> users = new ArrayList<>();
		when(usersService.all()).thenReturn(users);

		ResponseEntity<List<User>> response = usersController.getAllUsers();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(users, response.getBody());
	}

	@Test
	void testFindByIdNotFound() {
		String id = "1";
		when(usersService.findById(id)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> usersController.findById(id));
	}

	@Test
	void testUpdateUserNotFound() {
		String id = "1";
		UserDto newInfo = new UserDto();
		when(usersService.findById(id)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> usersController.updateUser(id, newInfo));
	}

	@Test
	void testDeleteUserNotFound() {
		String id = "1";
		when(usersService.findById(id)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> usersController.deleteUser(id));
	}
}
