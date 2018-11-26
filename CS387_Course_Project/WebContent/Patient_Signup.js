
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

function loadHomeError() {
	var xhr = new XMLHttpRequest();
	xhr.open('GET','Patient_Form.html',true);
	xhr.onreadystatechange = function(){
		if(this.readyState!==4) return;
		if(this.status!==200) return;
		$('html').html(this.responseText);
		$("#errortext").text("UserId/Phone No already taken");
	}
	xhr.send();
}