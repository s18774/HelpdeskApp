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
import java.util.ArrayList;
import java.util.List;

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


    public List<UserTicket> getTickets(Integer ticketId, Integer userId, Integer slaId) {
        return (List<UserTicket>) userTicketRepository.findByTicketIdAndUserIdAndSlaId(ticketId, userId, slaId);
    }

    @Transactional
    public void addTicket(Integer slaId, Integer departmentId, Integer floor, String title,
                          String description, Integer userId, Integer userAuthorId,
                          Integer helpdeskId, Integer groupId) throws UserNotExistsException, PermissionsException, EntityNotExists {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (!userId.equals(userAuthorId) && !userHasPermissions(userAuthor)) {
            throw new PermissionsException();
        }

        if (slaId == null) {
            slaId = 1;
        }

        SLA sla = slaRepository.findById(slaId).orElseThrow(() -> new EntityNotExists(SLA.class));
        Department department = null;
        if(departmentId != null) {
            department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotExists(Department.class));
        }

        User helpdesk = null;
        if(helpdeskId != null) {
            helpdesk = userRepository.findById(helpdeskId).orElseThrow(UserNotExistsException::new);
        }

        Group group = null;
        if(groupId != null) {
            group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotExists(Group.class));
        }

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
                .build();
        userTicketRepository.save(userTicket);
    }

    private boolean userHasPermissions(User user) {
        List<String> roles = List.of(RoleType.ADMIN.getName(), RoleType.HELP_DESK.getName());
        return roles.contains(user.getRole().getRoleName());
    }
}
