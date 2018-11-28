/* Patient Home Javascript */

$(document).ready(function() {
	loadHome();
});

function loadHome()
{
	
  Treatments = "<table id=\"trt_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr><th> </th> <th>TREATMENT ID</th> <th>DOCTOR_ID</th> <th>START TIME</th> <th>NEXT APPOINTMENT</th> <th> FEEDBACK </th> </tr>"  
	      + " </thead>"
	      + " </table>";
  
  init_bar ="<center><h1>Find a Doctor </h1><br><br>"+ 
	  		"Name : <input type=\"text\" id = \"name\" name = \"name\"> " +
			"Hospital : <input type=\"text\" id = \"hospital\" name = \"hospital\"> " +
			"Locality : <input type=\"text\" id = \"locality\" name = \"locality\"> " +
			"Qualifications : <input type=\"text\" id = \"qualifications\" name = \"qualifications\"> " +
		    "<button id=\"search_doc\"> Submit </button></center> <br> <br>";
  
  var ongoingTreat;
  
  $("#content").html(Treatments).promise().done(function()
	  		{
				  ongoingTreat = $("#trt_table").DataTable({
				      columns: [{
			                	"className":      'details-control',
			                	"orderable":      false,
			                	"data":           null,
			                	"defaultContent": ''
			            		},{data:"treatment_id"}, {data:"doctor_id"}, {data:"start_time"}, {data:"next_appointment"},
			            		{
						    		  data: null,
						    		  render: function(data,type,row){
						    			  
						    			  var ret = '<button onclick=give_feedback(' + data["treatment_id"] + ')>' +  'GIVE FEEDBACK </button>';
						    			  console.log(ret);
						    			  return ret;
						    		  }
						    	  }],
					  ajax : {
							url: "Treatments",
							data: {
								resolved: "false"
							}
						}
				  });			        
	  		});

	$('#trt_table tbody').on('click', 'td.details-control', function () {
	    var tr = $(this).closest('tr');
	    var row = ongoingTreat.row( tr );
	    console.log(row);
	    if ( row.child.isShown() ) {
	        // This row is already open - close it
	        row.child.hide();
	        tr.removeClass('shown');
	    }
	    else {
	        // Open this row
	    	var tid_trt = "trt_detail_table" + row.index();
	  	  row.child	("<table id=" + tid_trt + " class=\"display\">"
	  		      + " <thead>" 
	  		      + " <tr> <th></th> <th>APPOINTMENT ID</th> <th>SCHEDULED TIME</th> </tr>"
	  		      + " </thead>"
	  		      + " </table>").show();
	  	  console.log(row.data()["treatment_id"]);
	  	  loadTreatmentDetail(row.data()["treatment_id"], true,tid_trt);
	  	  tr.addClass('shown');
	    }
	 });
  
  $("#hide_order").html(init_bar).promise().done(function(){
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
  });
  
}

function give_feedback(treatment_id) {
    document.getElementById("myForm").style.display = "block";
    document.getElementById("treatment_id").value = treatment_id;
    document.getElementById("feedback").value = "";
}

function closeForm() {
	document.getElementById("feedback").value = "";
    document.getElementById("myForm").style.display = "none";
}	

function treatment_history()
{
	
  Treatments = "<table id=\"trt_table\" class=\"display\">"
      + " <thead>" 
      + " <tr> <th>TREATMENT ID</th> <th>DOCTOR_ID</th> <th>START TIME</th> <th>END TIME</th> </tr>"  
      + " </thead>"
      + " </table>";
  
  var Treathist;
  $("#content").html(Treatments).promise().done(function()
	  		{
				  Treathist = $("#trt_table").DataTable({
				      columns: [{data:"treatment_id"}, {data:"doctor_id"}, {data:"start_time"}, {data:"end_time"}],
					  ajax : {
							url: "Treatments",
							data: {
								resolved: "true"
							}
						}
				  });
		        
	  		});
  
  $('#trt_table tbody').on( 'click', 'tr', function () {
	 loadTreatmentDetail(ongoingTreat.row(this).data()["treatment_id"],"true");
  });
  
}



