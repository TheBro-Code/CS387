/* Doctor Home Javascript */

$(document).ready(function() {
	loadHome();
});

function loadProfile()
{
	
}

function loadHome()
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
	 loadTreatmentDetail(ongoingTreat.row(this).data()["treatment_id"]);
  });
}

function loadTreatmentDetail(treatment_id)
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
