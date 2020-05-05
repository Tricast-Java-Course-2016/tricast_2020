//LoggedInUser Hardcoded
let loggedInUser = null;
//Every row has an unique identifier for delete method
let rowId = 0;
//False if day not exists yet, with current user
//let newDay = false;
const newWorkdayIdMinusOne = -1;
const newWorktimeIdZero = 0;
let currentWorkdayId = newWorkdayIdMinusOne;
let deletedFieldCount = 0;
//const workdayId = 2;

window.onload = function () {
  WT.WorktimeUtils.initAjax();
  bindListeners();
  loggedInUser = SB.Utils.getUserId();
  currentWorkdayId = WT.WorktimeUtils.getWorkdayId();
  loadWorktimes();
};
/* Soon display the worktime's worktype*/

function bindListeners() {
  $("#saveWorkdayWorktimes").click(function (e) {
    acceptWorktimes();
  });
  //New worktime added
  $("#addNewWorktime").click(function (e) {
    addWorktime();
  });
}

//Delete field
function deleteWorktime(rowIds) {
  let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList(
    $("#WorktimesForm")
  );
  dataFromWorktimesForm = dataFromWorktimesForm.filter(
    (worktime) => parseInt(worktime.rowId) !== rowIds
  );
  refreshToDisplayWorktimes(dataFromWorktimesForm);
  deletedFieldCount++;
}

//Save or Update, delete empty worktimes
function deleteEmptyWorktime(dataFromWorktimesForm) {
  let listWithoutEmptyField = dataFromWorktimesForm.filter(
    (worktime) => worktime.startTime !== "" || worktime.endTime !== ""
  );
  return listWithoutEmptyField;
}

function refreshToDisplayWorktimes(worktimesList) {
  $("#worktimes-table").html(
    Handlebars.compile($("#worktimes-row-template").html())({
      workdayWorktimes: worktimesList,
    })
  );
}

//Add an empty worktime row
function addWorktime() {
  let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList(
    $("#WorktimesForm")
  );
  dataFromWorktimesForm.push(new emptyWorktimesToDisplay());
  $("#worktimes-table").html(
    Handlebars.compile($("#worktimes-row-template").html())({
      workdayWorktimes: dataFromWorktimesForm,
    })
  );
}

function acceptWorktimes() {
  if (currentWorkdayId === newWorkdayIdMinusOne.toString()) {
    saveWorkday();
  } else {
    UpdateWorkday();
  }
  deletedFieldCount = 0;
}

//Update
function UpdateWorkday() {
  let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList(
    $("#WorktimesForm")
  );
  dataFromWorktimesForm = deleteEmptyWorktime(dataFromWorktimesForm);
  let worktimesUpdateRequest = [];
  let workdayUpdateRequest = {};
  let worktime;

  //Check Worktimes
  let startTimes = [];
  let endTimes = [];
  let isCorrectWorktimes = false;

  dataFromWorktimesForm.forEach((e) => {
    worktime = new WorktimeRequestFromFormData(e);
    worktimesUpdateRequest.push(worktime);
    startTimes.push(worktime.startTime);
    endTimes.push(worktime.endTime);
  });

  isCorrectWorktimes = checkWorktimes(startTimes, endTimes);
  workdayUpdateRequest = new WorkdayUpdateRequest(worktimesUpdateRequest);
  if (!isCorrectWorktimes) {
    let updateResponse = $.ajax(
      new PutRequest(
        "/workinghours/rest/worktimes/" + currentWorkdayId,
        workdayUpdateRequest
      )
    );
  } else {
    console.log("The worktimes are not correct", workdayUpdateRequest);
    alert("The worktimes are not correct");
  }
  setUpdatedWorkdayDependsOnTheresWorktime(dataFromWorktimesForm);
}

class WorktimeRequestFromFormData {
  constructor(e) {
    //Modify the start- and end-Time to the correct format which can be save as ZonedDateTime
    let startTimeInCorrectForm = getSelectedWorkdayDate();
    let endTimeInCorrectForm = getSelectedWorkdayDate();

    //Cut the ":" from '08:00' and set the hour and it's minutes
    endTimeInCorrectForm.setHours(
      e.endTime.substring(0, 2),
      e.endTime.substring(3, 5)
    );
    startTimeInCorrectForm.setHours(
      e.startTime.substring(0, 2),
      e.startTime.substring(3, 5)
    );

    //Use the value which has been set before
    this.startTime = startTimeInCorrectForm;
    this.endTime = endTimeInCorrectForm;
    this.type = e.type;
    this.comment = e.comment;
    this.modifiedBy = loggedInUser;
    this.id = e.id;
    //Modified
    //this.workdayId = parseInt(workdayId);
    this.workdayId = currentWorkdayId;
  }
}

