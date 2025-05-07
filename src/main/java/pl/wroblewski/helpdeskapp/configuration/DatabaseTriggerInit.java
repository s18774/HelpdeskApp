package pl.wroblewski.helpdeskapp.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class DatabaseTriggerInit {
    private static final String userTickerTrigger = """          
                CREATE TRIGGER updateUserTicketDefaults
                AFTER INSERT ON user_ticket
                FOR EACH ROW
                BEGIN
                    UPDATE user_ticket
                    SET
                        deadline_date = COALESCE(NEW.deadline_date, datetime('now', '+14 days')),
                        opening_date = COALESCE(NEW.opening_date, datetime('now'))
                    WHERE rowid = NEW.rowid;
                END;
                """;

    private static final String userApplicationTrigger = """   
                CREATE TRIGGER updateUserApplicationDefaults
                AFTER INSERT ON user_application
                FOR EACH ROW
                BEGIN
                    UPDATE user_application
                    SET
                        opening_date = COALESCE(NEW.opening_date, datetime('now')),
                        stage_id = COALESCE(NEW.stage_id, 1)
                    WHERE rowid = NEW.rowid;
                END;
                """;

    private final JdbcTemplate jdbcTemplate;

    @Bean
    public void initTriggers() {
        if(!triggerExists("createUserTicket")) {
            jdbcTemplate.execute(userTickerTrigger);
        }
        if(!triggerExists("createUserApplication")) {
            jdbcTemplate.execute(userApplicationTrigger);
        }
    }

    private boolean triggerExists(String triggerName) {
        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type = 'trigger' AND name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, triggerName);
        return count != null && count > 0;
    }

}
