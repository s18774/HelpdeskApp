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
public class TicketService extends BasePermissionService {
    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;
    private final UserRepository userRepository;
    private final SLARepository slaRepository;
    private final DepartmentRepository departmentRepository;
    private final GroupRepository groupRepository;
    private final StageRepository stageRepository;
    private final LogsService logsService;


    public List<UserTicket> getTickets(Integer ticketNumber, Integer userId, Integer slaId,
                                       Integer stageId, Integer userAuthorId)
            throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (RoleType.isUser(userAuthor)) {
            userId = userAuthorId;
        }
        return userTicketRepository.findByTicketNumberAndUserIdAndSlaIdAndStageId(ticketNumber, userId, slaId, stageId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addTicket(Integer slaId, Integer departmentId, Integer floor, String title,
                          String description, Integer userId, Integer userAuthorId,
                          Integer helpdeskId, String roomNumber, Integer groupId)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        userHasPermissions(user, userAuthor, slaId, helpdeskId, groupId);

        if (slaId == null) {
            slaId = 1;
        }

        SLA sla = slaRepository.findById(slaId).orElseThrow(() -> new EntityNotExists(SLA.class));
        Department department = null;
        if (departmentId != null) {
            department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotExists(Department.class));
        }

        User helpdesk = null;
        if (helpdeskId != null) {
            helpdesk = userRepository.findById(helpdeskId).orElseThrow(UserNotExistsException::new);
        }

        Group group = null;
        if (groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        Stage stage = stageRepository.findById(1).orElseThrow(() -> new EntityNotExists(Stage.class));

        Ticket ticket = Ticket.builder()
                .ticketNumber(ticketRepository.findMaxTicketNumber() + 1)
                .title(title)
                .description(description)
                .department(department)
                .sla(sla)
                .floor(floor)
                .roomNumber(roomNumber)
                .build();
        ticketRepository.save(ticket);

        UserTicket userTicket = UserTicket.builder()
                .id(new UserTicketId(user.getUserId(), ticket.getTicketId()))
                .user(user)
                .ticket(ticket)
                .groupId(group)
                .helpDeskId(helpdesk)
                .stageId(stage)
                .build();
        userTicketRepository.save(userTicket);

        logsService.log(String.format("%s (%d) created ticket (%d)", user.getFullName(),
                user.getUserId(), ticket.getTicketId()));
    }

    public UserTicket getTicket(Integer ticketId, Integer userAuthorId)
            throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        UserTicket userTicket = userTicketRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new EntityNotExists(UserTicket.class));

        if (RoleType.isUser(userAuthor) && !Objects.equals(userTicket.getId().getUserId(), userAuthorId)) {
            throw new PermissionsException();
        }
        return userTicket;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateTicket(Integer ticketId, Integer slaId, Integer stageId, String title, String description,
                             Integer helpdeskId, Integer groupId, String roomNumber, Integer floor, Integer userAuthorId)
            throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        UserTicket userTicket = userTicketRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new EntityNotExists(UserTicket.class));

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotExists(Stage.class));
        if (stage.getStageName().equals("Closed")) {
            throw new PermissionsException();
        }

        SLA sla = slaRepository.findById(slaId).orElseThrow(() -> new EntityNotExists(SLA.class));

        User helpdesk = null;
        if (helpdeskId != null) {
            helpdesk = userRepository.findById(helpdeskId).orElseThrow(UserNotExistsException::new);
        }

        Group group = null;
        if(groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

        if (!RoleType.isAdmin(userAuthor) && helpdesk != userTicket.getHelpDeskId()) {
            throw new PermissionsException();
        }

        Ticket ticket = userTicket.getTicket();
        ticket.setSla(sla);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setFloor(floor);
        ticket.setRoomNumber(roomNumber);
        ticketRepository.save(ticket);

        userTicket.setStageId(stage);
        userTicket.setGroupId(group);

        if (stage.getStageName().equals("Closed")) {
            userTicket.setClosingDate(LocalDate.now());
            userTicket.setResolverUser(userAuthor);
        }
        userTicket.setHelpDeskId(helpdesk);
        userTicketRepository.save(userTicket);

        logsService.log(String.format("%s (%d) updated ticket (%d)", userAuthor.getFullName(),
                userAuthor.getUserId(), ticket.getTicketId()));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void closeTicket(Integer ticketId, Integer userAuthorId)
            throws EntityNotExists, PermissionsException, UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        UserTicket userTicket = userTicketRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new EntityNotExists(UserTicket.class));
        if (userTicket.getStageId().getStageName().equals("Closed")) {
            throw new PermissionsException();
        }

        Stage stage = stageRepository.findByStageName("Closed").orElseThrow(() -> new EntityNotExists(Stage.class));
        userTicket.setStageId(stage);
        userTicket.setClosingDate(LocalDate.now());
        userTicket.setResolverUser(userAuthor);
        userTicketRepository.save(userTicket);

        logsService.log(String.format("%s (%d) closed ticket (%d)", userAuthor.getFullName(),
                userAuthor.getUserId(), userTicket.getId().getTicketId()));

    }
}

