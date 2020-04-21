window.onload = function() {
	SB.Utils.initAjax();
	bindListeners();
};

function bindListeners() {
	$("#getSpecialdays").click(function(e) {
		loadSpecialdays();
	});

	$("#saveSpecialday").click(function(e) {
		saveSpecialday();
	});
}

function loadSpecialdays() {

	let url = "/workinghours/rest/specialdays";

	$.getJSON(url).done(function(data) {
		// Successful call
		displaySpecialdays(data);
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// Error happened
		SB.Utils.defaultErrorHandling(jqXHR);
	}).always(function() {
		// Run always
		console.log("complete");
	});
}

function displaySpecialdays(data) {
	let specialdayList = [];
	data.forEach(function(entry) {
		specialdayList.push({
			'id' : entry.id,
			'date' : entry.date,
		});
	});

	$('#specialdays-table tbody').html(
			Handlebars.compile($('#specialdays-row-template').html())({
				specialday : specialdayList
			}));
}


function saveSpecialday() {
	
	let dateValue = document.getElementById("inputDate").value;
	console.log(dateValue);
	let selectedDate = new Date(dateValue.substring(0, 4),dateValue.substring(5, 7)-1,dateValue.substring(8, 10));
	
	let requestObejct = {
		'date' : selectedDate
	};

	let data = SB.Utils.readFormData($('#postSpecialday'));

	$.post("/workinghours/rest/specialdays", JSON.stringify(requestObejct),
			function(requestObejct) {
				//
				alert('OK');
			});
}