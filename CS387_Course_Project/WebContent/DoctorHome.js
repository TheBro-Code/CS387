/* Doctor Home Javascript */

$(document).ready(function() {
	loadHome();
});

function loadHome()
{
  Appointments = "<table id=\"appt_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th> </th><th>APPOINTMENT ID</th> <th>PATIENT NAME</th> <th>START TIME</th> <th> COMMENTS</th> <th> PRESCRIPTIONS </th> <th>TREATMENT ID</th></tr>"  
	      + " </thead>"
	      + " </table>";
  var upcoming_app;
  $("#content").html(Appointments).promise().done(function()
	  		{
				  upcoming_app = $("#appt_table").DataTable({
				      columns: [{
		                	"className":      'details-control',
		                	"orderable":      false,
		                	"data":           null,
		                	"defaultContent": ''
		            		},{data:"appointment_id"}, {data:"name"}, {data:"start_time"},
				    	  {
			    		  data: null,
			    		  render: function(data,type,row){
			    			  var ret = '<button onclick=add_comments(' + data["appointment_id"] + ')>' +  'ADD COMMENTS </button>';
			    			  return ret;
			    		  }
			    	  },
			    	  {
			    		  data: null,
			    		  render: function(data,type,row){
			    			  var ret = '<button onclick=add_prescription(' + data["appointment_id"] + ')>' +  'ADD PRESCRIPTION </button>';
			    			  return ret;
			    		  }
			    	  },
			    	  {data: "treatment_id"}],
					  ajax : {
							url: "Appointments",
							data: {

							}
						}
				  }); 
	  		});
  
  $('#appt_table tbody').on('click', 'td.details-control', function () {
	    var tr = $(this).closest('tr');
	    var row = upcoming_app.row( tr );
	    if ( row.child.isShown() ) {
	        // This row is already open - close it
	        row.child.hide();
	        tr.removeClass('shown');
	    }
	    else {
	        // Open this row
	    	var tid_appt = "app_detail_table" + row.index();
	  	  row.child	("<div id=\"sched_time\"> </div>"
	  			+ "<div id = \"reason_visit\"> </div>"
	  			+ "<div id = \"comments1\"> </div>"
	  			  + "<table id=" + tid_appt + " class=\"display\">"
	  		      + " <thead>" 
	  		      + " <tr> <th>MEDICINE ID</th> <th> NAME </th> <th> RETAILER </th> <th> PRICE PER UNIT </th> <th> DISEASES </th> <th>QUANTITY</th> <th> PRESCRIPTION REQUIRED </th> </tr>"  
	  		      + " </thead>"
	  		      + " </table>").show();
	  	  loadAppointmentDetail(row.data()["appointment_id"],tid_appt);
	  	  tr.addClass('shown');
	    }
	 });
  
}



function add_comments(appointment_id)
{
    document.getElementById("myForm").style.display = "block";
    document.getElementById("appointment_id").value = appointment_id;
    document.getElementById("comments").value = "";	
}


function closeForm() {
	document.getElementById("comments").value = "";
    document.getElementById("myForm").style.display = "none";
}

function add_prescription(appointment_id)
{
    document.getElementById("myForm1").style.display = "block";
    $("#med_id").autocomplete({
	    source: function( request, response ) {
	        $.ajax({
	          url: "AutoCompleteMed",
	          dataType: "json",
	          data: {
	            term: request.term,
	            input: "name" 
	          },
	          success: function( data ) {
//	          	$("#dummy").html(data);
	            response( data );
	          }
	        });
	      }
	});
    document.getElementById("appointment_id").value = appointment_id;
    document.getElementById("med_id").value = "";
	document.getElementById("quantity").value = "";	
}


function closeForm1() {
	document.getElementById("med_id").value = "";
	document.getElementById("quantity").value = "";
    document.getElementById("myForm1").style.display = "none";
}

function formclick()
{
	var comments = document.getElementById("comments").value;
	var appointment_id = document.getElementById("appointment_id").value;
	
//	console.log(comments);
	
//	var params = 'feedback=' + feedback + '&treatment_id=' + treatment_id;
	
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
       if (this.readyState == 4 && this.status == 200)
       {
    	   
       }
       else
       {   
       }
       
       }
    xhttp.open("GET", "AddComments?comments=" + comments + "&appointment_id=" + appointment_id, true);
    xhttp.send();
    
    closeForm();
}

