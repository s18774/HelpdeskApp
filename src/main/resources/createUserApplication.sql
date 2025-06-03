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
