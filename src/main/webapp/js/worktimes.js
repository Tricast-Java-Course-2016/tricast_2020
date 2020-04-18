window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
    loadWorktimes();
};

function bindListeners() {
    $("#saveWorkdayWorktimes").click(function(e) {
    	//saveWorktimes();
    });

}

//Save a not existing workday
function saveWorktimes(){
	let url = "/workinghours/rest/worktimes/create";
	
	let data = SB.Utils.readFormData($('#WorktimesForm'));
	
	$.post("/workinghours/rest/worktimes/create", JSON.stringify(data), function(data){
		alert('OK');
	});
}


//Get Workday's Worktimes
function loadWorktimes(){
	let id = "4";
	let url = "/workinghours/rest/worktimes/4";
	
	$.getJSON(url).done(function(data){
		displayWorktimes(data);
		
	}).fail(function(jqXHR, textStatus, errorThrown){
		SB.Utils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        console.log("completed Worktimes");
    });
	
}

function displayWorktimes(data){
	let workdayWorktimesList = [];
	data.forEach(function(entry) {
		worktime = new Worktime(entry);
		workdayWorktimesList.push(worktime);
	});
	console.log(workdayWorktimesList);
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
        workdayWorktimes : workdayWorktimesList
    }));
}

class Worktime {
	constructor(entry){
		this.id = entry.id;
		this.comment = entry.comment;
		this.modifiedBy = entry.modifiedBy;
		this.type = entry.type;
		this.workdayId = entry.workdayId;
		
		//Set the time fields with the correct time and it's format
		if (entry.modifiedEndTime == null){
			//Use the "simple" endTime field if we create a new Worktime
			if(entry.endTime[11] > 0){
				this.endTime = entry.endTime.substring(11,16);
			} else {
				this.endTime = entry.endTime.substring(12,16);
			}
		} else {
			//If modifiedEndTime is not empty, we always use this field over the "simple" endTime
			if(entry.modifiedEndTime[11] > 0){
				this.endTime = entry.modifiedEndTime.substring(11,16);
			} else {
				this.endTime = entry.modifiedEndTime.substring(12,16);
			}
		}

		if (entry.modifiedStartTime == null){
			//Use the "simple" startTime field if we create a new Worktime
			if(entry.startTime[11] > 0){
				this.startTime = entry.endTime.substring(11,16);
			} else {
				this.startTime = entry.endTime.substring(12,16);
			}
		} else {
			//If modifiedStartTime is not empty, we always use this field over the "simple" startTime
			if(entry.modifiedStartTime[11] > 0){
				this.startTime = entry.modifiedStartTime.substring(11,16);
			} else {
				this.startTime = entry.modifiedStartTime.substring(12,16);
			}
		}

		
	}
}