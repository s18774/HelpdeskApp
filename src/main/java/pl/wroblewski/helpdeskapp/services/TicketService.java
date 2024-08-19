package pl.wroblewski.helpdeskapp.services;

import jakarta.transaction.Transactional;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    //to do list

    //pobieranie listy zgłoszeń
    //pobieranie zgłoszeń o konkretnym statusie/otwarte zamknięte
    //pobieranie zgłoszeń dla konkretnego użytkownika(użytkownik po zalogowaniu może zobaczyć listę swoich zgłoszeń otwartych/zamkniętych)
    //pobieranie zgłoszeń z konkretnym sla(ważnością wykonania)
    //pobieranie zgłoszeń z danego przedziału czasu od - do
    //
    //tworzenie zgłoszenia(zgłoszenie może stworzyć zalogowany użytkownik, helpdesk, admin)
    //zamykanie zgłoszenia(helpdesk, admin)
    //nadawanie sla do zgłoszenia(helpdesk, admin)
    //przypisanie konkretnego pracownika do zgłoszenia(może to zrobić tylko admin)
    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;
    private final UserRepository userRepository;
    private final SLARepository slaRepository;
    private final DepartmentRepository departmentRepository;
    private final GroupRepository groupRepository;
    private final StageRepository stageRepository;


    public List<UserTicket> getTickets(Integer ticketId, Integer userId, Integer slaId, Integer stageId, Integer userAuthorId) throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if(RoleType.isUser(userAuthor)) {
            userId = userAuthorId;
        }
        return (List<UserTicket>) userTicketRepository.findByTicketIdAndUserIdAndSlaIdAndStageId(ticketId, userId, slaId, stageId);
    }

    @Transactional
    public void addTicket(Integer slaId, Integer departmentId, Integer floor, String title,
                          String description, Integer userId, Integer userAuthorId,
                          Integer helpdeskId, Integer groupId) throws UserNotExistsException, PermissionsException, EntityNotExists {
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
                .title(title)
                .description(description)
                .department(department)
                .sla(sla)
                .floor(floor)
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
    }

    public UserTicket getTicket(Integer ticketId, Integer userAuthorId) throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        UserTicket userTicket = userTicketRepository.findByTicketId(ticketId).orElseThrow(() -> new EntityNotExists(UserTicket.class));

        if(RoleType.isUser(userAuthor) && !Objects.equals(userTicket.getId().getUserId(), userAuthorId)) {
            throw new PermissionsException();
        }
        return userTicket;
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

    @Transactional
    public void updateTicket(Integer ticketId, Integer slaId, Integer stageId, String title, String description, Integer helpdeskId, Integer userAuthorId) throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        UserTicket userTicket = userTicketRepository.findByTicketId(ticketId).orElseThrow(() -> new EntityNotExists(UserTicket.class));

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotExists(Stage.class));
        if(stage.getStageName().equals("Closed")) {
            throw new PermissionsException();
        }

        SLA sla = slaRepository.findById(slaId).orElseThrow(() -> new EntityNotExists(SLA.class));

        User helpdesk = null;
        if(helpdeskId != null) {
            helpdesk = userRepository.findById(helpdeskId).orElseThrow(UserNotExistsException::new);
        }

        if(!RoleType.isAdmin(userAuthor) && helpdesk != userTicket.getHelpDeskId()) {
            throw new PermissionsException();
        }

        Ticket ticket = userTicket.getTicket();
        ticket.setSla(sla);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticketRepository.save(ticket);

        userTicket.setStageId(stage);
        if(stage.getStageName().equals("Closed")) {
            userTicket.setClosingDate(LocalDate.now());
        }
        userTicket.setHelpDeskId(helpdesk);
        userTicketRepository.save(userTicket);
    }

    public void closeTicket(Integer ticketId, Integer userAuthorId) throws EntityNotExists, PermissionsException, UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!RoleType.isAdmin(userAuthor) && !RoleType.isHelpdesk(userAuthor)) {
            throw new PermissionsException();
        }

        UserTicket userTicket = userTicketRepository.findByTicketId(ticketId).orElseThrow(() -> new EntityNotExists(UserTicket.class));
        if(userTicket.getStageId().getStageName().equals("Closed")) {
            throw new PermissionsException();
        }

        Stage stage = stageRepository.findByStageName("Closed").orElseThrow(() -> new EntityNotExists(Stage.class));
        userTicket.setStageId(stage);
        userTicket.setClosingDate(LocalDate.now());
        userTicketRepository.save(userTicket);
    }
}

