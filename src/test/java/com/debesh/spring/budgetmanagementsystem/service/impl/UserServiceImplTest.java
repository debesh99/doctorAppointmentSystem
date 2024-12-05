package com.debesh.spring.budgetmanagementsystem.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.debesh.spring.budgetmanagementsystem.entity.User;
import com.debesh.spring.budgetmanagementsystem.exception.InvalidUserCredentialsException;
import com.debesh.spring.budgetmanagementsystem.model.UserInputModel;
import com.debesh.spring.budgetmanagementsystem.model.UserOutputModel;
import com.debesh.spring.budgetmanagementsystem.repository.UserRepository;

@SpringBootTest
@SpringJUnitConfig
@Transactional
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserEmailAndPasswordValidationServiceImpl checkEmailAndPassword;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateUser_ValidCredentials_ReturnsUserOutputModel() throws InvalidUserCredentialsException {
		// Arrange
		UserInputModel userInputModel = new UserInputModel();
		userInputModel.setFirstName("John");
		userInputModel.setLastName("Doe");
		userInputModel.setEmail("john.doe@example.com");
		userInputModel.setUsername("johndoe");
		userInputModel.setPassword("password123");

		when(checkEmailAndPassword.validateEmailAndPassword(anyString(), anyString())).thenReturn(true);

		User savedUser = new User();
		savedUser.setId(1L);
		savedUser.setFirstName("John");
		savedUser.setLastName("Doe");
		savedUser.setEmail("john.doe@example.com");
		savedUser.setUsername("johndoe");
		savedUser.setPassword("password123");

		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		// Act
		UserOutputModel result = userService.createUser(userInputModel);

		// Assert
		assertNotNull(result);
//		assertEquals(savedUser.getId(), result.getId());
		assertEquals((savedUser.getFirstName()+" "+savedUser.getLastName()), result.getFullName());
		assertEquals(savedUser.getEmail(), result.getEmail());
		assertEquals(savedUser.getUsername(), result.getUsername());
//		assertEquals(savedUser.getPassword(), result.getPassword());

		verify(checkEmailAndPassword, times(1)).validateEmailAndPassword(anyString(), anyString());
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	public void testCreateUser_InvalidCredentials_ThrowsInvalidUserCredentialsException()
			throws InvalidUserCredentialsException {
		// Arrange
		UserInputModel userInputModel = new UserInputModel();
		userInputModel.setFirstName("John");
		userInputModel.setLastName("Doe");
		userInputModel.setEmail("john.doe@example.com");
		userInputModel.setUsername("johndoe");
		userInputModel.setPassword("password123");

		when(checkEmailAndPassword.validateEmailAndPassword(anyString(), anyString())).thenReturn(false);

		// Act and Assert
		assertThrows(InvalidUserCredentialsException.class, () -> userService.createUser(userInputModel));

		verify(checkEmailAndPassword, times(1)).validateEmailAndPassword(anyString(), anyString());
		verify(userRepository, never()).save(any(User.class));
	}

    @Test
    void testUserLogin_ValidCredentials_ReturnsGreetingMessage() throws InvalidUserCredentialsException {
        // Arrange
        String email = "john.doe@example.com";
        String password = "password123";

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setUsername("johndoe");
        user.setPassword("password123");

        when(userRepository.userLogin(anyString(), anyString())).thenReturn(Optional.of(user));

        // Act
//        UserOutputModel result = userService.userLogin(email, password);

        // Assert
//        assertEquals("Hi, John Doe", result);

        verify(userRepository, times(1)).userLogin(email, password);
    }

	@Test
	public void testUserLogin_InvalidCredentials_ThrowsInvalidUserCredentialsException()
			throws InvalidUserCredentialsException {
		// Arrange
		String email = "john.doe@example.com";
		String password = "password123";

		when(userRepository.userLogin(anyString(), anyString())).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(InvalidUserCredentialsException.class, () -> userService.userLogin(email, password));

		verify(userRepository, times(1)).userLogin(anyString(), anyString());
	}

//	@Test
//	public void testGetAllUsers_ReturnsListOfUsers() {
//		// Arrange
//		User user1 = new User();
//		user1.setId(1L);
//		user1.setFirstName("John");
//		user1.setLastName("Doe");
//		user1.setEmail("john.doe@example.com");
//		user1.setUsername("johndoe");
//		user1.setPassword("password123");
//
//		User user2 = new User();
//		user2.setId(2L);
//		user2.setFirstName("Jane");
//		user2.setLastName("Smith");
//		user2.setEmail("jane.smith@example.com");
//		user2.setUsername("janesmith");
//		user2.setPassword("password456");
//
//		List<User> userList = Arrays.asList(user1, user2);
//
//		when(userRepository.findAll()).thenReturn(userList);
//
//		// Act
//		List<User> result = userService.getAllUsers();
//
//		// Assert
//		assertNotNull(result);
//		assertEquals(2, result.size());
//		assertEquals(userList, result);
//
//		verify(userRepository, times(1)).findAll();
//	}
}