function formclick1()
{
	var med_id = document.getElementById("med_id").value;
	var quantity = document.getElementById("quantity").value;
	var appointment_id = document.getElementById("appointment_id").value;
	
//	console.log(comments);
	
//	var params = 'feedback=' + feedback + '&treatment_id=' + treatment_id;
	
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
       if (this.readyState == 4 && this.status == 200)
       {
    	   
       }
       else
       {   
       }
       
       }
    xhttp.open("GET", "AddPrescription?med_id=" + med_id + "&appointment_id=" + appointment_id + "&quantity=" + quantity, true);
    xhttp.send();
    
    closeForm1();
}



function treatment_history()
{
	
  Treatments = "<table id=\"trt_table\" class=\"display\">"
      + " <thead>" 
      + " <tr> <th>TREATMENT ID</th> <th>PATIENT ID</th> <th>START TIME</th> <th>END TIME</th> </tr>"  
      + " </thead>"
      + " </table>";
  
  var Treathist;
  $("#content").html(Treatments).promise().done(function()
	  		{
				  Treathist = $("#trt_table").DataTable({
				      columns: [{data:"treatment_id"}, {data:"patient_id"}, {data:"start_time"}, {data:"end_time"}],
					  ajax : {
							url: "Treatments",
							data: {
								resolved: "true"
							}
						}
				  });
		        
	  		});
  
  $('#trt_table tbody').on( 'click', 'tr', function () {
	 loadTreatmentDetail(Treathist.row(this).data()["treatment_id"],"true");
  });
  
}


function ongoingTreatments()
{
	  Treatments = "<table id=\"trt_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>TREATMENT ID</th> <th>PATIENT ID</th> <th>START TIME</th> <th>NEXT APPOINTMENT</th> </tr>"  
	      + " </thead>"
	      + " </table>";
	
	  var ongoingTreat;
	  
	  $("#content").html(Treatments).promise().done(function()
		  		{
					  ongoingTreat = $("#trt_table").DataTable({
					      columns: [{data:"treatment_id"}, {data:"patient_id"}, {data:"start_time"}, {data:"next_appointment"}],
						  ajax : {
								url: "Treatments",
								data: {
									resolved: "false"
								}
							}
					  });			        
		  		});
	  
	  $('#trt_table tbody').on( 'click', 'tr', function () {
		 loadTreatmentDetail(ongoingTreat.row(this).data()["treatment_id"], ongoingTreat.row(this).data()["patient_id"], "true");
	  });
	  
	  
}


function loadTreatmentDetail(treatment_id)
{
	TreatmentDetail = "<table id=\"trt_detail_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>APPOINTMENT ID</th> <th>SCHEDULED TIME</th> </tr>"  
	      + " </thead>"
	      + " </table>";
	
	console.log(treatment_id);
	
	  var listAppointments;
	  $("#content").html(TreatmentDetail).promise().done(function()
		  		{
				  listAppointments = $("#trt_detail_table").DataTable({
				      columns: [{data:"appointment_id"}, {data:"start_time"}],
					  ajax : {
							url: "TreatmentDetail",
							data: {
								current: "true",
								treatment_id: treatment_id
							}
						}
				  });
			        
		  		});
	  
	  $('#trt_detail_table tbody').on( 'click', 'tr', function () {
			 loadAppointmentDetail(listAppointments.row(this).data()["appointment_id"]);
		  });
}

