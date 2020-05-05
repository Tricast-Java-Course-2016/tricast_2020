window.WT = window.WT || {};

const workdayId = "WH_WORKDAY_ID";
const workdayDate = "WH_WORKDAY_DATE";
const selectedUserId = "WH_WORKDAY_SELECTED_USER_ID";
const selectedUsername = "WH_WORKDAY_SELECTED_USER_NAME";

window.WT.WorktimeUtils = {
  saveWorkdayData: function saveWorkdayData(id, date) {
    localStorage.setItem(workdayId, id);
    localStorage.setItem(workdayDate, date);
  },
  
  setDefaultValues: function setDefaultValues(wId, wDate, sUId, sUsername) {
	  localStorage.setItem(workdayId, wId);
	  localStorage.setItem(workdayDate, wDate);
	  localStorage.setItem(selectedUserId, sUId);
	  localStorage.setItem(selectedUsername, sUsername);
  },

  setWorkdayId: function setWorkdayId(id) {
    localStorage.setItem(workdayId, id);
  },

  setSelectedUserId: function setSelectedUserId(selectedId) {
    localStorage.setItem(selectedUserId, selectedId);
  },

  setSelectedUsername: function setSelectedUsername(selectedName) {
    localStorage.setItem(selectedUsername, selectedName);
  },

  getWorkdayId: function getWorkdayId() {
    return localStorage.getItem(workdayId);
  },

  getWorkdayDate: function getWorkdayDate() {
    return localStorage.getItem(workdayDate);
  },

  getSelectedUserId: function getSelectedUserId() {
    return localStorage.getItem(selectedUserId);
  },
  getSelectedUsername: function getSelectedUsername() {
    return localStorage.getItem(selectedUsername);
  },
  //EXAMPLE
  readFormData: function readFormData($form) {
    // The FormData interface provides a way to easily construct a set of key/value pairs representing form fields
    // and their values, which can then be easily sent in a http request
    // The FormData.entries() method returns an iterator allowing to go through all key/value pairs contained in
    // this object.
    let data = {};
    for (let val of new FormData($form.get(0)).entries()) {
      data[val[0]] = val[1];
    }
    return data;
  },
  //My DATAREADER
  readWorktimesFormDataList: function readFormDataList($form) {
    let dataList = [];
    let data = {};
    //The 5th field is hidden -> rowId
    const worktimeInputFieldsCount = 6;
    let nthInputField = 0;

    for (let val of new FormData($form.get(0)).entries()) {
      data[val[0]] = val[1];
      nthInputField++;
      if (nthInputField === worktimeInputFieldsCount) {
        dataList.push(data);
        nthInputField = 0;
        data = {};
      }
    }
    return dataList;
  },

  defaultErrorHandling: function defaultErrorHandling(xhr) {
    let errorMsg;
    if (xhr.status == 0) {
      errorMsg = "The server is not responding or is not reachable.";
    } else {
      errorMsg = xhr.statusText != "" ? xhr.responseText : xhr.response;
    }
    console.log(errorMsg);
    //alert(errorMsg);
  },

  initAjax: function initAjax() {
    $.ajaxSetup({
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      timeout: 20000,
      error: WT.WorktimeUtils.defaultErrorHandling,
      beforeSend: function (xhr) {
        xhr.setRequestHeader("Authorization", SB.Utils.getToken());
      },
    });
  },
};
