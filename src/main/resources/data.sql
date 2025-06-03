INSERT INTO Role (role_name)
VALUES ('Guest');
INSERT INTO Role (role_name)
VALUES ('User');
INSERT INTO Role (role_name)
VALUES ('Admin');
INSERT INTO Role (role_name)
VALUES ('HelpDesk');

INSERT INTO Department (department_name, building)
VALUES ('IT', 'A1'),
       ('Support', 'B2'),
       ('HR', 'C3'),
       ('Security', 'D4'),
       ('Management', 'E5');


INSERT INTO _Group (group_name, is_group_active)
VALUES ('Developers', 1);
INSERT INTO _Group (group_name, is_group_active)
VALUES ('Support Team', 1);
INSERT INTO _Group (group_name, is_group_active)
VALUES ('HR & Payroll', 1);
INSERT INTO _Group (group_name, is_group_active)
VALUES ('IT Security', 1);
INSERT INTO _Group (group_name, is_group_active)
VALUES ('Project Management', 0);

INSERT INTO experience_level (exp_level)
VALUES ('Junior');
INSERT INTO experience_level (exp_level)
VALUES ('Mid');
INSERT INTO experience_level (exp_level)
VALUES ('Senior');

INSERT INTO SLA (sla_level)
VALUES (1);
INSERT INTO SLA (sla_level)
VALUES (2);
INSERT INTO SLA (sla_level)
VALUES (3);
INSERT INTO SLA (sla_level)
VALUES (4);
INSERT INTO SLA (sla_level)
VALUES (5);

INSERT INTO Stage (stage_name)
VALUES ('Open');
INSERT INTO Stage (stage_name)
VALUES ('In progress');
INSERT INTO Stage (stage_name)
VALUES ('Closed');

INSERT INTO device_type (type_description)
VALUES ('Printer');
INSERT INTO device_type (type_description)
VALUES ('PC');
INSERT INTO device_type (type_description)
VALUES ('Mobile');
INSERT INTO device_type (type_description)
VALUES ('Monitor');
INSERT INTO device_type (type_description)
VALUES ('Tablet');
INSERT INTO device_type (type_description)
VALUES ('Notebook');

INSERT INTO device (device_type_id, brand, model, serial_number, inventory_number, date_of_purchase,
                    is_guarantee, ip_addres, mac_addres)
