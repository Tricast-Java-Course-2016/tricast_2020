window.onload = function() {
	SB.Utils.initAjax();
	bindListeners();
};

function bindListeners() {
	$("#changePasswordSubmit").click(function(e) {
		changePassword();
	});
}

function changePassword() {
	let data = SB.Utils.readFormData($('#changePassword'));

	if ($('#newPassword').val() == $('#confirmNewPassword').val()) {
		$.ajax({
			type : 'PUT',
			dataType : 'json',
			url : "/workinghours/rest/users/pwdc",
			headers : {
				"X-HTTP-Method-Override" : "PUT"

			},
			success : function(response) {
				alert('OK pwd changed');
			},
			error : function(response) {
				alert("Old password is not correct");
			},
			data : JSON.stringify(data)
		});

	} else {
		alert('New Pwd is not correct');
	}
}
