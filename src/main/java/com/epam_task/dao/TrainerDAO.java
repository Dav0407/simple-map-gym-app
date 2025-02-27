package com.epam_task.dao;

import com.epam_task.domain.Trainer;
import com.epam_task.storage.TrainerStorage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Component
public class TrainerDAO {

    private TrainerStorage storage;

    // CRUD methods
    public Trainer save(Trainer trainer) {
        return storage.save(trainer);
    }

    public Trainer read(UUID id) {
        return storage.findById(id);
    }

    public Trainer update(UUID id, Trainer trainer) {
        return storage.updateById(id, trainer);
    }

    public List<Trainer> findAll() {
        return new ArrayList<>(getStorage().getTrainers().values());
    }

}
