
function loadHome() {
	
	var xhr = new XMLHttpRequest();
	xhr.open('GET','Doctor_form.html',true);
	xhr.onreadystatechange = function(){
		if(this.readyState!==4) return;
		if(this.status!==200) return;
		console.log($('html'));	
//		console.log(this.responseText);
//		$('html').html(this.responseText);
		var newDoc = document.open("text/html", "replace");
		newDoc.write(this.responseText);
		newDoc.close();
	}
	xhr.send();
}

function loadHomeError() {
	var xhr = new XMLHttpRequest();
	xhr.open('GET','Doctor_form.html',true);
	xhr.onreadystatechange = function(){
		if(this.readyState!==4) return;
		if(this.status!==200) return;
		var newDoc = document.open("text/html", "replace");
		newDoc.write(this.responseText);
		newDoc.close();
		$("#errortext").text("UserId/Phone No already taken");
	}
	xhr.send();
}

var currentTab = 0; // Current tab is set to be the first tab (0)
$(document).ready(function(){
	showTab(currentTab); // Display the current tab
});

function showTab(n) {
  // This function will display the specified tab of the form...
  var x = document.getElementsByClassName("tab");
  var y = document.getElementsByTagName("h1");
  x[n].style.display = "block";
  y[n].style.display = "block";

  //... and fix the Previous/Next buttons:
  if (n == 0) {
    document.getElementById("prevBtn").style.display = "none";
  } else {
    document.getElementById("prevBtn").style.display = "inline";
  }
  if (n == (x.length - 1)) {
    document.getElementById("nextBtn").innerHTML = "Submit";
  } else {
    document.getElementById("nextBtn").innerHTML = "Next";
  }
  //... and run a function that will display the correct step indicator:
  fixStepIndicator(n)
}

function nextPrev(n) {
  // This function will figure out which tab to display
  var x = document.getElementsByClassName("tab");
  var y = document.getElementsByTagName("h1");
  // Exit the function if any field in the current tab is invalid:
  if (n == 1 && !validateForm()) return false;
  // Hide the current tab:
  x[currentTab].style.display = "none";
  y[currentTab].style.display = "none";
  // Increase or decrease the current tab by 1:
  currentTab = currentTab + n;
  // if you have reached the end of the form...
  if (currentTab >= x.length) {
    // ... the form gets submitted:
    document.getElementById("regForm").submit();
    return false;
  }
  // Otherwise, display the correct tab:
  showTab(currentTab);
}

function validateForm() {
  // This function deals with validation of the form fields
  var x, y, i, valid = true;
  x = document.getElementsByClassName("tab");
  y = x[currentTab].getElementsByTagName("input");
  // A loop that checks every input field in the current tab:
  for (i = 0; i < y.length; i++) {
    // If a field is empty...
    if (y[i].value == "") {
      // add an "invalid" class to the field:
      y[i].className += " invalid";
      // and set the current valid status to false
      valid = false;
    }
  }
  // If the valid status is true, mark the step as finished and valid:
  if (valid) {
    document.getElementsByClassName("step")[currentTab].className += " finish";
  }
  return valid; // return the valid status
}

function fixStepIndicator(n) {
  // This function removes the "active" class of all steps...
  var i, x = document.getElementsByClassName("step");
  for (i = 0; i < x.length; i++) {
    x[i].className = x[i].className.replace(" active", "");
  }
  //... and adds the "active" class on the current step:
  x[n].className += " active";
}