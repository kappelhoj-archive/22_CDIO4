
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