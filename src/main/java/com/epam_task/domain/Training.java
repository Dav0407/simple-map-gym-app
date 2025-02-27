package com.epam_task.domain;

import com.epam_task.enums.TrainingType;
import lombok.Setter;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id;

    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID traineeId;

    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID trainerId;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String trainingName;

    @EqualsAndHashCode.Include
    @ToString.Include
    private TrainingType trainingType;

    @EqualsAndHashCode.Include
    @ToString.Include
    private LocalDate trainingDate;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer trainingDuration;
}
