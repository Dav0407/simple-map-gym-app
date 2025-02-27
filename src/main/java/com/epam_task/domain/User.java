package com.epam_task.domain;

import lombok.Setter;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Include
    @ToString.Include
    private String firstName;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String lastName;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String username;

    private String password; // Excluded from equals & toString for security reasons

    @EqualsAndHashCode.Include
    @ToString.Include
    private Boolean isActive;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
