/* Patient Home Javascript */

$(document).ready(function() {
	loadHome();
});

function loadHome()
{
  Treatments = "<table id=\"trt_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>TREATMENT ID</th> <th>DOCTOR_ID</th> <th>START TIME</th> <th>NEXT APPOINTMENT</th> </tr>"  
	      + " </thead>"
	      + " </table>";
  
  var ongoingTreat;
  $("#content").html(Treatments).promise().done(function()
	  		{
				  ongoingTreat = $("#trt_table").DataTable({
				      columns: [{data:"treatment_id"}, {data:"doctor_id"}, {data:"start_time"}, {data:"next_appointment"}],
					  ajax : {
							url: "Treatments",
							data: {
								resolved: "false"
							}
						}
				  });
		        
	  		});
  
  $('#trt_table tbody').on( 'click', 'tr', function () {
	 loadTreatmentDetail(ongoingTreat.row(this).data()["treatment_id"],"true");
  });
  
  $("#name").autocomplete({
      source: function( request, response ) {
          $.ajax({
            url: "AutoCompleteDoctor",
            dataType: "json",
            data: {
              term: request.term,
              input: "name" 
            },
            success: function( data ) {
            	$("#dummy").html(data);
              response( data );
            }
          });
        },
      select: function( event, ui ) {
    	  $("#search_doc").off().on('click',function(){
    		  searchDoctor($("#name").val(),$("#hospital").val(),$("#locality").val(),$("#qualifications").val());
    	});
     }
  });
  
  $("#hospital").autocomplete({
      source: function( request, response ) {
          $.ajax({
            url: "AutoCompleteDoctor",
            dataType: "json",
            data: {
              term: request.term,
              input: "hospital" 
            },
            success: function( data ) {
              response( data );
            }
          });
        },
      select: function( event, ui ) {
    	  $("#search_doc").off().on('click',function(){
    		  searchDoctor($("#name").val(),$("#hospital").val(),$("#locality").val(),$("#qualifications").val());
    	});
     }
  });
  
  $("#locality").autocomplete({
      source: function( request, response ) {
          $.ajax({
            url: "AutoCompleteDoctor",
            dataType: "json",
            data: {
              term: request.term,
              input: "locality" 
            },
            success: function( data ) {
            	$("#dummy").html(data);
              response( data );
            }
          });
        },
      select: function( event, ui ) {
    	  $("#search_doc").off().on('click',function(){
    		  searchDoctor($("#name").val(),$("#hospital").val(),$("#locality").val(),$("#qualifications").val());
    	});
     }
  });
  
  $("#qualifications").autocomplete({
      source: function( request, response ) {
          $.ajax({
            url: "AutoCompleteDoctor",
            dataType: "json",
            data: {
              term: request.term,
              input: "qualifications" 
            },
            success: function( data ) {
              response( data );
            }
          });
        },
      select: function( event, ui ) {
    	  $("#search_doc").off().on('click',function(){
    		  searchDoctor($("#name").val(),$("#hospital").val(),$("#locality").val(),$("#qualifications").val());
    	});
     }
  });
  
  $("#search_doc").off().on('click',function(){
	  searchDoctor($("#name").val(),$("#hospital").val(),$("#locality").val(),$("#qualifications").val())});
  
}

function treatment_history()
{
	Treatments = "<table id=\"trt_history_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>TREATMENT ID</th> <th>DOCTOR_ID</th> <th>START TIME</th> <th>`NEXT APPOINTMENT</th> </tr>"  
	      + " </thead>"
	      + " </table>";

	var ongoingTreat;
	$("#content").html(Treatments).promise().done(function()
		  		{
					  ongoingTreat = $("#trt_history_table").DataTable({
					      columns: [{data:"treatment_id"}, {data:"doctor_id"}, {data:"start_time"}, {data:"next_appointment"}],
						  ajax : {
								url: "Treatments",
								data: {
									resolved: "true"
								}
							}
					  });
			        
		  		});
	
	
	$('#trt_history_table tbody').on( 'click', 'tr', function () {
		 loadTreatmentDetail(ongoingTreat.row(this).data()["treatment_id"],"false");
	});
}



