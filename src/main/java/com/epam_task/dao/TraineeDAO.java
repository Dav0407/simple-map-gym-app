package com.epam_task.dao;

import com.epam_task.domain.Trainee;
import com.epam_task.storage.TraineeStorage;
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
public class TraineeDAO {

    private TraineeStorage storage;

    // CRUD methods
    public Trainee save(Trainee trainee) {
        return storage.save(trainee);
    }

    public Trainee read(UUID id) {
        return storage.findById(id);
    }

    public Trainee update(UUID id, Trainee trainee) {
        return storage.updateById(id, trainee);
    }

    public boolean delete(UUID id) {
        return storage.deleteById(id);
    }

    public List<Trainee> findAll() {
        return new ArrayList<>(getStorage().getTrainees().values());
    }

}
