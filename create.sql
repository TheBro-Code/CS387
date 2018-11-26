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
Drop table colleges;

create table colleges (
    name varchar(100) primary key
);

create table users(
    userid varchar(10) primary key,
    passwd varchar(20),
    name varchar(20),
    gender varchar(10),
    house_no varchar(10),
    street varchar(20),
    state varchar(20),
    pin_code int,
    phone_no varchar(10) unique,
    emergency_no varchar(10),
    blood_type varchar(10),
    DOB varchar(10),
    age int
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
    college varchar(100) references colleges(name),
    completion int,
    experience int,
    regnum varchar(20) unique,
    regcouncil varchar(30),
    regyear int,
    start_time time,
    end_time time,
    slot_time interval,
    hospital varchar(20),
    hospital_address varchar(50),
    fees int, 
    rating int
);

create table treatment(
    treatment_id varchar(20) primary key,
    patient_id varchar(20) references patients,
    doctor_id varchar(20) references doctors,
    start_time timestamp default current_timestamp,
    end_time timestamp
);

create table appointment(
    appointment_id varchar(20) primary key,
    treatment_id varchar(20) references treatment,
    reason_visit varchar(50),
    start_time timestamp default current_timestamp,
    comments varchar(50)
);

create table medicine(
    medicine_id varchar(20) primary key,
    name varchar(20),
    retailer varchar(30),
    price_per_unit int,
    side_effects varchar(20),
    disease varchar(20),
    prescription_required varchar(5)
);

create table prescription(
    appointment_id varchar(20) references appointment,
    medicine_id varchar(20) references medicine,
    quantity int,
    primary key (appointment_id, medicine_id)
);

create table medicine_order(
   order_id varchar(20) primary key,
   quantity int,
   patient_id varchar(20) references patients,
   medicine_id varchar(20) references medicine
);

create table feedback(
   feedback_id varchar(20) primary key,
   treatment_id varchar(20) references treatment,
   stars int,
   text varchar(100)
);

create table conversations(
     patient_id varchar(20) references patients,
     doctor_id varchar(20) references doctors,
     thread_id serial,
     primary key (patient_id, doctor_id),
     unique(thread_id));

create table posts (
    post_id serial primary key,
    thread_id integer references conversations(thread_id),
    user_id varchar(20) references users,
    timestamp timestamp,
    text varchar(256)
);
-----------------------------------------------------------------------------

insert into users values ('123','hell','amani','male','A-20','Govindpur','Maharashtra','302018','9876543210','1234567890','A+', '15/10/1975', 44);
insert into users values ('124','hell','amanj','male','A-20','Govindpur','Maharashtra','302018','9876542211','1234567890','B+', '15/10/1975', 44);
insert into users values ('125','hell','kartik','male','A-20','Govindpur','Maharashtra','302018','9876543212','1234567890','AB+', '15/10/1975', 44);
insert into users values ('126','hell','syamantak','male','A-20','Govindpur','Maharashtra','302018','9876543213','1234567890','AB+', '15/10/1975', 44);
insert into users values ('127','hell','sushil','male','A-20','Govindpur','Maharashtra','302018','9876543214','1234567890','A-', '15/10/1975', 44);
insert into users values ('128','hell','bansal','male','A-20','Govindpur','Maharashtra','302018','9876543215','1234567890','A-', '15/10/1975', 44);
insert into users values ('130','hell','kunal','male','A-20','Govindpur','Maharashtra','302018','9876543216','1234567890','B-', '15/10/1975', 44);
insert into users values ('140','hell','mittal','male','A-20','Govindpur','Maharashtra','302018','9876543217','1234567890','A+','15/10/1975', 44);
insert into users values ('150','hell','gd','female','A-20','Govindpur','Maharashtra','302018','9876543218','1234567890','AB-','15/10/1975', 44);
insert into users values ('160','hell','gaurav','female','A-20','Govindpur','Maharashtra','302018','9876543219','1234567890','AB+','15/10/1975', 44);
insert into users values ('170','hell','andrew','male','A-20','Govindpur','Maharashtra','302018','9876543230','1234567890','O+','15/10/1975', 44);
insert into users values ('180','hell','garfield','female','A-20','Govindpur','Maharashtra','302018','9876543240','1234567890','O-','15/10/1975', 44);
insert into users values ('190','hell','leo','male','A-20','Govindpur','Maharashtra','302018','9876543250','1234567890','O+','15/10/1975', 44);
insert into users values ('200','hell','tom','male','A-20','Govindpur','Maharashtra','302018','9876543260','1234567890','O-','15/10/1975', 44);
insert into users values ('210','hell','holland','male','A-20','Govindpur','Maharashtra','302018','9876543270','1234567890','O+','15/10/1975', 44);
insert into users values ('220','hell','yoyo','male','A-20','Govindpur','Maharashtra','302018','9876543280','1234567890','B+','15/10/1975', 44);

