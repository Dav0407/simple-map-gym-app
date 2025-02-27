package com.epam_task.service;

import com.epam_task.dao.TraineeDAO;
import com.epam_task.domain.Trainee;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@NoArgsConstructor
@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Log log = LogFactory.getLog(TraineeServiceImpl.class);

    private TraineeDAO traineeDAO;

    private UserService userService;

    //CRUD methods
    @Override
    public Trainee createTrainee(String firstName, String lastName, String address, LocalDate localDate) {
        log.info("Attempting to create a trainee: firstName=" + firstName + ", lastName=" + lastName);

        if (firstName == null || lastName == null || address == null || localDate == null) {
            log.error("Trainee creation failed: Missing required fields.");
            throw new IllegalArgumentException("All fields (firstName, lastName, address, date) are required.");
        }

        if (localDate.isAfter(LocalDate.now())) {
            log.error("Trainee creation failed: Provided date " + localDate + " is in the future.");
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }

        Trainee trainee = new Trainee();
        trainee.setUserId(UUID.randomUUID());
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setAddress(address);
        trainee.setDate(localDate);
        trainee.setUsername(userService.generateUsername(firstName, lastName));
        trainee.setPassword(userService.generateRandomPassword());
        trainee.setIsActive(true);

        log.info("Generated trainee: userId=" + trainee.getUserId() + ", username=" + trainee.getUsername());

        return save(trainee);
    }

    @Override
    public Trainee save(Trainee trainee) {
        log.info("Saving trainee with ID: " + trainee.getUserId());
        Trainee savedTrainee = traineeDAO.save(trainee);

        log.info("Trainee saved successfully: userId=" + savedTrainee.getUserId() + ", username=" + savedTrainee.getUsername());
        return savedTrainee;
    }

    @Override
    public Trainee read(UUID id) {
        log.info("Fetching trainee with ID: " + id);
        if (id == null) {
            log.error("Read failed: Trainee ID is null.");
            throw new IllegalArgumentException("Trainee ID cannot be null.");
        }

        Trainee trainee = traineeDAO.read(id);
        if (trainee == null) {
            log.warn("Trainee with ID " + id + " not found.");
        } else {
            log.info("Trainee retrieved successfully: userId=" + trainee.getUserId() + ", username=" + trainee.getUsername());
        }
        return trainee;
    }

    @Override
    public Trainee update(UUID id, Trainee trainee) {
        log.info("Updating trainee with ID: " + id);

        if (id == null || trainee == null) {
            log.error("Update failed: ID or trainee object is null.");
            throw new IllegalArgumentException("Trainee ID and updated trainee details are required.");
        }

        Trainee updatedTrainee = traineeDAO.update(id, trainee);
        log.info("Trainee updated successfully: userId=" + updatedTrainee.getUserId() + ", username=" + updatedTrainee.getUsername());
        return updatedTrainee;
    }

    @Override
    public boolean delete(UUID id) {
        log.info("Attempting to delete trainee with ID: " + id);

        if (id == null) {
            log.error("Delete failed: Trainee ID is null.");
            throw new IllegalArgumentException("Trainee ID cannot be null.");
        }

        boolean deleted = traineeDAO.delete(id);
        if (deleted) {
            log.info("Trainee with ID " + id + " deleted successfully.");
        } else {
            log.warn("Trainee with ID " + id + " not found, deletion skipped.");
        }

        return deleted;
    }
}
