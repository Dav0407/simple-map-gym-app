package com.epam_task.service;

import com.epam_task.domain.Trainer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface TrainerService {

    Trainer createTrainer(String firstName, String lastName, String specialization);

    Trainer save(Trainer trainer);

    Trainer read(UUID id);

    Trainer update(UUID id, Trainer trainer);

}
