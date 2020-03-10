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
														
INSERT INTO timeoffcal.users(id, firstname, middlename, lastname, dob, username, password, gender, email, address, postcode, phone, isactive, companyname, lastlogin, accountcreated, roleid) VALUES 	(1, 'Márk', '', 'Albrecht', '1994-06-12', 'AlbMark', '12345678', 'male', 'alb.mark94@gmail.com', 'Veszprém', '8200','06301231234', 'true', '', '2020-03-06 12:00:00', '2020-03-06 12:00:00', 3),
																																																		(2, 'Balázs', 'Kristóf', 'Tóth', '2000-03-24', 'Bazsi', '12345678', 'male', 'alb.mark94@gmail.com', 'Veszprém', '8200','06305672310', 'false', '', '2020-03-08 12:00:00', '2020-03-08 8:00:00', 2),
																																																		(3, 'Béla', '', 'Test', '1990-03-10', 'TESTACC', '12345678', 'male', 'bela.bela@gmail.com', 'Veszprém', '8200','063045671234', 'true', '', '2020-02-06 10:00:00', '2019-03-06 15:00:00', 1);
INSERT INTO timeoffcal.workdays(id, userid, date) VALUES  	(1, 1, '2020-01-03'),
															(2, 2, '2020-01-03'),
															(3, 3, '2020-01-03'),
															(4, 2, '2020-01-04'),
															(5, 3, '2020-01-04');
															
INSERT INTO timeoffcal.worktimes(id, starttime, endtime, modifiedstarttime, modifiedendtime, type, comment, modifiedby, workdayid) VALUES 	(1, '2020-01-03 8:00:00', '2020-01-03 16:30:00', null, null, 'office', 'programming', 1, 1),
																																			(2, '2020-01-03 8:00:00', '2020-01-03 16:30:00', null, null, 'office', 'testing', 2, 2),
																																			(3, '2020-01-03 8:00:00', '2020-01-03 16:30:00', null, null, 'homeoffice', 'programming', 3, 3),
																																			(4, '2020-01-04 8:00:00', '2020-01-04 16:30:00', null, null, 'office', 'programming', 2, 4),
																																			(5, '2020-01-04 8:00:00', '2020-01-04 16:30:00', null, null, 'office', 'programming', 2, 5);

INSERT INTO timeoffcal.offdaylimits( id, year, maximumamount, type, userid) VALUES  (1, '2020', 20, 'offday', 1),
																					(2, '2020', 10, 'sick', 1),
																					(3, '2020', 5, 'offday', 2),
																					(4, '2020', 14, 'sick', 2),
																					(5, '2020', 13, 'offday', 3),
																					(6, '2020', 5, 'sick', 3);

INSERT INTO timeoffcal.offdays(id, date, type, status, approvedby, userid, starttime, endtime) VALUES   (1, '2019-12-17', 'sick', 'accepted', 1, 2, '2019-12-17 00:00:00', '2019-12-18 00:00:00'),
																										(2, '2019-12-18', 'offday', 'accepted', 1, 3, '2019-12-17 08:00:00', '2019-12-17 12:00:00'),
																										(3, '2019-12-17', 'offday', 'accepted', 1, 2, '2020-01-01 00:00:00', '2020-01-02 00:00:00'),
																										(4, '2019-12-17', 'sick', 'accepted', 1, 3, '2020-02-17 00:00:00', '2020-02-17 00:00:00');
