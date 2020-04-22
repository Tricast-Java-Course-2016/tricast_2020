
//LoggedInUser Hardcoded
const loggedInUser = 2;
//Use a list for store Workday's Worktimes

let rowId = 0;

window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
    //displayEmptyWorktimeFieldIfNotExistWorkdayAtCurrentDate();
    loadWorktimes();
};

function bindListeners() {
	
    $("#saveWorkdayWorktimes").click(function(e) {
    	//UpdateWorktimes();
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
		this.type = "HOMEOFFICE";
		this.comment = "";
		this.modifiedBy = loggedInUser;
		this.rowId = rowId++;
	}
}

//Delete field
function deleteWorktime(rowIds){
	let dataFromWorktimesForm = SB.Utils.readWorktimesFormDataList($('#WorktimesForm'));
	dataFromWorktimesForm = dataFromWorktimesForm.filter(worktime => parseInt(worktime.rowId) !== rowIds);
	refreshToDisplayWorktimes(dataFromWorktimesForm);
}


function refreshToDisplayWorktimes(worktimesList) {
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
        workdayWorktimes : worktimesList
    }));
}

//Add an empty worktime row
function addWorktime(){
	
	let dataFromWorktimesForm = SB.Utils.readWorktimesFormDataList($('#WorktimesForm'));
	dataFromWorktimesForm.push(new emptyWorktimesToDisplay());
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
		workdayWorktimes : dataFromWorktimesForm
    }));
}


function UpdateWorktimes(){
	let selectedDate = getSelectedWorkdayDate();
	
	let worktimesCreationRequest = [];
	let workdayCreationRequest = {};
	let worktime;
	let dataFromWorktimesForm = SB.Utils.readWorktimesFormDataList($('#WorktimesForm'));
	dataFromWorktimesForm.forEach(e => {
		worktime = new WorktimeRequestFromFormData(e, selectedDate);
		worktimesCreationRequest.push(worktime);
	});
	console.log(worktimesCreationRequest);
}


//Save a not existing workday
function saveWorktimes(){
	let dataFromWorktimesForm = SB.Utils.readWorktimesFormDataList($('#WorktimesForm'));
	if(dataFromWorktimesForm.length != 0)
	{
		//Go through the input fields and push them into a List
		let selectedDate = getSelectedWorkdayDate();
		
		let worktimesCreationRequest = [];
		let workdayCreationRequest = {};
		let worktime;
		
		dataFromWorktimesForm.forEach(e => {
			worktime = new WorktimeRequestFromFormData(e);
			worktimesCreationRequest.push(worktime);
		});
		workdayCreationRequest = new WorkdayCreationRequest(selectedDate, loggedInUser, worktimesCreationRequest);
		
		$.post("/workinghours/rest/worktimes/create", JSON.stringify(workdayCreationRequest), function(workdayCreationRequest){
			console.log('Saved Worktime');
		});
	} else {
		console.log("Cannot save empty list");
	}
	
}

class WorkdayCreationRequest {
	constructor(date,userId,worktimesCreationRequest){
		this.date = date;
		this.userId = userId;
		this.worktimesCreationRequest = worktimesCreationRequest;
	}
}

function getSelectedWorkdayDate(){
	let dateTextFromCurrentHtmlElement = document.getElementById("currentDate").textContent;
	const yearMonthDay = convertStringToYearMonthDay(dateTextFromCurrentHtmlElement);
	let selectedDate = new Date(yearMonthDay[0], yearMonthDay[1], yearMonthDay[2]);
	return selectedDate;
}
//Example: 2020.04.10 => 2020, 5-1, 10
function convertStringToYearMonthDay(currentDayString){
	const yearMonthDay = [
		currentDayString.substring(0, 4),
		parseInt(currentDayString.substring(5, 7))-1,
		currentDayString.substring(8, 10)
	];
	return yearMonthDay;
}

class WorktimeRequestFromFormData {
	constructor(e){
		//Modifie the start- and end-Time to the correct format which can be save as ZonedDateTime
		let startTimeInCorrectForm = getSelectedWorkdayDate();
		let endTimeInCorrectForm = getSelectedWorkdayDate();
		
		//Cut the ":" from '08:00' and set the hour and it's minutes
		endTimeInCorrectForm.setHours(e.endTime.substring(0, 2), e.endTime.substring(3,5));
		startTimeInCorrectForm.setHours(e.startTime.substring(0, 2), e.startTime.substring(3,5));
		
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

	///workinghours/rest/worktimes/{workdayId}
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
	let loadedListForAsyncronousWorking = [];
	data.forEach(function(entry) {
		worktime = new WorktimeResponseToDisplay(entry);
		loadedListForAsyncronousWorking.push(worktime);
	});
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
        workdayWorktimes : loadedListForAsyncronousWorking
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
		this.rowId = rowId++;
		
	}
}