package pl.wroblewski.helpdeskapp.services;

import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.models.RoleType;
import pl.wroblewski.helpdeskapp.models.User;

public abstract class BasePermissionService {
    protected void userHasPermissions(User user, User userAuthor, Integer slaId, Integer helpdeskId, Integer groupId)
            throws PermissionsException {

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            if (user.getUserId() != userAuthor.getUserId() || slaId != null) {
                throw new PermissionsException();
            }
        }
        if (!RoleType.isAdmin(userAuthor)) {
            if (helpdeskId != null || groupId != null) {
                throw new PermissionsException();
            }
        }
    }
}
