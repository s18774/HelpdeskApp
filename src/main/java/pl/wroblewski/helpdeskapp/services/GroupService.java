package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.Group;
import pl.wroblewski.helpdeskapp.models.RoleType;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.repositories.GroupRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final LogsService logsService;

    public List<Group> getAllGroups() {
        return groupRepository.findAllByOrderByGroupName();
    }

    public Group createGroup(String groupName, int userAuthorId) throws PermissionsException, UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        Group newGroup = groupRepository.save(Group.builder()
                .groupName(groupName)
                .isGroupActive((byte) 1)
                .build());
        logsService.log(String.format("%s (%d) created group %s (%d)", userAuthor.getFullName(),
                userAuthorId, groupName, newGroup.getGroupId()));
        return newGroup;
    }

    public Group getGroup(Integer groupId, Integer userAuthorId)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        return groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
    }

    public List<User> getGroupUsers(Integer groupId, Integer userAuthorId)
            throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        Group group = getGroup(groupId, userAuthorId);
        return userRepository.findAllByGroupOrderBySecondName(group);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void removeUser(Integer groupId, Integer userId, Integer userAuthorId)
            throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        Group group = getGroup(groupId, userAuthorId);
        User user = userRepository.findByUserIdAndGroup(userId, group).orElseThrow(UserNotExistsException::new);
        user.setGroup(null);
        userRepository.save(user);

        logsService.log(String.format("%s (%d) removed user %s (%d) from group %s (%d)",
                userAuthor.getFullName(),
                userAuthor.getUserId(),
                user.getFullName(),
                user.getUserId(),
                group.getGroupName(),
                group.getGroupId()));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addUser(Integer groupId, Integer userId, Integer userAuthorId)
            throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        Group group = getGroup(groupId, userAuthorId);
        User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        user.setGroup(group);
        userRepository.save(user);

        logsService.log(String.format("%s (%d) added user %s (%d) to group %s (%d)",
                userAuthor.getFullName(),
                userAuthor.getUserId(),
                user.getFullName(),
                user.getUserId(),
                group.getGroupName(),
                group.getGroupId()));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateGroup(Integer groupId, String groupName, Byte isGroupActive, Integer userAuthorId)
            throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        group.setGroupName(groupName);
        group.setIsGroupActive(isGroupActive);
        groupRepository.save(group);

        logsService.log(String.format("%s (%d) updated group %s (%d)", userAuthor.getFullName(),
                userAuthorId, groupName, group.getGroupId()));

    }
}
