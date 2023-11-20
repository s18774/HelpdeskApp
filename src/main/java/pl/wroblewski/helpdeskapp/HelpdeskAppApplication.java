package pl.wroblewski.helpdeskapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.wroblewski.helpdeskapp.models.SLA;

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

}
