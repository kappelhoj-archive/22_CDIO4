$(document).ready(function() {
	
	/*
	 * User Edit link and page
	 * */
	$(document).on("click", ".user-edit", function(event) {
		event.preventDefault();
		var userid = $(this).parents("tr").children("td:first").text();
		$.ajax({
			url : "rest/CRUD/get-user",
			type : "POST",
			data : userid,
			contentType: "application/json",
			success : function(data){
				console.log(data);
				$(".users-page").load("user-edit.html", function(){
//					formmodified=0;
//					$('form *').change(function(){
//					formmodified=1;
//					});
//					window.onbeforeunload = confirmExit;
//					function confirmExit() {
//					if (formmodified == 1) {
//					return "New information not saved. Do you wish to leave the page?";
//					}
//					}
					$("input[name=\"userId\"]").val(data.userId);
					$("input[name=\"userName\"]").val(data.userName);
					$("input[name=\"ini\"]").val(data.ini);
					$("input[name=\"cpr\"]").val(data.cpr);
					$("input[name=\"roles[]\"]").val(data.roles);
				});
			},
			error: function(data){
				console.log(data);
				alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});

	/*
	 * Delete User
	 * */
	$(document).on("click", ".user-delete", function(event) {
		event.preventDefault();
		var userid = $(this).parents("tr").children("td:first").text();
		$.ajax({
			url : "rest/CRUD/delete-user",
			type : "DELETE",
			data : userid,
			contentType: "application/json",
			success : function(data){
				showUserListPage();
			},
			error: function(data){
				console.log(data);
				alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});

	/*
	 * Edit User PUT
	 * */
	$(document).on("submit", ".user-edit-page form", function(event) {
		event.preventDefault();
		$.ajax({
			url : "rest/CRUD/edit-user",
			type : "PUT",
			contentType: "application/json",
			data : $(this).serializeJSON(),
			success : function(data){
				$(".users-page").load("user-list.html", function() {
					showUserListPage();
				});
			},
			error: function(data){
				console.log(data);
				alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});

	/*
	 * Create user page
	 * */
	$(document).on("submit", ".user-create-page form", function(event) {
		event.preventDefault();
		$.ajax({
			url : "rest/CRUD/create-user",
			type : "POST",
			contentType: "application/json",
			data : $(this).serializeJSON(),
			success : function(data){
				$(".users-page").load("user-list.html", function() {
					showUserListPage();
				});
			},
			error: function(data){
				console.log(data);
				alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});
	
});


//Check if a password follows the given rules
function checkPassword(password)
{
	//Counter for the different groups.
	var counter = 0;
	//Check passsword length
	if(password.length<6){
		return false;
	}
	//Check for capital letters.
	if(password.search(/[A-Z]/)>-1){
		counter++;
	}
	//Check for lowercase letters
	if(password.search(/[a-z]/)>-1){

		counter++;
	}
	//Check for numbers 
	if(password.search(/[0-9]/)>-1){
		counter++;
	}
	//Check for any of the given symbols.
	if(password.search(/[-._+!?=]/)>-1){
		counter++;
	}
	
	//Are 3 or more groups included.
	if(counter>=3){
		return true;
	}
	else{
		return false
	}
}

function checkCPR(cpr){
	
	//Check if the cpr contains 10 numbers.
	if(cpr.search(/\d\d\d\d\d\d\d\d\d\d/)<-1){
		return false;
	}
	
	//Split into characther pairs.
	splitCPR=cpr.split("",2);
	
	//Valid date?
	if(parseInt(splitCPR[0])>31){
		return false;
	}
	//Valid Month?
	if(parseInt(splitCPR[1])>12){
		return false;
	}
	
	//Split into single characthers.
	splitCPR=cpr.split("");
	var cprsum=0;
	var controlNumber= [4,3,2,7,6,5,4,3,2];
	var controlCifre=parseInt(splitCPR[9])
	for(var i=0;i<9;i++){
		cprsum+=parseInt(splitCPR[i])*controlNumber[i];
	}
	if(11-(cprsum%11)==controlCifre){
		return true
	}
	else{
		return false;
	}
	
}

//generate initials from a name.
function generateInitials(name){
	var initials="";
	splitName=name.split(" ");
	if(splitName.length<3){
		//Add the two first letters of every name.
		for(var i=0;i<splitName.length;i++){
			
			initials+=splitName[i].substring(0,2);
		}
	}
	else{
		//Add first letter of the first name. 
		initials+=splitName[0].substring(0,1);
		
		//Make sure the initials length match:
		var modifier;
		if(splitName.length==3){
			modifier=2;
		}
		else{
			modifier=3;
		}
		
		//Add the first letter of the last three names, or two if there are 3 names.
		for(var i=splitName.length-modifier;i<splitName.length;i++){
			initials+=splitName[i].substring(0,1);
		}
	}
	return initials
}

