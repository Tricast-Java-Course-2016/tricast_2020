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

	var d = new Date();
	var n = d.getTimezoneOffset();

	let requestObejct = {
		date : $("#date").val(),
	};

	let data = SB.Utils.readFormData($('#postSpecialday'));

	$.post("/workinghours/rest/specialdays", JSON.stringify(data),
			function(data) {
				//
				alert('OK');
			});
}