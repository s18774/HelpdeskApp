package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.InvalidCredentialsException;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.GroupRepository;
import pl.wroblewski.helpdeskapp.repositories.RoleRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;

    public void authUser(String login, String password) throws InvalidCredentialsException {
        User user = userRepository.findByUsername(login).orElseThrow(InvalidCredentialsException::new);
        //dalsze porowanie hasla itp
        //sprawdzanie do jakiego role należy login
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public List<User> getAllUsers(Integer userAuthorId) throws UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (RoleType.isUser(userAuthor)) {
            return List.of(userAuthor);
        }
        return (List<User>) userRepository.findAll();
    }

    public List<User> getAllUsersDetails(String firstName, String secondName, String positionName, String groupName, Integer userAuthorId) throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        if (groupName != null && !groupName.isEmpty()) {
            return userRepository.findAllByFirstnameAndSecondNameAndPositionNameAndGroupName(firstName, secondName, positionName, groupName);
        } else {
            return userRepository.findAllByFirstnameAndSecondNameAndPositionName(firstName, secondName, positionName);
        }
    }

    public List<User> getAllHelpdesk() throws EntityNotExists {
        Role helpdeskRole = roleRepository.findById(RoleType.HELP_DESK.ordinal()).orElseThrow(() -> new EntityNotExists(Role.class));
        Role adminRole = roleRepository.findById(RoleType.ADMIN.ordinal()).orElseThrow(() -> new EntityNotExists(Role.class));

        ArrayList<User> users = new ArrayList<>(userRepository.findAllByRole(helpdeskRole));
        users.addAll(userRepository.findAllByRole(adminRole));
        return users;
    }

    public User createUser(String firstname, String secondname, String position, Integer groupId, Integer userAuthorId)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        Group group = null;
        if (groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        return userRepository.save(User.builder()
                .firstName(firstname)
                .secondName(secondname)
                .positionName(position)
                .group(group)
                .employmentDate(LocalDate.now())
                .build());
    }
}
