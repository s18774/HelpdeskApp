package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final UserApplicationRepository userApplicationRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final SLARepository slaRepository;
    private final DepartmentRepository departmentRepository;
    private final GroupRepository groupRepository;
    private final StageRepository stageRepository;

    //to do list

    //pobieranie listy wniosków
    //pobieranie wniosków o konkretnym statusie/otwarte zamknięte
    //pobieranie wniosków dla konkretnego użytkownika
    //pobieranie wniosków z konkretnym sla(ważnością wykonania)
    //pobieranie wniosków z danego przedziału czasu od - do
    //
    //tworzenie wniosku(wniosek może stworzyć zalogowany użytkownik, helpdesk, admin)
    //zamykanie wniosku(helpdesk, admin)
    //nadawanie sla do wniosku(helpdesk, admin)
    //przypisanie konkretnego pracownika do wniosku(może to zrobić tylko admin)
    public List<UserApplication> getAllApplications(Integer applicationId, Integer userId, Integer slaId, Integer stageId, Integer userAuthorId) throws UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if(RoleType.isUser(userAuthor)) {
            userId = userAuthorId;
        }
        return userApplicationRepository.findByApplicationIdAndUserIdAndSlaIdAndStageId(applicationId, userId, slaId, stageId);
    }

    public void addApplication(Integer slaId, String subject, String description, Integer userId,
                               Integer userAuthorId, Integer helpdeskId, Integer groupId) throws UserNotExistsException, PermissionsException, EntityNotExists {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        userHasPermissions(user, userAuthor, slaId, helpdeskId, groupId);

        if (slaId == null) {
            slaId = 1;
        }

        SLA sla = slaRepository.findById(slaId).orElseThrow(() -> new EntityNotExists(SLA.class));

        User helpdesk = null;
        if (helpdeskId != null) {
            helpdesk = userRepository.findById(helpdeskId).orElseThrow(UserNotExistsException::new);
        }

        Group group = null;
        if (groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        Application application = Application.builder()
                .subject(subject)
                .description(description)
                .sla(sla)
                .build();
        applicationRepository.save(application);

        UserApplication userApplication = UserApplication.builder()
                .id(new UserApplicationId(user.getUserId(), application.getApplicationId()))
                .user(user)
                .application(application)
                .groupId(group)
                .helpDeskId(helpdesk)
                .build();
        userApplicationRepository.save(userApplication);
    }

    private void userHasPermissions(User user, User userAuthor, Integer slaId, Integer helpdeskId, Integer groupId)
            throws PermissionsException {

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            if (user.getUserId() != userAuthor.getUserId() || slaId != null) {
                throw new PermissionsException();
            }
        }
        if(!RoleType.isAdmin(userAuthor)) {
            if(helpdeskId != null || groupId != null) {
                throw new PermissionsException();
            }
        }

    }

    public UserApplication getApplication(Integer applicationId, Integer userAuthorId) throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        UserApplication userApplication = userApplicationRepository.findByApplicationId(applicationId).orElseThrow(() -> new EntityNotExists(UserTicket.class));

        if(RoleType.isUser(userAuthor) && !Objects.equals(userApplication.getId().getUserId(), userAuthorId)) {
            throw new PermissionsException();
        }
        return userApplication;
    }

    public void updateApplication(Integer applicationId, Integer slaId, Integer stageId, String subject, String description, Integer groupId, Integer helpdeskId, Integer userAuthorId) throws EntityNotExists, UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        UserApplication userApplication = userApplicationRepository.findByApplicationId(applicationId).orElseThrow(() -> new EntityNotExists(UserTicket.class));

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotExists(Stage.class));
        if(stage.getStageName().equals("Closed")) {
            throw new PermissionsException();
        }

        SLA sla = slaRepository.findById(slaId).orElseThrow(() -> new EntityNotExists(SLA.class));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));

        User helpdesk = null;
        if(helpdeskId != null){
            helpdesk = userRepository.findById(helpdeskId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        if(!RoleType.isAdmin(userAuthor) && helpdesk != userApplication.getHelpDeskId()) {
            throw new PermissionsException();
        }

        Application application = userApplication.getApplication();
        application.setSla(sla);
        application.setSubject(subject);
        application.setDescription(description);
        applicationRepository.save(application);

        userApplication.setStageId(stage);
        if(stage.getStageName().equals("Closed")) {
            userApplication.setClosingDate(LocalDate.now());
        }
        userApplication.setHelpDeskId(helpdesk);
        userApplication.setGroupId(group);
        userApplicationRepository.save(userApplication);
    }

    public void closeApplication(Integer applicationId, Integer userAuthorId) throws EntityNotExists, PermissionsException, UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        UserApplication userApplication = userApplicationRepository.findByApplicationId(applicationId).orElseThrow(() -> new EntityNotExists(UserTicket.class));
        if(userApplication.getStageId().getStageName().equals("Closed")) {
            throw new PermissionsException();
        }

        Stage stage = stageRepository.findByStageName("Closed").orElseThrow(() -> new EntityNotExists(Stage.class));
        userApplication.setStageId(stage);
        userApplication.setClosingDate(LocalDate.now());
        userApplicationRepository.save(userApplication);
    }

}
