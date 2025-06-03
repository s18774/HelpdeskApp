package pl.wroblewski.helpdeskapp.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabaseTriggerInit {

    private final JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initTriggers() {
        if (!triggerExists("createUserTicket")) {
            executeSqlFromResource("createUserTicket.sql");
        }
        if (!triggerExists("createUserApplication")) {
            executeSqlFromResource("createUserApplication.sql");
        }
    }

    private boolean triggerExists(String triggerName) {
        String sql = "SELECT COUNT(*) FROM sys.triggers WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, triggerName);
        return count != null && count > 0;
    }

    private void executeSqlFromResource(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            String sql = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
            ).lines().collect(Collectors.joining("\n"));
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute SQL from " + path, e);
        }
    }
}
