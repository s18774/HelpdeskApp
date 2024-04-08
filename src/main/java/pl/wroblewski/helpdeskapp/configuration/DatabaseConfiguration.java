package pl.wroblewski.helpdeskapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wroblewski.helpdeskapp.models.Role;
import pl.wroblewski.helpdeskapp.models.SLA;
import pl.wroblewski.helpdeskapp.models.Stage;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.repositories.RoleRepository;
import pl.wroblewski.helpdeskapp.repositories.SLARepository;
import pl.wroblewski.helpdeskapp.repositories.StageRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

import java.util.ArrayList;

@Configuration
public class DatabaseConfiguration {
    @Autowired
    public void initRolesUsers(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        if(((ArrayList<Role>) roleRepository.findAll()).isEmpty()) {
            roleRepository.save(Role.builder().roleId(1).roleName("Guest").build());
            roleRepository.save(Role.builder().roleId(2).roleName("User").build());
            roleRepository.save(Role.builder().roleId(3).roleName("Admin").build());
        }

        if(userRepository.findByUsername("tester").isEmpty()) {
            User user = User.builder()
                    .username("tester")
                    .password(passwordEncoder.encode("tester"))
                    .firstName("Jan")
                    .secondName("Kowalski")
                    .email("kowalski@gmail.com")
                    .role(roleRepository.findById(2).get())
                    .build();
            userRepository.save(user);
        }
    }

    @Autowired
    public void initStages(StageRepository stageRepository) {
        if(((ArrayList<Stage>) stageRepository.findAll()).isEmpty()) {
            stageRepository.save(Stage.builder().stageId(1).stageName("Open").build());
            stageRepository.save(Stage.builder().stageId(2).stageName("In progress").build());
            stageRepository.save(Stage.builder().stageId(3).stageName("Closed").build());
        }
    }

    @Autowired
    public void initSLA(SLARepository slaRepository) {
        if(slaRepository.findAll().isEmpty()) {
            for(int i = 1; i <= 5; i++) {
                slaRepository.save(SLA.builder().slaId(i).slaLevel((short)i).build());
            }
        }
    }
}
