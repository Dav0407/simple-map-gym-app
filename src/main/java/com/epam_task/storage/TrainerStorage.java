package com.epam_task.storage;

import com.epam_task.domain.Trainer;
import com.epam_task.utils.JsonFileReader;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class TrainerStorage {

    private static final Log LOG = LogFactory.getLog(TrainerStorage.class);

    private final String filePath;

    @Getter
    private final Map<UUID, Trainer> trainers = new HashMap<>();

    //Value is assigned using constructor injection instead of field injection for easier testing
    @Autowired
    public TrainerStorage(@Value("${spring.files.trainer_file_path}") String filePath) {
        this.filePath = filePath;
    }

    @PostConstruct
    public void postProcessor() {
        LOG.info("Initializing TrainerStorage from JSON file: " + filePath);

        try {
            List<Trainer> trainerList = new JsonFileReader().readJsonFile(filePath, new TypeReference<>() {
            });
            if (trainerList == null || trainerList.isEmpty()) {
                LOG.warn("No trainers found in the JSON file.");
                return;
            }

            trainerList.forEach(this::save);
            LOG.info("Trainers loaded from JSON and saved successfully.");
        } catch (Exception e) {
            LOG.error("Error loading trainers from JSON file: " + filePath, e);
        }
    }

    // CRUD methods
    // create
    public Trainer save(Trainer trainer) {

        if (trainer == null || trainer.getUserId() == null) {
            LOG.warn("Attempted to save a null trainer or trainer with null ID.");
            return null;
        }

        LOG.info("Saving trainer: " + trainer.getUsername());
        trainers.put(trainer.getUserId(), trainer);

        return trainer;
    }

    //read
    public Trainer findById(UUID id) {

        if (id == null) {
            LOG.warn("Attempted to find a trainer with null ID.");
            return null;
        }

        Trainer trainer = trainers.get(id);

        if (trainer == null) {
            LOG.warn("Trainer with ID " + id + " not found.");
        } else {
            LOG.info("Trainer found: " + trainer.getUsername());
        }

        return trainer;
    }

    //update
    public Trainer updateById(UUID id, Trainer trainer) {

        if (id == null || trainer == null) {
            LOG.warn("Attempted to update a trainer with null ID or null trainer object.");
            return null;
        }

        if (!trainers.containsKey(id)) {
            LOG.warn("Trainer with ID " + id + " not found. Update failed.");
            return null;
        }

        LOG.info("Updating trainer: " + trainer.getUsername());
        trainers.put(id, trainer);

        return trainer;
    }

}
