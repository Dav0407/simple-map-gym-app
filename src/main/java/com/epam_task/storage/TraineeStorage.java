package com.epam_task.storage;

import com.epam_task.domain.Trainee;
import com.epam_task.utils.JsonFileReader;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
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
public class TraineeStorage {

    private static final Log LOG = LogFactory.getLog(TraineeStorage.class);

    private final String filePath;

    private final Map<UUID, Trainee> trainees = new HashMap<>();

    //Value is assigned using constructor injection instead of field injection for easier testing
    @Autowired
    public TraineeStorage(@Value("${spring.files.trainee_file_path}") String filePath) {
        this.filePath = filePath;
    }

    @PostConstruct
    public void postProcessor() {
        LOG.info("Initializing TraineeStorage from JSON file: {} " + filePath);

        try {
            List<Trainee> traineesList = new JsonFileReader().readJsonFile(filePath, new TypeReference<>() {
            });
            if (traineesList == null || traineesList.isEmpty()) {
                LOG.warn("No trainees found in the JSON file.");
                return;
            }

            traineesList.forEach(this::save);
            LOG.info("Trainees loaded from JSON and saved successfully.");
        } catch (Exception e) {
            LOG.error("Error loading trainees from JSON file: " + filePath, e);
        }
    }

    // CRUD methods
    // create
    public Trainee save(Trainee trainee) {

        if (trainee == null || trainee.getUserId() == null) {
            LOG.warn("Attempted to save a null trainee or trainee with null ID.");
            return null;
        }

        LOG.info("Saving trainee: " + trainee.getUsername());
        trainees.put(trainee.getUserId(), trainee);

        return trainee;
    }

    //read
    public Trainee findById(UUID id) {

        if (id == null) {
            LOG.warn("Attempted to find a trainee with null ID.");
            return null;
        }

        Trainee trainee = trainees.get(id);

        if (trainee == null) {
            LOG.warn("Trainee with ID " + id + " not found.");
        } else {
            LOG.info("Trainee found: " + trainee.getUsername());
        }

        return trainee;
    }

    //update
    public Trainee updateById(UUID id, Trainee trainee) {

        if (id == null || trainee == null) {
            LOG.warn("Attempted to update a trainee with null ID or null trainee object.");
            return null;
        }

        if (!trainees.containsKey(id)) {
            LOG.warn("Trainee with ID " + id + " not found. Update failed.");
            return null;
        }

        LOG.info("Updating trainee: " + trainee.getUsername());
        trainees.put(id, trainee);

        return trainee;
    }

    //delete
    public boolean deleteById(UUID id) {

        if (id == null) {
            LOG.warn("Attempted to delete a trainee with null ID.");
            return false;
        }

        if (!trainees.containsKey(id)) {
            LOG.warn("Trainee with ID " + id + " not found. Deletion failed.");
            return false;
        }

        trainees.remove(id);
        LOG.info("Deleted trainee with ID: " + id);
        return true;
    }

    public Map<UUID, Trainee> getTrainees() {
        return trainees;
    }

}