function loadAppointmentDetail(appointment_id,tid_appt)
{
//	console.log(appointment_id);
//	AppointmentDetail = 
//		 "<div id=\"appointment_id\"> </div>"
//		+ "<div id = \"reason_visit\"> </div>"
//		+ "<div id = \"sched_time\"> </div>"
//		  + "<table id=\"app_detail_table\" class=\"display\">"
//	      + " <thead>" 
//	      + " <tr> <th>MEDICINE ID</th> <th>QUANTITY</th> <th></th> </tr>"  
//	      + " </thead>"
//	      + " </table>"
//	      + "<div id = \"comments\"> </div>";
	      
		
	
//	$("#content").html(AppointmentDetail).promise().done(function()
//	  		{
	
	console.log(tid_appt + appointment_id);
		
				var xhttp = new XMLHttpRequest();
			    xhttp.onreadystatechange = function() {
			       if (this.readyState == 4 && this.status == 200)
			       {
			        	
			        	var obj = JSON.parse(this.responseText);
//			        	document.getElementById("appointment_id").innerHTML = "Appointment id: " + obj.data[0].appointment_id;
			        	document.getElementById("reason_visit").innerHTML = "Reason for visit: " + obj.data[0].reason_visit;
			        	document.getElementById("sched_time").innerHTML = "Scheduled time: " + obj.data[0].start_time;
			        	document.getElementById("comments1").innerHTML = "Comments: " + obj.data[0].comments;
//			        	console.log(document.getElementById("comments").innerHTML);
			        	
			        	
			       }
			    };
			    xhttp.open("GET", "AppointmentDetail?appointment_id=" + appointment_id, true);
			    xhttp.send();
				
				
				  var listAppointments = $("#"+tid_appt).DataTable({
				      columns: [{data:"medicine_id"}, {data:"name"}, {data:"retailer"}, {data:"price_per_unit"}, {data:"disease"} , {data:"quantity"}, {data:"prescription_required"}],
					  ajax : {
							url: "AppointmentDetail",
							data: {
								appointment_id: appointment_id
							}
						},
						searching: false, paging: false, info: false
				  });
				 
		        
//	  		});
}


function viewProfileDetails(doctor_id)
{
	profile_Det = "<div id = \"name\"> </div>"
		  		+  "<div id = \"gender\"> </div>"
		  		+  "<div id = \"hospital\"> </div>"
		  		+  "<div id = \"weekday_hours\"> </div>"
		  		+  "<div id = \"weekend_hours\"> </div>"
		  		+  "<div id=\"hours\"> </div> " 
		  		+  "<div id=\"minutes\"> </div>"
		  		+  "<div id = \"hospital_address\"> </div>"
		  		+  "<div id = \"fees\"> </div>"
		  		+  "<div id = \"rating\"> </div>"
		  		+ "<div id = \"age\"> </div>"
		  		+  "<div id = \"doctor_id\"> </div>"
		  		+  "<div id = \"qualifications\"> </div>"
		  		+  "<div id = \"speciality\"> </div>"
		  		+  "<div id = \"college\"> </div>"
		  		+  "<div id = \"completion\"> </div>"
		  		+  "<div id = \"experience\"> </div>"
		  		+  "<div id = \"regnum\"> </div>"
		  		+ "<div id = \"regyear\"> </div>"
		  		+  "<div id = \"regcouncil\"> </div>";
	
	var listAppointments;
  	$("#content").html(profile_Det).promise().done(function()
	  		{
  		var xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function() {
	       if (this.readyState == 4 && this.status == 200)
	       {
	        	var obj = JSON.parse(this.responseText);
	        	document.getElementById("name").innerHTML = "name: " + obj.data[0].name;
	        	document.getElementById("gender").innerHTML = "Gender: " + obj.data[0].gender;
	        	document.getElementById("age").innerHTML = "Age: " + obj.data[0].age;
	        	document.getElementById("doctor_id").innerHTML = "Doc id: " + obj.data[0].doctor_id;
	        	document.getElementById("qualifications").innerHTML = "Qualification : " + obj.data[0].qualifications;
	        	document.getElementById("speciality").innerHTML = "Speciality: " + obj.data[0].speciality;
	        	document.getElementById("college").innerHTML = "College: " + obj.data[0].college;
	        	document.getElementById("completion").innerHTML = "Completion: " + obj.data[0].completion;
	        	document.getElementById("experience").innerHTML = "Experience: " + obj.data[0].experience;
	        	document.getElementById("regnum").innerHTML = "Regnum: " + obj.data[0].regnum;
	        	document.getElementById("regcouncil").innerHTML = "Regcouncil: " + obj.data[0].regcouncil;
	        	document.getElementById("regyear").innerHTML = "Regyear: " + obj.data[0].regyear;
	        	document.getElementById("weekday_hours").innerHTML = "Working Hours (Weekday): " + obj.data[0].weekday_hours;
	        	document.getElementById("weekend_hours").innerHTML = "Working Hours (Weekend): " + obj.data[0].weekend_hours;
		    	document.getElementById("hours").innerHTML = "Slot Time (Hours) : " + parseSlotTimeHour(obj.data[0].slot_time);
		    	document.getElementById("minutes").innerHTML = "Slot Time (Minutes) : " + parseSlotTimeMin(obj.data[0].slot_time);
	        	document.getElementById("hospital").innerHTML = "Hospital: " + obj.data[0].hospital;
	        	document.getElementById("hospital_address").innerHTML = "Hospital Address: " + obj.data[0].hospital_address;
	        	document.getElementById("fees").innerHTML = "Fees: " + obj.data[0].fees;
	        	document.getElementById("rating").innerHTML = "Rating: " + obj.data[0].rating;
	       }
	       else{
	    	   
	       }
	    };
	    xhttp.open("GET", "profile?user_view_id=" + doctor_id, true);
	    xhttp.send();
	  	});
}

