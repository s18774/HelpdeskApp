package pl.wroblewski.helpdeskapp.services;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.InvalidCredentialsException;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

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

    public User createUser(User newUser, Integer groupId, Integer departmentId, Integer supervisorId, Integer roleId, Integer userAuthorId)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        Group group = null;
        if (groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        User supervisor = null;
        if(supervisorId != null) {
            supervisor = userRepository.findById(supervisorId).orElseThrow(() -> new EntityNotExists(User.class));
        }

        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotExists(Department.class));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotExists(Role.class));

        newUser.setGroup(group);
        newUser.setEmploymentDate(LocalDate.now());
        newUser.setDepartmentId(department);
        newUser.setSupervisor(supervisor);
        newUser.setRole(role);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        return userRepository.save(newUser);
    }

    public List<Role> getAllRoles() {
        return (List<Role>)roleRepository.findAll();
    }

    public User getUser(Integer userId, Integer userAuthorId) throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if(!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor) && !userAuthorId.equals(userId)) {
            throw new PermissionsException();
        }

        return userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
    }

    @Transactional
    public void updateUser(Integer userId, String firstName, String secondName,
                           String positionName, Integer groupId,
                           Integer supervisorId, Integer departmentId,
                           Integer roleId, String phoneNumber,
                           String email, Integer floor,
                           Integer room, Integer userAuthorId) throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if(!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        User supervisor = null;
        if(supervisorId != null) {
            supervisor = userRepository.findById(supervisorId).orElseThrow(UserNotExistsException::new);
        }

        Department department = null;
        if(departmentId != null) {
            department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotExists(Department.class));
        }

        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotExists(Role.class));

        Group group = null;
        if(groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }
        User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setPositionName(positionName);
        user.setGroup(group);
        user.setSupervisor(supervisor);
        user.setDepartmentId(department);
        user.setRole(role);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setFloor(floor);
        user.setRoom(room); //TODO: brakuje username do edycji

        userRepository.save(user);
    }
}
