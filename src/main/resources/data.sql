INSERT INTO Role (role_id, role_name) VALUES (1, 'Guest');
INSERT INTO Role (role_id, role_name) VALUES (2, 'User');
INSERT INTO Role (role_id, role_name) VALUES (3, 'Admin');
INSERT INTO Role (role_id, role_name) VALUES (4, 'HelpDesk');

INSERT INTO _Group (group_id, group_name, is_group_active) VALUES (1, 'Developers', 1);
INSERT INTO _Group (group_id, group_name, is_group_active) VALUES (2, 'Support Team', 1);
INSERT INTO _Group (group_id, group_name, is_group_active) VALUES (3, 'HR & Payroll', 1);
INSERT INTO _Group (group_id, group_name, is_group_active) VALUES (4, 'IT Security', 1);
INSERT INTO _Group (group_id, group_name, is_group_active) VALUES (5, 'Project Management', 0);

INSERT INTO experience_level (exp_id, exp_level) VALUES (1, 'Junior');
INSERT INTO experience_level (exp_id, exp_level) VALUES (2, 'Mid');
INSERT INTO experience_level (exp_id, exp_level) VALUES (3, 'Senior');

INSERT INTO SLA (sla_id, sla_level) VALUES (1, 1);
INSERT INTO SLA (sla_id, sla_level) VALUES (2, 2);
INSERT INTO SLA (sla_id, sla_level) VALUES (3, 3);
INSERT INTO SLA (sla_id, sla_level) VALUES (4, 4);
INSERT INTO SLA (sla_id, sla_level) VALUES (5, 5);

INSERT INTO Stage (stage_id, stage_name) VALUES (1, 'Open');
INSERT INTO Stage (stage_id, stage_name) VALUES (2, 'In progress');
INSERT INTO Stage (stage_id, stage_name) VALUES (3, 'Closed');

INSERT INTO device_type (device_type_id, type_description) VALUES (1, 'Printer');
INSERT INTO device_type (device_type_id, type_description) VALUES (2, 'PC');
INSERT INTO device_type (device_type_id, type_description) VALUES (3, 'Mobile');
INSERT INTO device_type (device_type_id, type_description) VALUES (4, 'Monitor');
INSERT INTO device_type (device_type_id, type_description) VALUES (5, 'Tablet');
INSERT INTO device_type (device_type_id, type_description) VALUES (6, 'Notebook');

INSERT INTO device (device_id, device_type_id, brand, model, serial_number, inventory_number, date_of_purchase, is_guarantee, ip_addres, mac_addres) VALUES
(1, 1, 'HP', 'LaserJet Pro', 'SN0001A1B2', 'INV0001X1Y2', '2020-05-15 00:00:00', 1, NULL, NULL),
(2, 2, 'Dell', 'OptiPlex 3080', 'SN0002C3D4', 'INV0002X3Y4', '2021-02-20 00:00:00', 0, '192.168.1.100', 'a1:b2:c3:d4:e5:f6'),
(3, 3, 'Samsung', 'Galaxy S21', 'SN0003E5F6', 'INV0003X5Y6', '2021-08-10 00:00:00', 1, NULL, NULL),
(4, 4, 'LG', 'UltraGear 27GL650', 'SN0004G7H8', 'INV0004X7Y8', '2020-11-30 00:00:00', 1, '192.168.1.150', 'aa:bb:cc:dd:ee:ff'),
(5, 6, 'Lenovo', 'ThinkPad X1', 'SN0005I9J0', 'INV0005X9Y0', '2022-03-25 00:00:00', 1, '192.168.1.200', '11:22:33:44:55:66'),
(6, 1, 'Canon', 'Pixma TR4550', 'SN0006K1L2', 'INV0006X1Y2', '2023-01-05 00:00:00', 0, NULL, NULL),
(7, 5, 'Apple', 'iPad Pro 12.9', 'SN0007M3N4', 'INV0007X3Y4', '2021-07-12 00:00:00', 1, NULL, NULL),
(8, 2, 'HP', 'EliteDesk 800', 'SN0008O5P6', 'INV0008X5Y6', '2020-09-18 00:00:00', 0, '192.168.1.101', 'ff:ee:dd:cc:bb:aa'),
(9, 4, 'Samsung', 'Odyssey G7', 'SN0009Q7R8', 'INV0009X7Y8', '2022-06-22 00:00:00', 1, '192.168.1.151', '12:34:56:78:90:ab'),
(10, 6, 'Asus', 'ZenBook 14', 'SN0010S9T0', 'INV0010X9Y0', '2023-04-10 00:00:00', 1, '192.168.1.201', 'ab:cd:ef:12:34:56'),
(11, 3, 'Xiaomi', 'Redmi Note 11', 'SN0011U1V2', 'INV0011X1Y2', '2022-11-15 00:00:00', 0, NULL, NULL),
(12, 1, 'Epson', 'WorkForce WF-2860', 'SN0012W3X4', 'INV0012X3Y4', '2021-12-01 00:00:00', 1, NULL, NULL),
(13, 5, 'Huawei', 'MatePad 11', 'SN0013Y5Z6', 'INV0013X5Y6', '2020-07-07 00:00:00', 0, NULL, NULL),
(14, 2, 'Lenovo', 'ThinkCentre M90', 'SN0014Z7A8', 'INV0014X7Y8', '2023-03-18 00:00:00', 1, '192.168.1.102', 'aa:11:bb:22:cc:33'),
(15, 6, 'MSI', 'Katana GF76', 'SN0015B9C0', 'INV0015X9Y0', '2021-04-20 00:00:00', 0, '192.168.1.202', 'dd:44:ee:55:ff:66'),
(16, 4, 'Dell', 'UltraSharp U2722D', 'SN0016D1E2', 'INV0016X1Y2', '2022-08-05 00:00:00', 1, '192.168.1.152', '77:88:99:aa:bb:cc'),
(17, 3, 'Apple', 'iPhone 13', 'SN0017F3G4', 'INV0017X3Y4', '2021-10-30 00:00:00', 1, NULL, NULL),
(18, 5, 'Samsung', 'Tab S8 Ultra', 'SN0018H5I6', 'INV0018X5Y6', '2023-02-14 00:00:00', 1, NULL, NULL),
(19, 2, 'Acer', 'Aspire TC-895', 'SN0019J7K8', 'INV0019X7Y8', '2020-12-25 00:00:00', 0, '192.168.1.103', '12:ab:34:cd:56:ef'),
(20, 6, 'HP', 'Pavilion 15', 'SN0020L9M0', 'INV0020X9Y0', '2022-05-01 00:00:00', 1, '192.168.1.203', 'fe:dc:ba:98:76:54');

