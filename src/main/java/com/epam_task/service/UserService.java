package com.epam_task.service;

public interface UserService {

    String generateUsername(String firstName, String lastName);

    String generateRandomPassword();

}