function loadTreatmentDetail(treatment_id, current,tid_trt)
{
	
	  var listAppointments;
	  listAppointments = $("#" + tid_trt).DataTable({
	      columns: [{
          	"className":      'details-control',
        	"orderable":      false,
        	"data":           null,
        	"defaultContent": ''
    		},{data:"appointment_id"}, {data:"start_time"}],
		  ajax : {
				url: "TreatmentDetail",
				data: {
					current: current,
					treatment_id: treatment_id
				}
			},
	  searching: false, paging: false, info: false
	  });			       
	  
//	  $('#trt_detail_table tbody').on( 'click', 'tr', function () {
//			 loadAppointmentDetail(listAppointments.row(this).data()["appointment_id"]);
//		  });
	  $('#'+tid_trt + ' tbody').on('click', 'td.details-control', function () {
	      var tr = $(this).closest('tr');
	      var row = listAppointments.row( tr );
	      if ( row.child.isShown() ) {
	          // This row is already open - close it
	          row.child.hide();
	          tr.removeClass('shown');
	      }
	      else {
	          // Open this row
	    	  var tid = "app_detail_table" + row.index();
	    	  row.child("<table id=" + tid + " class=\"display\">"
	    			      + " <thead>" 
	    			      + " <tr> <th>MEDICINE ID</th> <th>QUANTITY</th> <th></th> </tr>"  
	    			      + " </thead>"
	    			      + " </table>").show();
	    	  loadAppointmentDetail(row.data()["appointment_id"],tid);
	    	  tr.addClass('shown');
	      }
	  } );
}

