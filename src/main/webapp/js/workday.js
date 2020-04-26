window.onload = function() {
	WT.WorktimeUtils.initAjax();
    bindListeners();
    loadWorkdays();
};

let currMonth = 3;
let currYear = 2020;

const monthNames = ["January", "February", "March", "April", "May", "June",
	  "July", "August", "September", "October", "November", "December"
	];

function bindListeners() {
	
}

function loadWorkdays(){
	let url = "/workinghours/rest/workdays/workedhours/2";
	//let url = "/workinghours/rest/worktimes/2";
	
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
	let workdayList = [];
	let workdaysSummary = new WorkdaysSummary(data);
	workdaysSummary.workdaysGetResponse.forEach(element => {
		
		workdayList.push(new WorkdayToDisplay(element))
	})
	console.log("data", data);
	console.log("workdayList", workdayList);
	let daysInMounth = getDaysInMonth(currMonth,currYear);
	//console.log("daysInMounth", daysInMounth);
	daysInMounth.forEach(e => {
		let workday = new WorkdayToDisplay();
	});
	
	$('#workdays-table').html(Handlebars.compile($('#workdays-row-template').html())({
		workdays : daysInMounth
    }));
	//console.log(getDaysInMonth(currMonth,currYear));
	//workdaySummary 
}

function getDaysInMonth(month, year) {
	let date = new Date(year, month, 1);
	let days = [];
	while (date.getMonth() === month) {
		  days.push(new Date(date));
		  date.setDate(date.getDate() + 1);
	 }
	 return days;
}

function modifieDateFormat(date){
	date = console.log();
}

class WorkdayToDisplay {
	constructor(entry) {
		this.id = entry.id;
		
		this.date = modifieDateFormat(entry.date);
		
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