INSERT INTO timeoffcal.roles(id, rolename) VALUES	(1, 'admin'), 
													(2, 'worker'),
													(3, 'visitor');


INSERT INTO timeoffcal.permissions(id, permissionname) VALUES   (1, 'add new offday'),
																(3,'add new role'),
																(2, 'accept offday'),
																(4, 'watch dashborad');

INSERT INTO timeoffcal.rolepermissionset(id, roleid, permissionid) VALUES 	(1, 1, 1),
																			(2, 1, 2),
																			(3, 1, 3),
																			(4, 2, 1),
																			(5, 3, 4);

INSERT INTO timeoffcal.specialdays(id, date) VALUES  	(1, '2019-12-25'),
														(2, '2020-01-01'),
														(3, '2020-03-15');
														
INSERT INTO timeoffcal.users(id, firstname, middlename, lastname, dob, username, password, gender, email, address, postcode, phone, isactive, companyname, lastlogin, accountcreated, roleid) VALUES 	(1, 'Márk', '', 'Albrecht', '1994-06-12', 'AlbMark', '12345678', 'MALE', 'alb.mark94@gmail.com', 'Veszprém', '8200','06301231234', 'true', '', '2020-03-06 12:00:00', '2020-03-06 12:00:00', 3),
																																																		(2, 'Balázs', 'Kristóf', 'Tóth', '2000-03-24', 'Bazsi', '12345678', 'MALE', 'alb.mark94@gmail.com', 'Veszprém', '8200','06305672310', 'false', '', '2020-03-08 12:00:00', '2020-03-08 8:00:00', 2),
																																																		(3, 'Béla', '', 'Test', '1990-03-10', 'TESTACC', '12345678', 'MALE', 'bela.bela@gmail.com', 'Veszprém', '8200','063045671234', 'true', '', '2020-02-06 10:00:00', '2019-03-06 15:00:00', 1);
INSERT INTO timeoffcal.workdays(id, userid, date) VALUES  	(1, 1, '2020-01-03'),
															(2, 2, '2020-01-03'),
															(3, 3, '2020-01-03'),
															(4, 2, '2020-01-04'),
															(5, 3, '2020-01-04');
															
INSERT INTO timeoffcal.worktimes(id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid) VALUES 	(1, '2020-01-03 8:00:00', '2020-01-03 16:30:00', null, null, 'OFFICE', 'programming', 1, 1),
																																			(2, '2020-01-03 8:00:00', '2020-01-03 16:30:00', null, null, 'OFFICE', 'testing', 2, 2),
																																			(3, '2020-01-03 8:00:00', '2020-01-03 16:30:00', null, null, 'HOMEOFFICE', 'programming', 3, 3),
																																			(4, '2020-01-04 8:00:00', '2020-01-04 16:30:00', null, null, 'DELEGACY', 'programming', 2, 4),
																																			(5, '2020-01-04 8:00:00', '2020-01-04 16:30:00', null, null, 'OFFICE', 'programming', 2, 5);

INSERT INTO timeoffcal.offdaylimits( id, year, maximumamount, type, userid) VALUES  (1, '2020', 20, 'PAID', 1),
																					(2, '2020', 10, 'SICK', 1),
																					(3, '2020', 5, 'PAID', 2),
																					(4, '2020', 14, 'SICK', 2),
																					(5, '2020', 13, 'PAID', 3),
																					(6, '2020', 5, 'SICK', 3);

INSERT INTO timeoffcal.offdays(id, date, type, status, approvedby, userid, starttime, endtime) VALUES   (1, '2019-12-17', 'SICK', 'APPROVED', 1, 2, '2019-12-17 08:00:00', '2019-12-18 16:30:00'),
																										(2, '2019-12-18', 'PAID', 'APPROVED', 1, 3, '2019-12-17 08:00:00', '2019-12-17 12:00:00'),
																										(3, '2019-12-17', 'PAID', 'APPROVED', 1, 2, '2020-01-01 08:00:00', '2020-01-02 16:30:00'),
																										(4, '2019-12-17', 'SICK', 'REQUESTED', 1, 3, '2020-02-17 08:00:00', '2020-02-17 16:30:00');