function loadProfile()
{
	profile_Det = "<div id = \"name\"> </div>"
  		+  "<div id = \"gender\"> </div>"
  		+  "<div id = \"blood_type\"> </div>"
  		+  "<div id = \"age\"> </div>"
  		+  "<div id = \"doctor_id\"> </div>"
  		+  "<div id = \"passwd\"> </div>"
  		+  "<div id = \"house_no\"> </div>"
  		+  "<div id = \"street\"> </div>"
  		+  "<div id = \"state\"> </div>"
  		+  "<div id = \"pin_code\"> </div>"
  		+  "<div id = \"phone_no\"> </div>"
  		+  "<div id = \"qualifications\"> </div>"
  		+  "<div id = \"speciality\"> </div>"
  		+  "<div id = \"college\"> </div>"
  		+  "<div id = \"completion\"> </div>"
  		+  "<div id = \"experience\"> </div>"
  		+  "<div id = \"weekday_hours\"> </div>"
  		+  "<div id = \"weekend_hours\"> </div>"
  		+  "<div id=\"hours\"> </div> " 
  		+  "<div id=\"minutes\"> </div>"
  		+  "<div id = \"hospital\"> </div>"
  		+  "<div id = \"hospital_address\"> </div>"
  		+  "<div id = \"fees\"> </div>"
  		+ "<button id=\"update_but\" onclick = update_profile() > Update your profile </button>";
	

	var listAppointments;
	var obj;
	$("#content").html(profile_Det).promise().done(function()
		{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	   if (this.readyState == 4 && this.status == 200)
	   {
	    	obj = JSON.parse(this.responseText);
	    	document.getElementById("name").innerHTML = "name: " + obj.data[0].name;
	    	document.getElementById("gender").innerHTML = "Gender: " + obj.data[0].gender;
	    	document.getElementById("age").innerHTML = "Age: " + obj.data[0].age;
	    	document.getElementById("blood_type").innerHTML = "Blood Type: " + obj.data[0].blood_type;
	    	document.getElementById("doctor_id").innerHTML = "Doctor id : " + obj.data[0].doctor_id;
	    	document.getElementById("passwd").innerHTML = "Password : " + obj.data[0].passwd;
	    	document.getElementById("house_no").innerHTML = "House No : " + obj.data[0].house_no;
	    	document.getElementById("street").innerHTML = "Street : " + obj.data[0].street;
	    	document.getElementById("state").innerHTML = "State : " + obj.data[0].state;
	    	document.getElementById("pin_code").innerHTML = "Pin code : " + obj.data[0].pin_code;
	    	document.getElementById("phone_no").innerHTML = "Phone no : " + obj.data[0].phone_no;
	    	
	    	document.getElementById("qualifications").innerHTML = "Qualifications: " + obj.data[0].qualifications;
	    	document.getElementById("speciality").innerHTML = "Speciality: " + obj.data[0].speciality;
	    	document.getElementById("college").innerHTML = "College: " + obj.data[0].college;
	    	document.getElementById("completion").innerHTML = "Completion: " + obj.data[0].completion;
	    	document.getElementById("experience").innerHTML = "Experience: " + obj.data[0].experience;
	    	document.getElementById("weekday_hours").innerHTML = "Weekday hours: " + obj.data[0].weekday_hours;
	    	document.getElementById("weekend_hours").innerHTML = "Weekend hours: " + obj.data[0].weekend_hours;
	    	document.getElementById("hours").innerHTML = "Slot Time (Hours) : " + parseSlotTimeHour(obj.data[0].slot_time);
	    	document.getElementById("minutes").innerHTML = "Slot Time (Minutes) : " + parseSlotTimeMin(obj.data[0].slot_time);
	    	document.getElementById("hospital").innerHTML = "Hospital : " + obj.data[0].hospital;
	    	document.getElementById("hospital_address").innerHTML = "Hospital_address : " + obj.data[0].hospital_address;
	    	document.getElementById("fees").innerHTML = "Fees : " + obj.data[0].fees;
	    	
	   }
	   else{
		   
	   }
	};
	xhttp.open("GET", "profile", true);
	xhttp.send();
	});
}

