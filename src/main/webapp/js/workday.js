let loggedInUser = null;
let currMonth = null;
let currYear = null;
let selectedUser = null;
const newDayId = -1;
let selectedYearAndMonthInString = null;

window.onload = function () {
  WT.WorktimeUtils.initAjax();
  bindListeners();
  init();
  loadLoggedInUserWorkdays();
};

function init() {
  loggedInUser = SB.Utils.getUserId();
  currMonth = new Date().getMonth();
  currYear = new Date().getFullYear();
  selectedUser = loggedInUser;
  WT.WorktimeUtils.setSelectedUserId(loggedInUser);
  WT.WorktimeUtils.setSelectedUsername(SB.Utils.getUsername());
  /*selectedYearAndMonthInString = {
		  "date" : new Date().getFullYear().toString() + "." + (new Date().getMonth()+1).toString()
		  }*/createSelectedDate
  selectedYearAndMonthInString = new createSelectedDate(new Date().getFullYear().toString() + "." + (new Date().getMonth()+1).toString());
  console.log(selectedYearAndMonthInString);
}

function bindListeners() {
	$("#stepBackward").click(function (e) {
		stepByMonth(selectedYearAndMonthInString, false);
	  });
	$("#stepForward").click(function (e) {
		stepByMonth(selectedYearAndMonthInString, true);
	  });
}

function stepByMonth(yearAndMonthInObject, forward){
	  let url = "/workinghours/rest/workdays/workedhours/" + selectedUser;
	
	let currentYear = parseInt(yearAndMonthInObject.date.substring(0,4));
	let currentMonth = parseInt(yearAndMonthInObject.date.substring(5,7));
	//console.log(currentYear);
	
	let modifiedYear = null;
	let modifiedMonth = null;
	//When forward is true modify the date to the next month, else prev month
	if (forward){
		if(currentMonth === 12){
			modifiedMonth = 1;
			modifiedYear = currentYear + 1;
		} else {
			modifiedMonth = currentMonth+1;
			modifiedYear = currentYear;
		}
	} else {
		if(currentMonth === 1){
			modifiedMonth = 12;
			modifiedYear = currentYear - 1;
		} else {
			modifiedMonth = currentMonth - 1;
			modifiedYear = currentYear;
		}
	}
	
	selectedYearAndMonthInString = new createSelectedDate(modifiedYear.toString() + "." + modifiedMonth.toString());

	  let workdayRequest = {"month": modifiedMonth, "year": modifiedYear};
	  
	  let workdaysGetResponse = $.post(
		  url,
		  JSON.stringify(workdayRequest),
		  function (workdayRequest) {
			  displayWorkdays(workdaysGetResponse.responseJSON, modifiedMonth-1, modifiedYear);
			  $("#currentDate").html(
		  		  Handlebars.compile($("#date-template").html())({
		  		  selectedDate: selectedYearAndMonthInString
		  		})
		  	);
	  });
}


function modifyWorktimes(workdayId, date) {
  WT.WorktimeUtils.saveWorkdayData(workdayId, date);
}

function loadLoggedInUserWorkdays() {
  let url = "/workinghours/rest/workdays/workedhours/" + loggedInUser;
  let response = $.getJSON(url)
    .done(function (data) {
      SB.Utils.getUserRole() == 1
        ? loadSelectionList(response.responseJSON.userList)
        : loadUsername();
      displayWorkdays(data, currMonth, currYear);
    })
    .fail(function (jqXHR, textStatus, errorThrown) {
      WT.WorktimeUtils.defaultErrorHandling(jqXHR);
    })
    .always(function () {
      // Run always
      console.log("onLoadWorkdays completed");
    });
  $("#currentDate").html(
		    Handlebars.compile($("#date-template").html())({
		      selectedDate: selectedYearAndMonthInString
		    })
		  );
}

//continue
function loadUsername() {
	$("#users-table").html(
		    Handlebars.compile($("#users-template").html())({
		      user: {"username": SB.Utils.getUsername()},
		    })
	);
}

function loadSelectionList(usersList) {
  let userSelectionList = [];
  const [idList, userList] = createUserLists(usersList);
  idList.forEach((userId, index) => {
    userSelectionList.push({ userId: userId, userName: userList[index] });
  });
  $("#users-table").html(
    Handlebars.compile($("#users-list-template").html())({
      users: userSelectionList,
      //selectedDate: selectedYearAndMonthInString
    })
  );
  $("#currentDate").html(
		    Handlebars.compile($("#date-template").html())({
		      selectedDate: selectedYearAndMonthInString
		    })
		  );
}


class createSelectedDate {
	constructor(dateInString){
		this.date = dateInString;
	}
}

function createUserLists(object) {
  const idList = Object.keys(object);
  const userList = Object.values(object);
  return [idList, userList];
}

