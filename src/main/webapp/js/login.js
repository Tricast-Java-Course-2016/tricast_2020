window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
};

function bindListeners() {
    $("#loginSubmit").click(function(e) {
        loginUser();
    });
}

function loginUser() {

    let data = SB.Utils.readFormData($('#loginUser'));
    
	$.post("/workinghours/rest/users/login", JSON.stringify(data),
			function(data) {
				alert('OK login');
			});
}



