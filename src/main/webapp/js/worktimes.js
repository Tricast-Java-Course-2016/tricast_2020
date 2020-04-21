
//LoggedInUser Hardcoded
const loggedInUser = 2;
//Use a list for store Workday's Worktimes
let workdayWorktimesList = [];

window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
    //displayEmptyWorktimeFieldIfNotExistWorkdayAtCurrentDate();
    loadWorktimes();
};

function bindListeners() {
	
    $("#saveWorkdayWorktimes").click(function(e) {
    	saveWorktimes();
    });
    //New worktime added
    $("#addNewWorktime").click(function(e) {
    	addWorktime();
    });

}

//Load empty rows if there is not a workday in a current date
//Not used yet, update soon -> when we want to create a workday which doesn't exists there gonna be 2 empty worktime row
function displayEmptyWorktimeFieldIfNotExistWorkdayAtCurrentDate() {
	let workdayWorktimesList = [];
	const worktimesToDisplayCount = 2;
	
	for (i = 0; i < worktimesToDisplayCount; i++){
		workdayWorktimesList.push(new emptyWorktimesToDisplay());
	}
	
	console.log(workdayWorktimesList);
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
        workdayWorktimes : workdayWorktimesList
    }));
}

//display Empty fields
class emptyWorktimesToDisplay {
	constructor() {
		this.startTime = "";
		this.endTime = "";
		this.type = "";
		this.comment = "";
	}
}

//Delete field
//Now just delete the dirst element from the list, then display the modified list
function deleteWorktime(){
	workdayWorktimesList.pop();
	
	displayWorktimes(workdayWorktimesList);
	console.log(workdayWorktimesList);
}

//Add an empty worktime row
function addWorktime(){
	
	workdayWorktimesList.push(new emptyWorktimesToDisplay());
	
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
		workdayWorktimes : workdayWorktimesList
    }));
	console.log(workdayWorktimesList);
}

//Save a not existing workday
function saveWorktimes(){
	
	if(workdayWorktimesList.length != 0)
	{
		///Date
		
		//Use the selected date from the WorktimesRecording.html 
		let dateTextFromCurrentHtmlElement = document.getElementById("currentDate").textContent;
		let selectedDate = new Date(dateTextFromCurrentHtmlElement.substring(0, 4),dateTextFromCurrentHtmlElement.substring(5, 7),dateTextFromCurrentHtmlElement.substring(8, 10));
		
		//Go through the input fields and push them into a List
		let worktimesCreationRequest = [];
		let workdayCreationRequest = {};
		let worktime;
		let dataFromWorktimesForm = SB.Utils.readWorktimesFormDataList($('#WorktimesForm'));
		dataFromWorktimesForm.forEach(e => {
			worktime = new WorktimeRequestFromFormData(e, selectedDate);
			worktimesCreationRequest.push(worktime);
		});
		console.log(worktimesCreationRequest);
		
		workdayCreationRequest = {
				'date' :  selectedDate,
				'userId' : loggedInUser,
				"worktimesCreationRequest" : worktimesCreationRequest
		}
		
		$.post("/workinghours/rest/worktimes/create", JSON.stringify(workdayCreationRequest), function(workdayCreationRequest){
			console.log('Saved Worktime');
			//alert('OK');
		});
	} else {
		console.log("Cannot save empty list");
	}
	
}


class WorktimeRequestFromFormData {
	constructor(e, selectedDate){
		//Modifie the start- and end-Time to the correct format which can be save as ZonedDateTime
		let startTimeInCorrectForm = selectedDate;
		let endTimeInCorrectForm = selectedDate;
		//We cut the ":" from '08:00' and set the hour and it's minutes
		startTimeInCorrectForm.setHours(e.startTime.substring(0, 2), e.startTime.substring(3,5))
		endTimeInCorrectForm.setHours(e.endTime.substring(0, 2), e.endTime.substring(3,5));
		//Use the value which has been set before
		this.startTime = startTimeInCorrectForm;
		this.endTime = endTimeInCorrectForm;
		
		this.type = e.type;
		this.comment = e.comment;
		this.modifiedBy = loggedInUser;
		
	}
}

//Get Workday's Worktimes
function loadWorktimes(){
	let url = "/workinghours/rest/worktimes/2";
	
	$.getJSON(url).done(function(data){
		displayWorktimes(data);
		
	}).fail(function(jqXHR, textStatus, errorThrown){
		SB.Utils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        //console.log("completed Worktimes");
    });
	
}

function displayWorktimes(data){
	//let workdayWorktimesList = [];
	data.forEach(function(entry) {
		worktime = new WorktimeResponseToDisplay(entry);
		workdayWorktimesList.push(worktime);
	});
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
        workdayWorktimes : workdayWorktimesList
    }));
}


//The loaded Worktime Format from request
class WorktimeResponseToDisplay {
	constructor(entry){
		const hoursStartsInDate = 11;
		const hoursEndsInDate = 16;
		
		this.id = entry.id;
		this.comment = entry.comment;
		this.modifiedBy = entry.modifiedBy;
		this.type = entry.type;
		this.workdayId = entry.workdayId;
		
		//Set the time fields with the correct time and it's format
		if (entry.modifiedEndTime == null){
			//Use the "simple" endTime field if we create a new Worktime
			this.endTime = entry.endTime.substring(hoursStartsInDate,hoursEndsInDate);
		} else {
			//If modifiedEndTime is not empty, we always use this field over the "simple" endTime
			this.endTime = entry.modifiedEndTime.substring(hoursStartsInDate,hoursEndsInDate);
		}

		if (entry.modifiedStartTime == null){
			//Use the "simple" startTime field if we create a new Worktime
			this.startTime = entry.startTime.substring(hoursStartsInDate,hoursEndsInDate);
		} else {
			//If modifiedStartTime is not empty, we always use this field over the "simple" startTime
			this.startTime = entry.modifiedStartTime.substring(hoursStartsInDate,hoursEndsInDate);
		}

		
	}
}