class PutRequest {
  constructor(url, updateRequest) {
    this.type = "PUT";
    this.dataType = "json";
    this.url = url;
    this.headers = { "X-HTTP-Method-Override": "PUT" };
    this.data = JSON.stringify(updateRequest);
  }
}

function setUpdatedWorkdayDependsOnTheresWorktime(dataFromWorktimesForm) {
  if (dataFromWorktimesForm.length === 0) {
    WT.WorktimeUtils.setWorkdayId(newWorkdayIdMinusOne);
    currentWorkdayId = WT.WorktimeUtils.getWorkdayId();
    displayNotExistingDayEmptyWorktimes();
  } else refreshToDisplayWorktimes(dataFromWorktimesForm);
}

function setWorkdayIdToZeroOneIfTheresNoWorktime(worktimeListLength) {
  if (worktimeListLength === 0)
    WT.WorktimeUtils.setWorkdayId(newWorkdayIdMinusOne);
}

function checkWorktimes(startTimes, endTimes) {
  let wrongWorktimes = false;
  startTimes.forEach((start, startIndex) => {
    //If any startTime has a bigger value than it's endTime
    if (start.getTime() >= endTimes[startIndex].getTime())
      return (wrongWorktimes = true);
    //Check if startTime is between any given time intervallum
    endTimes.forEach((end, endIndex) => {
      if (
        startIndex !== endIndex &&
        start.getTime() < end.getTime() &&
        start.getTime() > startTimes[endIndex].getTime()
      ) {
        return (wrongWorktimes = true);
      }
    });
  });
  return wrongWorktimes;
}

class WorkdayUpdateRequest {
  constructor(worktimeUpdatedListRequest) {
    this.datasList = worktimeUpdatedListRequest;
    this.userId = loggedInUser;
  }
}

//Save a not existing workday
function saveWorkday() {
  /*let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList($('#WorktimesForm'));
	deleteEmptyWorktime(dataFromWorktimesForm);*/

  let dataFromWorktimesForm = WT.WorktimeUtils.readWorktimesFormDataList(
    $("#WorktimesForm")
  );
  console.log("dataFromWorktimesForm", dataFromWorktimesForm);
  dataFromWorktimesForm = deleteEmptyWorktime(dataFromWorktimesForm);
  if (dataFromWorktimesForm.length != 0) {
    //Go through the input fields and push them into a List
    let selectedDate = getSelectedWorkdayDate();

    let worktimesCreationRequest = [];
    let workdayCreationRequest = {};
    let worktime;
    //Check Worktimes
    let startTimes = [];
    let endTimes = [];
    let isCorrectWorktimes = false;

    dataFromWorktimesForm.forEach((e) => {
      worktime = new WorktimeRequestFromFormData(e);
      worktimesCreationRequest.push(worktime);
      startTimes.push(worktime.startTime);
      endTimes.push(worktime.endTime);
    });
    isCorrectWorktimes = checkWorktimes(startTimes, endTimes);
    //ADMIN MODIFICATION loggedInUser switched to parseInt(WT.WorktimeUtils.getSelectedUserId())
    workdayCreationRequest = new WorkdayCreationRequest(
      selectedDate,
      parseInt(WT.WorktimeUtils.getSelectedUserId()),
      worktimesCreationRequest
    );

    if (!isCorrectWorktimes) {
      let response = $.post(
        "/workinghours/rest/worktimes",
        JSON.stringify(workdayCreationRequest),
        function (workdayCreationRequest) {
          WT.WorktimeUtils.saveWorkdayData(
            response.responseJSON.id,
            WT.WorktimeUtils.getWorkdayDate()
          );
          currentWorkdayId = WT.WorktimeUtils.getWorkdayId();
          console.log("Saved Worktime");
          //location.reload();
        }
      );
    } else {
      console.log("The worktimes are not correct", workdayCreationRequest);
      alert("The worktimes are not correct");
    }
    refreshToDisplayWorktimes(dataFromWorktimesForm);
  } else {
    console.log("Cannot save an empty list");
    alert("Cannot save an empty list!");
  }
}

class WorkdayCreationRequest {
  constructor(date, userId, worktimesCreationRequest) {
    this.date = date;
    this.userId = userId;
    this.worktimesCreationRequest = worktimesCreationRequest;
  }
}

