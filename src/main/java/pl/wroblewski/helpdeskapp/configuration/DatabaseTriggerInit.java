package pl.wroblewski.helpdeskapp.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class DatabaseTriggerInit {
    private static final String userTickerTrigger = """          
            CREATE TRIGGER createUserTicket
                BEFORE INSERT ON user_ticket
                FOR EACH ROW
                BEGIN
                    INSERT INTO user_ticket (
                        ticket_id,\s
                        user_id,\s
                        closing_date,\s
                        deadline_date,\s
                        opening_date,\s
                        helpdesk_id,\s
                        stage_id
                    )
                    VALUES (
                        NEW.ticket_id,
                        NEW.user_id,
                        NEW.closing_date,
                        COALESCE(NEW.deadline_date, datetime('now', '+14 days')),
                        COALESCE(NEW.opening_date, datetime('now')),
                        NEW.helpdesk_id,
                        NEW.stage_id
                 
                          );
                
                    SELECT RAISE(IGNORE);
                END;
                """;

    private static final String userApplicationTrigger = """   
                
            CREATE TRIGGER createUserApplication
                BEFORE INSERT ON user_application
                FOR EACH ROW
                BEGIN
                    INSERT INTO user_application (
                        application_id,\s
                        user_id,\s
                        closing_date,\s
                        opening_date,\s
                        helpdesk_id,\s
                        stage_id,\s
                        group_id
                    )
                    VALUES (
                        NEW.application_id,
                        NEW.user_id,
                        NEW.closing_date,
                        COALESCE(NEW.opening_date, datetime('now')),
                        NEW.helpdesk_id,
                        1,
                        NEW.group_id
                 
                          );
                
                    SELECT RAISE(IGNORE);
               
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
