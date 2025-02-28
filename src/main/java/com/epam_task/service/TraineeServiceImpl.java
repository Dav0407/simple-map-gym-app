package com.epam_task.service;

import com.epam_task.dao.TraineeDAO;
import com.epam_task.domain.Trainee;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Setter(onMethod = @__(@Autowired))
@NoArgsConstructor
@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Log LOG = LogFactory.getLog(TraineeServiceImpl.class);

    private TraineeDAO traineeDAO;

    private UserService userService;

    //CRUD methods
    @Override
    public Trainee createTrainee(String firstName, String lastName, String address, LocalDate localDate) {
        LOG.info("Attempting to create a trainee: firstName=" + firstName + ", lastName=" + lastName);

        if (firstName == null || lastName == null || address == null || localDate == null) {
            LOG.error("Trainee creation failed: Missing required fields.");
            throw new IllegalArgumentException("All fields (firstName, lastName, address, date) are required.");
        }

        if (localDate.isAfter(LocalDate.now())) {
            LOG.error("Trainee creation failed: Provided date " + localDate + " is in the future.");
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

        LOG.info("Generated trainee: userId=" + trainee.getUserId() + ", username=" + trainee.getUsername());

        return save(trainee);
    }

    @Override
    public Trainee save(Trainee trainee) {
        LOG.info("Saving trainee with ID: " + trainee.getUserId());
        Trainee savedTrainee = traineeDAO.save(trainee);

        LOG.info("Trainee saved successfully: userId=" + savedTrainee.getUserId() + ", username=" + savedTrainee.getUsername());
        return savedTrainee;
    }

    @Override
    public Trainee read(UUID id) {
        LOG.info("Fetching trainee with ID: " + id);
        if (id == null) {
            LOG.error("Read failed: Trainee ID is null.");
            throw new IllegalArgumentException("Trainee ID cannot be null.");
        }

        Trainee trainee = traineeDAO.read(id);
        if (trainee == null) {
            LOG.warn("Trainee with ID " + id + " not found.");
        } else {
            LOG.info("Trainee retrieved successfully: userId=" + trainee.getUserId() + ", username=" + trainee.getUsername());
        }
        return trainee;
    }

    @Override
    public Trainee update(UUID id, Trainee trainee) {
        LOG.info("Updating trainee with ID: " + id);

        if (id == null || trainee == null) {
            LOG.error("Update failed: ID or trainee object is null.");
            throw new IllegalArgumentException("Trainee ID and updated trainee details are required.");
        }

        trainee.setUsername(userService.generateUsername(trainee.getFirstName(), trainee.getLastName()));

        Trainee updatedTrainee = traineeDAO.update(id, trainee);
        LOG.info("Trainee updated successfully: userId=" + updatedTrainee.getUserId() + ", username=" + updatedTrainee.getUsername());
        return updatedTrainee;
    }

    @Override
    public boolean delete(UUID id) {
        LOG.info("Attempting to delete trainee with ID: " + id);

        if (id == null) {
            LOG.error("Delete failed: Trainee ID is null.");
            throw new IllegalArgumentException("Trainee ID cannot be null.");
        }

        boolean deleted = traineeDAO.delete(id);
        if (deleted) {
            LOG.info("Trainee with ID " + id + " deleted successfully.");
        } else {
            LOG.warn("Trainee with ID " + id + " not found, deletion skipped.");
        }

        return deleted;
    }
}
