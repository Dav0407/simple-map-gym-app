package com.epam_task.service;

import com.epam_task.dao.TrainingDAO;
import com.epam_task.domain.Training;
import com.epam_task.enums.TrainingType;
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
public class TrainingServiceImpl implements TrainingService {

    private static final Log LOG = LogFactory.getLog(TrainingServiceImpl.class);

    private TrainingDAO trainingDAO;

    //CRUD methods
    public Training createTraining(UUID traineeId, UUID trainerId, String trainingName, TrainingType trainingType, LocalDate trainingDate, Integer trainingDuration) {
        LOG.info("Creating a new training session: Name=" + trainingName + ", Date=" + trainingDate);

        // Validation checks
        if (traineeId == null || trainerId == null) {
            LOG.error("Training creation failed: Trainee ID or Trainer ID is null.");
            throw new IllegalArgumentException("Trainee ID and Trainer ID cannot be null.");
        }
        if (trainingName == null || trainingName.trim().isEmpty()) {
            LOG.error("Training creation failed: Training name is empty.");
            throw new IllegalArgumentException("Training name cannot be empty.");
        }
        if (trainingType == null) {
            LOG.error("Training creation failed: Training type is null.");
            throw new IllegalArgumentException("Training type cannot be null.");
        }
        if (trainingDate == null || trainingDate.isBefore(LocalDate.now())) {
            LOG.error("Training creation failed: Invalid training date.");
            throw new IllegalArgumentException("Training date cannot be in the past.");
        }
        if (trainingDuration == null || trainingDuration <= 0) {
            LOG.error("Training creation failed: Invalid training duration.");
            throw new IllegalArgumentException("Training duration must be a positive number.");
        }

        Training training = new Training();
        training.setId(UUID.randomUUID());
        training.setTraineeId(traineeId);
        training.setTrainerId(trainerId);
        training.setTrainingName(trainingName);
        training.setTrainingType(trainingType);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(trainingDuration);

        Training savedTraining = save(training);
        LOG.info("Training session created successfully: ID=" + savedTraining.getId());
        return savedTraining;
    }


    public Training save(Training training) {
        LOG.info("Attempting to save a training session: " + training);

        if (training == null) {
            LOG.error("Save failed: Training object is null.");
            throw new IllegalArgumentException("Training object cannot be null.");
        }

        Training savedTraining = trainingDAO.save(training);
        LOG.info("Training saved successfully: ID=" + savedTraining.getId());
        return savedTraining;
    }

    public Training read(UUID id) {
        LOG.info("Fetching training session with ID: " + id);

        if (id == null) {
            LOG.error("Read failed: Training ID is null.");
            throw new IllegalArgumentException("Training ID cannot be null.");
        }

        Training training = trainingDAO.read(id);
        if (training == null) {
            LOG.warn("Training session with ID " + id + " not found.");
        } else {
            LOG.info("Training retrieved successfully: ID=" + training.getId());
        }
        return training;
    }
}