//Edit
function loadSelectedUser(userId, userName) {
  console.log("userName", userName);
  let currentYear = parseInt(selectedYearAndMonthInString.date.substring(0,4));
  let currentMonth = parseInt(selectedYearAndMonthInString.date.substring(5,7));
  let url = "/workinghours/rest/workdays/workedhours/" + userId;
  //let userList
  let response = $.getJSON(url)
    .done(function (data) {
      displayWorkdays(data, currentMonth-1, currentYear);
      WT.WorktimeUtils.setSelectedUserId(userId);
      WT.WorktimeUtils.setSelectedUsername(userName);
      selectedUser = WT.WorktimeUtils.getSelectedUserId();
      console.log("setSelectedUserId", userId);
    })
    .fail(function (jqXHR, textStatus, errorThrown) {
      WT.WorktimeUtils.defaultErrorHandling(jqXHR);
    })
    .always(function () {
      // Run always
      console.log("loadSelectedUser completed");
    });
}

function displayWorkdays(data, selectedMonth, selectedYear) {
  let workdayFromDataList = [];
  let workdaysSummary = new WorkdaysSummary(data);
  workdaysSummary.workdaysGetResponse.forEach((element) => {
    workdayFromDataList.push(new WorkdayFromData(element));
  });
  //let daysInMonth = getDaysInMonth(currMonth, currYear);
  let daysInMonth = getDaysInMonth(selectedMonth, selectedYear);
  let workdayToDisplayList = createWorkdayListToDisplay(
    daysInMonth,
    workdayFromDataList
  );

  $("#workdays-table").html(
    Handlebars.compile($("#workdays-row-template").html())({
      workdays: workdayToDisplayList,
    })
  );
  $("#workdays-summary").html(
    Handlebars.compile($("#workdays-summary-row-template").html())({
      summary: workdaysSummary,
    })
  );
}

function createWorkdayListToDisplay(daysInMonth, workdayFromDataList) {
  let workdayToDisplayList = [];
  daysInMonth.forEach((dIM) => {
    let workdayToDisplay = null;
    let foundEqualDate = false;
    workdayFromDataList.forEach((wFDL) => {
      if (dIM === wFDL.date.substring(0, 10)) {
        workdayToDisplay = new WorkdayToDisplay(
          wFDL.id,
          modifyStringDateFormatToDisplay(wFDL.date),
          wFDL.userId,
          wFDL.workhours
        );
        workdayToDisplayList.push(workdayToDisplay);
        foundEqualDate = true;
      }
    });
    if (!foundEqualDate) {
      workdayToDisplay = new WorkdayToDisplay(
        newDayId,
        modifyStringDateFormatToDisplay(dIM),
        loggedInUser,
        0
      );
      workdayToDisplayList.push(workdayToDisplay);
    }
  });
  return workdayToDisplayList;
}

//2020-04-10 -> 2020.04.10
function modifyStringDateFormatToDisplay(stringDate) {
  return (
    stringDate.substring(0, 4) +
    "." +
    stringDate.substring(5, 7) +
    "." +
    stringDate.substring(8, 10)
  );
}

class WorkdayToDisplay {
  constructor(id, date, userId, workhours) {
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
    date.setDate(date.getDate() + 1);
    days.push(convertUTCSToZonedDateTimeString(new Date(date).toUTCString()));
  }
  return days;
}

function convertUTCSToZonedDateTimeString(dateString) {
  const monthNames = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
  ];
  const monthInNumberFormat = [
    "01",
    "02",
    "03",
    "04",
    "05",
    "06",
    "07",
    "08",
    "09",
    "10",
    "11",
    "12",
  ];

  const year = dateString.substring(12, 16);
  const monthName = dateString.substring(8, 11);
  let monthNumber = "";
  //Convert Apr -> 04
  monthNames.forEach((element, index) => {
    if (element === monthName) monthNumber = monthInNumberFormat[index];
  });

  const day = dateString.substring(5, 7);
  return year + "-" + monthNumber + "-" + day;
}

class WorkdayFromData {
  constructor(entry) {
    this.id = entry.id;
    this.date = entry.date;
    //this.date = modifyDateFormat(entry.date);
    this.workhours = entry.workhours;
    this.userId = entry.userId;
  }
}

function modifyDateFormat(date) {
  const month = date.substring(5, 7);
  const day = date.substring(8, 10);
  return month + "." + day;
}

class WorkdaysSummary {
  constructor(entry) {
    this.workdaysGetResponse = entry.workdaysGetResponse;
    this.workhoursCurrentWeek = entry.workhoursCurrentWeek;
    this.workhoursPreviouseWeek = entry.workhoursPreviouseWeek;
    this.workminutesCurrentWeek = entry.workminutesCurrentWeek;
  }
}
