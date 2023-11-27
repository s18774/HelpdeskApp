package pl.wroblewski.helpdeskapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.wroblewski.helpdeskapp.models.SLA;
import pl.wroblewski.helpdeskapp.repositories.SLARepository;
import pl.wroblewski.helpdeskapp.services.UserService;

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
    public void testService(UserService userService) {

    }
}
