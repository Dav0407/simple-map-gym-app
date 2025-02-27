package com.epam_task.service;

import com.epam_task.dao.TraineeDAO;
import com.epam_task.dao.TrainerDAO;
import com.epam_task.domain.Trainee;
import com.epam_task.domain.Trainer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@NoArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Log log = LogFactory.getLog(UserServiceImpl.class);

    private TraineeDAO traineeDAO;

    private TrainerDAO trainerDAO;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    public String generateUsername(String firstName, String lastName) {
        log.info("Generating username for: " + firstName + " " + lastName);

        String baseUsername = firstName + "." + lastName;
        int suffix = 1;
        String username = baseUsername;

        List<String> existingUsernames = getAllExistingUsernames();
        while (existingUsernames.contains(username)) {
            log.warn("Username conflict found: " + username + " already exists. Trying next.");
            username = baseUsername + suffix;
            suffix++;
        }

        log.info("Generated unique username: " + username);
        return username;
    }

    @Override
    public String generateRandomPassword() {
        String password = RANDOM.ints(10, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        log.info("Generated a random password.");
        return password;
    }

    public List<String> getAllExistingUsernames() {
        log.info("Fetching all existing usernames.");

        List<String> traineeUsernames = traineeDAO.findAll().stream()
                .map(Trainee::getUsername)
                .collect(Collectors.toList());

        List<String> trainerUsernames = trainerDAO.findAll().stream()
                .map(Trainer::getUsername)
                .collect(Collectors.toList());

        log.info("Found " + traineeUsernames.size() + " trainee usernames and " + trainerUsernames.size() + " trainer usernames.");

        traineeUsernames.addAll(trainerUsernames);
        return traineeUsernames;
    }
}
