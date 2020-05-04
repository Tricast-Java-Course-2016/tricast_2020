window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
    toggleAdminView();
};

function bindListeners() {
    
    $("#getOffDayLimits").click(function(e) {
        loadOffDayLimits();
    });

    $("#getOffDayRequests").click(function(e) {
        loadOffDayRequests();
    });

    $("#saveOffDay").click(function(e) {
        saveOffDay();
        $('#offdayModal').modal('hide');
        loadOffDayRequests();
    });
}

function toggleAdminView() {
    $("#toggle-admin").click(function() {
        $("#table-container").toggleClass("col-md-12 col-md-8");
        $(".hide").toggle();
    });
}

function loadOffDayLimits() {
	let url = "/workinghours/rest/offdaylimits";
    $.getJSON(url).done(function(data) {
        // Successful call
        displayOffDayLimits(data);
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // Error happened
        SB.Utils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        console.log("complete OffDayLimits");
    });
}

function displayOffDayLimits(data) {
    let offDayLimitList = [];
    data.forEach(function(entry) {
        offDayLimitList.push({
            'id' : entry.id,
            'userId' : entry.userId,
            'year' : entry.year,
            'type' : entry.type,
            'maxAmount' : entry.maximumAmount
        });
    });

    $('#offDayLimits-table tbody').html(Handlebars.compile($('#offDayLimits-row-template').html())({
        offDayLimits : offDayLimitList
    }));
}

function loadOffDayRequests() {
	let url = "/workinghours/rest/offdays/unapproved";
    $.getJSON(url).done(function(data) {
        // Successful call
        displayOffDayRequests(data);
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // Error happened
        SB.Utils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        console.log("complete OffDayRequests");
    });
}

function displayOffDayRequests(data) {
    let offDayList = [];
    data.forEach(function(entry) {
        offDayList.push({
            'userId' : entry.userId,
            'fullName' : entry.fullName,
            'startTime' : (entry.startTime).substring(0, 10),
            'endTime' : (entry.endTime).substring(0, 10),
            'type' : entry.type,
            'actualDayCount' : entry.actualDayCount
        });
    });

    $('.card-body').html(Handlebars.compile($('#offDayRequest').html())({
        offDays : offDayList
    }));
}

function saveOffDay() {
    let data = SB.Utils.readFormData($('#postOffDay'));

    $.post("/workinghours/rest/offdays/create", JSON.stringify(data), function(data) {
        console.log("Offday created");
    });
}