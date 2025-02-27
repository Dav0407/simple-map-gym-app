package com.epam_task.facade;

import com.epam_task.domain.Trainee;
import com.epam_task.domain.Trainer;
import com.epam_task.domain.Training;
import com.epam_task.enums.TrainingType;
import com.epam_task.service.TraineeService;
import com.epam_task.service.TrainerService;
import com.epam_task.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    // Trainee operations
    public Trainee createTrainee(String firstName, String lastName, String address, LocalDate dateOfBirth) {
        return traineeService.createTrainee(firstName, lastName, address, dateOfBirth);
    }

    public Trainee getTrainee(UUID traineeId) {
        return traineeService.read(traineeId);
    }

    public Trainee updateTrainee(UUID traineeId, Trainee trainee) {
        return traineeService.update(traineeId, trainee);
    }

    public boolean deleteTrainee(UUID traineeId) {
        return traineeService.delete(traineeId);
    }

    // Trainer operations
    public Trainer createTrainer(String firstName, String lastName, String specialization) {
        return trainerService.createTrainer(firstName, lastName, specialization);
    }

    public Trainer getTrainer(UUID trainerId) {
        return trainerService.read(trainerId);
    }

    public Trainer updateTrainer(UUID trainerId, Trainer trainer) {
        return trainerService.update(trainerId, trainer);
    }

    // Training operations
    public Training createTraining(UUID traineeId, UUID trainerId, String trainingName,
                                   TrainingType trainingType, LocalDate trainingDate,
                                   Integer trainingDuration) {
        return trainingService.createTraining(traineeId, trainerId, trainingName,
                trainingType, trainingDate, trainingDuration);
    }

    public Training getTraining(UUID trainingId) {
        return trainingService.read(trainingId);
    }

}