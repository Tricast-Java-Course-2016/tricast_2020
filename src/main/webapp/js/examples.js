window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
};

function bindListeners() {
    $("#getOffDayLimits").click(function(e) {
        loadOffDayLimits();
    });

    $("#saveOffDayLimit").click(function(e) {
        saveOffDayLimit();
    });
}

function loadOffDayLimits() {

    let url = "/workinghours/rest/offdaylimits";

    // We could add a year filter
    // let year = $('#exYear').val();
    // let url = "/workinghours/rest/offdaylimits?" + year;

    $.getJSON(url).done(function(data) {
        // Successful call
        displayOffDayLimits(data);
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // Error happened
        SB.Utils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        console.log("complete");
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

function saveOffDayLimit() {

    // let requestObejct = {
    // userId : $("#exUserId").val(),
    // year : $("#exDayOffYear").val()
    // };

    let data = SB.Utils.readFormData($('#postExample'));

    $.post("/workinghours/rest/offdaylimits", JSON.stringify(data), function(data) {
        //
        alert('OK');
    });
}