VALUES (1, 'HP', 'LaserJet Pro', 'SN0001A1B2', 'INV0001X1Y2', '2020-05-15 00:00:00', 1, NULL, NULL),
       (2, 'Dell', 'OptiPlex 3080', 'SN0002C3D4', 'INV0002X3Y4', '2021-02-20 00:00:00', 0, '192.168.1.100',
        'a1:b2:c3:d4:e5:f6'),
       (3, 'Samsung', 'Galaxy S21', 'SN0003E5F6', 'INV0003X5Y6', '2021-08-10 00:00:00', 1, NULL, NULL),
       (4, 'LG', 'UltraGear 27GL650', 'SN0004G7H8', 'INV0004X7Y8', '2020-11-30 00:00:00', 1, '192.168.1.150',
        'aa:bb:cc:dd:ee:ff'),
       (6, 'Lenovo', 'ThinkPad X1', 'SN0005I9J0', 'INV0005X9Y0', '2022-03-25 00:00:00', 1, '192.168.1.200',
        '11:22:33:44:55:66'),
       (1, 'Canon', 'Pixma TR4550', 'SN0006K1L2', 'INV0006X1Y2', '2023-01-05 00:00:00', 0, NULL, NULL),
       (5, 'Apple', 'iPad Pro 12.9', 'SN0007M3N4', 'INV0007X3Y4', '2021-07-12 00:00:00', 1, NULL, NULL),
       (2, 'HP', 'EliteDesk 800', 'SN0008O5P6', 'INV0008X5Y6', '2020-09-18 00:00:00', 0, '192.168.1.101',
        'ff:ee:dd:cc:bb:aa'),
       (4, 'Samsung', 'Odyssey G7', 'SN0009Q7R8', 'INV0009X7Y8', '2022-06-22 00:00:00', 1, '192.168.1.151',
        '12:34:56:78:90:ab'),
       (6, 'Asus', 'ZenBook 14', 'SN0010S9T0', 'INV0010X9Y0', '2023-04-10 00:00:00', 1, '192.168.1.201',
        'ab:cd:ef:12:34:56'),
       (3, 'Xiaomi', 'Redmi Note 11', 'SN0011U1V2', 'INV0011X1Y2', '2022-11-15 00:00:00', 0, NULL, NULL),
       (1, 'Epson', 'WorkForce WF-2860', 'SN0012W3X4', 'INV0012X3Y4', '2021-12-01 00:00:00', 1, NULL, NULL),
       (5, 'Huawei', 'MatePad 11', 'SN0013Y5Z6', 'INV0013X5Y6', '2020-07-07 00:00:00', 0, NULL, NULL),
       (2, 'Lenovo', 'ThinkCentre M90', 'SN0014Z7A8', 'INV0014X7Y8', '2023-03-18 00:00:00', 1, '192.168.1.102',
        'aa:11:bb:22:cc:33'),
       (6, 'MSI', 'Katana GF76', 'SN0015B9C0', 'INV0015X9Y0', '2021-04-20 00:00:00', 0, '192.168.1.202',
        'dd:44:ee:55:ff:66'),
       (4, 'Dell', 'UltraSharp U2722D', 'SN0016D1E2', 'INV0016X1Y2', '2022-08-05 00:00:00', 1, '192.168.1.152',
        '77:88:99:aa:bb:cc'),
       (3, 'Apple', 'iPhone 13', 'SN0017F3G4', 'INV0017X3Y4', '2021-10-30 00:00:00', 1, NULL, NULL),
       (5, 'Samsung', 'Tab S8 Ultra', 'SN0018H5I6', 'INV0018X5Y6', '2023-02-14 00:00:00', 1, NULL, NULL),
       (2, 'Acer', 'Aspire TC-895', 'SN0019J7K8', 'INV0019X7Y8', '2020-12-25 00:00:00', 0, '192.168.1.103',
        '12:ab:34:cd:56:ef'),
       (6, 'HP', 'Pavilion 15', 'SN0020L9M0', 'INV0020X9Y0', '2022-05-01 00:00:00', 1, '192.168.1.203',
        'fe:dc:ba:98:76:54');

INSERT INTO _user (department_id, first_name, second_name,
                   phone_number, email, employment_date, username,
                   password, position_name, floor, room,
                   supervisor_user_id, role_id, group_id, exp_id)
VALUES (1, 'Jan', 'Kowalski', NULL, 'admin@gmail.com', '2025-05-21', 'admin',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 1, 2, NULL, 3, 1, 3),
       (1, 'Adam', 'Nowak', NULL, 'helpdesk@gmail.com', '2025-05-21', 'helpdesk',
        '$2a$12$6iHJb8LDP6zVdmXrLtXaUeXEaz1evEz6sbDmGucYmXEccms3VAo/m', 'IT', 1, 6, NULL, 4, 2, 1),
       (1, 'Karol', 'Kowalczyk', NULL, 'user@gmail.com', '2025-05-21', 'user',
        '$2a$12$f3PNefLLQgUXgfL150kuCeTu6277bhLUDCoo6kZWIDqFfbLAHMiMi', 'IT', 1, 5, 1, 2, 3, 1);

INSERT INTO _user (department_id, first_name, second_name, phone_number, email, employment_date, username,
                   password, position_name, floor, room, supervisor_user_id, role_id, group_id, exp_id)
