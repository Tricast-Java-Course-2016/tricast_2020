INSERT INTO timeoffcal.roles (id, rolename)	VALUES (1, 'admin');
INSERT INTO timeoffcal.roles (id, rolename)	VALUES (2, 'employee');
INSERT INTO timeoffcal.roles (id, rolename) VALUES (3, 'guest');

INSERT INTO timeoffcal.permissions (id, permissionname)	VALUES (1, 'View other holidays');
INSERT INTO timeoffcal.permissions (id, permissionname)	VALUES (2, 'Modify own holidays');
INSERT INTO timeoffcal.permissions (id, permissionname)	VALUES (3, 'Modify all holidays');

INSERT INTO timeoffcal.rolepermissionset (id, roleid, permissionid)	VALUES (1, 1, 3);
INSERT INTO timeoffcal.rolepermissionset (id, roleid, permissionid)	VALUES (2, 1, 2);
INSERT INTO timeoffcal.rolepermissionset (id, roleid, permissionid)	VALUES (3, 1, 1);
INSERT INTO timeoffcal.rolepermissionset (id, roleid, permissionid)	VALUES (4, 2, 2);
INSERT INTO timeoffcal.rolepermissionset (id, roleid, permissionid)	VALUES (5, 3, 2);

-- original password to username 'kbgy75'is'123123123'
-- original password to username'szkv88'is'951753654'
INSERT INTO timeoffcal.users (i d, firstname, middlename, lastname, dob, username, password, gender, email, address, postcode, phone, isactive, companyname, lastlogin, accountcreated, roleid)
	VALUES (1, 'Béla', 'Gyula', 'Kovács', '1975-02-26', 'kbgy75', '$2a$10$jb8VgXdituhO1QUR/8vxwOfNtiT2SR/kPJ2dGATeFgZVrel2K4t.K', 'MALE', 'bela@kovacs.hu', 'Pécs, Petőfi utca 47,', '7621', '+36307964223', 'true', 'Gyula cége', '2020-03-06 8:30:00', '2020-02-29 12:30:00', 1);
INSERT INTO timeoffcal.users (id, firstname, middlename, lastname, dob, username, password, gender, email, address, postcode, phone, isactive, companyname, lastlogin, accountcreated, roleid)
	VALUES (2, 'Kata', 'Viktória', 'Szabó', '1988-10-21', 'szkv88', '$2a$10$v4xte7exIKTaaR3ORw5v9O.tB4mLD5uUyRxs190NoFYOEvzsZKYxO', 'FEMALE', 'kataviki@freemail.hu', 'Győr, Kossuth utca 8.', '9023', '+36201237854', 'false', 'Gyula cége', '2020-03-07 8:13:00', '2020-03-04 11:42:00', 2);
	
INSERT INTO timeoffcal.workdays (id, userid, date)	VALUES (1, 1, '2020-03-05');
INSERT INTO timeoffcal.workdays (id, userid, date)	VALUES (2, 1, '2020-03-06');

INSERT INTO timeoffcal.worktimes (id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid)
	VALUES (1, '2020-03-05 8:00:00', '2020-03-05 11:30:00', NULL, NULL, 'OFFICE', 'testing', 1, 1);
INSERT INTO timeoffcal.worktimes (id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid)
	VALUES (2, '2020-03-05 13:00:00', '2020-03-05 17:30:00', NULL, NULL, 'OFFICE', 'programming', 1, 1);
INSERT INTO timeoffcal.worktimes (id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid)
	VALUES (3, '2020-03-06 7:00:00', '2020-03-06 12:30:00', NULL, NULL, 'OFFICE', 'programming', 1, 2);
INSERT INTO timeoffcal.worktimes (id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid)
	VALUES (4, '2020-03-06 14:00:00', '2020-03-06 16:30:00', NULL, NULL, 'OFFICE', 'testing', 1, 2);

INSERT INTO timeoffcal.offdays (id, date, type, status, approvedby, userid, starttime, endtime)
	VALUES (1, '2020-03-02', 'PAID', 'APPROVED', 1, 1, '2020-03-03 8:00:00', '2020-03-04 17:30:00');
INSERT INTO timeoffcal.offdays (id, date, type, status, approvedby, userid, starttime, endtime)
	VALUES (2, '2020-03-03', 'SICK', 'APPROVED', 1, 1, '2020-03-04 8:00:00', '2020-03-04 12:00:00');

INSERT INTO timeoffcal.offdaylimits (id, year, maximumamount, type, userid)
	VALUES (1, '2020', 22, 'SICK', 1);	
INSERT INTO timeoffcal.offdaylimits (id, year, maximumamount, type, userid)
	VALUES (2, '2020', 20, 'PAID', 1);
	
INSERT INTO timeoffcal.specialdays (id, date)	VALUES (1, '2020-05-01');
INSERT INTO timeoffcal.specialdays (id, date)	VALUES (2, '2020-08-20');