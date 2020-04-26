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
//	$("#backTo").click(function(e) {
//		backTo();
//	});
}

//function backTo() {
//	window.location.href = "/workinghours/WorktimesRecording.html";
//}

function searchUserSubmit() {
	let user = $('#searchedUser').val();

	let url = "/workinghours/rest/users/search?userName=" + user;

	$.getJSON(url).done(function(data) {
		displaySearchedUser(data);
	}).fail(function(jqXHR, textStatus, errorThrown) {
		SB.Utils.defaultErrorHandling(jqXHR);
	}).always(function() {
		console.log("complete");
	});
}

function displaySearchedUser(data) {
	let searchedUser = [];

	$('#manAccFirstName').val(data.firstName);
	$('#manAccMiddleName').val(data.middleName);
	$('#manAccLastName').val(data.lastName);
	$('#manAccUserName').val(data.userName);
	$('#manAccEmail').val(data.email);
	$('#manAccGenderField').val(data.gender);
	$('#manAccDob').val(data.dob);
	$('#manAccPhone').val(data.phone);
	$('#manAccPostcode').val(data.postcode);
	$('#manAccAddress').val(data.address);
	$('#manAccCompanyName').val(data.companyName);
	$('#manAccRoleNameField').val(data.roleId);
	$('#id').val(data.id);
	if (data.active)
		$('#manAccActiveField').val('1');
	else
		$('#manAccActiveField').val('0');
}

function saveChangesSubmit() {
	let data = SB.Utils.readFormData($('#postUser'));
	if ($('#id').val()) {
		$.ajax({
			type : 'PUT',
			dataType : 'json',
			url : "/workinghours/rest/users/update",
			headers : {
				"X-HTTP-Method-Override" : "PUT"
			},
			success : function(response) {
				alert('OK! Changes saved!');
			},
			error : function(response) {
				
				alert("Wrong input format!");
			},
			data : JSON.stringify(data)
		});
		

	} else {
		$.post("/workinghours/rest/users/create", JSON.stringify(data),
				function(data) {
					alert('OK! New user saved!');
					
				});
	}
	
	$('#manAccFirstName').val("");
	$('#manAccMiddleName').val("");
	$('#manAccLastName').val("");
	$('#manAccUserName').val("");
	$('#id').val("");
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