
//LoggedInUser Hardcoded
const loggedInUser = 2;
//Every row has an unique identifier for delete method
let rowId = 0;
//False if day not exists yet, with current user
let newDay = false;

const workdayId = 2;

window.onload = function() {
	WT.WorktimeUtils.initAjax();
    bindListeners();
    loadWorktimes();
};

function bindListeners() {
	
    $("#saveWorkdayWorktimes").click(function(e) {
    	//UpdateWorkday();
    	saveWorkday();
    });
    //New worktime added
    $("#addNewWorktime").click(function(e) {
    	addWorktime();
    });

}





//Delete field
function deleteWorktime(rowIds){
	let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList($('#WorktimesForm'));
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
	let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList($('#WorktimesForm'));
	dataFromWorktimesForm.push(new emptyWorktimesToDisplay());
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
		workdayWorktimes : dataFromWorktimesForm
    }));
}


function aceptWorktimes(){
	if (newDay === false){
		saveWorkday();
	} else {
		UpdateWorkday();
	}
}


//Update
function UpdateWorkday(){
	let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList($('#WorktimesForm'));
	console.log("dataFromWorktimesForm", dataFromWorktimesForm);
	let worktimesUpdateRequest = [];
	let workdayUpdateRequest = {};
	let worktime;
	dataFromWorktimesForm.forEach(e => {
		worktime = new WorktimeRequestFromFormData(e);
		worktimesUpdateRequest.push(worktime);
	});
	console.log("worktimesCreationRequest", worktimesUpdateRequest);
	workdayUpdateRequest = new WorkdayUpdateRequest(worktimesUpdateRequest);
	console.log("workdayUpdateRequest", workdayUpdateRequest);
	/*
	$.ajax({
	    type: 'PUT',
	    url: '/workinghours/rest/worktimes/2',
	    contentType: 'application/json;charset=utf-8',
	    //workdayUpdateRequest: JSON.stringify(workdayUpdateRequest), // access in body
	    workdayUpdateRequest : {
	    	  "datasList": [
	    		    {
	    		      "comment": "Happy Pipe",
	    		      "endTime": "2020-04-25T13:00:28.473Z",
	    		      "modifiedBy": 1,
	    		      "id": 221,
	    		      "startTime": "2020-04-25T09:00:28.473Z",
	    		      "type": "OFFICE",
	    		      "workdayId": 2
	    		    }
	    		  ]
	    		}
	}).done(function () {
	    console.log('SUCCESS');
	}).fail(function (msg) {
	    console.log('FAIL');
	}).always(function (msg) {
	    console.log('ALWAYS');
	});
	
	*/
	/*$.ajax({
		/*type : 'PUT',
		dataType : 'json',
		url : "/workinghours/rest/worktimes/2",
		headers : {
			"X-HTTP-Method-Override" : "PUT"

		},
		success : function(response) {
			console.log("Workday has been changed");
		},
		error : function(response) {
			alert("Workday update failure.", response);
		},
		//workdayWorktimes : JSON.stringify(workdayUpdateRequest)
		workdayWorktimes : JSON.stringify(workdayUpdateRequest)
	});*/
	
	/*
	dataFromWorktimesForm.forEach(e => {
		worktime = new WorktimeRequestFromFormData(e);
		worktimesCreationRequest.push(worktime);
	});

	$.put("/workinghours/rest/worktimes/2", JSON.stringify(workdayCreationRequest), function(worktimesCreationRequest){
		console.log('Updated Worktime');
	});*/
	
	
	
}

class WorkdayUpdateRequest {
	constructor(worktimeUpdatedListRequest){
		this.datasList = worktimeUpdatedListRequest;
	}
}


//Save a not existing workday
function saveWorkday(){
	let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList($('#WorktimesForm'));
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
		console.log("Cannot save an empty list");
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
		
		//Modified
		this.workdayId = workdayId;
	}
}

//Get Workday's Worktimes
function loadWorktimes(){

	///workinghours/rest/worktimes/{workdayId}
	let url = "/workinghours/rest/worktimes/2";
	
	$.getJSON(url).done(function(data){
		displayWorktimes(data);
		
	}).fail(function(jqXHR, textStatus, errorThrown){
		WT.WorktimeUtils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        //console.log("completed Worktimes");
    });
	
}
/********************************
 * Display Worktimes
 *******************************/
function displayWorktimes(data){
	if (data.length !== 0){
		displayExistingDayWorktimes(data);
	} else {
		newDay = true;
		displayNotExistingDayEmptyWorktimes();
	}
}

function displayExistingDayWorktimes(data){
	let loadedListForAsyncronousWorking = [];
	data.forEach(function(entry) {
		worktime = new WorktimeResponseToDisplay(entry);
		loadedListForAsyncronousWorking.push(worktime);
	});
	$('#worktimes-table').html(Handlebars.compile($('#worktimes-row-template').html())({
        workdayWorktimes : loadedListForAsyncronousWorking
    }));
}

//Load empty rows if there is not a workday in a current date
function displayNotExistingDayEmptyWorktimes() {
	let workdayWorktimesList = [];
	const worktimesToDisplayCount = 2;
	for (i = 0; i < worktimesToDisplayCount; i++){
		workdayWorktimesList.push(new emptyWorktimesToDisplay());
	}
	
	//console.log(workdayWorktimesList);
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
		this.comment = "Happy time";
		this.modifiedBy = loggedInUser;
		this.rowId = rowId++;
	}
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