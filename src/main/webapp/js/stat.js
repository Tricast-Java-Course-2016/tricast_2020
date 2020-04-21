window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
    loadStats();
};

function bindListeners() {
    $("#getOffDayLimits").click(function(e) {
        loadOffDayLimits();
    });

    $("#saveOffDayLimit").click(function(e) {
        saveOffDayLimit();
    });
}


function loadStats(){
	let url = "/workinghours/rest/worktimes/Stats/2020/2";
	
	$.getJSON(url).done(function(data) {
        // Successful call
        displayStats(data);
        console.log(data);
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // Error happened
        SB.Utils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        console.log("complete");
    });
}

function displayStats(data) {
	
	let summaryStats = {
			'numberOfworkday' : data.numberOfworkday,
			'worktimehours' : data.worktimehours,
			'overtimes' : data.overtimes
	};
	
	let statistiOfTheYearList = data.worktimesOfTheWeeks;
	
	
	console.log(statistiOfTheYearList);
		
	
	$('#statsSummary-table').html(Handlebars.compile($('#stats-row-template').html())({
		numberOfworkday : summaryStats.numberOfworkday,
		worktimehours : summaryStats.worktimehours,
		overtimes : summaryStats.overtimes
    }));
	
	/*data.forEach(function(entry) {
		
	})*/
	
   /*let StatsList = [];
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
    }));*/
}
