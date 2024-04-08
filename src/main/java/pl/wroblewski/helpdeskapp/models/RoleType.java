package pl.wroblewski.helpdeskapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public enum RoleType {
    GUEST("Guest"), USER("User"), ADMIN("Admin"), HELP_DESK("HelpDesk");

    public static boolean isAdmin(User user) {
        return user.getRole().getRoleName().equals(RoleType.ADMIN.getName());
    }

    public static boolean isHelpdesk(User user) {
        return user.getRole().getRoleName().equals(RoleType.HELP_DESK.getName());
    }

    private final String name;
}
