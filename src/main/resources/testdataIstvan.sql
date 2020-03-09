INSERT INTO timeoffcal.roles (id, roleName) VALUES (1, 'admin');
INSERT INTO timeoffcal.roles (id, roleName) VALUES (2, 'user');
INSERT INTO timeoffcal.roles (id, roleName) VALUES (3, 'guest');

INSERT INTO timeoffcal.permissions (id, permissionName) VALUES (1, 'timeOffView');
INSERT INTO timeoffcal.permissions (id, permissionName) VALUES (2, 'adminView');
INSERT INTO timeoffcal.permissions (id, permissionName) VALUES (3, 'changePassword');
INSERT INTO timeoffcal.permissions (id, permissionName) VALUES (4, 'workedHoursLogging');
INSERT INTO timeoffcal.permissions (id, permissionName) VALUES (5, 'manageAccounts');

INSERT INTO timeoffcal.rolePermissionSet (id, roleId, permissionId) VALUES (1, 1, 1);
INSERT INTO timeoffcal.rolePermissionSet (id, roleId, permissionId) VALUES (2, 1, 2);
INSERT INTO timeoffcal.rolePermissionSet (id, roleId, permissionId) VALUES (3, 1, 5);
INSERT INTO timeoffcal.rolePermissionSet (id, roleId, permissionId) VALUES (4, 2, 1);
INSERT INTO timeoffcal.rolePermissionSet (id, roleId, permissionId) VALUES (5, 2, 3);
INSERT INTO timeoffcal.rolePermissionSet (id, roleId, permissionId) VALUES (6, 2, 4);
INSERT INTO timeoffcal.rolePermissionSet (id, roleId, permissionId) VALUES (7, 3, 1);

INSERT INTO timeoffcal.users (id, firstName, middleName, lastName, dob, userName, "password", gender, email, address, postcode, phone, isActive, companyName, lastLogin, accountCreated, roleId)
VALUES (1, 'Gábor', 'Péter', 'Kiss', '1987-03-20', 'kissgabi', 'password', 'male', 'kissgabi@gmail.com', 'Győr, Somogyi Béla u. 10.', '9022', '+36309491513', true, 'Tricast', '2020-03-02 14:05:06', '2010-02-27 15:01:17', 1);
INSERT INTO timeoffcal.users (id, firstName, lastName, dob, userName, "password", gender, email, address, postcode, phone, isActive, companyName, lastLogin, accountCreated, roleId)
VALUES (2, 'Krisztina', 'Szigethy', '1971-09-18', 'krisztike', '12345', 'female', 'sz.krisztina@gmail.com', 'Orfű, Kossuth Lajos u. 82.', '7677', '+36703283265', true, 'Tricast', '2019-07-12 07:49:55', '2019-05-12 04:35:11', 2);
INSERT INTO timeoffcal.users (id, firstName, lastName, dob, userName, "password", gender, email, address, postcode, phone, isActive, companyName, lastLogin, accountCreated, roleId)
VALUES (3, 'Arnold', 'Kárpáti', '1985-07-04', 'arnold', 'x&34!>aaaa', 'male', 'arnold.k@gmail.com', 'Budapest, Csabai kapu 90.', '1074', '+36708830089', true, 'Tricast', DEFAULT, '2017-05-12 21:11:33', 3);

INSERT INTO timeoffcal.workDays (id, userId, "date") VALUES (1, 2, to_date('20200302','YYYYMMDD'));
INSERT INTO timeoffcal.workDays (id, userId, "date") VALUES (2, 2, to_date('2020 Mar 03','YYYY Mon DD'));

INSERT INTO timeoffcal.workTimes (id, startTime, endTime, modifiedStartTime, modifiedEndTime, "type", "comment", modifiedBy, workDayId)
VALUES (1, TO_TIMESTAMP('2020-03-02 8:00','YYYY-MM-DD HH:MI'), TO_TIMESTAMP('2020-03-02 16:00','YYYY-MM-DD HH24:MI'), DEFAULT, DEFAULT, 'office', 'Kellemes nap az irodában.', 1, 1);
INSERT INTO timeoffcal.workTimes (id, startTime, endTime, modifiedStartTime, modifiedEndTime, "type", "comment", modifiedBy, workDayId)
VALUES (2, TO_TIMESTAMP('2020 Mar 03 8:00','YYYY MON DD HH:MI'), TO_TIMESTAMP('2020-03-03 16:00','YYYY-MM-DD HH24:MI'), TO_TIMESTAMP('2020 Mar 03 13:00','YYYY MON DD HH24:MI'), TO_TIMESTAMP('2020 Mar 03 21:00','YYYY MON DD HH24:MI'), 'homeOffice', 'Kellemes munkanap otthon.', 1, 2);

INSERT INTO timeoffcal.offDays (id, "date", "type", status, approvedBy, userId, startTime, endTime)
VALUES (1, to_date('20200304','YYYYMMDD'), 'paid', 'approved', 1, 2, TO_TIMESTAMP('2020 Mar 04 00:01','YYYY MON DD HH24:MI'), TO_TIMESTAMP('2020 Mar 04 23:59','YYYY MON DD HH24:MI'));
INSERT INTO timeoffcal.offDays (id, "date", "type", status, approvedBy, userId, startTime, endTime)
VALUES (2, to_date('20200305','YYYYMMDD'), 'sick', 'pending', DEFAULT, 2, TO_TIMESTAMP('2020 Mar 05 00:01','YYYY MON DD HH24:MI'), TO_TIMESTAMP('2020 Mar 05 23:59','YYYY MON DD HH24:MI'));

INSERT INTO timeoffcal.offDayLimits (id, "year", maximumAmount, "type", userId) VALUES (1, '2020', 27, 'paid', 1);
INSERT INTO timeoffcal.offDayLimits (id, "year", maximumAmount, "type", userId) VALUES (2, '2020', 10, 'sick', 1);
INSERT INTO timeoffcal.offDayLimits (id, "year", maximumAmount, "type", userId) VALUES (3, '2020', 5, 'other', 1);

INSERT INTO timeoffcal.specialDays (id, "date") VALUES (1, to_date('20200315','YYYYMMDD'));
INSERT INTO timeoffcal.specialDays (id, "date") VALUES (2, to_date('2020 Aug 20','YYYY Mon DD'));
INSERT INTO timeoffcal.specialDays (id, "date") VALUES (3, to_date('2020 Oct 23','YYYY Mon DD'));
