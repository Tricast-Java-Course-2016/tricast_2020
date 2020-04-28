window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
};

function bindListeners() {
    
    $("#getOffDayRequests").click(function(e) {
        saveoffday();
    });

    $("#deleteOffDay").click(function(e) {
    	deleteoffday();
    });
}

function saveoffday() {
	var offday = getoffdayParams();
	var method = "PUT";
	var url = "/workinghours/rest/offdays/createoffdays";
	if(offday.id === null || offday.id === undefined){
		method = "POST";
	}
	sendAjax(method, url, JSON.stringify(offday), 
	 	function(data, textStatus, xhr ) {
			selectedoffdayId = data.id;
			reloadOffdayAndUserData(false);
		},
		function(xhr) {
			showSaveInfo(false, getErrorMsg(xhr), false);
		}
	);
}

function deleteoffday() {
	var method = "DELETE";
	var url = "/workinghours/rest/offdays/deleteoffdays" + selectedOffdayId;
	sendAjax(method, url, null, 
	 	function(data, textStatus, xhr ) {
			reloadOffdayAndUserData(true);
		},
		function(xhr) {
			$("#saveInfo").html(getErrorMsg(xhr));
		}
	);
}