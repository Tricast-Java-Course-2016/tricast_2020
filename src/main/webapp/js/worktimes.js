
//LoggedInUser Hardcoded
let loggedInUser = null;
//Every row has an unique identifier for delete method
let rowId = 0;
//False if day not exists yet, with current user
let newDay = false;
const newDayId = -1;
let currentWorkdayId = newDayId;
//const workdayId = 2;

window.onload = function() {
	WT.WorktimeUtils.initAjax();
    bindListeners();
    loggedInUser = SB.Utils.getUserId();
    currentWorkdayId = WT.WorktimeUtils.getWorkdayId();
    loadWorktimes();
};
/* Soon display the worktime's worktype*/
window.Handlebars.registerHelper('select', function( value, options ){
    var $el = $('<select />').html( options.fn(this) );
    $el.find('[value="' + value + '"]').attr({'selected':'selected'});
    return $el.html();
});

function bindListeners() {
	
    $("#saveWorkdayWorktimes").click(function(e) {
    	acceptWorktimes();
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


function acceptWorktimes(){
	if (currentWorkdayId === newDayId.toString()){
		console.log("save");
		saveWorkday();
	} else {
		UpdateWorkday();
	}
}


//Update
function UpdateWorkday(){
	let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList($('#WorktimesForm'));
	//console.log("dataFromWorktimesForm", dataFromWorktimesForm);
	let worktimesUpdateRequest = [];
	let workdayUpdateRequest = {};
	let worktime;

	let notCorrectWorktimes = false;
	
	dataFromWorktimesForm.forEach(e => {
		worktime = new WorktimeRequestFromFormData(e);
		worktimesUpdateRequest.push(worktime);
		//if((check.getTime() <= to.getTime() && check.getTime() >= from.getTime())) 
	});
	
	//console.log("worktimesUpdateRequest", worktimesUpdateRequest);
	workdayUpdateRequest = new WorkdayUpdateRequest(worktimesUpdateRequest);
	//console.log("workdayUpdateRequest", workdayUpdateRequest);
	
	$.ajax({
		type : 'PUT',
		dataType : 'json',
		url : "/workinghours/rest/worktimes/" + currentWorkdayId,
		headers : {
			"X-HTTP-Method-Override" : "PUT"
		},
		data : JSON.stringify(workdayUpdateRequest)
	});
}

class WorkdayUpdateRequest {
	constructor(worktimeUpdatedListRequest){
		this.datasList = worktimeUpdatedListRequest;
		this.userId = loggedInUser;
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
		
		let response = $.post("/workinghours/rest/worktimes/create", JSON.stringify(workdayCreationRequest), function(workdayCreationRequest){
			WT.WorktimeUtils.saveWorkdayData(response.responseJSON.id, WT.WorktimeUtils.getWorkdayDate());
			console.log('Saved Worktime');
			//location.reload();
		});
		
	} else {
		console.log("Cannot save an empty list");
	}
	
}

function checkStartAndEndTime(startTimes, endTimes){
	
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
		//this.modifiedBy = SB.Utils.getUserId();
		//Modified
		//this.workdayId = parseInt(workdayId);
		this.workdayId = currentWorkdayId;
	}
}

//Get Workday's Worktimes
function loadWorktimes(){

	///workinghours/rest/worktimes/{workdayId}
	//let url = "/workinghours/rest/worktimes/" + WT.WorktimeUtils.getWorkdayId();
	let url = "/workinghours/rest/worktimes/" + currentWorkdayId
	//let url = "/workinghours/rest/worktimes/2";
	
	$.getJSON(url).done(function(data){
		displayWorktimes(data);
		displayUsernameAndWorkdayDate();
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

//Display Username and Workday's date
function displayUsernameAndWorkdayDate(){
	const userNameAndWorkdayDate = {
		username : SB.Utils.getUsername(),
		date : WT.WorktimeUtils.getWorkdayDate()
	};
	$('#row-first-table').html(Handlebars.compile($('#worktimes-row-first-template').html())({
		rowFirst : userNameAndWorkdayDate
    }));
	
}

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
		//this.modifiedBy = SB.Utils.getUserId();
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