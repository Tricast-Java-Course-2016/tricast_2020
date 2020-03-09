INSERT INTO timeoffcal.roles (id, rolename)	VALUES (1, 'admin');
INSERT INTO timeoffcal.roles (id, rolename)	VALUES (2, 'employee');
INSERT INTO timeoffcal.roles (id, rolename) VALUES (3, 'guest');

INSERT INTO timeoffcal.permissions (id, permissionname)	VALUES (1, 'admin');
INSERT INTO timeoffcal.permissions (id, permissionname)	VALUES (2, 'user');
INSERT INTO timeoffcal.permissions (id, permissionname)	VALUES (3, 'guest');

INSERT INTO timeoffcal.rolepermissionset (id, roleid, permissionid)	VALUES (1, 1, 1);
INSERT INTO timeoffcal.rolepermissionset (id, roleid, permissionid)	VALUES (2, 2, 2);
INSERT INTO timeoffcal.rolepermissionset (id, roleid, permissionid)	VALUES (3, 3, 3);

INSERT INTO timeoffcal.users (id, firstname, middlename, lastname, dob, username, password, gender, email, address, postcode, phone, isactive, companyname, lastlogin, accountcreated, roleid)
	VALUES (1, 'Béla', 'Gyula', 'Kovács', '1975-02-26', 'kbgy75', '123123123', 'male', 'bela@kovacs.hu', 'Pécs, Petőfi utca 47,', '7621', '+36307964223', 'true', 'Gyula cége', '2020-03-06', '2020-02-29', 1);
INSERT INTO timeoffcal.users (id, firstname, middlename, lastname, dob, username, password, gender, email, address, postcode, phone, isactive, companyname, lastlogin, accountcreated, roleid)
	VALUES (2, 'Kata', 'Viktória', 'Szabó', '1988-10-21', 'szkv88', '951753654', 'female', 'kataviki@freemail.hu', 'Győr, Kossuth utca 8.', '9023', '+36201237854', 'false', 'Gyula cége', '2020-03-07', '2020-03-04', 2);
	
INSERT INTO timeoffcal.workdays (id, userid, date)	VALUES (1, 1, '2020-03-05');
INSERT INTO timeoffcal.workdays (id, userid, date)	VALUES (2, 1, '2020-03-06');

INSERT INTO timeoffcal.worktimes (id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid)
	VALUES (1, '2020-03-05 8:00:00', '2020-03-05 11:30:00', NULL, NULL, 'office', 'testing', 1, 1);
INSERT INTO timeoffcal.worktimes (id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid)
	VALUES (2, '2020-03-05 13:00:00', '2020-03-05 17:30:00', NULL, NULL, 'office', 'programming', 1, 1);
INSERT INTO timeoffcal.worktimes (id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid)
	VALUES (3, '2020-03-06 7:00:00', '2020-03-06 12:30:00', NULL, NULL, 'office', 'programming', 1, 2);
INSERT INTO timeoffcal.worktimes (id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid)
	VALUES (4, '2020-03-06 14:00:00', '2020-03-06 16:30:00', NULL, NULL, 'office', 'testing', 1, 2);

INSERT INTO timeoffcal.offdays (id, date, type, status, approvedby, userid, starttime, endtime)
	VALUES (1, '2020-03-02', 'offday', 'accepted', 1, 1, '2020-03-03 00:00:00', '2020-03-04 00:00:00');
INSERT INTO timeoffcal.offdays (id, date, type, status, approvedby, userid, starttime, endtime)
	VALUES (2, '2020-03-03', 'offday', 'accepted', 1, 1, '2020-03-04 8:00:00', '2020-03-04 12:00:00');

INSERT INTO timeoffcal.offdaylimits (id, year, maximumamount, type, userid)
	VALUES (1, '2020', 22, 'offday', 1);	
INSERT INTO timeoffcal.offdaylimits (id, year, maximumamount, type, userid)
	VALUES (2, '2020', 20, 'offday', 1);
	
INSERT INTO timeoffcal.specialdays (id, date)	VALUES (1, '2020-05-01');
INSERT INTO timeoffcal.specialdays (id, date)	VALUES (2, '2020-08-20');