function parseSlotTimeHour(slot_time)
{
	var days_ind = slot_time.indexOf("days"); 
	var hour = slot_time.charAt(days_ind+5);
	
//	System.out.println("myhour: " + hour);
	return hour;
}

function parseSlotTimeMin(slot_time)
{
	var days_ind = slot_time.indexOf("days"); 
	var minutes = slot_time.substring(days_ind +13,days_ind +15);
	return minutes;
}

function update_profile()
{
	profile_Det = " Name: <input type=\"text\" id = \"newname\"> <br></input>" +
	"Gender: </input><input type=\"text\" id = \"gender\"> <br>"  +
	"Blood type: </input><input type=\"text\" id = \"blood_type\" > <br>" +
	"Age: </input><input type=\"text\" id = \"age\" > <br>" +
	"Doctor id: </input><input type=\"text\" id = \"doctor_id\" > <br>" +
	"Password: </input><input type=\"text\" id = \"passwd\" > <br>" +
	"House no: </input><input type=\"text\" id = \"house_no\" > <br>" +
	"Street: </input><input type=\"text\" id = \"street\" > <br>" +
	"State: </input><input type=\"text\" id = \"state\" > <br>" +
	"Pin code: </input><input type=\"text\" id = \"pin_code\" > <br>" +
	"Phone no: </input><input type=\"text\" id = \"phone_no\" > <br>" +
	
	"Qualifications: </input><input type=\"text\" id = \"qualifications\" > <br>" +
	"Speciality: </input><input type=\"text\" id = \"speciality\" > <br>" +
	"College: </input><input type=\"text\" id = \"college\" > <br>" +
	"Completion: </input><input type=\"text\" id = \"completion\" > <br>" +
	"Experience: </input><input type=\"text\" id = \"experience\" > <br>" +
	"Weekday_hours: </input><input type=\"text\" id = \"weekday_hours\" > <br>" +
	"Weekend_hours: </input><input type=\"text\" id = \"weekend_hours\" > <br>" +
	"Slot Time (Hours): </input> <input type=\"text\" id = \"hours\"> <br>" + 
	"Slot Time (Minutes): </input> <input type=\"text\" id = \"minutes\"> <br>" + 
	"Hospital: </input><input type=\"text\" id = \"hospital\" > <br>" +
	"Hospital_address: </input><input type=\"text\" id = \"hospital_address\" > <br>" +
	"Fees: </input><input type=\"text\" id = \"fees\" > <br>" +
	"<button id=\"submit_but\" onclick = exec_update()> Submit </button>";
	
	var listAppointments;
	var obj;
	$("#content").html(profile_Det).promise().done(function()
		{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	   if (this.readyState == 4 && this.status == 200)
	   {
	    	obj = JSON.parse(this.responseText);
	    	document.getElementById("newname").value =obj.data[0].name;
	    	document.getElementById("gender").value =obj.data[0].gender;
	    	document.getElementById("age").value =obj.data[0].age;
	    	document.getElementById("blood_type").value =obj.data[0].blood_type;
	    	document.getElementById("doctor_id").value =obj.data[0].doctor_id;
	    	document.getElementById("passwd").value = obj.data[0].passwd;
	    	document.getElementById("house_no").value = obj.data[0].house_no;
	    	document.getElementById("street").value = obj.data[0].street;
	    	document.getElementById("state").value = obj.data[0].state;
	    	document.getElementById("pin_code").value = obj.data[0].pin_code;
	    	document.getElementById("phone_no").value = obj.data[0].phone_no;
	    	document.getElementById("hours").value = parseSlotTimeHour(obj.data[0].slot_time);
	    	console.log(parseSlotTimeHour(obj.data[0].slot_time));
	    	document.getElementById("minutes").value = parseSlotTimeMin(obj.data[0].slot_time);
	    	document.getElementById("qualifications").value = obj.data[0].qualifications;
	    	document.getElementById("speciality").value = obj.data[0].speciality;
	    	document.getElementById("college").value = obj.data[0].college;
	    	document.getElementById("completion").value = obj.data[0].completion;
	    	document.getElementById("experience").value = obj.data[0].experience;
	    	document.getElementById("weekday_hours").value = obj.data[0].weekday_hours;
	    	document.getElementById("weekend_hours").value = obj.data[0].weekend_hours;
	    	document.getElementById("hospital").value = obj.data[0].hospital;
	    	document.getElementById("hospital_address").value = obj.data[0].hospital_address;
	    	document.getElementById("fees").value = obj.data[0].fees;
	   }
	   else{
		   
	   }
	};
	xhttp.open("GET", "profile", true);
	xhttp.send();
	});
	
}

