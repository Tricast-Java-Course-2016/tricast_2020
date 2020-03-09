INSERT INTO timeoffcal.roles VALUES (1, 'admin');
INSERT INTO timeoffcal.roles VALUES (2, 'employee');
INSERT INTO timeoffcal.roles VALUES (3, 'guest');

INSERT INTO timeoffcal.users VALUES ('6'::numeric, '2020-03-08 00:00:00+01'::timestamp with time zone, '2020-03-08 00:00:00+01'::timestamp with time zone, '3'::integer, 'Tricast'::character varying(50), true::boolean, '+36301111111'::character varying(12), '9023'::character varying(6), 'Győr, Szent István u. 1'::character varying(300), 'bela.lil@gmail.com'::character varying(100), 'male'::character varying(6), 'jelszo12'::character varying(30), 'Béla'::character varying(60), 'Kiss'::character varying(60), '1992.3.5.'::character varying(11), 'Bela12'::character varying(30));
INSERT INTO timeoffcal.users VALUES ('5'::numeric, 'Robert'::character varying(60), NULL::character varying(60), 'Horváth'::character varying(60), '1995.10.15.'::character varying(11), 'Boby'::character varying(30), 'BaTMan'::character varying(30), 'male'::character varying(6), 'horvatrobert995@gmail.com'::character varying(100), 'Győr, Földes Gábor u. 35/A'::character varying(300), '9023'::character varying(6), '+36204444444'::character varying(12), true::boolean, 'Tricast'::character varying(50), '2020-03-08 00:00:00+01'::timestamp with time zone, '2020-03-08 00:00:00+01'::timestamp with time zone, '2'::integer));

INSERT INTO timeoffcal.worktimes VALUES ('1'::numeric, '2020-03-09 08:00:00+01'::timestamp with time zone, '2020-03-09 16:00:00+01'::timestamp with time zone, '1'::integer, 'Office'::character varying(11), '2020-03-09 09:00:00+01'::timestamp with time zone, '2020-03-09 16:00:00+01'::timestamp with time zone, '6'::integer)
INSERT INTO timeoffcal.worktimes VALUES ('2'::numeric, '2020-03-04 08:00:00+01'::timestamp with time zone, '2020-03-04 12:00:00+01'::timestamp with time zone, '2020-03-04 08:00:00+01'::timestamp with time zone, '2020-03-04 16:00:00+01'::timestamp with time zone, '5'::integer, '2'::integer, 'Home Office'::character varying(11));

INSERT INTO timeoffcal.offdays VALUES (1, '2020-05-06', 'leave', 'requested', NULL, 1, '2020-05-06 08:00:00+02', '2020-05-06 16:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (2, '2020-05-07', 'leave', 'requested', NULL, 1, '2020-05-07 08:00:00+02', '2020-05-07 16:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (3, '2020-05-08', 'leave', 'requested', NULL, 1, '2020-05-08 08:00:00+02', '2020-06-08 16:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (4, '2020-05-07', 'leave', 'approved', 6, 5, '2020-05-07 08:00:00+02', '2020-05-07 16:00:00+02');
INSERT INTO timeoffcal.offdays VALUES (5, '2020-03-10', 'sickLea', 'approved', 6, 5, '2020-03-10 08:00:00+02', '2020-03-10 16:00:00+02');

INSERT INTO timeoffcal.offdaylimits VALUES (1, '2020', 24, 'leave', 5);
INSERT INTO timeoffcal.offdaylimits VALUES (2, '2020', 10, 'sickLea', 5);
INSERT INTO timeoffcal.offdaylimits VALUES (3, '2020', 24, 'leave', 6);
INSERT INTO timeoffcal.offdaylimits VALUES (4, '2020', 10, 'sickLea', 6);

INSERT INTO timeoffcal.specialdays VALUES (1, '2020-08-21');
INSERT INTO timeoffcal.specialdays VALUES (2, '2020-12-19');

INSERT INTO timeoffcal.permissions VALUES (1, 'employeePermission');
INSERT INTO timeoffcal.permissions VALUES (2, 'adminPermission');
INSERT INTO timeoffcal.permissions VALUES (3, 'guestPermission');
INSERT INTO timeoffcal.permissions VALUES (4, 'timeOffView');
INSERT INTO timeoffcal.permissions VALUES (5, 'adminView');
INSERT INTO timeoffcal.permissions VALUES (6, 'accountManagement');

INSERT INTO timeoffcal.rolepermissionset VALUES (1, 1, 2);
INSERT INTO timeoffcal.rolepermissionset VALUES (2, 2, 1);
INSERT INTO timeoffcal.rolepermissionset VALUES (3, 3, 3);
INSERT INTO timeoffcal.rolepermissionset VALUES (4, 1, 4);
INSERT INTO timeoffcal.rolepermissionset VALUES (5, 1, 5);
INSERT INTO timeoffcal.rolepermissionset VALUES (6, 1, 6);
