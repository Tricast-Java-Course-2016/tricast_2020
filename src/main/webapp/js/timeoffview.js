window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
    toggleAdminView();
    //responseToOffer();
};

function bindListeners() {
    
    $("#getCurrentOffDays").click(function(e) {
        loadCurrentOffDays();
    });

    $("#getOffDayRequests").click(function(e) {
        loadOffDayRequests();
    });

    $("#saveOffDay").click(function(e) {
        saveOffDay();
        $('#offdayModal').modal('hide');
        // location.reload();
        // setTimeout(function() { loadCurrentOffDays(); }, 500);
    });
}

function toggleAdminView() {
    $("#toggle-admin").click(function() {
        $("#table-container").toggleClass("col-md-12 col-md-8");
        $(".hide").toggle();
    });

    if (localStorage.getItem("WH_USER_ROLE") == "1") {
        $(".admin-view").show();
    } else {
        $(".admin-view").hide();
    }
}

function responseToOffer() {
	$("#acceptOffday").click(function() {
        $(this).parents().hide();
        console.log('accept hide');
        // send accept
    });

    $(".request").on('click', '#declineOffday',function() {
        $(this).parents().hide();
        console.log('decline hide');
        // send decline
    });
}

function loadCurrentOffDays() {
	let url = "/workinghours/rest/offdays/current";
    $.getJSON(url).done(function(data) {
        // Successful call
        displayCurrentOffDays(data);
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // Error happened
        SB.Utils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        console.log("complete Current OffDays");
    });
}

function displayCurrentOffDays(data) {
    let currentOffDayList = [];
    data.forEach(function(entry) {
        currentOffDayList.push({
            'fullName' : entry.fullName,
            'startTime' : entry.startTime,
            'endTime' : entry.endTime,
            'actualDayCount' : entry.actualDayCount,
            'type' : entry.type
        });
    });

    $('#currentOffDays-table tbody').html(Handlebars.compile($('#currentOffDays-row-template').html())({
        currentOffDays : currentOffDayList
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