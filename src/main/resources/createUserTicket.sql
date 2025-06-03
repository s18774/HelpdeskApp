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
END;
