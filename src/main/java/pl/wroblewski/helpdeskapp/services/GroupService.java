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

    public Object createGroup(String groupName, int userAuthorId) throws PermissionsException, UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if(!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        return groupRepository.save(Group.builder()
                .groupName(groupName)
                .isGroupActive((byte)1)
                .build());
    }
}
