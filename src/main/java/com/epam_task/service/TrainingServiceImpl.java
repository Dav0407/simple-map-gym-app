package com.epam_task.service;

import com.epam_task.dao.TrainingDAO;
import com.epam_task.domain.Training;
import com.epam_task.enums.TrainingType;
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
public class TrainingServiceImpl implements TrainingService {

    private static final Log log = LogFactory.getLog(TrainingServiceImpl.class);

    private TrainingDAO trainingDAO;

    //CRUD methods
    public Training createTraining(UUID traineeId, UUID trainerId, String trainingName, TrainingType trainingType, LocalDate trainingDate, Integer trainingDuration) {
        log.info("Creating a new training session: Name=" + trainingName + ", Date=" + trainingDate);

        // Validation checks
        if (traineeId == null || trainerId == null) {
            log.error("Training creation failed: Trainee ID or Trainer ID is null.");
            throw new IllegalArgumentException("Trainee ID and Trainer ID cannot be null.");
        }
        if (trainingName == null || trainingName.trim().isEmpty()) {
            log.error("Training creation failed: Training name is empty.");
            throw new IllegalArgumentException("Training name cannot be empty.");
        }
        if (trainingType == null) {
            log.error("Training creation failed: Training type is null.");
            throw new IllegalArgumentException("Training type cannot be null.");
        }
        if (trainingDate == null || trainingDate.isBefore(LocalDate.now())) {
            log.error("Training creation failed: Invalid training date.");
            throw new IllegalArgumentException("Training date cannot be in the past.");
        }
        if (trainingDuration == null || trainingDuration <= 0) {
            log.error("Training creation failed: Invalid training duration.");
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
        log.info("Training session created successfully: ID=" + savedTraining.getId());
        return savedTraining;
    }


    public Training save(Training training) {
        log.info("Attempting to save a training session: " + training);

        if (training == null) {
            log.error("Save failed: Training object is null.");
            throw new IllegalArgumentException("Training object cannot be null.");
        }

        Training savedTraining = trainingDAO.save(training);
        log.info("Training saved successfully: ID=" + savedTraining.getId());
        return savedTraining;
    }

    public Training read(UUID id) {
        log.info("Fetching training session with ID: " + id);

        if (id == null) {
            log.error("Read failed: Training ID is null.");
            throw new IllegalArgumentException("Training ID cannot be null.");
        }

        Training training = trainingDAO.read(id);
        if (training == null) {
            log.warn("Training session with ID " + id + " not found.");
        } else {
            log.info("Training retrieved successfully: ID=" + training.getId());
        }
        return training;
    }
}
