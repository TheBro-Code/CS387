Drop table posts;
Drop table conversations;
Drop table feedback;
Drop table medicine_order;
Drop table prescription;
Drop table medicine;
Drop table appointment;
Drop table treatment;
Drop table doctors;
Drop table patients;
Drop table users;
-- Drop table colleges;

-- create table colleges (
--     name varchar(100) primary key
-- );

create table users(
    userid varchar(10) primary key,
    passwd varchar(20),
    name varchar(20),
    gender varchar(10),
    house_no varchar(10),
    street varchar(20),
    state varchar(20),
    pin_code varchar(10),
    phone_no varchar(10) unique,
    emergency_no varchar(10),
    blood_type varchar(10),
    DOB varchar(10),
    age varchar(5),
    image bytea
);

create table patients(
    patient_id varchar(20) references users(userid) primary key,
    blood_pressure varchar(20),
    blood_sugar varchar(20),
    cardiac_ailment varchar(50),
    asthma varchar(20),
    allergies varchar(20),
    chronic_diseases varchar(50),
    major_surgeries varchar(50),
    long_term_med varchar(50),
    transf_hist varchar(50)
);

create table doctors(
    doctor_id varchar(10) references users(userid)  primary key,
    qualifications varchar(20),
    speciality varchar(20),
    college varchar(100),
    completion varchar(10),
    experience varchar(10),
    regnum varchar(20) unique,
    regcouncil varchar(30),
    regyear varchar(10),
    weekday_hours varchar(200),
    weekend_hours varchar(200),
    slot_time interval,
    hospital varchar(20),
    hospital_address varchar(50),
    fees varchar(10),
    rating varchar(10)
);

create table treatment(
    treatment_id serial primary key,
    patient_id varchar(20) references patients,
    doctor_id varchar(20) references doctors,
    start_time timestamp default current_timestamp,
    end_time timestamp
);

create table appointment(
    appointment_id serial primary key,
    treatment_id integer references treatment,
    reason_visit varchar(500),
    start_time timestamp default current_timestamp,
    comments varchar(50)
);

create table medicine(
    medicine_id varchar(20) primary key,
    name varchar(20),
    retailer varchar(30),
    price_per_unit varchar(10),
    side_effects varchar(20),
    disease varchar(20),
    chronic_diseases varchar(20),
    prescription_required varchar(5)
);

create table prescription(
    appointment_id integer references appointment,
    medicine_id varchar(20) references medicine,
    quantity varchar(10),
    primary key (appointment_id, medicine_id)
);

create table medicine_order(
   order_id serial primary key,
   quantity varchar(10),
   patient_id varchar(20) references patients,
   medicine_id varchar(20) references medicine
);

create table feedback(
   feedback_id serial primary key,
   treatment_id integer references treatment,
   stars varchar(10),
   text varchar(100)
);

create table conversations(
     patient_id varchar(20) references patients,
     doctor_id varchar(20) references doctors,
     thread_id serial,
     primary key (patient_id, doctor_id),
     unique(thread_id)
     );

create table posts (
    post_id serial primary key,
    thread_id integer references conversations(thread_id),
    userid varchar(20) references users,
    timestamp timestamp,
    text varchar(256)
);
-----------------------------------------------------------------------------

insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('123','hell','amani','male','A-20','Govindpur','Maharashtra','302018','9876543210','1234567890','A+', '15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('124','hell','amanj','male','A-20','Govindpur','Maharashtra','302018','9876542211','1234567890','B+', '15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('125','hell','kartik','male','A-20','Govindpur','Maharashtra','302018','9876543212','1234567890','AB+', '15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('126','hell','syamantak','male','A-20','Govindpur','Maharashtra','302018','9876543213','1234567890','AB+', '15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('127','hell','sushil','male','A-20','Govindpur','Maharashtra','302018','9876543214','1234567890','A-', '15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('128','hell','bansal','male','A-20','Govindpur','Maharashtra','302018','9876543215','1234567890','A-', '15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('130','hell','kunal','male','A-20','Govindpur','Maharashtra','302018','9876543216','1234567890','B-', '15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('140','hell','mittal','male','A-20','Govindpur','Maharashtra','302018','9876543217','1234567890','A+','15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('150','hell','gd','female','A-20','Govindpur','Maharashtra','302018','9876543218','1234567890','AB-','15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('160','hell','gaurav','female','A-20','Govindpur','Maharashtra','302018','9876543219','1234567890','AB+','15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('170','hell','andrew','male','A-20','Govindpur','Maharashtra','302018','9876543230','1234567890','O+','15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('180','hell','garfield','female','A-20','Govindpur','Maharashtra','302018','9876543240','1234567890','O-','15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('190','hell','leo','male','A-20','Govindpur','Maharashtra','302018','9876543250','1234567890','O+','15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('200','hell','tom','male','A-20','Govindpur','Maharashtra','302018','9876543260','1234567890','O-','15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('210','hell','holland','male','A-20','Govindpur','Maharashtra','302018','9876543270','1234567890','O+','15/10/1975', '44');
insert into users(userid,passwd,name,gender,house_no,street,state,pin_code,phone_no,emergency_no,blood_type,DOB,age) values ('220','hell','yoyo','male','A-20','Govindpur','Maharashtra','302018','9876543280','1234567890','B+','15/10/1975', '44');

-- insert into colleges values('AIIMS, Delhi');
-- insert into colleges values('AIIMS, Jodhpur');
-- insert into colleges values('Sawai Man Singh, Jaipur');
-- insert into colleges values('AFMC, Pune');
-- insert into colleges values('AIIMS, Bhuvaneshwar');

insert into doctors values ('123','MBBS','Dentist', 'AIIMS, Delhi','1998', '20', 'R1', 'MCI', '1995', '09:00-13:00,14:00-17:00,20:00-22:00','11:00-13:00,14:00-17:00','45 mins','Jc clinic','Meera road','250','5');
insert into doctors values ('124','MD','General Physician', 'AIIMS, Delhi','1998', '20', 'R2', 'MCI', '1997', '09:00-13:00,14:00-17:00,20:00-22:00','11:00-13:00,14:00-17:00','45 mins','Jc clinic','Meera road','250','5');
insert into doctors values ('125','BMBS','Dermatologist', 'AIIMS, Delhi','1998', '20', 'R3', 'MCI', '1996','09:00-13:00,14:00-17:00,20:00-22:00','11:00-13:00,14:00-17:00','45 mins','Jc clinic','Meera road','250','5');
insert into doctors values ('126','MBBS','Homeopath', 'AIIMS, Delhi', '1998','20', 'R4', 'MCI', '1996', '09:00-13:00,14:00-17:00,20:00-22:00','11:00-13:00,14:00-17:00','45 mins','Jc clinic','Meera road','250','5');
insert into doctors values ('127','MBChB','Gynocologist', 'AIIMS, Delhi','1998', '20', 'R5', 'MCI', '1999', '09:00-13:00,14:00-17:00,20:00-22:00','11:00-13:00,14:00-17:00','45 mins','Jc clinic','Meera road','250','5');
insert into doctors values ('128','MBBCh','Ayurveda', 'AIIMS, Delhi','1998', '20', 'R6', 'MCI', '2002', '09:00-13:00,14:00-17:00,20:00-22:00','11:00-13:00,14:00-17:00','45 mins','Jc clinic','Meera road','250','5');

insert into patients values ('130','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('140','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('150','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('160','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('170','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('180','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('190','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('200','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('210','80/120','100','Yes','No','None','None','None','None','None');
insert into patients values ('220','80/120','100','Yes','No','None','None','None','None','None');


insert into treatment(patient_id, doctor_id) values ('130','123');
insert into treatment(patient_id, doctor_id) values ('140','123');
insert into treatment(patient_id, doctor_id) values ('150','124');
insert into treatment(patient_id, doctor_id) values ('160','125');
insert into treatment(patient_id, doctor_id) values ('170','125');
insert into treatment(patient_id, doctor_id) values ('180','126');
insert into treatment(patient_id, doctor_id) values ('190','127');
insert into treatment(patient_id, doctor_id) values ('200','127');
insert into treatment(patient_id, doctor_id) values ('210','128');
insert into treatment(patient_id, doctor_id) values ('220','128');

insert into appointment(treatment_id, reason_visit,start_time) values (1,'Stomach ache','2018-11-12 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (1,'Head ache','2018-09-13 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (1,'Heart ache','2018-09-14 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (2,'Heart break','2018-09-15 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (2,'Stomach ache','2018-09-16 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (2,'Stomach ache','2018-09-17 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (3,'Stomach ache','2018-09-18 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (4,'Stomach ache','2018-09-19 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (5,'Stomach ache','2018-09-12 15:09');
insert into appointment(treatment_id, reason_visit,start_time) values (5,'Stomach ache','2018-09-13 15:09');

insert into medicine values ('50','Paracetamol','Apollo','25','sleeplessness','Fever','None','No');
insert into medicine values ('15','Citrezine','Apollo','25','sleeplessness','Fever','None','Yes');
insert into medicine values ('60','Recofast','Apollo','25','sleeplessness','Fever','None','No');
insert into medicine values ('20','Strepsils','Apollo','25','sleeplessness','Cough','None','No');

-- insert into prescription values (1,,'3');
-- insert into prescription values (1,,'3');
-- insert into prescription values (2,3,'3');
-- insert into prescription values (3,4,'3');
-- insert into prescription values (4,4,'3');

