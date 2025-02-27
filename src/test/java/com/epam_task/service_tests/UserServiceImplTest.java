package com.epam_task.service_tests;

import com.epam_task.dao.TraineeDAO;
import com.epam_task.dao.TrainerDAO;
import com.epam_task.domain.Trainee;
import com.epam_task.domain.Trainer;
import com.epam_task.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(traineeDAO, trainerDAO);
    }

    @Test
    void generateUsername_shouldGenerateUniqueUsername() {
        // Arrange
        when(traineeDAO.findAll()).thenReturn(Arrays.asList(
                new Trainee("john.doe", "password1"),
                new Trainee("jane.doe", "password2")
        ));
        when(trainerDAO.findAll()).thenReturn(Arrays.asList(
                new Trainer("trainer1", "password3"),
                new Trainer("trainer2", "password4")
        ));

        // Act
        String username = userService.generateUsername("John", "Doe");

        // Assert
        assertEquals("John.Doe", username);
        verify(traineeDAO).findAll();
        verify(trainerDAO).findAll();
    }

    @Test
    void generateUsername_shouldAppendSuffixWhenUsernameExists() {
        // Arrange
        when(traineeDAO.findAll()).thenReturn(Arrays.asList(
                new Trainee("John.Doe", "password1"),
                new Trainee("Jane.Doe", "password2")
        ));
        when(trainerDAO.findAll()).thenReturn(Arrays.asList(
                new Trainer("trainer1", "password3"),
                new Trainer("trainer2", "password4")
        ));

        // Act
        String username = userService.generateUsername("John", "Doe");

        // Assert
        assertEquals("John.Doe1", username);
        verify(traineeDAO).findAll();
        verify(trainerDAO).findAll();
    }

    @Test
    void generateRandomPassword_shouldGenerateRandomPassword() {
        // Act
        String password = userService.generateRandomPassword();

        // Assert
        assertNotNull(password);
        assertEquals(10, password.length());
    }

    @Test
    void getAllExistingUsernames_shouldReturnAllUsernames() {
        // Arrange
        when(traineeDAO.findAll()).thenReturn(Arrays.asList(
                new Trainee("john.doe", "password1"),
                new Trainee("jane.doe", "password2")
        ));
        when(trainerDAO.findAll()).thenReturn(Arrays.asList(
                new Trainer("trainer1", "password3"),
                new Trainer("trainer2", "password4")
        ));

        // Act
        List<String> usernames = userService.getAllExistingUsernames();

        // Assert
        assertNotNull(usernames);
        assertEquals(4, usernames.size());
        assertTrue(usernames.containsAll(Arrays.asList("john.doe", "jane.doe", "trainer1", "trainer2")));
        verify(traineeDAO).findAll();
        verify(trainerDAO).findAll();
    }
}