function loadAppointmentDetail(appointment_id,tid)
{
	
	console.log(appointment_id + " " + tid);
//	AppointmentDetail = 
//		 "<div id=\"appointment_id\"> </div>"
//		+ "<div id = \"reason_for_visit\"> </div>"
//		+ "<div id = \"sched_time\"> </div>"
//		  + "<table id=\"app_detail_table\" class=\"display\">"
//	      + " <thead>" 
//	      + " <tr> <th>MEDICINE ID</th> <th>QUANTITY</th> <th></th> </tr>"  
//	      + " </thead>"
//	      + " </table>"
//	      + "<div id = \"comments\"> </div>";
	      
		
	
//	$("#content").html(AppointmentDetail).promise().done(function()
//	  		{
				var xhttp = new XMLHttpRequest();
			    xhttp.onreadystatechange = function() {
			       if (this.readyState == 4 && this.status == 200)
			       {
			        	
			        	var obj = JSON.parse(this.responseText);
//			        	document.getElementById("appointment_id").innerHTML = "Appointment id: " + obj.data[0].appointment_id;
//			        	document.getElementById("reason_visit").innerHTML = "Reason for visit: " + obj.data[0].reason_visit;
//			        	document.getElementById("sched_time").innerHTML = "Scheduled time: " + obj.data[0].start_time;
//			        	document.getElementById("comments").innerHTML = "Comments: " + obj.data[0].comments;
			        	
			        	
			       }
			    };
			    xhttp.open("GET", "AppointmentDetail?appointment_id=" + appointment_id, true);
			    xhttp.send();
		
//				$("#appointment_id").html("Appointment_id:" + appointment_id);
				
				
				  var listAppointments = $("#"+tid).DataTable({
				      columns: [{data:"medicine_id"}, {data:"quantity"}],
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
	profile_Det = "<div id = \"name1\"> </div>"
		  		+  "<div id = \"gender\"> </div>"
		  		+  "<div id = \"hospital1\"> </div>"
		  		+  "<div id = \"start_work_time\"> </div>"
		  		+  "<div id = \"end_work_time\"> </div>"
		  		+  "<div id = \"hours\"> </div>"
		  		+  "<div id = \"minutes\"> </div>"
		  		+  "<div id = \"hospital_address\"> </div>"
		  		+  "<div id = \"fees\"> </div>"
		  		+  "<div id = \"rating\"> </div>"
		  		+  "<div id = \"age1\"> </div>"
		  		+  "<div id = \"doctor_id\"> </div>"
		  		+  "<div id = \"qualifications1\"> </div>"
		  		+  "<div id = \"speciality\"> </div>"
		  		+  "<div id = \"college\"> </div>"
		  		+  "<div id = \"completion\"> </div>"
		  		+  "<div id = \"experience\"> </div>"
		  		+  "<div id = \"regnum\"> </div>"
		  		+ "<div id = \"regyear\"> </div>"
		  		+  "<div id = \"regcouncil\"> </div> <br>"
		  		+ "<button id=\"bookTreatment\" onclick=\"book_treatment(" + doctor_id + ")\">Get an Appoitment</button>";
	
	var listAppointments;
  	$("#content").html(profile_Det).promise().done(function()
  		{
  		var xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function() {
	       if (this.readyState == 4 && this.status == 200)
	       {
	        	var obj = JSON.parse(this.responseText);
	        	document.getElementById("name1").innerHTML = "Name: " + obj.data[0].name;

	        	document.getElementById("gender").innerHTML = "Gender: " + obj.data[0].gender;
	        	document.getElementById("age1").innerHTML = "Age: " + obj.data[0].age;
	        	document.getElementById("doctor_id").innerHTML = "Doc id: " + obj.data[0].doctor_id;
	        	document.getElementById("qualifications1").innerHTML = "Qualification : " + obj.data[0].qualifications;
	        	document.getElementById("speciality").innerHTML = "Speciality: " + obj.data[0].speciality;
	        	document.getElementById("college").innerHTML = "College: " + obj.data[0].college;
	        	document.getElementById("completion").innerHTML = "Completion: " + obj.data[0].completion;
	        	document.getElementById("experience").innerHTML = "Experience: " + obj.data[0].experience;
	        	document.getElementById("regnum").innerHTML = "Regnum: " + obj.data[0].regnum;
	        	document.getElementById("regcouncil").innerHTML = "Regcouncil: " + obj.data[0].regcouncil;
	        	document.getElementById("regyear").innerHTML = "Regyear: " + obj.data[0].regyear;
	        	document.getElementById("start_work_time").innerHTML = "Working Hours (Weekdays): " + obj.data[0].weekday_hours;
	        	document.getElementById("end_work_time").innerHTML = "Working Hours (Weekends): " + obj.data[0].weekend_hours;
	        	document.getElementById("hours").innerHTML = "Hours: " + parseSlotTimeHour(obj.data[0].slot_time);
	        	document.getElementById("minutes").innerHTML = "Minutes: " + parseSlotTimeMin(obj.data[0].slot_time);
	        	document.getElementById("hospital1").innerHTML = "Hospital: " + obj.data[0].hospital;
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

function parseSlotTimeHour(slot_time)
{
	var days_ind = slot_time.indexOf("days"); 
	var hour = slot_time.charAt(days_ind+5);
	return hour;
}

function parseSlotTimeMin(slot_time)
{
	var days_ind = slot_time.indexOf("days"); 
	var minutes = slot_time.substring(days_ind +13,days_ind +15);
	return minutes;
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
		   if (this.readyState == 4 && this.status == 200) {
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


function order_medicines(){
	
	mydiv = "Name : <input type=\"text\" id = \"name\" name = \"name\"> " +
			"Disease : <input type=\"text\" id = \"disease\" name = \"disease\"> " +
		    "<button id=\"search_med\"> Submit </button> <br> <br>"
			
	SearchMedicines = "<table id=\"order_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>MEDICINE ID</th> <th>NAME</th> <th>RETAILER</th> <th>PRICE PER UNIT</th> <th>SIDE EFFECTS</th> <th>DISEASE</th> <th>PRESCRIPTION REQUIRED</th> </tr>"  
	      + " </thead>"
	      + " </table>" ;
			
	

	var allMedicines;
	$("#hide_order").html(mydiv).promise().done(function()
	{
		$("#content").html("").promise().done(function(){
			allMedicines = $("#order_table").DataTable({
			      columns: [{data:"medicine_id"}, {data:"name"}, {data:"retailer"}, {data:"price_per_unit"},
			    	  {data:"side_effects"}, {data:"chronic_diseases"}, {data:"prescription_required"}],
				  ajax : {
						url: "SearchMedicines",
						data: {
							
						}
					}
			  });
		});
	});
	
	
	$("#name").autocomplete({
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
	      },
	    select: function( event, ui ) {
	  	  $("#search_med").off().on('click',function(){
	  		  searchMedicine($("#name").val(),$("#disease").val());
	  	});
	   }
	});
	
	$("#disease").autocomplete({
	    source: function( request, response ) {
	        $.ajax({
	          url: "AutoCompleteMed",
	          dataType: "json",
	          data: {
	            term: request.term,
	            input: "disease" 
	          },
	          success: function( data ) {
	            response( data );
	          }
	        });
	      },
	    select: function( event, ui ) {
	  	  $("#search_med").off().on('click',function(){
	  		  searchMedicine($("#name").val(),$("#disease").val());
	  	});
	   }
	});

	
	$("#search_med").off().on('click',function(){
		  searchMedicine($("#name").val(),$("#disease").val());
	});
}


function searchMedicine(name,disease)
{
	console.log(disease);
	SearchMedicines = "<table id=\"med_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>MEDICINE ID</th> <th>NAME</th> <th>RETAILER</th> <th>PRICE PER UNIT</th> <th>SIDE EFFECTS</th> " +
	      		"<th>DISEASE</th> <th>CHRONIC DISEASE</th> <th>PRESCRIPTION REQUIRED</th> <th>QUANTITY</th> </tr>"  
	      + " </thead>"
	      + " </table>"
	      +	"<button id=\"OrderIt\" onclick=\"orderit()\"> Order </button>";
	
		var listMedicines;
	  	$("#content").html(SearchMedicines).promise().done(function()
  		{
			  listMedicines = $("#med_table").DataTable({
			      columns: [{data: "medicine_id"}, {data:"name"}, {data:"retailer"}, {data:"price_per_unit"},{data:"side_effects"},{data:"disease"},{data:"chronic_diseases"},{data:"prescription_required"},
			    	  {
			    		  data: null,
			    		  render: function(data,type,row){
			    			  
			    			  var ret = '<button onclick=inc(' + data["medicine_id"] + ')>' + '+'  +'</button>' 
			    			  + '<div id=' + data["medicine_id"] + '>' + 0 + '</div>'
			    			  +'<button onclick=dec(' + data["medicine_id"] + ')>' + '-' + '</button>';
			    			  
			    			  return ret;
			    		  }
			    	  }],
				  ajax : {
						url: "SearchMedicines",
						data: {
							name: name,
							disease: disease
						}
					}
			  });
  		});
}

function orderit()
{
	
//	$('#example').DataTable();.rows().iterator( 'row', function ( context, index ) {
//	    console.log($( this.row( index ).node() ));
//	} );
	
	$('#med_table').DataTable().rows().every(function(){
//	    console.log(this.data()["medicine_id"]);
	    var xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function() {
	       if (this.readyState == 4 && this.status == 200)
	       {
	    	   
	       }
	       else{
	    	   
	       }
	    }
	    if(Number(document.getElementById(this.data()["medicine_id"]).innerText) > 0){
		    xhttp.open("GET", "PlaceOrder?medicine_id=" + this.data()["medicine_id"] + "&quantity=" + Number(document.getElementById(this.data()["medicine_id"]).innerText), true);
	//	    console.log(this.data()["medicine_id"] + " " + Number(document.getElementById(this.data()["medicine_id"]).innerText));
		    xhttp.send();
		    loadHome();
	    }
	});
	
}

function inc(id)
{
	document.getElementById(id).innerHTML = Number(document.getElementById(id).innerText) + 1;
}

function dec(id)
{
	if(Number(document.getElementById(id).innerText) > 0)
		document.getElementById(id).innerHTML = Number(document.getElementById(id).innerText) - 1;
}


function loadchatdetails(other_id)
{
	
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
       if (this.readyState == 4 && this.status == 200)
       {
        	var myTable = "<table style=\"background-repeat:no-repeat; width:100%;margin:0;\" text-align: center; cellpadding=\"5\" cellspacing=\"7\" border=\"3\">";
        	myTable += "<tr>";
//        	myTable += "<th> post_id </th>";
//        	myTable += "<th> thread_id </th>";
        	myTable += "<th> uid </th>";
        	myTable += "<th> timestamp </th>";
        	myTable += "<th> text </th></tr>";
        	
        	var obj = JSON.parse(this.responseText);
        	
    		for(var i=0;i<obj.data.length;i++){
    			myTable += "<tr>";
//            	myTable += "<td>" + obj.data[i].post_id + "</td>";
//            	myTable += "<td>" + obj.data[i].thread_id + "</td>";
            	myTable += "<td>" + obj.data[i].userid + "</td>";
            	myTable += "<td>" + obj.data[i].timestamp + "</td>";
            	myTable += "<td>" + obj.data[i].text + "</td>";
            	myTable += "</tr>";
    		}
    		
    		myTable += "</table> <br> <br>";
    		
    		myTable += ""
//				+ "Enter uid:     <input type=\"text\" id = \"other_id\"> "
				+ "Enter message: <input type=\"text\" id = \"msg\"> "
				+ "<button id = \"Submit\">Submit </button>"
				+ "";
    		
//    		document.getElementById("content").innerHTML = myTable;
    		$("#content").html(myTable).promise().done(function(){
    			$('#Submit').on("click",function () {
//    				  var uid = $("#other_id").val();
    				  var msg = $("#msg").val();
    				  var xhttp1 = new XMLHttpRequest();
    				  xhttp1.onreadystatechange = function() {
    				  	if (this.readyState == 4 && this.status == 200) {
    				  		var str = this.responseText;
    				  		loadchatdetails(other_id);
    				  	}
    				  	else{
    				  	}
    				  };
    				  xhttp1.open("GET", "NewMessage?other_id=" + other_id + "&msg=" + msg, true);
  				      xhttp1.send();
    			  });
    		});
       }
       else{
       }
    };
    xhttp.open("GET", "ConversationDetail?other_id=" + other_id, true);
    xhttp.send();
}

function book_treatment(doctor_id) {
	var dateSelector = "<div><span>Date: <input type=\"text\" id=\"datepicker\" " +
			"onchange=\"bookTreatmentUtil(" + doctor_id + ")\"></span><div><br>";
	$("#appointment").html(dateSelector).promise().done(function(){
		var $datepicker = $('#datepicker');
		$datepicker.datepicker();
		$datepicker.datepicker('setDate', new Date());
		
		
		bookTreatmentUtil(doctor_id);
	});
	
}

Date.prototype.yyyymmdd = function() {
	  var mm = this.getMonth() + 1; // getMonth() is zero-based
	  var dd = this.getDate();

	  return [this.getFullYear(),'-',
	          (mm>9 ? '' : '0') + mm,'-',
	          (dd>9 ? '' : '0') + dd
	         ].join('');
	};


function bookTreatmentUtil(doctor_id) {
	var $datepicker = $('#datepicker');
	$datepicker.datepicker();
//	$datepicker.datepicker('setDate', new Date());
	var date = $datepicker.datepicker('getDate');
	var dayOfWeek = date.getUTCDay();
	console.log(date.yyyymmdd());
	console.log(dayOfWeek);
	
	var xhttp1 = new XMLHttpRequest();
	xhttp1.onreadystatechange = function() {
	  	if (this.readyState == 4 && this.status == 200) {
	  		var str = this.responseText;
	  		var json_object = JSON.parse(str);
	  		console.log(json_object);
	  		var appointmentSlots = "<div><span>Date: <input type=\"text\" id=\"datepicker\"" +
	  				" onchange=\"bookTreatmentUtil(" + doctor_id + ")\"></span><div><br>" +
	  		"   <div>\n" + 
			"		<span>Choose a time slot to book an appointment for "+ date + " </span>\n" + 
			"	</div>\n" + 
			"	<div>\n" + 
			"		<div style=\"width: 100%\">\n" + 
			"			<div>Morning</div>\n" + 
			"			<div>\n";
	  		
	  		var i = 0;
	  		while(true){
	  			if(i >= json_object.data.length)
	  				break;
	  			var d = new Date(json_object.data[i].freeslot);
	  			if(Date.parse('14 Oct 1998 ' + d.toLocaleTimeString()) >= Date.parse('14 Oct 1998 12:00:00 PM')) {
	  				break;
	  			}
	  			appointmentSlots += "<button onclick=\"bookAppointment(" +
  				doctor_id + ",'" + date.yyyymmdd() + "', '" + d.toLocaleTimeString() 
  				+ "')\">" + d.toLocaleTimeString() + "</button><br>";
//            	alert("here");
            	console.log(new Date(json_object.data[i].freeslot));
            	console.log(i);
            	i += 1;
    		}
 
			appointmentSlots += "</div>\n" + 
			"		</div>\n" + 
			"		<div style=\"width: 100%\">\n" + 
			"			<div>Afternoon</div>\n" + 
			"			<div>\n";
			
			while(true){
				if(i >= json_object.data.length)
	  				break;
	  			var d = new Date(json_object.data[i].freeslot);
	  			if(Date.parse('14 Oct 1998 ' + d.toLocaleTimeString()) >= Date.parse('14 Oct 1998 05:00:00 PM')) {
	  				break;
	  			}
	  			appointmentSlots += "<button onclick=\"bookAppointment(" +
  				doctor_id + ",'" + date.yyyymmdd() + "', '" + d.toLocaleTimeString() 
  				+ "')\">" + d.toLocaleTimeString() + "</button><br>";
//            	alert("here");
            	console.log(new Date(json_object.data[i].freeslot));
            	console.log(i);
            	i += 1;
    		}
			
			appointmentSlots +=  "</div>\n" + 
			"		</div>\n" + 
			"		<div style=\"width: 100%\">\n" + 
			"			<div>Evening</div>\n" + 
			"			<div>\n"
			
			while(true){
				if(i >= json_object.data.length)
	  				break;
	  			var d = new Date(json_object.data[i].freeslot);
	  			if(Date.parse('14 Oct 1998 ' + d.toLocaleTimeString()) >= Date.parse('14 Oct 1998 08:00:00 PM')) {
	  				break;
	  			}
	  			appointmentSlots += "<button onclick=\"bookAppointment(" +
  				doctor_id + ",'" + date.yyyymmdd() + "', '" + d.toLocaleTimeString() 
  				+ "')\">" + d.toLocaleTimeString() + "</button><br>";
//            	alert("here");
            	console.log(new Date(json_object.data[i].freeslot));
            	console.log(i);
            	i += 1;
    		}
			
			appointmentSlots +=  "</div>\n" + 
			"		</div>\n" + 
			"		<div style=\"width: 100%\">\n" + 
			"			<div>Night</div>\n" + 
			"			<div>\n"
			
			while(true){
				if(i >= json_object.data.length)
	  				break;
	  			var d = new Date(json_object.data[i].freeslot);
	  			appointmentSlots += "<button onclick=\"bookAppointment(" +
	  				doctor_id + ",'" + date.yyyymmdd() + "', '" + d.toLocaleTimeString() 
	  				+ "')\">" + d.toLocaleTimeString() + "</button><br>";
//            	alert("here");
            	console.log(new Date(json_object.data[i].freeslot));
            	console.log(i);
            	i += 1;
    		}
			
			appointmentSlots += "</div>\n" + 
			"		</div>\n" + 
			"	</div>";
			
//			document.getElementById("appointment").innnerHTML += appointmentSlots; 
			console.log(appointmentSlots);
			$("#appointment").html(appointmentSlots).promise().done(function(){
				$( "#datepicker" ).datepicker();
			});
	  	}
	  	else{
	  	}
	};
	
	xhttp1.open("GET", "GetFreeSlots?doctor_id=" + doctor_id + "&date=" + date.yyyymmdd() + "&day=" + dayOfWeek, true);
	xhttp1.send();
}

function bookAppointment(doctor_id, date, startTime) {

	var reasonForVisit = prompt("Please enter your reason for visit", "your reason....(limited for 500 characters)");
	console.log(reasonForVisit);
	if (reasonForVisit == null) {
//	    txt = "User cancelled the prompt.";
	} else {
		var xhttp1 = new XMLHttpRequest();
		xhttp1.onreadystatechange = function() {
		  	if (this.readyState == 4 && this.status == 200) {
		  		var str = this.responseText;
		  		$("#appointment").html("");
		  		alert("Your appointment has been booked (You can check its details under the treatment tab)");
		  		loadHome();
		  	}
		}
		console.log(reasonForVisit);
		xhttp1.open("GET", "BookTreatment?doctor_id=" + doctor_id + "&date=" + date
				+ "&startTime=" + startTime + "&reasonForVisit=" + reasonForVisit.toString(), true);
		xhttp1.send();
	}
}


function formclick()
{
	var feedback = document.getElementById("feedback").value;
	var treatment_id = document.getElementById("treatment_id").value;
	
	console.log(feedback);
	
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
    xhttp.open("GET", "Feedback?feedback=" + feedback + "&treatment_id=" + treatment_id, true);
    xhttp.send();
    
    closeForm();
}

function your_orders()
{
	Treatments = "<table id=\"trt_table\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>ORDER ID</th> <th>PATIENT ID</th> <th>MEDICINE ID</th> <th>QUANTITY</th></tr>"  
	      + " </thead>"
	      + " </table>";

	var ongoingTreat;
	
	$("#content").html(Treatments).promise().done(function()
		  		{
					  ongoingTreat = $("#trt_table").DataTable({
					      columns: [{data:"order_id"}, {data:"patient_id"}, {data:"medicine_id"}, {data:"quantity"},
				            		],
						  ajax : {
								url: "YourOrders",
								data: {

								}
							}
					  });			        
		  		});
}
