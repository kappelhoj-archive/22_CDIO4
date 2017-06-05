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