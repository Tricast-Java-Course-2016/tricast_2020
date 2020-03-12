INSERT INTO timeoffcal.roles VALUES (1, 'admin');
INSERT INTO timeoffcal.roles VALUES (2, 'employee');
INSERT INTO timeoffcal.roles VALUES (3, 'guest');

INSERT INTO timeoffcal.users VALUES (1, 'János', 'Aladár', 'Kiss', '1980.04.01.', 'jani80', 'wert56hu', 'MALE', 'janos.kiss@gmail.com', 'Győr, Kossuth utca 10.', '9030', '+36309999111', true, 'Tricast', '2020-03-10 00:00:00+01', '2020-03-04 00:00:00+01', 2);
INSERT INTO timeoffcal.users VALUES (2, 'Géza', 'Béla', 'Nagy', '1981.04.01.', 'geza81', 'asdfjkle45', 'MALE', 'geza.nagy@gmail.com', 'Győr, Petőfi utca 15.', '9026', '+36309999441', true, 'Tricast', '2020-03-10 00:00:00+01', '2020-03-04 00:00:00+01', 1);
INSERT INTO timeoffcal.users VALUES (3, 'Mária', 'Laura', 'Tóth', '1982.04.01.', 'mari82', 'serfdr46', 'FEMALE', 'maria.toth@gmail.com', 'Győr, Széchenyi utca 15.', '9026', '+36309934345', true, 'Tricast', '2020-03-10 00:00:00+01', '2020-03-05 00:00:00+01', 1);
INSERT INTO timeoffcal.users VALUES (4, 'John', NULL, 'Eismann', '1999.03.27.', 'johnny99', 'qwert123', 'MALE', 'johnny99.eismann@gmail.com','London, Garbage street 40', '90876', '+44330993434', true, 'Fourcast', '2020-03-10 00:00:00+01', '2020-03-05 00:00:00+01', 3);

INSERT INTO timeoffcal.workdays VALUES (1, 1, '2020-03-04');
INSERT INTO timeoffcal.workdays VALUES (2, 1, '2020-03-05');
INSERT INTO timeoffcal.workdays VALUES (3, 2, '2020-03-04');

INSERT INTO timeoffcal.worktimes VALUES (1, '2020-03-04 08:00:00+01', '2020-03-04 16:00:00+01', '2020-03-04 08:30:00+01', '2020-03-04 15:00:00+01', 'homeOffice', NULL, 2, 1);
INSERT INTO timeoffcal.worktimes VALUES (2, '2020-03-05 09:00:00+01', '2020-03-05 17:00:00+01', NULL, NULL, 'office', NULL, 1, 2);
INSERT INTO timeoffcal.worktimes VALUES (3, '2020-03-04 07:00:00+01', '2020-03-04 17:00:00+01', NULL, NULL, 'office', NULL, 2, 3);

INSERT INTO timeoffcal.offdays VALUES (1, '2020-06-06', 'leave', 'requeste', NULL, 1, '2020-06-06 08:00:00+02', '2020-06-06 16:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (2, '2020-06-07', 'leave', 'requeste', NULL, 1, '2020-06-07 08:00:00+02', '2020-06-07 16:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (3, '2020-06-08', 'leave', 'requeste', NULL, 1, '2020-06-08 08:00:00+02', '2020-06-08 16:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (4, '2020-06-07', 'leave', 'approved', 3, 2, '2020-06-07 08:00:00+02', '2020-06-07 16:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (5, '2020-06-08', 'leave', 'approved', 3, 2, '2020-06-08 08:00:00+02', '2020-06-08 12:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (6, '2020-06-05', 'sickLea', 'approved', 3, 1, '2020-06-05 08:00:00+02', '2020-06-05 16:00:00+02');


INSERT INTO timeoffcal.offdaylimits VALUES (1, '2020', 24, 'leave', 1);
INSERT INTO timeoffcal.offdaylimits VALUES (2, '2020', 10, 'sickLea', 1);
INSERT INTO timeoffcal.offdaylimits VALUES (3, '2020', 26, 'leave', 2);
INSERT INTO timeoffcal.offdaylimits VALUES (4, '2020', 10, 'sickLea', 2);
INSERT INTO timeoffcal.offdaylimits VALUES (5, '2020', 30, 'leave', 3);
INSERT INTO timeoffcal.offdaylimits VALUES (6, '2020', 10, 'sickLea', 3);

INSERT INTO timeoffcal.specialdays VALUES (1, '2020-08-21');
INSERT INTO timeoffcal.specialdays VALUES (2, '2020-12-19');


INSERT INTO timeoffcal.permissions VALUES (1, 'normalPermission');
INSERT INTO timeoffcal.permissions VALUES (2, 'adminPermission');
INSERT INTO timeoffcal.permissions VALUES (3, 'guestPermission');

INSERT INTO timeoffcal.rolepermissionset VALUES (1, 1, 2);
INSERT INTO timeoffcal.rolepermissionset VALUES (2, 2, 1);
INSERT INTO timeoffcal.rolepermissionset VALUES (3, 3, 3);
