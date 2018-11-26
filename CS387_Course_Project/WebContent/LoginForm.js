function loadHome() {
	
	var xhr = new XMLHttpRequest();
	xhr.open('GET','LoginForm.html',true);
	xhr.onreadystatechange = function(){
		if(this.readyState!==4) return;
		if(this.status!==200) return;
		var newDoc = document.open("text/html", "replace");
		newDoc.write(this.responseText);
		newDoc.close();
	}
	xhr.send();
}

function loadHomeError() {
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(this.readyState!==4) return;
		if(this.status!==200) return;
		var newDoc = document.open("text/html", "replace");
		newDoc.write(this.responseText);
		newDoc.close();
		$("#errortext").text("Incorrect UserID/Password");
		console.log($('#errortext').text());
		
	}
	xhr.open('GET','LoginForm.html',true)
	xhr.send();
}