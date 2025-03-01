package com.epam_task.dao;

import com.epam_task.domain.Training;
import com.epam_task.storage.TrainingStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Setter(onMethod = @__(@Autowired))
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TrainingDAO {

    private TrainingStorage storage;

    // CRUD methods
    public Training save(Training training) {
        return storage.save(training);
    }

    public Training read(UUID id) {
        return storage.findById(id);
    }
}