function exec_update()
{
	
	newname = document.getElementById("newname").value ; 
	gender = document.getElementById("gender").value  ;
	age = document.getElementById("age").value ;
	blood_type = document.getElementById("blood_type").value ;  
	doctor_id = document.getElementById("doctor_id").value ; 
	passwd = document.getElementById("passwd").value ;
	house_no = document.getElementById("house_no").value ;  
	street = document.getElementById("street").value ;
	state = document.getElementById("state").value ;
	pin_code = document.getElementById("pin_code").value ;  
	phone = document.getElementById("phone_no").value ;
	
	qualifications = document.getElementById("qualifications").value ; 
	speciality = document.getElementById("speciality").value ;
	college = document.getElementById("college").value ;  
	completion = document.getElementById("completion").value ;
	experience = document.getElementById("experience").value ;
	weekday_hours = document.getElementById("weekday_hours").value ;
	weekend_hours = document.getElementById("weekend_hours").value ;
	slot_time_hours = document.getElementById("hours").value ;
	slot_time_mins = document.getElementById("minutes").value ;
	hospital = document.getElementById("hospital").value ;
	hospital_address = document.getElementById("hospital_address").value ;
	fees = document.getElementById("fees").value ;
	
		
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	   if (this.readyState == 4 && this.status == 200)
	   {
		   loadProfile();
	   }
	   else{
		   
	   }
	};
	xhttp.open("GET", 
			"profile?update=1" +
			"&newname=" + newname +
			"&gender=" + gender +
			"&age=" + age + 
			"&blood_type=" + blood_type +
			"&passwd=" + passwd + 
			"&house_no=" + house_no +
			"&street=" + street +  
			"&state=" + state +
			"&pin_code=" + pin_code + 
			"&phone_no=" + phone + 
			
			"&qualifications=" + qualifications + 
			"&speciality=" + speciality +
			"&college=" + college + 
			"&completion=" + completion + 
			"&experience=" + experience + 
			"&weekday_hours=" + weekday_hours + 
			"&weekend_hours=" + weekend_hours + 
			"&hours=" + slot_time_hours +
			"&minutes=" + slot_time_mins + 
			"&hospital=" + hospital +
			"&hospital_address=" + hospital_address +	
			"&fees=" + fees
			, true);
	xhttp.send();
}


