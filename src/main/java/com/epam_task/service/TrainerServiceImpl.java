package com.epam_task.service;

import com.epam_task.dao.TrainerDAO;
import com.epam_task.domain.Trainer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Setter(onMethod = @__(@Autowired))
@NoArgsConstructor
@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Log LOG = LogFactory.getLog(TrainerServiceImpl.class);

    private TrainerDAO trainerDAO;

    private UserService userService;

    //CRUD methods
    @Override
    public Trainer createTrainer(String firstName, String lastName, String specialization) {
        LOG.info("Attempting to create a trainer: firstName=" + firstName + ", lastName=" + lastName + ", specialization=" + specialization);

        if (firstName == null || lastName == null || specialization == null) {
            LOG.error("Trainer creation failed: Missing required fields.");
            throw new IllegalArgumentException("All fields (firstName, lastName, specialization) are required.");
        }

        Trainer trainer = new Trainer();
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setSpecialization(specialization);
        trainer.setUserId(UUID.randomUUID());
        trainer.setUsername(userService.generateUsername(firstName, lastName));
        trainer.setPassword(userService.generateRandomPassword());
        trainer.setIsActive(true);

        LOG.info("Generated trainer: userId=" + trainer.getUserId() + ", username=" + trainer.getUsername());

        return save(trainer);
    }

    @Override
    public Trainer save(Trainer trainer) {
        LOG.info("Saving trainer with ID: " + trainer.getUserId());
        Trainer savedTrainer = trainerDAO.save(trainer);
        LOG.info("Trainer saved successfully: userId=" + savedTrainer.getUserId() + ", username=" + savedTrainer.getUsername());
        return savedTrainer;
    }

    @Override
    public Trainer read(UUID id) {
        LOG.info("Fetching trainer with ID: " + id);

        if (id == null) {
            LOG.error("Read failed: Trainer ID is null.");
            throw new IllegalArgumentException("Trainer ID cannot be null.");
        }

        Trainer trainer = trainerDAO.read(id);

        if (trainer == null) {
            LOG.warn("Trainer with ID " + id + " not found.");
        } else {
            LOG.info("Trainer retrieved successfully: userId=" + trainer.getUserId() + ", username=" + trainer.getUsername());
        }

        return trainer;
    }

    @Override
    public Trainer update(UUID id, Trainer trainer) {
        LOG.info("Updating trainer with ID: " + id);

        if (id == null || trainer == null) {
            LOG.error("Update failed: ID or trainer object is null.");
            throw new IllegalArgumentException("Trainer ID and updated trainer details are required.");
        }

        trainer.setUsername(userService.generateUsername(trainer.getFirstName(), trainer.getLastName()));

        Trainer updatedTrainer = trainerDAO.update(id, trainer);
        LOG.info("Trainer updated successfully: userId=" + updatedTrainer.getUserId() + ", username=" + updatedTrainer.getUsername());
        return updatedTrainer;
    }
}