function loadTreatmentDetail(treatment_id,current)
{
	TreatmentDetail = "<table id=\"trt_detail_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>APPOINTMENT ID</th> <th>SCHEDULED TIME</th> </tr>"
	      + " </thead>"
	      + " </table>";
	  var listAppointments;
	  $("#content").html(TreatmentDetail).promise().done(function()
		  		{
					  listAppointments = $("#trt_detail_table").DataTable({
					      columns: [{data:"appointment_id"}, {data:"start_time"}],
						  ajax : {
								url: "TreatmentDetail",
								data: {
									current: current,
									treatment_id: treatment_id
								}
							}
					  });
			        
		  		});
	  
	  $('#trt_detail_table tbody').on( 'click', 'tr', function () {
			 loadAppointmentDetail(listAppointments.row(this).data()["appointment_id"]);
		  });
}

function loadAppointmentDetail(appointment_id)
{
	AppointmentDetail = 
		 "<div id=\"appointment_id\"> </div>"
		+ "<div id = \"reason_for_visit\"> </div>"
		+ "<div id = \"sched_time\"> </div>"
		  + "<table id=\"app_detail_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>MEDICINE ID</th> <th>QUANTITY</th> <th></th> </tr>"  
	      + " </thead>"
	      + " </table>"
	      + "<div id = \"comments\"> </div>";
	      
		
	
	$("#content").html(AppointmentDetail).promise().done(function()
	  		{
				var xhttp = new XMLHttpRequest();
			    xhttp.onreadystatechange = function() {
			       if (this.readyState == 4 && this.status == 200)
			       {
			        	
			        	var obj = JSON.parse(this.responseText);
			        	document.getElementById("appointment_id").innerHTML = "Appointment id: " + obj.data[0].appointment_id;
			        	document.getElementById("reason_visit").innerHTML = "Reason for visit: " + obj.data[0].reason_visit;
			        	document.getElementById("sched_time").innerHTML = "Scheduled time: " + obj.data[0].start_time;
			        	document.getElementById("comments").innerHTML = "Comments: " + obj.data[0].comments;
			        	
			        	
			       }
			       else{
			    	   
			       }
			    };
			    xhttp.open("GET", "AppointmentDetail?appointment_id=" + appointment_id, true);
			    xhttp.send();
		
				$("#appointment_id").html("Appointment_id:" + appointment_id);
				
				
				  var listAppointments = $("#app_detail_table").DataTable({
				      columns: [{data:"medicine_id"}, {data:"quantity"}],
					  ajax : {
							url: "AppointmentDetail",
							data: {
								appointment_id: appointment_id
							}
						}
				  });
		        
	  		});
}

function searchDoctor(name,hospital,locality,qualifications)
{
	
	SearchDoctor = "<table id=\"doctor_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th> DOC ID </th> <th>NAME</th> <th> HOSPITAL</th> <th>ADDRESS </th> <th> QUALIFICATIONS </th> </tr>"
	      + " </thead>"
	      + " </table>";
	
		var listAppointments;
	  	$("#content").html(SearchDoctor).promise().done(function()
		  		{
					  listAppointments = $("#doctor_table").DataTable({
					      columns: [{data: "doctor_id"}, {data:"name"}, {data:"hospital"}, {data:"hospital_address"},{data:"qualifications"}],
						  ajax : {
								url: "SearchDoctor",
								data: {
									name: name,
									hospital: hospital,
									locality: locality,
									qualifications: qualifications
								}
							}
					  });
		  		});
	  	
	  	$('#doctor_table tbody').on( 'click', 'tr', function () {
			 viewProfileDetails(listAppointments.row(this).data()["doctor_id"]);
		  });
}


