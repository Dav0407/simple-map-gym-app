package com.epam_task.domain;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Trainee extends User {

    @EqualsAndHashCode.Include
    @ToString.Include
    private LocalDate date;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String address;

    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID userId;

    public Trainee(String username, String password) {
        super(username, password);
    }

    public Trainee(String firstName, String lastName, String username, String password, Boolean isActive, LocalDate date, String address, UUID userId) {
        super(firstName, lastName, username, password, isActive);
        this.date = date;
        this.address = address;
        this.userId = userId;
    }
}
