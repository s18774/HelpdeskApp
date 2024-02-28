package pl.wroblewski.helpdeskapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public enum RoleType {
    GUEST("Guest"), USER("User"), ADMIN("Admin"), HELP_DESK("HelpDesk");

    private final String name;
}
