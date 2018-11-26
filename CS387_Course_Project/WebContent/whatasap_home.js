/**
 * Sample javascript file. Read the contents and understand them, 
 * then modify this file for your use case.
 */

$(document).ready(function() {
	loadHome();
});

function loadHome()
{
  AllConversations = "<table id=\"table1\" class=\"display\">"
	      + " <thead>" 
	      + " <tr> <th>UID</th> <th>LAST_TIMESTAMP</th> <th>NUM_MSGS</th> </tr>"  
	      + " </thead>"
	      + " </table>";
  
  
  $("#content").html(AllConversations).promise().done(function()
  		{
	        myTable1 = $("#table1").DataTable({
	            columns: [{data:"uid"}, {data:"last_timestamp"}, {data:"num_msgs"}],
	        	ajax : {
	        		url: "AllConversations",
	        		data: {
	        			resolved: "0"
	        		}
	        	}
	        });
	        
  		});
  
  $('#table1 tbody').on( 'click', 'tr', function () {
  	 loadConversationDetail(myTable1.row(this).data()["uid"]);
  });
  
  $("#autocomp1").autocomplete({
      source : "AutoCompleteUser",
      select: function( event, ui ) {
    	  $("#autocomp_submit1").off().on('click',function(){
    		  loadConversationDetail($("#autocomp1").val());
    	});
     }
  });
  
  $("#autocomp_submit1").off().on('click',function(){
	  loadConversationDetail($("#autocomp1").val());
  });
  
   
	  

  
  $("#createconv").click(function(event)
  {
	 event.preventDefault();
	 
	 var CreateConversation = "<input type=\"text\" id = \"autocomp2\"> " +
                      "<button id=\"autocomp_submit2\"> Submit </button>";
	 
	 $("#content").html(CreateConversation).promise().done(function(){
		 
		 $("#autocomp_submit2").off().on('click',function(){
			  createConversation($("#autocomp2").val());
		  });
		 
		 $("#autocomp2").autocomplete({
		      source : "AutoCompleteUser",
		      select: function( event, ui ) {
		    	  $("#autocomp_submit2").off().on('click',function(){
		    		  createConversation($("#autocomp2").val());
		    	});
		     }
		  });
	 });
  });
}

function loadConversationDetail(other_id)
{
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
       if (this.readyState == 4 && this.status == 200)
       {
        	var myTable = "<table style=\"background-repeat:no-repeat; width:100%;margin:0;\" text-align: center; cellpadding=\"5\" cellspacing=\"7\" border=\"3\">";
        	myTable += "<tr>";
        	myTable += "<th> post_id </th>";
        	myTable += "<th> thread_id </th>";
        	myTable += "<th> uid </th>";
        	myTable += "<th> timestamp </th>";
        	myTable += "<th> text </th></tr>";
        	
        	var obj = JSON.parse(this.responseText);
        	
    		for(var i=0;i<obj.data.length;i++){
    			myTable += "<tr>";
            	myTable += "<td>" + obj.data[i].post_id + "</td>";
            	myTable += "<td>" + obj.data[i].thread_id + "</td>";
            	myTable += "<td>" + obj.data[i].uid + "</td>";
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
    		document.getElementById("content").innerHTML = myTable;
    		$("#content").html(myTable).promise().done(function(){
    			$('#Submit').on("click",function () {
//    				  var uid = $("#other_id").val();
    				  var msg = $("#msg").val();
    				  var xhttp1 = new XMLHttpRequest();
    				  xhttp1.onreadystatechange = function() {
    				  	if (this.readyState == 4 && this.status == 200) {
    				  		var str = this.responseText;
    				  		var json_object = JSON.parse(str);
    				  		if(str.indexOf("true") < 0 || json_object.status == false)
    				  		{
    				  			alert(json_object.message);
    				  		}
    				  		else
    				  		{
								loadConversationDetail(other_id);
    				  		}
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


function createConversation(other_id)
{
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() 
    {
      if (this.readyState == 4 && this.status == 200)
      {
    	  var str = this.responseText;
    	  json_obj = JSON.parse(str);
    	  
    	  if(str == null || str == "")
    	  {
    	      alert("Error : Empty String");
    		  return;
    	  }
    	  else if(str.indexOf("true") < 0)
	  	  {
	  		alert(json_obj.message);
    		return;
	  	  }
	  	  else
	  	  {
			alert("Conversation created");
			return;
	  	  }
      }
      else
      {
      }
    };
    xhttp.open("GET", "CreateConversation?other_id=" + other_id, true);
    xhttp.send();
	
}