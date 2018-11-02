---------------------------------------------------------------------------------
-- drop table posts;
-- drop table conversations;
-- drop table feedback;
-- drop table medicine_order;
-- drop table prescription;
-- drop table medicine;
-- drop table appointment;
-- drop table treatment;
-- drop table patients;
-- drop table doctors;
-- drop table users;

---------------------------------------------------------------------------------

create table users(
    userid varchar(20) primary key,
    passwd varchar(20),
    name varchar(20),
    gender varchar(20),
    house_no varchar(20),
    street varchar(20),
    state varchar(20),
    pin_code varchar(20),
    phone_no varchar(20) unique,
    emergency_no varchar(20),
    blood_type varchar(20)
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
    doctor_id varchar(20) references users(userid)  primary key,
    qualifications varchar(50),
    speciality varchar(50),
    working_hours varchar(20),
    slot_time varchar(20),
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


insert into users values ('123','hell','amani','male','A-20','Govindpur','Maharashtra','302018','9876543210','1234567890','A+');
insert into users values ('124','hell','amanj','male','A-20','Govindpur','Maharashtra','302018','9876542211','1234567890','B+');
insert into users values ('125','hell','kartik','male','A-20','Govindpur','Maharashtra','302018','9876543212','1234567890','AB+');
insert into users values ('126','hell','syamantak','male','A-20','Govindpur','Maharashtra','302018','9876543213','1234567890','AB+');
insert into users values ('127','hell','sushil','male','A-20','Govindpur','Maharashtra','302018','9876543214','1234567890','A-');
insert into users values ('128','hell','bansal','male','A-20','Govindpur','Maharashtra','302018','9876543215','1234567890','A-');
insert into users values ('130','hell','kunal','male','A-20','Govindpur','Maharashtra','302018','9876543216','1234567890','B-');
insert into users values ('140','hell','mittal','male','A-20','Govindpur','Maharashtra','302018','9876543217','1234567890','A+');
insert into users values ('150','hell','gd','female','A-20','Govindpur','Maharashtra','302018','9876543218','1234567890','AB-');
insert into users values ('160','hell','gaurav','female','A-20','Govindpur','Maharashtra','302018','9876543219','1234567890','AB+');
insert into users values ('170','hell','andrew','male','A-20','Govindpur','Maharashtra','302018','9876543230','1234567890','O+');
insert into users values ('180','hell','garfield','female','A-20','Govindpur','Maharashtra','302018','9876543240','1234567890','O-');
insert into users values ('190','hell','leo','male','A-20','Govindpur','Maharashtra','302018','9876543250','1234567890','O+');
insert into users values ('200','hell','tom','male','A-20','Govindpur','Maharashtra','302018','9876543260','1234567890','O-');
insert into users values ('210','hell','holland','male','A-20','Govindpur','Maharashtra','302018','9876543270','1234567890','O+');
insert into users values ('220','hell','yoyo','male','A-20','Govindpur','Maharashtra','302018','9876543280','1234567890','B+');


insert into doctors values ('123','B Tech','Eye specialist','9-5','45','Jc clinic','Meera road',250,5);
insert into doctors values ('124','B Tech','Stomach specialist','9-5','45','Jc clinic','Meera road',250,5);
insert into doctors values ('125','M tech','Eye specialist','9-5','45','Jc clinic','Meera road',250,5);
insert into doctors values ('126','MBBS','Eye specialist','9-5','45','Jc clinic','Meera road',250,5);
insert into doctors values ('127','AIIMS','Gyano specialist','9-5','45','Jc clinic','Meera road',250,5);
insert into doctors values ('128','B Tech','Neuro specialist','9-5','45','Jc clinic','Meera road',250,5);

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

insert into treatment values ('1','130','123');
insert into treatment values ('2','140','123');
insert into treatment values ('3','150','123');
insert into treatment values ('4','160','123');
insert into treatment values ('5','130','124');
insert into treatment values ('6','170','125');
insert into treatment values ('7','130','126');
insert into treatment values ('8','180','126');
insert into treatment values ('9','190','127');
insert into treatment values ('10','200','128');

insert into appointment values ('100','1','Stomach ache');
insert into appointment values ('101','1','Head ache');
insert into appointment values ('102','1','Heart ache');
insert into appointment values ('103','2','Heart break');
insert into appointment values ('104','2','Stomach ache');
insert into appointment values ('105','2','Stomach ache');
insert into appointment values ('106','3','Stomach ache');
insert into appointment values ('107','4','Stomach ache');
insert into appointment values ('108','5','Stomach ache');
insert into appointment values ('109','5','Stomach ache');

insert into medicine values ('300','Paracetamol','Apollo',25,'sleeplessness','Fever','No');
insert into medicine values ('301','Citrezine','Apollo',25,'sleeplessness','Fever','Yes');
insert into medicine values ('302','Recofast','Apollo',25,'sleeplessness','Fever','No');
insert into medicine values ('303','Strepsils','Apollo',25,'sleeplessness','Cough','No');

insert into prescription values ('100','300',3);
insert into prescription values ('100','301',3);
insert into prescription values ('101','302',30);
insert into prescription values ('102','303',3);
insert into prescription values ('103','303',3);













