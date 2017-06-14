$(document).ready(function() {
	$.validator.addMethod("validCPR", function checkCPR(value, element){
		//Check if the cpr contains 10 numbers.
		if(value.search(/\d\d\d\d\d\d\d\d\d\d/)<-1){
			return false;
		}
		
		//Split into characther pairs.
		splitCPR=value.split("",2);
		
		//Valid date?
		if(parseInt(splitCPR[0])>31){
			return false;
		}
		//Valid Month?
		if(parseInt(splitCPR[1])>12){
			return false;
		}
		
		//Split into single characthers.
		splitCPR=value.split("");
		var cprsum=0;
		var controlNumber= [4,3,2,7,6,5,4,3,2];
		var controlCifre=parseInt(splitCPR[9])
		for(var i=0;i<9;i++){
			cprsum+=parseInt(splitCPR[i])*controlNumber[i];
		}
		if(11-(cprsum%11)==controlCifre){
			return true;
		}
		else{
			return false;
		}
	}, "Indtast et gyldigt CPR-nummer.");
	
	$.validator.addMethod("validID", function validID(value, element){
		if(value > 0 && value < 99999999) {
			return true;
		}
		else {
			return false;
		}
	}, "Indtast et gyldigt id.");
	
	$.validator.addMethod("validPassword", function checkPassword(password)
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
	}, "Password skal indeholde små og store bogstaver og tal");
});
	
function validateCreateUser() {
	// Add validation to create user form
	$("#user_create_form").validate({
		rules : {
			id: {
				required: true
			},
			name: {
				required: true,
				minlength: 2
			}
		}
	});
}

function validateLoginNewPass() {
	$("#login_new_pass_form").validate({
		rules : {
			password: {
				required: true
			},
			repeat_password: {
				required: true,
				equalTo: "#password"
			}
		},
		errorElement: "div",
		errorPlacement: function(div, element) {			
	        div.addClass("form-control-feedback");
	        div.insertAfter(element);
	       
	    },
	    highlight: function(element) {
	    	$(element).parent().removeClass("has-success").addClass("has-danger");
	    	$(element).parent().find("input").removeClass("form-control-success").addClass("form-control-danger");
	    },
	    unhighlight: function(element) {
	    	$(element).parent().removeClass("has-danger").addClass("has-success");
	    	$(element).parent().find("input").removeClass("form-control-danger").addClass("form-control-success");        
	    }
	});
}

function validateEditUser() {
	$("#user_edit_form").validate({
		rules : {
			id: {
				required: true,
				validID: true
			},
			name: {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			ini: {
				required: true,
				minlength: 2,
				maxlength: 4
			},
			cpr: {
				required: true,
				validCPR: true
			},
			role: {
				required: true
			},
			new_password: {
				required: function(element){
		            return $("input[name=\"password\"]").val() != "";
		        },
				validPassword: function(element){
					if($("input[name=\"password\"]").val() == "") {
						return false;
					}
					else {
						return true;
					}
		        },
			},
			repeat_new_password: {
				required: function(element){
		            return $("input[name=\"password\"]").val() != "";
		        },
				equalTo: function(element) {
					if($("input[name=\"password\"]").val() != "") {
						return "#new_password";
					}
					else {
						return false;
					}
				}
			}
		},
		errorElement: "div",
		errorPlacement: function(div, element) {			
	        div.addClass("form-control-feedback");
	        div.insertAfter(element);
	       
	    },
	    highlight: function(element) {
	    	$(element).parent().removeClass("has-success").addClass("has-danger");
	    	$(element).parent().find("input").removeClass("form-control-success").addClass("form-control-danger");
	    },
	    unhighlight: function(element) {
	    	$(element).parent().removeClass("has-danger").addClass("has-success");
	    	$(element).parent().find("input").removeClass("form-control-danger").addClass("form-control-success");        
	    }
	});
}

function validateEditUserAdmin() {
	$("#user_edit_admin_form").validate({
		rules : {
			id: {
				required: true,
				validID: true
			},
			name: {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			ini: {
				required: true,
				minlength: 2,
				maxlength: 4
			},
			cpr: {
				required: true,
				validCPR: true
			},
			role: {
				required: true
			}
		},
		errorElement: "div",
		errorPlacement: function(div, element) {			
	        div.addClass("form-control-feedback");
	        div.insertAfter(element);
	       
	    },
	    highlight: function(element) {
	    	$(element).parent().removeClass("has-success").addClass("has-danger");
	    	$(element).parent().find("input").removeClass("form-control-success").addClass("form-control-danger");
	    },
	    unhighlight: function(element) {
	    	$(element).parent().removeClass("has-danger").addClass("has-success");
	    	$(element).parent().find("input").removeClass("form-control-danger").addClass("form-control-success");        
	    }
	});
}

function validateCreateUser() {
	$("#user_create_form").validate({
		rules : {
			id: {
				required: true,
				validID: true
			},
			name: {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			ini: {
				required: true,
				minlength: 2,
				maxlength: 4
			},
			cpr: {
				required: true,
				validCPR: true
			},
			role: {
				required: true
			}
		},
		errorElement: "div",
		errorPlacement: function(div, element) {			
	        div.addClass("form-control-feedback");
	        div.insertAfter(element);
	       
	    },
	    highlight: function(element) {
	    	$(element).parent().removeClass("has-success").addClass("has-danger");
	    	$(element).parent().find("input").removeClass("form-control-success").addClass("form-control-danger");
	    },
	    unhighlight: function(element) {
	    	$(element).parent().removeClass("has-danger").addClass("has-success");
	    	$(element).parent().find("input").removeClass("form-control-danger").addClass("form-control-success");        
	    }
	});
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