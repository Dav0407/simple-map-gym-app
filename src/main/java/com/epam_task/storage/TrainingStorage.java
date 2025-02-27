package com.epam_task.storage;

import com.epam_task.domain.Training;
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

@Getter
@Component
public class TrainingStorage {

    private static final Log LOG = LogFactory.getLog(TrainingStorage.class);

    private final String filePath;

    private final Map<UUID, Training> trainings = new HashMap<>();

    //Value is assigned using constructor injection instead of field injection for easier testing
    @Autowired
    public TrainingStorage(@Value("${spring.files.training_file_path}") String filePath) {
        this.filePath = filePath;
    }

    @PostConstruct
    public void postProcessor() {
        LOG.info("Initializing TrainingStorage from JSON file: " + filePath);

        try {
            List<Training> trainingList = new JsonFileReader().readJsonFile(filePath, new TypeReference<>() {
            });
            if (trainingList == null || trainingList.isEmpty()) {
                LOG.warn("No trainings found in the JSON file.");
                return;
            }

            trainingList.forEach(this::save);
            LOG.info("Trainings loaded from JSON and saved successfully.");
        } catch (Exception e) {
            LOG.error("Error loading trainings from JSON file: " + filePath, e);
        }
    }

    // CRUD methods
    // create
    public Training save(Training training) {
        if (training == null || training.getId() == null) {
            LOG.warn("Attempted to save a null training or training with null ID.");
            return null;
        }
        LOG.info("Saving training: " + training.getTrainingName());
        trainings.put(training.getId(), training);

        return training;
    }

    //read
    public Training findById(UUID id) {
        if (id == null) {
            LOG.warn("Attempted to find a training with null ID.");
            return null;
        }
        Training training = trainings.get(id);
        if (training == null) {
            LOG.warn("Training with ID " + id + " not found.");
        } else {
            LOG.info("Training found: " + training.getTrainingName());
        }
        return training;
    }
}
