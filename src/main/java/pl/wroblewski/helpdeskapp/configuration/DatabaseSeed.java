package pl.wroblewski.helpdeskapp.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeed {
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final DeviceRepository deviceRepository;
    private final ExperienceLevelRepository experienceLevelRepository;
    private final SLARepository slaRepository;
    private final StageRepository stageRepository;
    private final PasswordEncoder passwordEncoder;
    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;
    private final ApplicationRepository applicationRepository;
    private final UserApplicationRepository userApplicationRepository;

    @Bean
    @Transactional
    public void seedData() {
        seedRoles();
        seedGroups();
        seedDepartments();
        seedExperienceLevels();
        seedUsers();
        seedDeviceTypes();
        seedDevices();
        seedSLA();
        seedStages();
        seedTickets();
        seedApplications();
    }

    private void seedRoles() {
        Role[] roles = {
                Role.builder().roleId(1).roleName("Guest").build(),
                Role.builder().roleId(2).roleName("User").build(),
                Role.builder().roleId(3).roleName("Admin").build(),
                Role.builder().roleId(4).roleName("HelpDesk").build(),
        };
        for (var role : roles) {
            if (!roleRepository.existsById(role.getRoleId())) {
                roleRepository.save(role);
            }
        }
    }

    private void seedGroups() {
        Group[] groups = {
                Group.builder().groupId(1).groupName("Grupa A").isGroupActive((byte) 1).build(),
                Group.builder().groupId(2).groupName("Grupa B").isGroupActive((byte) 1).build(),
                Group.builder().groupId(3).groupName("Grupa C").isGroupActive((byte) 0).build(),
        };
        for (var group : groups) {
            if (!groupRepository.existsById(group.getGroupId())) {
                groupRepository.save(group);
            }
        }
    }

    private void seedUsers() {
        User[] users = {
                User.builder()
                        .email("admin@gmail.com")
                        .positionName("IT")
                        .password(passwordEncoder.encode("admin"))
                        .username("admin")
                        .firstName("Jan")
                        .secondName("Kowalski")
                        .experienceLevel(experienceLevelRepository.findById(1).get())
                        .floor(1)
                        .role(roleRepository.findById(3).get())
                        .employmentDate(LocalDate.now())
                        .departmentId(departmentRepository.findById(1).get())
                        .build(),
                User.builder()
                        .email("helpdesk@gmail.com")
                        .positionName("IT")
                        .password(passwordEncoder.encode("helpdesk"))
                        .username("helpdesk")
                        .firstName("Adam")
                        .secondName("Nowak")
                        .experienceLevel(experienceLevelRepository.findById(1).get())
                        .floor(1)
                        .role(roleRepository.findById(4).get())
                        .employmentDate(LocalDate.now())
                        .departmentId(departmentRepository.findById(1).get())
                        .build(),
                User.builder()
                        .email("user@gmail.com")
                        .positionName("IT")
                        .password(passwordEncoder.encode("user"))
                        .username("user")
                        .firstName("Karol")
                        .secondName("Kowalczyk")
                        .experienceLevel(experienceLevelRepository.findById(1).get())
                        .floor(1)
                        .role(roleRepository.findById(2).get())
                        .employmentDate(LocalDate.now())
                        .departmentId(departmentRepository.findById(1).get())
                        .build()
        };
        for (var user : users) {
            if (!userRepository.existsByUsername(user.getUsername())) {
                userRepository.save(user);
            }
        }
    }

    private void seedDepartments() {
        Department[] departments = {
                Department.builder().departmentId(1).departmentName("IT").building("A").build(),
                Department.builder().departmentId(2).departmentName("HR").building("B").build()
        };
        for (var department : departments) {
            if (!departmentRepository.existsById(department.getDepartmentId())) {
                departmentRepository.save(department);
            }
        }
    }

    private void seedDeviceTypes() {
        DeviceType[] deviceTypes = {
                DeviceType.builder().deviceTypeId(1).typeDescription("Printer").build(),
                DeviceType.builder().deviceTypeId(2).typeDescription("PC").build(),
                DeviceType.builder().deviceTypeId(3).typeDescription("Mobile").build(),
                DeviceType.builder().deviceTypeId(4).typeDescription("Monitor").build(),
                DeviceType.builder().deviceTypeId(5).typeDescription("Tablet").build(),
                DeviceType.builder().deviceTypeId(6).typeDescription("Notebook").build(),
        };
        for (var deviceType : deviceTypes) {
            if (!deviceTypeRepository.existsById(deviceType.getDeviceTypeId())) {
                deviceTypeRepository.save(deviceType);
            }
        }
    }

    private void seedDevices() {
        Device[] devices = {
                Device.builder()
                        .deviceId(1)
                        .deviceType(deviceTypeRepository.findById(3).get())
                        .isGuarantee((byte) 1)
                        .dateOfPurchase(LocalDateTime.now())
                        .brand("Samsung")
                        .serialNumber("987654321")
                        .model("S24")
                        .inventoryNumber("1234")
                        .build(),
                Device.builder()
                        .deviceId(2)
                        .deviceType(deviceTypeRepository.findById(4).get())
                        .isGuarantee((byte) 1)
                        .dateOfPurchase(LocalDateTime.now())
                        .brand("HP")
                        .serialNumber("123456789")
                        .model("P27Q")
                        .inventoryNumber("5678")
                        .build(),
        };
        for (var device : devices) {
            if (!deviceRepository.existsById(device.getDeviceId())) {
                deviceRepository.save(device);
            }
        }
    }

    private void seedExperienceLevels() {
        ExperienceLevel[] experienceLevels = {
                ExperienceLevel.builder().expId(1).expLevel("Junior").build(),
                ExperienceLevel.builder().expId(2).expLevel("Mid").build(),
                ExperienceLevel.builder().expId(3).expLevel("Senior").build(),
        };
        for (var experienceLevel : experienceLevels) {
            if (!experienceLevelRepository.existsById(experienceLevel.getExpId())) {
                experienceLevelRepository.save(experienceLevel);
            }
        }
    }

    private void seedSLA() {
        SLA[] slas = {
                SLA.builder().slaId(1).slaLevel((short) 1).build(),
                SLA.builder().slaId(2).slaLevel((short) 2).build(),
                SLA.builder().slaId(3).slaLevel((short) 3).build(),
                SLA.builder().slaId(4).slaLevel((short) 4).build(),
                SLA.builder().slaId(5).slaLevel((short) 5).build(),
        };
        for (var sla : slas) {
            if (!slaRepository.existsById(sla.getSlaId())) {
                slaRepository.save(sla);
            }
        }
    }

    private void seedStages() {
        Stage[] stages = {
                Stage.builder().stageId(1).stageName("Open").build(),
                Stage.builder().stageId(2).stageName("In progress").build(),
                Stage.builder().stageId(3).stageName("Closed").build()
        };
        for (var stage : stages) {
            if (!stageRepository.existsById(stage.getStageId())) {
                stageRepository.save(stage);
            }
        }
    }

    private void seedTickets() {
        Ticket[] tickets = {
                Ticket.builder()
                        .ticketId(1)
                        .ticketNumber(12)
                        .roomNumber("1A")
                        .floor(1)
                        .description("This is test ticket")
                        .department(departmentRepository.findById(1).get())
                        .title("Ticker no. 1")
                        .sla(slaRepository.findById(1).get())
                        .build(),
                Ticket.builder()
                        .ticketId(2)
                        .ticketNumber(15)
                        .roomNumber("2B")
                        .floor(2)
                        .description("This is test ticket no 2")
                        .department(departmentRepository.findById(2).get())
                        .title("Ticker no. 2")
                        .sla(slaRepository.findById(2).get())
                        .build()
        };


        for (var ticket : tickets) {
            if (!ticketRepository.existsById(ticket.getTicketId())) {
                ticketRepository.save(ticket);
            }
        }

        var user = userRepository.findById(1).get();

        UserTicket[] userTickets = {
                UserTicket.builder()
                        .id(new UserTicketId(user.getUserId(), tickets[0].getTicketId()))
                        .ticket(tickets[0])
                        .user(user)
                        .stageId(stageRepository.findById(1).get())
                        .openingDate(LocalDate.now())
                        .build(),
                UserTicket.builder()
                        .id(new UserTicketId(user.getUserId(), tickets[1].getTicketId()))
                        .ticket(tickets[1])
                        .user(user)
                        .stageId(stageRepository.findById(1).get())
                        .openingDate(LocalDate.now())
                        .build()
        };


        for (var userTicket : userTickets) {
            if(!userTicketRepository.existsById(new UserTicketId(userTicket.getUser().getUserId(), userTicket.getTicket().getTicketId()))) {
                userTicketRepository.save(userTicket);
            }
        }
    }

    private void seedApplications() {
        Application[] applications = {
            Application.builder()
                    .applicationId(1)
                    .sla(slaRepository.findById(1).get())
                    .typeOfApplication("test")
                    .applicationNumber(20)
                    .description("This is test application description")
                    .subject("Test application")
                    .typeOfApplication("Permission request")
                    .build(),
                Application.builder()
                        .applicationId(2)
                        .sla(slaRepository.findById(2).get())
                        .typeOfApplication("test 2")
                        .applicationNumber(21)
                        .description("This is test application description no 2")
                        .subject("Test application 2")
                        .typeOfApplication("Order device")
                        .build()
        };
        for(var application : applications) {
            if (!applicationRepository.existsById(application.getApplicationId())) {
                applicationRepository.save(application);
            }
        }

        var user = userRepository.findById(1).get();

        UserApplication[] userApplications = {
                UserApplication.builder()
                        .id(new UserApplicationId(user.getUserId(), applications[0].getApplicationId()))
                        .application(applications[0])
                        .user(user)
                        .stageId(stageRepository.findById(1).get())
                        .openingDate(LocalDate.now())
                        .build(),
                UserApplication.builder()
                        .id(new UserApplicationId(user.getUserId(), applications[1].getApplicationId()))
                        .application(applications[1])
                        .user(user)
                        .stageId(stageRepository.findById(1).get())
                        .openingDate(LocalDate.now())
                        .build(),
        };

        for(var userApplication : userApplications) {
            if(!userApplicationRepository.existsById(new UserApplicationId(userApplication.getUser().getUserId(), userApplication.getApplication().getApplicationId()))) {
                userApplicationRepository.save(userApplication);
            }
        }

    }
}
