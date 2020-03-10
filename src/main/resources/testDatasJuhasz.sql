
INSERT INTO TIMEOFFCAL.roles(id, roleName) VALUES(1, 'admin');
INSERT INTO TIMEOFFCAL.roles(id, roleName) VALUES(2, 'user');

INSERT INTO TIMEOFFCAL.rolePermissionSet(id, roleId, permissionId) VALUES (1, 1, 1);
INSERT INTO TIMEOFFCAL.rolePermissionSet(id, roleId, permissionId) VALUES (2, 2, 2);

INSERT INTO TIMEOFFCAL.permissions(id, permissionName) VALUES(1, 'Accept Holiday');
INSERT INTO TIMEOFFCAL.permissions(id, permissionName) VALUES(2, 'View Dashboard');



INSERT INTO TIMEOFFCAL.users(id, firstName, middleName, lastName, dob, userName, password, gender, email, address, postcode, 
							 phone, isActive, companyName, lastLogin, accountCreated, roleId)
							 Values (1, 'Kristóf', '', 'Juhász', 'qwe', 'Nachosm', 'password', 'male', 'juhkris96@gmail.com', 'Győr, Szigethy Attila u, 12', 
									'9086', '06205668478', 't', 'Széchenyi István Egyetem', '2020-03-08', '2020-03-08', 1);


INSERT INTO TIMEOFFCAL.users(id, firstName, middleName, lastName, dob, userName, password, gender, email, address, postcode, 
							 phone, isActive, companyName, lastLogin, accountCreated, roleId)
							 Values (2, 'Tracey', '', 'Morgan', 'asd', 'Tracey', 'password', 'female', 'juhkris96@gmail.com', 'Budapest, Szegedi u, 12', 
									'9086', '06205668478', 't', 'Eclipse', '2020-03-08', '2020-03-08', 2);

INSERT INTO TIMEOFFCAL.workDays(id, userId, date) VALUES (1, 1, '2020-04-9');
INSERT INTO TIMEOFFCAL.workDays(id, userId, date) VALUES (2, 1, '2020-04-10');

INSERT INTO TIMEOFFCAL.workDays(id, userId, date) VALUES (3, 2, '2020-05-9');
INSERT INTO TIMEOFFCAL.workDays(id, userId, date) VALUES (4, 2, '2020-05-10');


INSERT INTO TIMEOFFCAL.worktimes(id, startTime, endTime, "type", "comment", modifiedBy, workDayId)
								VALUES(1, '2020-04-09 8:00', '2020-04-09 17:00', 'normal', 'Normal Day', 1, 1);
INSERT INTO TIMEOFFCAL.worktimes(id, startTime, endTime, modifiedStartTime, modifiedEndTime, "type", "comment", modifiedBy, workDayId)
								VALUES(2, '2020-04-09 8:00', '2020-04-09 17:00', '2020-04-10 9:00', '2020-04-10 18:00', 'normal', 'Happy Day', 1, 2);

INSERT INTO TIMEOFFCAL.worktimes(id, startTime, endTime, "type", "comment", modifiedBy, workDayId)
								VALUES(3, '2020-05-09 8:00', '2020-05-09 17:00', 'normal', 'Normal Day', 1, 3);
INSERT INTO TIMEOFFCAL.worktimes(id, startTime, endTime, modifiedStartTime, modifiedEndTime, "type", "comment", modifiedBy, workDayId)
								VALUES(4, '2020-05-09 8:00', '2020-05-09 17:00', '2020-04-10 9:00', '2020-04-10 18:00', 'normal', 'Happy Day', 1, 4);





INSERT INTO TIMEOFFCAL.offDays(id, "date", "type", status, approvedBy, userId, startTime, endTime)
								Values(1, '2020-04-13', 'freeday', 'accepted', 1, 1, '2020-04-13 8:00', '2020-04-13 12:00');
INSERT INTO TIMEOFFCAL.offDays(id, "date", "type", status, approvedBy, userId, startTime, endTime)
								Values(2, '2020-04-14', 'freeday', 'accepted', 1, 1, '2020-04-14 8:00', '2020-04-14 17:00');

INSERT INTO TIMEOFFCAL.offDays(id, "date", "type", status, approvedBy, userId, startTime, endTime)
								Values(3, '2020-05-13', 'freeday', 'accepted', 1, 2, '2020-05-13 8:00', '2020-05-13 12:00');
INSERT INTO TIMEOFFCAL.offDays(id, "date", "type", status, approvedBy, userId, startTime, endTime)
								Values(4, '2020-05-14', 'freeday', 'accepted', 1, 2, '2020-05-14 8:00', '2020-05-14 17:00');




INSERT INTO TIMEOFFCAL.offDayLimits(id, "year", maximumAmount, "type", userId)
									VALUES(1, '2020', 20, 'freeday', 1);
INSERT INTO TIMEOFFCAL.offDayLimits(id, "year", maximumAmount, "type", userId)
									VALUES(2, '2021', 21, 'freeday', 1);

INSERT INTO TIMEOFFCAL.offDayLimits(id, "year", maximumAmount, "type", userId)
									VALUES(3, '2020', 22, 'freeday', 2);
INSERT INTO TIMEOFFCAL.offDayLimits(id, "year", maximumAmount, "type", userId)
									VALUES(4, '2021', 23, 'freeday', 2);



INSERT INTO TIMEOFFCAL.specialDays(id, "date") VALUES(1, '2020.07.09');
INSERT INTO TIMEOFFCAL.specialDays(id, "date") VALUES(2, '2020.08.20');







									
									

