function loadHome() {
	
	var xhr = new XMLHttpRequest();
	xhr.open('GET','LoginForm.html',true);
	xhr.onreadystatechange = function(){
		if(this.readyState!==4) return;
		if(this.status!==200) return;
//		console.log($('html'));	
//		console.log(this.responseText);
//		$('html').html(this.responseText);
		var newDoc = document.open("text/html", "replace");
		newDoc.write(this.responseText);
		newDoc.close();
//		console.log($('html').html());
	}
	xhr.send();
}