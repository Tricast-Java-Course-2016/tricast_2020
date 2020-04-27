let loggedInUser = null;
let currMonth = null;
let currYear = null;
const newDayId = -1;
window.onload = function() {
	WT.WorktimeUtils.initAjax();
    bindListeners();
    init();
    loadWorkdays();

};

function init(){
	loggedInUser = SB.Utils.getUserId();
    currMonth = new Date().getMonth();
    currYear = new Date().getFullYear();
}

/*
const monthNames = ["January", "February", "March", "April", "May", "June",
	  "July", "August", "September", "October", "November", "December"
	];
*/

function bindListeners() {
	
}

function modifyWorktimes(workdayId, date){
	WT.WorktimeUtils.saveWorkdayData(workdayId,date);
}

function loadWorkdays(){
	//let url = "/workinghours/rest/workdays/workedhours/2";
	let url = "/workinghours/rest/workdays/workedhours/" + loggedInUser;
	
	$.getJSON(url).done(function(data){
		displayWorkdays(data);
		
	}).fail(function(jqXHR, textStatus, errorThrown){
		WT.WorktimeUtils.defaultErrorHandling(jqXHR);
    }).always(function() {
        // Run always
        //console.log("completed Worktimes");
    });
}


function displayWorkdays(data){
	let workdayFromDataList = [];
	let workdaysSummary = new WorkdaysSummary(data);
	workdaysSummary.workdaysGetResponse.forEach(element => {
		workdayFromDataList.push(new WorkdayFromData(element));
	});


	
	let daysInMonth = getDaysInMonth(currMonth,currYear);
	
	let workdayToDisplayList = createWorkdayListToDisplay(daysInMonth,workdayFromDataList);
	
	$('#workdays-table').html(Handlebars.compile($('#workdays-row-template').html())({
		workdays : workdayToDisplayList
    }));
	$('#workdays-summary').html(Handlebars.compile($('#workdays-summary-row-template').html())({
		summary : workdaysSummary
    }));
	
}

function createWorkdayListToDisplay(daysInMonth,workdayFromDataList){
	let workdayToDisplayList = [];
	daysInMonth.forEach(dIM => {
		let workdayToDisplay = null;
		let foundEqualDate = false;
		workdayFromDataList.forEach(wFDL => {
			//console.log("wFDL.date.substring(0,10)", wFDL.date.substring(0,10));
			if (dIM === wFDL.date.substring(0,10)){
				workdayToDisplay = new WorkdayToDisplay(wFDL.id, modifyStringDateFormatToDisplay(wFDL.date), wFDL.userId, wFDL.workhours);
				workdayToDisplayList.push(workdayToDisplay);
				foundEqualDate = true;
				//console.log("workdayToDisplay", modifyStringDateFormatToDisplay(workdayToDisplay.date));
			}
		});
		if(!foundEqualDate){
			workdayToDisplay = new WorkdayToDisplay(newDayId, modifyStringDateFormatToDisplay(dIM), loggedInUser, 0);
			workdayToDisplayList.push(workdayToDisplay);
		}
			
	});
	return workdayToDisplayList;
}

//2020-04-10 -> 2020.04.10
function modifyStringDateFormatToDisplay(stringDate){
	return stringDate.substring(0,4) + "." + stringDate.substring(5,7) + "." + stringDate.substring(8,10);
}

class WorkdayToDisplay {
	constructor(id, date, userId, workhours){
		this.id = id;
		this.date = date;
		this.userId = userId;
		this.workhours = workhours;
	}
}

function getDaysInMonth(month, year) {
	let date = new Date(year, month, 1);
	let days = [];
	while (date.getMonth() === month) {
		//convertUTCSToZonedDateTimeString()
		
		date.setDate(date.getDate() + 1);
		days.push(convertUTCSToZonedDateTimeString(new Date(date).toUTCString()));
		//days.push((new Date(date)).toUTCString());
		
	 }
	 return days;
}

function convertUTCSToZonedDateTimeString(dateString){
	
	const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
		  "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
		];
	const monthInNumberFormat = ["01", "02", "03", "04", "05", "06",
		  "07", "08", "09", "10", "11", "12"
		];
	
	const year = dateString.substring(12,16);
	const monthName = dateString.substring(8,11);
	let monthNumber = "";
	//Convert Apr -> 04
	monthNames.forEach((element, index) => {
		if (element === monthName)
			monthNumber = monthInNumberFormat[index];
	});
	
	const day = dateString.substring(5,7);
	return year + "-" + monthNumber + "-" + day;
}

/*
function modifieDateFormat(date){
	date = console.log();
}
*/
class WorkdayFromData {
	constructor(entry) {
		this.id = entry.id;
		this.date = entry.date;
		//this.date = modifieDateFormat(entry.date);
		this.workhours = entry.workhours;
		this.userId = entry.userId;
	}
}

function modifieDateFormat(date) {
	const month = date.substring(5,7);
	const day = date.substring(8,10);
	return month + "." + day;
}

class WorkdaysSummary {
	constructor(entry){
		this.workdaysGetResponse = entry.workdaysGetResponse;
		this.workhoursCurrentWeek = entry.workhoursCurrentWeek;
		this.workhoursPreviouseWeek = entry.workhoursPreviouseWeek;
		this.workminutesCurrentWeek = entry.workminutesCurrentWeek;
		//this.workminutesPreviouseWeek = workminutesPreviouseWeek;
	}
}