package com.epam_task.service;

import com.epam_task.domain.Trainee;

import java.time.LocalDate;
import java.util.UUID;

public interface TraineeService {

    Trainee createTrainee(String firstName, String lastName, String address, LocalDate localDate);

    Trainee save(Trainee trainee);

    Trainee read(UUID id);

    Trainee update(UUID id, Trainee trainee);

    boolean delete(UUID id);

}
