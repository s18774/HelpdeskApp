package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.GroupRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public List<Group> getAllGroups() {
        return (List<Group>)groupRepository.findAll();
    }

    public Group createGroup(String groupName, int userAuthorId) throws PermissionsException, UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if(!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        return groupRepository.save(Group.builder()
                .groupName(groupName)
                .isGroupActive((byte)1)
                .build());
    }

    public Group getGroup(Integer groupId, Integer userAuthorId) throws UserNotExistsException, PermissionsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
                throw new PermissionsException();
        }

        return groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
    }

    public List<User> getGroupUsers(Integer groupId, Integer userAuthorId) throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        Group group = getGroup(groupId, userAuthorId);
        return userRepository.findAllByGroup(group);
    }

    public void removeUser(Integer groupId, Integer userId, Integer userAuthorId) throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        Group group = getGroup(groupId, userAuthorId);
        User user = userRepository.findByUserIdAndGroup(userId, group).orElseThrow(UserNotExistsException::new);
        user.setGroup(null);
        userRepository.save(user);
    }

    public void addUser(Integer groupId, Integer userId, Integer userAuthorId) throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        Group group = getGroup(groupId, userAuthorId);
        User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        user.setGroup(group);
        userRepository.save(user);
    }
}
