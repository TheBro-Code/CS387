$(document).ready(function() {
	loadHome();
});


function loadHome() {
	
	var xhr = new XMLHttpRequest();
	xhr.open('GET','Patient_Form.html',true);
	xhr.onreadystatechange = function(){
		if(this.readyState!==4) return;
		if(this.status!==200) return;
		$('html').html(this.responseText);
	}
	xhr.send();
}

//function trySignUp(){
//	var xhr = new XMLHttpRequest();
//	xhr.onreadystatechange = function(){
//		if(this.status == 200 && this.readystate == 4){
//			var successfulSignUp = JSON.parse(this.responseText).status;
//			if(successfulSignUp){
//				console.log("chal gaya");
//				alert("Signed Up successfully");
//			}
//			else{
//				console.log("chal gaya");
//				alert("Sign Up Failed");
//			}
//		}
//	}
//	xhr.open("POST","PatientSignup",true);
//	xhr.send();
//}