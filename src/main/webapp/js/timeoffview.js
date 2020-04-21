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
	let url = "/workinghours/rest/offdays";
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
    let offDayLimitList = [];
    data.forEach(function(entry) {
        offDayLimitList.push({
            'userId' : entry.userId,
            'startTime' : (entry.startTime).substring(0, 10), // Backenden kellene lekezelni
            'endTime' : (entry.endTime).substring(0, 10),
            'type' : entry.type
            // Calculated day
        });
    });

    $('.card-body').html(Handlebars.compile($('#offDayRequest').html())({
        offDayLimits : offDayLimitList
    }));
}

function toggleAdminView() {
    $("#toggle-admin").click(function() {
        $("#table-container").toggleClass("col-md-12 col-md-8");
        $(".hide").toggle();
    });
}