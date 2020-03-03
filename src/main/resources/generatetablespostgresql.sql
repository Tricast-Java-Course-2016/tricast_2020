DO $$
BEGIN
    IF EXISTS
    (SELECT 
      schema_name
    FROM 
      information_schema.schemata 
    WHERE 
      schema_name = 'timeoffcal')
    THEN
      DROP SCHEMA TIMEOFFCAL CASCADE; 
    END IF; 

    CREATE SCHEMA IF NOT EXISTS TIMEOFFCAL;
	
	CREATE TABLE TIMEOFFCAL.roles
	(
	  id SERIAL NOT NULL, 
	  roleName VARCHAR(60) NOT NULL,
	  CONSTRAINT Roles_PK PRIMARY KEY (id)
	);
	
	CREATE TABLE TIMEOFFCAL.permissions
	(
	  id SERIAL NOT NULL,
	  permissionName VARCHAR(60) NOT NULL,
	  CONSTRAINT Permissions_PK PRIMARY KEY (id)
	);
	
	CREATE TABLE TIMEOFFCAL.rolePermissionSet
	(
	  id SERIAL NOT NULL,
	  roleId INTEGER NOT NULL,
	  permissionId INTEGER NOT NULL,
	  CONSTRAINT RolePermissionSet_PK PRIMARY KEY (id),
	  CONSTRAINT RolePermissionSet_Roles_FK FOREIGN KEY (roleId) REFERENCES TIMEOFFCAL.roles (id),
	  CONSTRAINT RolePermissionSet_Permissions_FK FOREIGN KEY (permissionId) REFERENCES TIMEOFFCAL.permissions (id)
	);

	CREATE TABLE TIMEOFFCAL.users
	( 
	  id SERIAL NOT NULL, 
	  firstName VARCHAR(60) NOT NULL,
	  middleName VARCHAR(60),
	  lastName VARCHAR(60) NOT NULL,
	  dob VARCHAR(11) NOT NULL,
	  userName VARCHAR(30) NOT NULL,
	  password VARCHAR(30) NOT NULL,
	  gender VARCHAR(6) NOT NULL,
	  email VARCHAR(100) NOT NULL,
	  address VARCHAR(300) NOT NULL,
	  postcode VARCHAR(6) NOT NULL,
	  phone VARCHAR(12) NOT NULL,
	  isActive BOOLEAN,
	  companyName VARCHAR(50),
	  lastLogin TIMESTAMP(6) WITH TIME ZONE,
	  accountCreated TIMESTAMP(6) WITH TIME ZONE,
	  roleId INTEGER NOT NULL,
	  CONSTRAINT Users_PK PRIMARY KEY (id),
	  CONSTRAINT Users_Roles_FK FOREIGN KEY (roleId) REFERENCES TIMEOFFCAL.roles (id)
	);
	
	CREATE TABLE TIMEOFFCAL.workDays
	(
	  id SERIAL NOT NULL,
	  userId INTEGER NOT NULL,
	  "date" DATE NOT NULL,
	  CONSTRAINT WorkDays_PK PRIMARY KEY (id),
	  CONSTRAINT WorkDays_Users_FK FOREIGN KEY (userId) REFERENCES TIMEOFFCAL.users (id)
	);
	
	CREATE TABLE TIMEOFFCAL.workTimes
	(
	  id SERIAL NOT NULL,
	  startTime TIMESTAMP(6) WITH TIME ZONE NOT NULL,
	  endTime TIMESTAMP(6) WITH TIME ZONE NOT NULL,
	  modifiedStartTime TIMESTAMP(6) WITH TIME ZONE,
	  modifiedEndTime TIMESTAMP(6) WITH TIME ZONE,
	  "type" VARCHAR(11) NOT NULL,
	  "comment" VARCHAR(200),
	  modifiedBy INTEGER NOT NULL,
	  workDayId INTEGER NOT NULL,
	  CONSTRAINT WorkTimes_PK PRIMARY KEY (id),
	  CONSTRAINT WorkTimes_Users_FK FOREIGN KEY (modifiedBy) REFERENCES TIMEOFFCAL.users (id),
	  CONSTRAINT WorkTimes_WorkDays_FK FOREIGN KEY (workDayId) REFERENCES TIMEOFFCAL.workDays (id)
	);
		
	CREATE TABLE TIMEOFFCAL.offDays
	(
	  id SERIAL NOT NULL,
	  "date" DATE NOT NULL,
	  "type" VARCHAR(7) NOT NULL,
	  status VARCHAR(8) NOT NULL,
	  approvedBy INTEGER,
	  userId INTEGER NOT NULL,
	  startTime TIMESTAMP(6) WITH TIME ZONE NOT NULL,
	  endTime TIMESTAMP(6) WITH TIME ZONE NOT NULL,
	  CONSTRAINT OffDays_PK PRIMARY KEY (id),
	  CONSTRAINT OffDays_Users_ApprovedBy_FK FOREIGN KEY (approvedBy) REFERENCES TIMEOFFCAL.users (id),
	  CONSTRAINT OffDays_Users_UserId_FK FOREIGN KEY (userId) REFERENCES TIMEOFFCAL.users (id)
	);
	
	CREATE TABLE TIMEOFFCAL.offDayLimits
	(
	  id SERIAL NOT NULL,
	  "year" VARCHAR(4) NOT NULL,
	  maximumAmount NUMERIC(18,0) NOT NULL,
	  "type" VARCHAR(7) NOT NULL,
	  userId INTEGER NOT NULL,
	  CONSTRAINT OffDayLimits_PK PRIMARY KEY (id),
	  CONSTRAINT OffDayLimits_Users_FK FOREIGN KEY (userId) REFERENCES TIMEOFFCAL.users (id)
	);
	
	CREATE TABLE TIMEOFFCAL.specialDays
	(
	  id SERIAL NOT NULL,
	  "date" DATE NOT NULL,
	  CONSTRAINT SpecialDays_PK PRIMARY KEY (id)
	);
	   
END$$;