function viewProfileDetails(doctor_id)
{
	profile_Det = "<div id = \"name\"> </div>"
		  		+  "<div id = \"gender\"> </div>"
		  		+  "<div id = \"hospital\"> </div>"
		  		+  "<div id = \"start_work_time\"> </div>"
		  		+  "<div id = \"end_work_time\"> </div>"
		  		+  "<div id = \"slot_time\"> </div>"
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
	        	document.getElementById("start_work_time").innerHTML = "Working Hours (Start): " + obj.data[0].start_time;
	        	document.getElementById("end_work_time").innerHTML = "Working Hours (End): " + obj.data[0].end_time;
	        	document.getElementById("slot_time").innerHTML = "Slot_time: " + obj.data[0].slot_time;
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
  		+  "<div id = \"patient_id\"> </div>"
  		+  "<div id = \"passwd\"> </div>"
  		+  "<div id = \"house_no\"> </div>"
  		+  "<div id = \"street\"> </div>"
  		+  "<div id = \"state\"> </div>"
  		+  "<div id = \"pin_code\"> </div>"
  		+  "<div id = \"phone_no\"> </div>"
  		+  "<div id = \"blood_pressure\"> </div>"
  		+  "<div id = \"blood_sugar\"> </div>"
  		+  "<div id = \"cardiac_ailment\"> </div>"
  		+  "<div id = \"asthma\"> </div>"
  		+  "<div id = \"allergies\"> </div>"
  		+  "<div id = \"chronic_diseases\"> </div>"
  		+  "<div id = \"major_surgeries\"> </div>"
  		+  "<div id = \"long_term_med\"> </div>"
  		+  "<div id = \"transf_hist\"> </div>"
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
	    	document.getElementById("patient_id").innerHTML = "Patient id : " + obj.data[0].patient_id;
	    	document.getElementById("passwd").innerHTML = "Password : " + obj.data[0].passwd;
	    	document.getElementById("house_no").innerHTML = "House No : " + obj.data[0].house_no;
	    	document.getElementById("street").innerHTML = "Street : " + obj.data[0].street;
	    	document.getElementById("state").innerHTML = "State : " + obj.data[0].state;
	    	document.getElementById("pin_code").innerHTML = "Pin code : " + obj.data[0].pin_code;
	    	document.getElementById("phone_no").innerHTML = "Phone no : " + obj.data[0].phone_no;
	    	document.getElementById("blood_pressure").innerHTML = "Blood pressure: " + obj.data[0].blood_pressure;
	    	document.getElementById("blood_sugar").innerHTML = "Blood Sugar: " + obj.data[0].blood_sugar;
	    	document.getElementById("cardiac_ailment").innerHTML = "Cardiac Ailment : " + obj.data[0].cardiac_ailment;
	    	document.getElementById("asthma").innerHTML = "Asthma: " + obj.data[0].asthma;
	    	document.getElementById("allergies").innerHTML = "Allergies: " + obj.data[0].allergies;
	    	document.getElementById("chronic_diseases").innerHTML = "Chronic Diseases: " + obj.data[0].chronic_diseases;
	    	document.getElementById("major_surgeries").innerHTML = "Major surgeries: " + obj.data[0].major_surgeries;
	    	document.getElementById("long_term_med").innerHTML = "Long term med: " + obj.data[0].long_term_med;
	    	document.getElementById("transf_hist").innerHTML = "Transfusion history : " + obj.data[0].transf_hist;
	   }
	   else{
		   
	   }
	};
	xhttp.open("GET", "profile", true);
	xhttp.send();
	});
}

