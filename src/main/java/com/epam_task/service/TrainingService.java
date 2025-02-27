package com.epam_task.service;


import com.epam_task.domain.Training;
import com.epam_task.enums.TrainingType;

import java.time.LocalDate;
import java.util.UUID;

public interface TrainingService {

    Training createTraining(UUID traineeId, UUID trainerId, String trainingName, TrainingType trainingType, LocalDate trainingDate, Integer trainingDuration);

    Training save(Training training);

    Training read(UUID id);

}