INSERT INTO _user (
    user_id, department_id, first_name, second_name,
    phone_number, email, employment_date, username,
    password, position_name, floor, room,
    supervisor_user_id, role_id, group_id, exp_id
) VALUES
(1, 1, 'Jan', 'Kowalski', NULL, 'admin@gmail.com', '2025-05-21', 'admin',
 '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 1, 2, NULL, 3, 1, 3),
(2, 1, 'Adam', 'Nowak', NULL, 'helpdesk@gmail.com', '2025-05-21', 'helpdesk',
 '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 1, 6, NULL, 4, 2, 1),
(3, 1, 'Karol', 'Kowalczyk', NULL, 'user@gmail.com', '2025-05-21', 'user',
 '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 1, 5, 1, 2, 3, 1);

INSERT INTO _user (user_id, department_id, first_name, second_name, phone_number, email, employment_date, username, password, position_name, floor, room, supervisor_user_id, role_id, group_id, exp_id) VALUES
(4, 2, 'Dorota', 'Jaworski', NULL, 'dorota.jaworski4@example.com', '2023-01-22', 'dorotajaworski4', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 4, 6, NULL, 1, 1, 2),
(5, 3, 'Barbara', 'Baran', NULL, 'barbara.baran5@company.net', '2023-01-04', 'barbarabaran5', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 1, 2, NULL, 3, 1, 2),
(6, 4, 'Dorota', 'Sikora', NULL, 'dorota.sikora6@company.net', '2023-06-02', 'dorotasikora6', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 3, 2, 3, 4, 4, 3),
(7, 5, 'Dorota', 'Sikora', NULL, 'dorota.sikora7@company.net', '2023-11-14', 'dorotasikora7', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 4, 4, NULL, 1, 2, 3),
(8, 3, 'Marcin', 'Kowalczyk', NULL, 'marcin.kowalczyk8@mail.com', '2023-06-20', 'marcinkowalczyk8', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 3, 3, 3, 2, 1, 1),
(9, 1, 'Barbara', 'Kaczmarek', NULL, 'barbara.kaczmarek9@mail.com', '2023-09-19', 'barbarakaczmarek9', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 4, 5, NULL, 4, 3, 2),
(10, 1, 'Paweł', 'Dąbrowski', NULL, 'paweł.dąbrowski10@mail.com', '2023-04-06', 'pawełdąbrowski10', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 3, 5, NULL, 4, 5, 2),
(11, 3, 'Michał', 'Kubiak', NULL, 'michał.kubiak11@mail.com', '2023-10-16', 'michałkubiak11', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 3, 3, 1, 4, 2, 1),
(12, 5, 'Grzegorz', 'Baran', NULL, 'grzegorz.baran12@test.org', '2023-03-13', 'grzegorzbaran12', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 2, 6, 1, 1, 2, 1),
(13, 3, 'Monika', 'Woźniak', NULL, 'monika.woźniak13@test.org', '2023-03-25', 'monikawoźniak13', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 5, 4, 3, 3, 5, 3),
(14, 1, 'Michał', 'Wiśniewski', NULL, 'michał.wiśniewski14@mail.com', '2023-05-16', 'michałwiśniewski14', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 1, 9, NULL, 4, 1, 3),
(15, 5, 'Rafał', 'Sikora', NULL, 'rafał.sikora15@example.com', '2023-10-21', 'rafałsikora15', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 2, 1, 2, 2, 4, 2),
(16, 2, 'Grzegorz', 'Sikora', NULL, 'grzegorz.sikora16@company.net', '2023-01-10', 'grzegorzsikora16', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 3, 5, 3, 4, 3, 1),
(17, 3, 'Katarzyna', 'Kamiński', NULL, 'katarzyna.kamiński17@mail.com', '2023-06-09', 'katarzynakamiński17', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 5, 4, 2, 3, 1, 1),
(18, 2, 'Monika', 'Sikora', NULL, 'monika.sikora18@company.net', '2023-12-07', 'monikasikora18', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 1, 2, 2, 3, 4, 3),
(19, 1, 'Michał', 'Wiśniewski', NULL, 'michał.wiśniewski19@mail.com', '2023-09-12', 'michałwiśniewski19', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 2, 7, 2, 1, 4, 1),
(20, 4, 'Mateusz', 'Wiśniewski', NULL, 'mateusz.wiśniewski20@test.org', '2023-04-09', 'mateuszwiśniewski20', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 5, 6, 1, 1, 4, 1),
(21, 1, 'Barbara', 'Sikora', NULL, 'barbara.sikora21@test.org', '2023-05-28', 'barbarasikora21', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 3, 2, 2, 2, 5, 3),
(22, 2, 'Rafał', 'Mazur', NULL, 'rafał.mazur22@mail.com', '2023-09-16', 'rafałmazur22', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Support', 1, 7, 3, 2, 3, 3),
(23, 4, 'Grzegorz', 'Dąbrowski', NULL, 'grzegorz.dąbrowski23@test.org', '2023-02-16', 'grzegorzdąbrowski23', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 5, 6, NULL, 4, 5, 3),
(24, 4, 'Grzegorz', 'Pawlak', NULL, 'grzegorz.pawlak24@mail.com', '2023-09-17', 'grzegorzpawlak24', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 4, 9, 1, 2, 1, 3),
(25, 2, 'Jakub', 'Woźniak', NULL, 'jakub.woźniak25@mail.com', '2023-08-02', 'jakubwoźniak25', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 4, 4, NULL, 4, 2, 2),
(26, 4, 'Jakub', 'Kubiak', NULL, 'jakub.kubiak26@company.net', '2023-03-28', 'jakubkubiak26', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'HR', 1, 6, 1, 3, 3, 2),
(27, 4, 'Joanna', 'Sikora', NULL, 'joanna.sikora27@test.org', '2023-08-25', 'joannasikora27', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 3, 1, NULL, 1, 1, 1),
(28, 4, 'Monika', 'Dąbrowski', NULL, 'monika.dąbrowski28@mail.com', '2023-09-06', 'monikadąbrowski28', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 2, 8, 2, 1, 5, 1),
(29, 1, 'Paweł', 'Kamiński', NULL, 'paweł.kamiński29@company.net', '2023-04-28', 'pawełkamiński29', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 2, 1, 1, 2, 3, 1),
(30, 4, 'Mateusz', 'Baran', NULL, 'mateusz.baran30@company.net', '2023-11-18', 'mateuszbaran30', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 2, 10, NULL, 3, 1, 1),
(31, 1, 'Paweł', 'Szymański', NULL, 'paweł.szymański31@test.org', '2023-06-26', 'pawełszymański31', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'IT', 5, 2, 3, 3, 4, 1),
(32, 4, 'Rafał', 'Pawlak', NULL, 'rafał.pawlak32@example.com', '2023-10-28', 'rafałpawlak32', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Security', 5, 9, 1, 4, 4, 2),
(33, 3, 'Michał', 'Szymański', NULL, 'michał.szymański33@company.net', '2023-10-06', 'michałszymański33', '$2a$12$BEG9mAmDa9BDp6xHd3BRm.tBozCi/dMngVvgkeiL85ialGbF3t.nq', 'Management', 4, 8, NULL, 3, 1, 1);
