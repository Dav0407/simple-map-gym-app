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
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class App {

    private static final Log LOG = LogFactory.getLog(App.class);
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        LOG.info("Welcome to the GYM Management System!");
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

            int choice;
            try {
                String input = SCANNER.nextLine();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

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
        SCANNER.close();
    }

    private static void createTrainee(GymFacade gymFacade) {
        System.out.print("Enter first name: ");
        String firstName = SCANNER.nextLine();
        System.out.print("Enter last name: ");
        String lastName = SCANNER.nextLine();
        System.out.print("Enter address: ");
        String address = SCANNER.nextLine();

        LocalDate dateOfBirth = null;
        while (dateOfBirth == null) {
            System.out.print("Enter date of birth (yyyy-MM-dd): ");
            String dateInput = SCANNER.nextLine();
            try {
                dateOfBirth = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd with valid values for month (1-12) and day.");
            }
        }

        Trainee trainee = gymFacade.createTrainee(firstName, lastName, address, dateOfBirth);
        System.out.println("Trainee created: " + trainee);
    }

    private static void getTrainee(GymFacade gymFacade) {
        System.out.print("Enter trainee ID: ");
        UUID traineeId = UUID.fromString(SCANNER.nextLine());

        Trainee trainee = gymFacade.getTrainee(traineeId);
        if (trainee != null) {
            System.out.println("Trainee found: " + trainee);
        } else {
            System.out.println("Trainee not found.");
        }
    }

    private static void updateTrainee(GymFacade gymFacade) {
        System.out.print("Enter trainee ID: ");
        UUID traineeId = UUID.fromString(SCANNER.nextLine());

        Trainee trainee = gymFacade.getTrainee(traineeId);
        if (trainee == null) {
            System.out.println("Trainee not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        trainee.setFirstName(SCANNER.nextLine());
        System.out.print("Enter new last name: ");
        trainee.setLastName(SCANNER.nextLine());
        System.out.print("Enter new address: ");
        trainee.setAddress(SCANNER.nextLine());

        // Date validation loop
        LocalDate newDate = null;
        while (newDate == null) {
            System.out.print("Enter new date of birth (yyyy-MM-dd): ");
            String dateInput = SCANNER.nextLine();
            try {
                newDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd with valid values for month (1-12) and day.");
            }
        }
        trainee.setDate(newDate);

        Trainee updatedTrainee = gymFacade.updateTrainee(traineeId, trainee);
        System.out.println("Trainee updated: " + updatedTrainee);
    }

    private static void deleteTrainee(GymFacade gymFacade) {
        System.out.print("Enter trainee ID: ");
        UUID traineeId = UUID.fromString(SCANNER.nextLine());

        boolean isDeleted = gymFacade.deleteTrainee(traineeId);
        if (isDeleted) {
            System.out.println("Trainee deleted successfully.");
        } else {
            System.out.println("Failed to delete trainee.");
        }
    }

    private static void createTrainer(GymFacade gymFacade) {
        System.out.print("Enter first name: ");
        String firstName = SCANNER.nextLine();
        System.out.print("Enter last name: ");
        String lastName = SCANNER.nextLine();
        System.out.print("Enter specialization: ");
        String specialization = SCANNER.nextLine();

        Trainer trainer = gymFacade.createTrainer(firstName, lastName, specialization);
        System.out.println("Trainer created: " + trainer);
    }

    private static void getTrainer(GymFacade gymFacade) {
        System.out.print("Enter trainer ID: ");
        UUID trainerId = UUID.fromString(SCANNER.nextLine());

        Trainer trainer = gymFacade.getTrainer(trainerId);
        if (trainer != null) {
            System.out.println("Trainer found: " + trainer);
        } else {
            System.out.println("Trainer not found.");
        }
    }

    private static void updateTrainer(GymFacade gymFacade) {
        System.out.print("Enter trainer ID: ");
        UUID trainerId = UUID.fromString(SCANNER.nextLine());

        Trainer trainer = gymFacade.getTrainer(trainerId);
        if (trainer == null) {
            System.out.println("Trainer not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        trainer.setFirstName(SCANNER.nextLine());
        System.out.print("Enter new last name: ");
        trainer.setLastName(SCANNER.nextLine());
        System.out.print("Enter new specialization: ");
        trainer.setSpecialization(SCANNER.nextLine());

        Trainer updatedTrainer = gymFacade.updateTrainer(trainerId, trainer);
        System.out.println("Trainer updated: " + updatedTrainer);
    }

    private static void createTraining(GymFacade gymFacade) {
        System.out.print("Enter trainee ID: ");
        UUID traineeId = UUID.fromString(SCANNER.nextLine());
        System.out.print("Enter trainer ID: ");
        UUID trainerId = UUID.fromString(SCANNER.nextLine());
        System.out.print("Enter training name: ");
        String trainingName = SCANNER.nextLine();
        System.out.print("Enter training type (CARDIO, STRENGTH, YOGA): ");
        TrainingType trainingType = TrainingType.valueOf(SCANNER.nextLine().toUpperCase());

        LocalDate trainingDate = null;
        while (trainingDate == null) {
            System.out.print("Enter training date (yyyy-MM-dd): ");
            String dateInput = SCANNER.nextLine();
            try {
                trainingDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd with valid values.");
            }
        }

        System.out.print("Enter training duration (in minutes): ");
        int trainingDuration = SCANNER.nextInt();
        SCANNER.nextLine(); // Consume the newline character

        Training training = gymFacade.createTraining(traineeId, trainerId, trainingName, trainingType, trainingDate, trainingDuration);
        System.out.println("Training created: " + training);
    }

    private static void getTraining(GymFacade gymFacade) {
        System.out.print("Enter training ID: ");
        UUID trainingId = UUID.fromString(SCANNER.nextLine());

        Training training = gymFacade.getTraining(trainingId);
        if (training != null) {
            System.out.println("Training found: " + training);
        } else {
            System.out.println("Training not found.");
        }
    }
}