VALUES (2, 'Dorota', 'Jaworski', NULL, 'dorota.jaworski4@example.com', '2023-01-22', 'dorotajaworski4',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 4, 6, NULL, 1, 1, 2),
       (3, 'Barbara', 'Baran', NULL, 'barbara.baran5@company.net', '2023-01-04', 'barbarabaran5',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 1, 2, NULL, 3, 1, 2),
       (4, 'Dorota', 'Sikora', NULL, 'dorota.sikora6@company.net', '2023-06-02', 'dorotasikora6',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 3, 2, 3, 4, 4, 3),
       (5, 'Dorota', 'Sikora', NULL, 'dorota.sikora7@company.net', '2023-11-14', 'dorotasikora7',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 4, 4, NULL, 1, 2, 3),
       (3, 'Marcin', 'Kowalczyk', NULL, 'marcin.kowalczyk8@mail.com', '2023-06-20', 'marcinkowalczyk8',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 3, 3, 3, 2, 1, 1),
       (1, 'Barbara', 'Kaczmarek', NULL, 'barbara.kaczmarek9@mail.com', '2023-09-19', 'barbarakaczmarek9',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 4, 5, NULL, 4, 3, 2),
       (1, 'Paweł', 'Dąbrowski', NULL, 'paweł.dąbrowski10@mail.com', '2023-04-06', 'pawełdąbrowski10',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 3, 5, NULL, 4, 5, 2),
       (3, 'Michał', 'Kubiak', NULL, 'michał.kubiak11@mail.com', '2023-10-16', 'michałkubiak11',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 3, 3, 1, 4, 2, 1),
       (5, 'Grzegorz', 'Baran', NULL, 'grzegorz.baran12@test.org', '2023-03-13', 'grzegorzbaran12',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 2, 6, 1, 1, 2, 1),
       (3, 'Monika', 'Woźniak', NULL, 'monika.woźniak13@test.org', '2023-03-25', 'monikawoźniak13',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 5, 4, 3, 3, 5, 3),
       (1, 'Michał', 'Wiśniewski', NULL, 'michał.wiśniewski14@mail.com', '2023-05-16', 'michałwiśniewski14',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 1, 9, NULL, 4, 1, 3),
       (5, 'Rafał', 'Sikora', NULL, 'rafał.sikora15@example.com', '2023-10-21', 'rafałsikora15',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 2, 1, 2, 2, 4, 2),
       (2, 'Grzegorz', 'Sikora', NULL, 'grzegorz.sikora16@company.net', '2023-01-10', 'grzegorzsikora16',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 3, 5, 3, 4, 3, 1),
       (3, 'Katarzyna', 'Kamiński', NULL, 'katarzyna.kamiński17@mail.com', '2023-06-09', 'katarzynakamiński17',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 5, 4, 2, 3, 1, 1),
       (2, 'Monika', 'Sikora', NULL, 'monika.sikora18@company.net', '2023-12-07', 'monikasikora18',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 1, 2, 2, 3, 4, 3),
       (1, 'Michał', 'Wiśniewski', NULL, 'michał.wiśniewski19@mail.com', '2023-09-12', 'michałwiśniewski19',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 2, 7, 2, 1, 4, 1),
       (4, 'Mateusz', 'Wiśniewski', NULL, 'mateusz.wiśniewski20@test.org', '2023-04-09', 'mateuszwiśniewski20',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 5, 6, 1, 1, 4, 1),
       (1, 'Barbara', 'Sikora', NULL, 'barbara.sikora21@test.org', '2023-05-28', 'barbarasikora21',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 3, 2, 2, 2, 5, 3),
       (2, 'Rafał', 'Mazur', NULL, 'rafał.mazur22@mail.com', '2023-09-16', 'rafałmazur22',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 1, 7, 3, 2, 3, 3),
       (4, 'Grzegorz', 'Dąbrowski', NULL, 'grzegorz.dąbrowski23@test.org', '2023-02-16', 'grzegorzdąbrowski23',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 5, 6, NULL, 4, 5, 3),
       (4, 'Grzegorz', 'Pawlak', NULL, 'grzegorz.pawlak24@mail.com', '2023-09-17', 'grzegorzpawlak24',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 4, 9, 1, 2, 1, 3),
       (2, 'Jakub', 'Woźniak', NULL, 'jakub.woźniak25@mail.com', '2023-08-02', 'jakubwoźniak25',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 4, 4, NULL, 4, 2, 2),
       (4, 'Jakub', 'Kubiak', NULL, 'jakub.kubiak26@company.net', '2023-03-28', 'jakubkubiak26',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 1, 6, 1, 3, 3, 2),
       (4, 'Joanna', 'Sikora', NULL, 'joanna.sikora27@test.org', '2023-08-25', 'joannasikora27',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 3, 1, NULL, 1, 1, 1),
       (4, 'Monika', 'Dąbrowski', NULL, 'monika.dąbrowski28@mail.com', '2023-09-06', 'monikadąbrowski28',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 2, 8, 2, 1, 5, 1),
       (1, 'Paweł', 'Kamiński', NULL, 'paweł.kamiński29@company.net', '2023-04-28', 'pawełkamiński29',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 2, 1, 1, 2, 3, 1),
       (4, 'Mateusz', 'Baran', NULL, 'mateusz.baran30@company.net', '2023-11-18', 'mateuszbaran30',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 2, 10, NULL, 3, 1, 1),
       (1, 'Paweł', 'Szymański', NULL, 'paweł.szymański31@test.org', '2023-06-26', 'pawełszymański31',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 5, 2, 3, 3, 4, 1),
       (4, 'Rafał', 'Pawlak', NULL, 'rafał.pawlak32@example.com', '2023-10-28', 'rafałpawlak32',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 5, 9, 1, 4, 4, 2),
       (3, 'Michał', 'Szymański', NULL, 'michał.szymański33@company.net', '2023-10-06', 'michałszymański33',
        '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 4, 8, NULL, 3, 1, 1);

INSERT INTO Ticket (sla_id, department_id, ticket_number, floor, room_number, titile, description)
VALUES (3, 1, 1001, 1, '101', 'Brak internetu', 'Użytkownik zgłasza brak połączenia z internetem od rana.'),
       (2, 2, 1002, 2, '202', 'Niesprawna drukarka', 'Drukarka HP nie reaguje na polecenia drukowania.'),
       (5, 3, 1003, 3, '303', 'Problem z logowaniem', 'Nie można zalogować się do systemu HR.'),
       (1, 4, 1004, 4, '404', 'Awaria monitora', 'Monitor miga i wyświetla przekłamane kolory.'),
       (4, 1, 1005, 5, '105', 'Utrata danych', 'Użytkownik przypadkowo skasował folder z ważnymi plikami.'),
       (2, 2, 1006, 1, '109', 'Brak dostępu do serwera', 'Nie można połączyć się z serwerem plików.'),
       (3, 3, 1007, 3, '307', 'Błąd aplikacji kadrowej',
        'Podczas otwierania aplikacji pojawia się komunikat o błędzie.'),
       (1, 4, 1008, 2, '208', 'Wolno działający komputer', 'Komputer uruchamia się bardzo długo i zacina.'),
       (5, 1, 1009, 4, '410', 'Awaria sieci Wi-Fi', 'Sieć bezprzewodowa przestała działać na całym piętrze.'),
       (2, 2, 1010, 1, '111', 'Problem z hasłem', 'Użytkownik zapomniał hasła do systemu.'),
       (4, 3, 1011, 2, '206', 'Zła konfiguracja maila', 'Nowy pracownik nie otrzymuje maili.'),
       (3, 4, 1012, 5, '505', 'Nie działa myszka', 'Myszka USB przestała reagować na ruchy.'),
       (1, 1, 1013, 3, '312', 'Problem z VPN', 'Nie można połączyć się z siecią firmową zdalnie.'),
       (2, 2, 1014, 4, '412', 'Zamówienie sprzętu', 'Prośba o przygotowanie nowego laptopa dla nowego pracownika.'),
       (5, 3, 1015, 1, '103', 'Awaria systemu płacowego', 'System nie przelicza składek ZUS poprawnie.'),
       (3, 4, 1016, 2, '204', 'Awaria drukarki sieciowej', 'Drukarka zgłasza błąd tonera mimo wymiany.'),
       (1, 1, 1017, 5, '502', 'Laptop nie uruchamia się', 'Po naciśnięciu przycisku zasilania brak reakcji.'),
       (4, 2, 1018, 3, '303', 'Monitor nie wykrywa sygnału', 'Monitor pozostaje czarny mimo podłączonego kabla.'),
       (2, 3, 1019, 1, '102', 'Brak dostępności plików', 'Brak dostępu do katalogów działu HR.'),
       (5, 4, 1020, 4, '406', 'Spalony zasilacz', 'Komputer nie działa, zasilacz wydzielał zapach spalenizny.');


INSERT INTO Application (sla_id, subject, type_of_application, application_number, description)
VALUES (1, 'Prośba o VPN', 'Request', 2001, 'Użytkownik potrzebuje dostępu do VPN w celu pracy zdalnej.'),
       (2, 'Zmiana hasła', 'Request', 2002, 'Prośba o zresetowanie hasła do konta domenowego.'),
       (3, 'Problem z fakturą', 'Complaint', 2003, 'Zgłoszenie błędów w fakturach za oprogramowanie.'),
       (4, 'Dostęp do SharePoint', 'Access', 2004, 'Wniosek o dostęp do zasobów zespołu w SharePoint.'),
       (5, 'Nowy laptop', 'Request', 2005, 'Prośba o zamówienie nowego laptopa do pracy zdalnej.'),
       (2, 'Usunięcie konta', 'Request', 2006, 'Prośba o usunięcie konta użytkownika po zakończeniu współpracy.'),
       (3, 'Zmiana adresu e-mail', 'Change', 2007, 'Potrzebna aktualizacja adresu e-mail po zmianie nazwiska.'),
       (1, 'Instalacja MS Project', 'Request', 2008, 'Wniosek o instalację programu Microsoft Project.'),
       (4, 'Brak uprawnień do folderu', 'Access', 2009, 'Użytkownik nie ma dostępu do folderu współdzielonego działu.'),
       (5, 'Niewłaściwy login', 'Complaint', 2010, 'Logowanie do systemu kończy się błędem użytkownika.'),
       (2, 'Aktualizacja danych osobowych', 'Change', 2011, 'Użytkownik zgłasza nieaktualne dane w systemie HR.'),
       (1, 'Dodanie nowego użytkownika', 'Request', 2012, 'Wniosek o założenie konta dla nowego pracownika.'),
       (4, 'Brak dostępu do poczty', 'Complaint', 2013, 'Użytkownik nie może zalogować się do poczty firmowej.'),
       (3, 'Dostęp do systemu ERP', 'Access', 2014, 'Potrzebny dostęp do systemu ERP w celach analitycznych.'),
       (5, 'Problem z uprawnieniami', 'Inquiry', 2015, 'Czy użytkownik może mieć dostęp do danych działu IT?'),
       (2, 'Nowe oprogramowanie', 'Request', 2016, 'Wniosek o instalację Adobe Acrobat Pro.'),
       (1, 'Błąd systemu rekrutacyjnego', 'Complaint', 2017, 'Nie można zapisać kandydatów w systemie.'),
       (4, 'Zgłoszenie błędu', 'Complaint', 2018, 'Podczas eksportu danych pojawia się błąd krytyczny.'),
       (3, 'Przeniesienie konta', 'Change', 2019, 'Użytkownik zmienia dział – prośba o migrację konta.'),
       (2, 'Konto czasowe', 'Request', 2020, 'Prośba o konto z ograniczonym dostępem dla gościa.');

INSERT INTO user_ticket (user_id, ticket_id, stage_id, helpdesk_id, group_id, opening_date, closing_date, deadline_date,
                         resolver_user)
VALUES (19, 1, 2, 14, 5, '2023-06-02', '2023-06-12', '2023-06-16', 13),
       (14, 2, 1, 30, 4, '2023-05-16', NULL, '2023-05-30', 12),
       (1, 3, 1, 8, 4, '2023-10-07', '2023-11-06', NULL, 3),
       (4, 4, 2, 11, 1, '2023-02-01', '2023-03-05', '2023-02-15', 11),
       (5, 5, 2, 6, 5, '2023-07-19', '2023-07-30', '2023-08-02', 8),
       (20, 6, 3, 32, 3, '2023-02-21', '2023-04-06', NULL, 1),
       (2, 7, 2, 30, 5, '2023-11-11', NULL, '2023-11-25', 17),
       (3, 8, 2, 26, 2, '2023-08-11', NULL, '2023-08-25', 12),
       (9, 9, 1, 24, 5, '2023-09-01', NULL, NULL, 22),
       (3, 10, 2, 1, 5, '2023-11-24', '2024-01-02', '2023-12-08', 20),
       (21, 11, 3, 30, 1, '2023-08-15', NULL, NULL, 30),
       (15, 12, 3, 3, 3, '2023-12-17', NULL, NULL, 10),
       (3, 13, 1, 2, 2, '2023-04-01', '2023-04-14', '2023-04-15', 20),
       (12, 14, 1, 5, 2, '2023-04-09', NULL, '2023-04-23', 28),
       (25, 15, 2, 11, 5, '2023-08-14', '2023-09-19', NULL, 7),
       (10, 16, 3, 20, 4, '2023-02-02', '2023-02-13', NULL, 17),
       (6, 17, 3, 7, 3, '2023-01-19', '2023-03-17', '2023-02-02', 14),
       (1, 18, 3, 16, 5, '2023-03-21', NULL, '2023-04-04', 23),
       (7, 19, 2, 27, 5, '2023-05-11', NULL, NULL, 23),
       (11, 20, 2, 27, 1, '2023-06-11', '2023-07-01', NULL, 9);



INSERT INTO user_application (user_id, application_id, stage_id, helpdesk_id, group_id, opening_date, closing_date,
                              resolver_user)
VALUES (20, 1, 1, 9, 4, '2023-11-15', NULL, 12),
       (24, 2, 2, 11, 2, '2023-01-29', NULL, 22),
       (7, 3, 2, 8, 3, '2023-08-03', '2023-09-07', 8),
       (23, 4, 2, 18, 2, '2023-07-28', '2023-08-23', 27),
       (19, 5, 2, 21, 4, '2023-03-01', NULL, 26),
       (21, 6, 1, 25, 3, '2023-01-04', '2023-02-12', 4),
       (1, 7, 2, 17, 3, '2023-08-30', NULL, 11),
       (12, 8, 3, 21, 5, '2023-03-06', NULL, 6),
       (13, 9, 3, 3, 1, '2023-06-08', '2023-06-23', 4),
       (1, 10, 3, 25, 1, '2023-07-20', '2023-09-02', 25),
       (5, 11, 3, 3, 1, '2023-02-23', '2023-04-03', 1),
       (19, 12, 1, 4, 1, '2023-12-18', NULL, 28),
       (2, 13, 2, 32, 1, '2023-03-25', NULL, 2),
       (16, 14, 1, 26, 1, '2023-02-06', '2023-03-21', 6),
       (13, 15, 1, 21, 5, '2023-04-18', '2023-05-17', 6),
       (30, 16, 1, 8, 1, '2023-02-01', NULL, 13),
       (10, 17, 2, 1, 3, '2023-01-07', '2023-02-14', 8),
       (20, 18, 2, 19, 1, '2023-08-25', NULL, 1),
       (2, 19, 1, 28, 5, '2023-10-04', NULL, 11),
       (24, 20, 2, 18, 3, '2023-09-14', NULL, 22);


INSERT INTO user_device (user_id, device_id, location_of_device)
VALUES (13, 1, '58C'),
       (19, 2, '44D'),
       (27, 3, '10B'),
       (6, 4, '34A'),
       (14, 5, '34B'),
       (1, 6, '41A'),
       (23, 7, '33A'),
       (14, 8, '23D'),
       (11, 9, '14C'),
       (28, 10, '53A'),
       (16, 11, '53B'),
       (23, 12, '10A'),
       (10, 13, '41B'),
       (32, 14, '45C'),
       (24, 15, '50D'),
       (6, 16, '14D'),
       (8, 17, '40C'),
       (24, 18, '40A'),
       (6, 19, '12B'),
       (15, 20, '14B');