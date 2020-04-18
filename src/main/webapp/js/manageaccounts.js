window.onload = function() {
	SB.Utils.initAjax();
	bindListeners();
};

function bindListeners() {
	$("#searchUserSubmit").click(function(e) {
		searchUserSubmit();
	});

	$("#saveChangesSubmit").click(function(e) {
		saveChangesSubmit();
	});
}

function searchUserSubmit() {
	let user = $('#searchedUser').val();

	let url = "/workinghours/rest/users/search?userName=" + user;

	console.log(url);
//	alert(url);

	
	$.getJSON(url).done(function(data) {
		// Successful call
		console.log("jott adat a servetol");
		displaySearchedUser(data);
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// Error happened
		SB.Utils.defaultErrorHandling(jqXHR);
	}).always(function() {
		// Run always
		console.log("complete");
	});
}

function displaySearchedUser(data) {
	let searchedUser = [];

    console.log(data.id);

    $('#manAccFirstName').val(data.firstName);
    $('#manAccMiddleName').val(data.middleName);
    $('#manAccLastName').val(data.lastName);
    $('#manAccUserName').val(data.userName);
    $('#manAccUserNameHidden').val(data.userName);
    //$('#manAccPassword').val(data.id);
    $('#manAccEmail').val(data.email);
    $('#manAccGenderField').val(data.gender);
    $('#manAccDob').val(data.dob);
    $('#manAccPhone').val(data.phone);
    $('#manAccPostcode').val(data.postcode);
    $('#manAccAddress').val(data.address);
    $('#manAccCompanyName').val(data.companyName);
    $('#manAccRoleNameField').val(data.roleId);
    
    if (data.active)
    	$('#manAccActiveField').val('1');
    else
    	$('#manAccActiveField').val('0');
    
    $('#id').val(data.id);
    
    // user name input field disabled
    //$('#manAccUserName').prop("disabled", true);
}

function saveChangesSubmit() {
	if($('#manAccUserNameHidden').val()){
		let data = SB.Utils.readFormData($('#postUser'));

		 /*
		$.post("/workinghours/rest/users/update", JSON.stringify(data), function(
				data) {
			//
			alert('OK Update');
		});
		*/
		$.ajax({
		    type: 'PUT', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
		    dataType: 'json', // Set datatype - affects Accept header
		    url: "/workinghours/rest/users/update", // A valid URL
		    headers: {"X-HTTP-Method-Override": "PUT"}, // X-HTTP-Method-Override set to PUT.
		    data: JSON.stringify(data) // Some data e.g. Valid JSON as a string
		});
		
	}	else {
		let data = SB.Utils.readFormData($('#postUser'));

		$.post("/workinghours/rest/users/create", JSON.stringify(data), function(
				data) {
			//
			alert('OK Save');
		});
	}
	
	
	
	// field cleanup
    $('#manAccFirstName').val("");
    $('#manAccMiddleName').val("");
    $('#manAccLastName').val("");
    $('#manAccUserName').val("");
    $('#manAccUserNameHidden').val("");
    $('#manAccPassword').val("");
    $('#manAccEmail').val("");
    $('#manAccGenderField').val("");
    $('#manAccDob').val("");
    $('#manAccPhone').val("");
    $('#manAccPostcode').val("");
    $('#manAccAddress').val("");
    $('#manAccCompanyName').val("");
    $('#manAccRoleNameField').val("");
    $('#manAccActiveField').val("");
    $('#searchedUser').val("");
    
}