package com.epam_task.domain;

import lombok.Setter;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends User {

    @EqualsAndHashCode.Include
    @ToString.Include
    private String specialization;

    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID userId;

    public Trainer(String username, String password) {
        super(username, password);
    }

    public Trainer(String firstName, String lastName, String username, String password, Boolean isActive, String specialization, UUID userId) {
        super(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }
}