insert into colleges values('AIIMS, Delhi');
insert into colleges values('AIIMS, Jodhpur');
insert into colleges values('Sawai Man Singh, Jaipur');
insert into colleges values('AFMC, Pune');
insert into colleges values('AIIMS, Bhuvaneshwar');

insert into doctors values ('123','MBBS','Dentist', 'AIIMS, Delhi', 1998, 20, 'R1', 'MCI', 1995, '09:00','17:00','45 mins','Jc clinic','Meera road',250,5);
insert into doctors values ('124','MD','General Physician', 'AIIMS, Delhi', 1998, 20, 'R2', 'MCI', 1997, '09:00','17:00','45 mins','Jc clinic','Meera road',250,5);
insert into doctors values ('125','BMBS','Dermatologist', 'AIIMS, Delhi', 1998, 20, 'R3', 'MCI', 1996, '09:00','17:00','45 mins','Jc clinic','Meera road',250,5);
insert into doctors values ('126','MBBS','Homeopath', 'AIIMS, Delhi', 1998, 20, 'R4', 'MCI', 1996, '09:00','17:00','45 mins','Jc clinic','Meera road',250,5);
insert into doctors values ('127','MBChB','Gynocologist', 'AIIMS, Delhi', 1998, 20, 'R5', 'MCI', 1999, '09:00','17:00','45 mins','Jc clinic','Meera road',250,5);
insert into doctors values ('128','MBBCh','Ayurveda', 'AIIMS, Delhi', 1998, 20, 'R6', 'MCI', 2002, '09:00','17:00','45 mins','Jc clinic','Meera road',250,5);

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

insert into treatment(treatment_id, patient_id, doctor_id) values ('1','130','123');
insert into treatment(treatment_id, patient_id, doctor_id) values ('2','140','123');
insert into treatment(treatment_id, patient_id, doctor_id) values ('3','150','124');
insert into treatment(treatment_id, patient_id, doctor_id) values ('4','160','125');
insert into treatment(treatment_id, patient_id, doctor_id) values ('5','170','125');
insert into treatment(treatment_id, patient_id, doctor_id) values ('6','180','126');
insert into treatment(treatment_id, patient_id, doctor_id) values ('7','190','127');
insert into treatment(treatment_id, patient_id, doctor_id) values ('8','200','127');
insert into treatment(treatment_id, patient_id, doctor_id) values ('9','210','128');
insert into treatment(treatment_id, patient_id, doctor_id) values ('10','220','128');

insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('100','1','Stomach ache','2018-11-12 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('101','1','Head ache','2018-09-13 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('102','1','Heart ache','2018-09-14 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('103','2','Heart break','2018-09-15 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('104','2','Stomach ache','2018-09-16 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('105','2','Stomach ache','2018-09-17 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('106','3','Stomach ache','2018-09-18 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('107','4','Stomach ache','2018-09-19 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('108','5','Stomach ache','2018-09-12 15:09');
insert into appointment(appointment_id, treatment_id, reason_visit,start_time) values ('109','5','Stomach ache','2018-09-13 15:09');

insert into medicine values ('300','Paracetamol','Apollo',25,'sleeplessness','Fever','No');
insert into medicine values ('301','Citrezine','Apollo',25,'sleeplessness','Fever','Yes');
insert into medicine values ('302','Recofast','Apollo',25,'sleeplessness','Fever','No');
insert into medicine values ('303','Strepsils','Apollo',25,'sleeplessness','Cough','No');

insert into prescription values ('100','300',3);
insert into prescription values ('100','301',3);
insert into prescription values ('101','302',30);
insert into prescription values ('102','303',3);
insert into prescription values ('103','303',3);