function update_profile()
{
	profile_Det = " Name: <input type=\"text\" id = \"newname\"> <br></input>" +
	"Gender: </input><input type=\"text\" id = \"gender\"> <br>"  +
	"Blood type: </input><input type=\"text\" id = \"blood_type\" > <br>" +
	"Age: </input><input type=\"text\" id = \"age\" > <br>" +
	"Patient id: </input><input type=\"text\" id = \"patient_id\" > <br>" +
	"Password: </input><input type=\"text\" id = \"passwd\" > <br>" +
	"House no: </input><input type=\"text\" id = \"house_no\" > <br>" +
	"Street: </input><input type=\"text\" id = \"street\" > <br>" +
	"State: </input><input type=\"text\" id = \"state\" > <br>" +
	"Pin code: </input><input type=\"text\" id = \"pin_code\" > <br>" +
	"Phone no: </input><input type=\"text\" id = \"phone_no\" > <br>" +
	"Blood pressure: </input><input type=\"text\" id = \"blood_pressure\" > <br>" +
	"Blood sugar: </input><input type=\"text\" id = \"blood_sugar\" > <br>" +
	"Cardiac ailment: </input><input type=\"text\" id = \"cardiac_ailment\" > <br>" +
	"Asthma: </input><input type=\"text\" id = \"asthma\" > <br>" +
	"Allergies: </input><input type=\"text\" id = \"allergies\" > <br>" +
	"Chronic diseases: </input><input type=\"text\" id = \"chronic_diseases\" > <br>" +
	"Major surgeries: </input><input type=\"text\" id = \"major_surgeries\" > <br>" +
	"Long term medication: </input><input type=\"text\" id = \"long_term_med\" > <br>" +
	"Transfusion history: </input><input type=\"text\" id = \"transf_hist\" > <br>" +
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
	    	document.getElementById("patient_id").value =obj.data[0].patient_id;
	    	document.getElementById("passwd").value = obj.data[0].passwd;
	    	document.getElementById("house_no").value = obj.data[0].house_no;
	    	document.getElementById("street").value = obj.data[0].street;
	    	document.getElementById("state").value = obj.data[0].state;
	    	document.getElementById("pin_code").value = obj.data[0].pin_code;
	    	document.getElementById("phone_no").value = obj.data[0].phone_no;
	    	document.getElementById("blood_pressure").value = obj.data[0].blood_pressure;
	    	document.getElementById("blood_sugar").value = obj.data[0].blood_sugar;
	    	document.getElementById("cardiac_ailment").value = obj.data[0].cardiac_ailment;
	    	document.getElementById("asthma").value = obj.data[0].asthma;
	    	document.getElementById("allergies").value = obj.data[0].allergies;
	    	document.getElementById("chronic_diseases").value = obj.data[0].chronic_diseases;
	    	document.getElementById("major_surgeries").value = obj.data[0].major_surgeries;
	    	document.getElementById("long_term_med").value = obj.data[0].long_term_med;
	    	document.getElementById("transf_hist").value = obj.data[0].transf_hist;
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
	patient_id = document.getElementById("patient_id").value ; 
	passwd = document.getElementById("passwd").value ;
	house_no = document.getElementById("house_no").value ;  
	street = document.getElementById("street").value ;
	state = document.getElementById("state").value ;
	pin_code = document.getElementById("pin_code").value ;  
	phone = document.getElementById("phone_no").value ;
	blood_pressure = document.getElementById("blood_pressure").value ; 
	blood_sugar = document.getElementById("blood_sugar").value ;
	cardiac_ailment = document.getElementById("cardiac_ailment").value ;  
	asthma = document.getElementById("asthma").value ;
	allergies = document.getElementById("allergies").value ;
	chronic_diseases = document.getElementById("chronic_diseases").value ;
	major_surgeries = document.getElementById("major_surgeries").value ;
	long_term_med = document.getElementById("long_term_med").value ;
	transf_hist = document.getElementById("transf_hist").value ;
	
	
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
			"profile?update=" + "yes" +
			"&newname=" + newname +
			"&gender=" + gender +
			"&age=" + age + 
			"&blood_type=" + blood_type + 
//			"&patient_id=" + patient_id + 
			"&passwd=" + passwd + 
			"&house_no=" + house_no +
			"&street=" + street + 
			"&state=" + state +
			"&pin_code=" + pin_code + 
			"&phone_no=" + phone + 
			"&blood_pressure=" + blood_pressure + 
			"&blood_sugar=" + blood_sugar +
			"&cardiac_ailment=" + cardiac_ailment + 
			"&asthma=" + asthma + 
			"&allergies=" + allergies + 
			"&chronic_diseases=" + chronic_diseases + 
			"&major_surgeries=" + major_surgeries + 
			"&long_term_med=" + long_term_med + 
			"&transf_hist=" + transf_hist
			, true);
	xhttp.send();
	
	
}