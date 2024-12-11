package pl.wroblewski.helpdeskapp.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class DatabaseTriggerInit {
    private static final String userTickerTrigger = """          
                CREATE TRIGGER [dbo].[createUserTicket]
                ON [dbo].[user_ticket]
                INSTEAD OF INSERT
                AS
                BEGIN
                    INSERT INTO user_ticket(ticket_id, user_id, closing_date, deadline_date, opening_date, helpdesk_id, stage_id)
                    SELECT 
                    ticket_id, 
                    user_id, 
                    closing_date, 
                    COALESCE(deadline_date, DATEADD(WEEK, 2, GETDATE())), 
                    COALESCE(opening_date, GETDATE()), 
                    helpdesk_id, 
                    stage_id
                                
                    FROM inserted
                END;""";

    private static final String userApplicationTrigger = """   
                CREATE TRIGGER [dbo].[createUserApplication]
                ON [dbo].[user_application]
                INSTEAD OF INSERT
                AS
                BEGIN
                    INSERT INTO [user_application]([application_id], user_id, closing_date, opening_date, helpdesk_id, stage_id, group_id)
                    SELECT 
                    [application_id], 
                    user_id, 
                    closing_date, 
                    COALESCE(opening_date, GETDATE()), 
                    helpdesk_id, 
                    1,
                    group_id
                                
                    FROM inserted
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
        String sql = "SELECT COUNT(*) FROM sys.triggers WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, triggerName);
        return count != null && count > 0;
    }
}