function getSelectedWorkdayDate() {
  let dateTextFromCurrentHtmlElement = document.getElementById("currentDate")
    .textContent;
  const yearMonthDay = convertStringToYearMonthDay(
    dateTextFromCurrentHtmlElement
  );
  let selectedDate = new Date(
    yearMonthDay[0],
    yearMonthDay[1],
    yearMonthDay[2]
  );
  return selectedDate;
}
//Example: 2020.04.10 => 2020, 5-1, 10
function convertStringToYearMonthDay(currentDayString) {
  const yearMonthDay = [
    currentDayString.substring(0, 4),
    parseInt(currentDayString.substring(5, 7)) - 1,
    currentDayString.substring(8, 10),
  ];
  return yearMonthDay;
}

//Get Workday's Worktimes
function loadWorktimes() {
  ///workinghours/rest/worktimes/{workdayId}
  let url = "/workinghours/rest/worktimes/" + currentWorkdayId;
  //Gonna be some default value to dismiss this alert
  if(WT.WorktimeUtils.getWorkdayId() === "" || WT.WorktimeUtils.getWorkdayId() === null)
	  alert("First go to workday.html and select a workday, after that all be fine");
  
  $.getJSON(url)
    .done(function (data) {
      displayWorktimes(data);
    })
    .fail(function (jqXHR, textStatus, errorThrown) {
      WT.WorktimeUtils.defaultErrorHandling(jqXHR);
      displayNotExistingDayEmptyWorktimes();
    })
    .always(function () {
      console.log("Worktimes Loaded");
      displayUsernameAndWorkdayDate();
    });
}

/********************************
 * Display Worktimes
 *******************************/

//Display Username and Workday's date
function displayUsernameAndWorkdayDate() {
  const userNameAndWorkdayDate = {
    //username : SB.Utils.getUsername(),
    username: WT.WorktimeUtils.getSelectedUsername().toString(),
    date: WT.WorktimeUtils.getWorkdayDate(),
  };
  //Before worktimes
  $("#row-first-table").html(
    Handlebars.compile($("#worktimes-row-first-template").html())({
      rowFirst: userNameAndWorkdayDate,
    })
  );
  //After worktimes
  $("#row-last-table").html(
    Handlebars.compile($("#worktimes-row-last-template").html())({
      rowLast: userNameAndWorkdayDate,
    })
  );
}

function displayWorktimes(data) {
  if (data.length !== 0) {
    displayExistingDayWorktimes(data);
  } else {
    //newDay = true;
    displayNotExistingDayEmptyWorktimes();
  }
}

function displayExistingDayWorktimes(data) {
  let worktimeList = [];
  data.forEach(function (entry) {
    worktime = new WorktimeResponseToDisplay(entry);
    worktimeList.push(worktime);
  });

  $("#worktimes-table").html(
    Handlebars.compile($("#worktimes-row-template").html())({
      workdayWorktimes: sortByStartTime(worktimeList),
    })
  );
}

function sortByStartTime(worktimeList) {
  return worktimeList.sort((a, b) => {
    return a.startTime.substring(0, 2) - b.startTime.substring(0, 2);
  });
}

//Load empty rows if there is not a workday in a current date
function displayNotExistingDayEmptyWorktimes() {
  let workdayWorktimesList = [];
  const worktimesToDisplayCount = 2;
  for (i = 0; i < worktimesToDisplayCount; i++) {
    workdayWorktimesList.push(new emptyWorktimesToDisplay());
  }
  $("#worktimes-table").html(
    Handlebars.compile($("#worktimes-row-template").html())({
      workdayWorktimes: workdayWorktimesList,
    })
  );
}

//display Empty fields
class emptyWorktimesToDisplay {
  constructor() {
    this.id = newWorktimeIdZero;
    this.startTime = "";
    this.endTime = "";
    this.type = "OFFICE";
    this.comment = "Happy Hours";
    this.modifiedBy = loggedInUser;

    this.rowId = rowId++;
  }
}

//The loaded Worktime Format from request
class WorktimeResponseToDisplay {
  constructor(entry) {
    const hoursStartsInDate = 11;
    const hoursEndsInDate = 16;

    this.id = entry.id;
    this.comment = entry.comment;
    this.modifiedBy = entry.modifiedBy;
    this.type = entry.type;
    this.workdayId = entry.workdayId;

    this.endTime = entry.endTime.substring(hoursStartsInDate, hoursEndsInDate);
    this.startTime = entry.startTime.substring(
      hoursStartsInDate,
      hoursEndsInDate
    );
    this.rowId = rowId++;
  }
}
