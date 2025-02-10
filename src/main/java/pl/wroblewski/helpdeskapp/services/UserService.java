package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.InvalidRoleException;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExperienceLevelRepository experienceLevelRepository;
    private final LogsService logsService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), new ArrayList<>());
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public List<User> getAllUsers(Integer userAuthorId) throws UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (RoleType.isUser(userAuthor)) {
            return List.of(userAuthor);
        }
        return userRepository.findAllByOrderBySecondName();
    }

    public List<User> getAllUsersDetails(String firstName, String secondName, String positionName,
                                         String groupName, Integer userAuthorId)
            throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }
        if (groupName != null && !groupName.isEmpty()) {
            return userRepository.findAllByFirstnameAndSecondNameAndPositionNameAndGroupName(firstName, secondName,
                    positionName, groupName);
        } else {
            return userRepository.findAllByFirstnameAndSecondNameAndPositionName(firstName, secondName, positionName);
        }
    }

    public List<User> getAllHelpdesk() throws EntityNotExists {
        Role helpdeskRole = roleRepository.findByRoleNameOrderByRoleName(RoleType.HELP_DESK.getName())
                .orElseThrow(() -> new EntityNotExists(Role.class));
        Role adminRole = roleRepository.findByRoleNameOrderByRoleName(RoleType.ADMIN.getName())
                .orElseThrow(() -> new EntityNotExists(Role.class));

        ArrayList<User> users = new ArrayList<>(userRepository.findAllByRoleOrderBySecondName(helpdeskRole));
        users.addAll(userRepository.findAllByRoleOrderBySecondName(adminRole));
        return users.stream().sorted(Comparator.comparing(User::getSecondName)).toList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User createUser(User newUser, Integer groupId, Integer departmentId, Integer supervisorId,
                           Integer roleId, Integer experienceLevelId, Integer userAuthorId)
            throws UserNotExistsException, PermissionsException, EntityNotExists, InvalidRoleException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        Group group = null;
        if (groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        User supervisor = null;
        if (supervisorId != null) {
            supervisor = userRepository.findById(supervisorId).orElseThrow(() -> new EntityNotExists(User.class));
        }

        ExperienceLevel experienceLevel = experienceLevelRepository.findById(experienceLevelId)
                .orElseThrow(() -> new EntityNotExists(ExperienceLevel.class));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotExists(Department.class));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotExists(Role.class));

        if ((role.getRoleName().equals("Admin") || role.getRoleName().equals("HelpDesk"))
                && !department.getDepartmentName().equals("IT")) {
            throw new InvalidRoleException("Admin/HelpDesk have to connect with IT Department");
        }

        newUser.setUserId(0);
        newUser.setGroup(group);
        newUser.setEmploymentDate(LocalDate.now());
        newUser.setDepartmentId(department);
        newUser.setSupervisor(supervisor);
        newUser.setRole(role);
        newUser.setExperienceLevel(experienceLevel);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        newUser = userRepository.save(newUser);
        logsService.log(String.format("%s (%d) crated user: %s (%d)",
                userAuthor.getFullName(),
                userAuthor.getUserId(),
                newUser.getFullName(),
                newUser.getUserId()));
        return newUser;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAllByOrderByRoleName();
    }

    public List<ExperienceLevel> getAllExperienceLevels() {
        return (List<ExperienceLevel>) experienceLevelRepository.findAll();
    }

    public User getUser(Integer userId, Integer userAuthorId) throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor) && !userAuthorId.equals(userId)) {
            throw new PermissionsException();
        }

        return userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateUser(Integer userId, String username, String firstName, String secondName,
                           String positionName, Integer groupId,
                           Integer supervisorId, Integer departmentId,
                           Integer roleId, String phoneNumber,
                           String email, Integer floor,
                           Integer room,
                           Integer experienceLevelId,
                           Integer userAuthorId) throws PermissionsException, UserNotExistsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        User supervisor = null;
        if (supervisorId != null) {
            supervisor = userRepository.findById(supervisorId).orElseThrow(UserNotExistsException::new);
        }

        Department department = null;
        if (departmentId != null) {
            department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotExists(Department.class));
        }

        ExperienceLevel experienceLevel = experienceLevelRepository.findById(experienceLevelId)
                .orElseThrow(() -> new EntityNotExists(ExperienceLevel.class));

        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotExists(Role.class));

        Group group = null;
        if (groupId != null) {
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
        user.setRoom(room);
        user.setExperienceLevel(experienceLevel);
        user.setUsername(username);

        userRepository.save(user);

        logsService.log(String.format("%s (%d) updated user: %s (%d)",
                userAuthor.getFullName(),
                userAuthor.getUserId(),
                user.getFullName(),
                user.getUserId()));
    }
}
