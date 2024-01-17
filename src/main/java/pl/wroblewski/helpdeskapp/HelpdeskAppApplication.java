package pl.wroblewski.helpdeskapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wroblewski.helpdeskapp.models.Role;
import pl.wroblewski.helpdeskapp.models.SLA;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserDeviceId;
import pl.wroblewski.helpdeskapp.repositories.RoleRepository;
import pl.wroblewski.helpdeskapp.repositories.SLARepository;
import pl.wroblewski.helpdeskapp.repositories.UserDeviceRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;
import pl.wroblewski.helpdeskapp.services.TicketService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class HelpdeskAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpdeskAppApplication.class, args);
//
//        SLA sla = SLA.builder()
//                .slaId(1)
//                .slaLevel("X")
//                .build();
    }

    @Autowired
    public void test(SLARepository slaRepository) {
//        SLA sla = SLA.builder()
//                .slaLevel((short)1)
//                .build();
//        slaRepository.save(sla);
        Optional<SLA> sla = slaRepository.findById(1);
        if(sla.isPresent()) {
            SLA slaObj = sla.get();
            System.out.println(slaObj.getSlaId());
        }

        List<SLA> all = slaRepository.findAll();
        System.out.println(all);

        List<SLA> all5 = slaRepository.findAllBySlaLevelOrderBySlaIdDesc((short)5);
        System.out.println(all5);

        System.out.println(slaRepository.findAllBySlaLevelGreaterThan((short)3));
    }

    @Autowired
    public void testService(TicketService ticketService) {
        var tickets = ticketService.getAllTickets();
        System.out.println();
    }

    @Autowired
    public void testUserDevice(UserDeviceRepository userDeviceRepository) {
        var userDevice = userDeviceRepository.findById(UserDeviceId.builder().deviceId(1).userId(1).build());
        System.out.println();
    }

    @Autowired
    public void init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        if(((ArrayList<Role>) roleRepository.findAll()).isEmpty()) {
            roleRepository.save(Role.builder().roleName("Guest").build());
            roleRepository.save(Role.builder().roleName("User").build());
            roleRepository.save(Role.builder().roleName("Admin").build());
        }

        System.out.println(passwordEncoder.encode("test"));
//        if(userRepository.findByLogin("tester").isEmpty()) {
//            User user = User.builder()
//                    .login("tester")
//                    .password(passwordEncoder.encode("tester"))
//                    .role(roleRepository.findById(2).get())
//                    .build();
//            userRepository.save(user);
//        }
    }
}
