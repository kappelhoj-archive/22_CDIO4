/*
 * jQuery Validation methods
 * */
$.validator.addMethod("validCPR", function checkCPR(cpr){
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
		return true;
	}
	else{
		return false;
	}
}, "Indtast et gyldigt CPR-nummer.");

$.validator.addMethod("validID", function validID(id){
	if(id >= 1 && id <= 99999999 && id % 1 == 0) {
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


$.validator.addMethod("validNetto", function checkNetto(netto)
{
	if(netto >= 0.05 && netto <= 20) {
		return true
	}
	else {
		return false;
	}

}, "Netto besked");

$.validator.addMethod("validTolerance", function checkTolerance(tolerance)
{
	if(tolerance >= 0.1 && tolerance <= 10) {
		return true
	}
	else {
		return false;
	}

}, "Tolerance besked");

/*
 * jQuery Validation set defaults
 * */

$.validator.setDefaults({
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

/*
 * Functions
 * */

function validateLoginNewPass() {
	$("#login_new_pass_form").validate({
		rules : {
			password: {
				required: true,
				validPassword: true
			},
			repeat_password: {
				required: true,
				equalTo: function(element) {
					if($("#password").val() != "") {
						return "#password";
					}
					else {
						return false;
					}
				}
			}
		}
	});
}

function validateUser(form) {
	$(form).validate({
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
		}
	});
}

function validateRawMaterial(form) {
	$(form).validate({
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
			supplier: {
				required: true,
				minlength: 2,
				maxlength: 20
			}
		}
	});
}

function validateRecipe(form) {
	$(form).validate({
		rules : {
			recipeId: {
				required: true,
				validID: true
			},
			recipeName: {
				required: true,
				minlength: 2,
				maxlength: 20
			}
		}
	});
}

function validateRecipeComp(form) {
	$(form).validate({
		rules : {
			recipeId: {
				required: true,
				validID: true
			},
			rawMaterialId: {
				required: true,
				validID: true
			},
			nomNetto: {
				required: true,
				validNetto: true
			},
			tolerance: {
				required: true,
				validTolerance: true
			}
		}
	});
}

function validateRawMaterialBatch(form) {
	$(form).validate({
		rules : {
			rbId: {
				required: true,
				validID: true
			},
			rawMaterialId: {
				required: true,
				validID: true
			},
			amount: {
				required: true
			}
		}
	});
}

function validateProductBatch(form) {
	$(form).validate({
		rules : {
			pbId: {
				required: true,
				validID: true
			},
			recipeId: {
				required: true,
				validID: true
			}
		}
	});
}

/*new_password: {
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
}*/