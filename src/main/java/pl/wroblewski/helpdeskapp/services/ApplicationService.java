package pl.wroblewski.helpdeskapp.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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
public class ApplicationService extends BasePermissionService {
    private final UserApplicationRepository userApplicationRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final SLARepository slaRepository;
    private final GroupRepository groupRepository;
    private final StageRepository stageRepository;
    private final LogsService logsService;


    public List<UserApplication> getAllApplications(Integer applicationNumer, Integer userId, Integer slaId,
                                                    Integer stageId, Integer userAuthorId)
            throws UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (RoleType.isUser(userAuthor)) {
            userId = userAuthorId;
        }
        return userApplicationRepository.findByApplicationNumberAndUserIdAndSlaIdAndStageId(applicationNumer,
                userId, slaId, stageId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addApplication(Integer slaId, String subject, String description, Integer userId,
                               Integer userAuthorId, Integer helpdeskId, Integer groupId, String typeOfApplication)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
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
                .applicationNumber(applicationRepository.findMaxApplicationNumber() + 1)
                .subject(subject)
                .description(description)
                .sla(sla)
                .typeOfApplication(typeOfApplication)
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

        logsService.log(String.format("%s (%d) created application (%d)", user.getFullName(),
                user.getUserId(), application.getApplicationId()));
    }

    public UserApplication getApplication(Integer applicationId, Integer userAuthorId)
            throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        UserApplication userApplication = userApplicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new EntityNotExists(UserTicket.class));

        if (RoleType.isUser(userAuthor) && !Objects.equals(userApplication.getId().getUserId(), userAuthorId)) {
            throw new PermissionsException();
        }
        return userApplication;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateApplication(Integer applicationId, Integer slaId, Integer stageId, String subject,
                                  String description, Integer groupId, Integer helpdeskId, Integer userAuthorId)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        UserApplication userApplication = userApplicationRepository
                .findByApplicationId(applicationId).orElseThrow(() -> new EntityNotExists(UserTicket.class));

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotExists(Stage.class));
        if (stage.getStageName().equals("Closed")) {
            throw new PermissionsException();
        }

        SLA sla = slaRepository.findById(slaId).orElseThrow(() -> new EntityNotExists(SLA.class));
        Group group = null;
        if (groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        User helpdesk = null;
        if (helpdeskId != null) {
            helpdesk = userRepository.findById(helpdeskId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        if (!RoleType.isAdmin(userAuthor) && helpdesk != userApplication.getHelpDeskId()) {
            throw new PermissionsException();
        }

        Application application = userApplication.getApplication();
        application.setSla(sla);
        application.setSubject(subject);
        application.setDescription(description);
        applicationRepository.save(application);

        userApplication.setStageId(stage);
        if (stage.getStageName().equals("Closed")) {
            userApplication.setClosingDate(LocalDate.now());
        }
        userApplication.setHelpDeskId(helpdesk);
        userApplication.setGroupId(group);
        userApplicationRepository.save(userApplication);

        logsService.log(String.format("%s (%d) updated application (%d)", userAuthor.getFullName(),
                userAuthor.getUserId(), application.getApplicationId()));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void closeApplication(Integer applicationId, Integer userAuthorId)
            throws EntityNotExists, PermissionsException, UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        UserApplication userApplication = userApplicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new EntityNotExists(UserTicket.class));
        if (userApplication.getStageId().getStageName().equals("Closed")) {
            throw new PermissionsException();
        }

        Stage stage = stageRepository.findByStageName("Closed").orElseThrow(() -> new EntityNotExists(Stage.class));
        userApplication.setStageId(stage);
        userApplication.setClosingDate(LocalDate.now());
        userApplication.setResolverUser(userAuthor);
        userApplicationRepository.save(userApplication);

        logsService.log(String.format("%s (%d) closed application (%d)", userAuthor.getFullName(), userAuthor.getUserId(), userApplication.getId().getApplicationId()));

    }
}
