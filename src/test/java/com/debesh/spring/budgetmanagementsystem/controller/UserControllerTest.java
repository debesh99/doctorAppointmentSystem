package com.debesh.spring.budgetmanagementsystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.debesh.spring.budgetmanagementsystem.exception.InvalidUserCredentialsException;
import com.debesh.spring.budgetmanagementsystem.model.UserInputModel;
import com.debesh.spring.budgetmanagementsystem.model.UserOutputModel;
import com.debesh.spring.budgetmanagementsystem.service.impl.UserServiceImpl;

class UserControllerTest {

	@Mock
	private UserServiceImpl userServiceImpl;

	@Mock
	private Logger logger;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createUser_ValidInput_ReturnsUserOutputModel() throws InvalidUserCredentialsException {
		// Arrange
		UserInputModel userInputModel = new UserInputModel();
		userInputModel.setFirstName("John");
		userInputModel.setLastName("Doe");
		userInputModel.setEmail("john.doe@example.com");
		userInputModel.setPassword("password123");
		userInputModel.setUsername("johndoe");

		UserOutputModel createdUser = new UserOutputModel();
		createdUser.setId(1L);
		createdUser.setEmail("john.doe@example.com");
		createdUser.setUsername("johndoe");
		createdUser.setFullName("John Doe");

		when(userServiceImpl.createUser(userInputModel)).thenReturn(createdUser);

		// Act
		ResponseEntity<UserOutputModel> response = userController.createUser(userInputModel);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(createdUser, response.getBody());

//		verify(logger).info("Creating a new user: {}", userInputModel);
//		verify(logger).info("User created successfully: {}", createdUser);
		verify(userServiceImpl).createUser(userInputModel);
	}

	@Test
	void createUser_InvalidInput_ThrowsInvalidUserCredentialsException() throws InvalidUserCredentialsException {
		// Arrange
		UserInputModel userInputModel = new UserInputModel();
		userInputModel.setFirstName("John");
		userInputModel.setLastName("Doe");
		userInputModel.setEmail("john.doe@example.com");
		userInputModel.setPassword("password123");
		userInputModel.setUsername("johndoe");

		when(userServiceImpl.createUser(userInputModel)).thenThrow(InvalidUserCredentialsException.class);

		// Act and Assert
		try {
			userController.createUser(userInputModel);
		} catch (InvalidUserCredentialsException e) {
			// Exception expected
		}

//		verify(logger).info("Creating a new user: {}", userInputModel);
		verify(userServiceImpl).createUser(userInputModel);
	}

	  @Test
	    void userLogin_ValidCredentials_ReturnsUserOutputModel() throws InvalidUserCredentialsException {
	        // Arrange
	        String email = "john.doe@example.com";
	        String password = "password123";

	        UserInputModel userInputModel = new UserInputModel();
	        userInputModel.setEmail(email);
	        userInputModel.setPassword(password);

	        UserOutputModel expectedUserOutputModel = new UserOutputModel();
	        expectedUserOutputModel.setId(1L);
	        expectedUserOutputModel.setFullName("John Doe");
//	        expectedUserOutputModel.setLastName("Doe");

	        when(userServiceImpl.userLogin(email, password)).thenReturn(expectedUserOutputModel);

	        // Act
	        ResponseEntity<UserOutputModel> response = userController.userLogin(userInputModel);

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(expectedUserOutputModel, response.getBody());

//	        verify(logger).info("User login attempt for email: {}", email);
//	        verify(logger).info("User logged in successfully: {}", expectedUserOutputModel);
	        verify(userServiceImpl).userLogin(email, password);
	    }

	    @Test
	    void userLogin_InvalidCredentials_ThrowsInvalidUserCredentialsException() throws InvalidUserCredentialsException {
	        // Arrange
	        String email = "john.doe@example.com";
	        String password = "password123";

	        UserInputModel userInputModel = new UserInputModel();
	        userInputModel.setEmail(email);
	        userInputModel.setPassword(password);

	        when(userServiceImpl.userLogin(email, password)).thenThrow(InvalidUserCredentialsException.class);

	        // Act and Assert
	        assertThrows(InvalidUserCredentialsException.class, () -> userController.userLogin(userInputModel));

//	        verify(logger).info("User login attempt for email: {}", email);
	        verify(userServiceImpl).userLogin(email, password);
	    }
	}
