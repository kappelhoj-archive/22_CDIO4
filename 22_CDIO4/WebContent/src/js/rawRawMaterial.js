/**
 * 
 */

$(document).ready(function(){
	
	/* Create raw material javascript */
	
	$(document).on("submit", "#raw_material_create_form", function(event){
		event.preventDefault();
		$.ajax({
			url: "rest/raw_material/create",
			type: "POST",
			contentType: "application/json",
			data: $("#raw_material_create_form").serializeJSON(),
			success: function(data) {
				if(data == "success")
				{
					
				}
				else
				{
					
				}
			},
			error: function(data){
				console.log(data);
			}
		});
	});
});
