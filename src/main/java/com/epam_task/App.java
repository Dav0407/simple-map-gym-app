package com.epam_task;

import com.epam_task.config.ApplicationConfig;
import com.epam_task.domain.Trainee;
import com.epam_task.domain.Trainer;
import com.epam_task.domain.Training;
import com.epam_task.enums.TrainingType;
import com.epam_task.facade.GymFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

public class App {

    private static final Log log = LogFactory.getLog(App.class);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        log.info("Welcome to the GYM Management System!");
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        GymFacade gymFacade = context.getBean(GymFacade.class);

        boolean running = true;
        while (running) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Create Trainee");
            System.out.println("2. Get Trainee by ID");
            System.out.println("3. Update Trainee");
            System.out.println("4. Delete Trainee");
            System.out.println("5. Create Trainer");
            System.out.println("6. Get Trainer by ID");
            System.out.println("7. Update Trainer");
            System.out.println("8. Create Training");
            System.out.println("9. Get Training by ID");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createTrainee(gymFacade);
                    break;
                case 2:
                    getTrainee(gymFacade);
                    break;
                case 3:
                    updateTrainee(gymFacade);
                    break;
                case 4:
                    deleteTrainee(gymFacade);
                    break;
                case 5:
                    createTrainer(gymFacade);
                    break;
                case 6:
                    getTrainer(gymFacade);
                    break;
                case 7:
                    updateTrainer(gymFacade);
                    break;
                case 8:
                    createTraining(gymFacade);
                    break;
                case 9:
                    getTraining(gymFacade);
                    break;
                case 10:
                    running = false;
                    System.out.println("Exiting the GYM Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void createTrainee(GymFacade gymFacade) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter date of birth (yyyy-MM-dd): ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Trainee trainee = gymFacade.createTrainee(firstName, lastName, address, dateOfBirth);
        System.out.println("Trainee created: " + trainee);
    }

    private static void getTrainee(GymFacade gymFacade) {
        System.out.print("Enter trainee ID: ");
        UUID traineeId = UUID.fromString(scanner.nextLine());

        Trainee trainee = gymFacade.getTrainee(traineeId);
        if (trainee != null) {
            System.out.println("Trainee found: " + trainee);
        } else {
            System.out.println("Trainee not found.");
        }
    }

    private static void updateTrainee(GymFacade gymFacade) {
        System.out.print("Enter trainee ID: ");
        UUID traineeId = UUID.fromString(scanner.nextLine());

        Trainee trainee = gymFacade.getTrainee(traineeId);
        if (trainee == null) {
            System.out.println("Trainee not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        trainee.setFirstName(scanner.nextLine());
        System.out.print("Enter new last name: ");
        trainee.setLastName(scanner.nextLine());
        System.out.print("Enter new address: ");
        trainee.setAddress(scanner.nextLine());
        System.out.print("Enter new date of birth (yyyy-MM-dd): ");
        trainee.setDate(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Trainee updatedTrainee = gymFacade.updateTrainee(traineeId, trainee);
        System.out.println("Trainee updated: " + updatedTrainee);
    }

    private static void deleteTrainee(GymFacade gymFacade) {
        System.out.print("Enter trainee ID: ");
        UUID traineeId = UUID.fromString(scanner.nextLine());

        boolean isDeleted = gymFacade.deleteTrainee(traineeId);
        if (isDeleted) {
            System.out.println("Trainee deleted successfully.");
        } else {
            System.out.println("Failed to delete trainee.");
        }
    }

    private static void createTrainer(GymFacade gymFacade) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();

        Trainer trainer = gymFacade.createTrainer(firstName, lastName, specialization);
        System.out.println("Trainer created: " + trainer);
    }

    private static void getTrainer(GymFacade gymFacade) {
        System.out.print("Enter trainer ID: ");
        UUID trainerId = UUID.fromString(scanner.nextLine());

        Trainer trainer = gymFacade.getTrainer(trainerId);
        if (trainer != null) {
            System.out.println("Trainer found: " + trainer);
        } else {
            System.out.println("Trainer not found.");
        }
    }

    private static void updateTrainer(GymFacade gymFacade) {
        System.out.print("Enter trainer ID: ");
        UUID trainerId = UUID.fromString(scanner.nextLine());

        Trainer trainer = gymFacade.getTrainer(trainerId);
        if (trainer == null) {
            System.out.println("Trainer not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        trainer.setFirstName(scanner.nextLine());
        System.out.print("Enter new last name: ");
        trainer.setLastName(scanner.nextLine());
        System.out.print("Enter new specialization: ");
        trainer.setSpecialization(scanner.nextLine());

        Trainer updatedTrainer = gymFacade.updateTrainer(trainerId, trainer);
        System.out.println("Trainer updated: " + updatedTrainer);
    }

    private static void createTraining(GymFacade gymFacade) {
        System.out.print("Enter trainee ID: ");
        UUID traineeId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter trainer ID: ");
        UUID trainerId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter training name: ");
        String trainingName = scanner.nextLine();
        System.out.print("Enter training type (CARDIO, STRENGTH, YOGA): ");
        TrainingType trainingType = TrainingType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter training date (yyyy-MM-dd): ");
        LocalDate trainingDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.print("Enter training duration (in minutes): ");
        int trainingDuration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Training training = gymFacade.createTraining(traineeId, trainerId, trainingName, trainingType, trainingDate, trainingDuration);
        System.out.println("Training created: " + training);
    }

    private static void getTraining(GymFacade gymFacade) {
        System.out.print("Enter training ID: ");
        UUID trainingId = UUID.fromString(scanner.nextLine());

        Training training = gymFacade.getTraining(trainingId);
        if (training != null) {
            System.out.println("Training found: " + training);
        } else {
            System.out.println("Training not found.");
        }
    }
}