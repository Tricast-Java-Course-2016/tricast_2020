window.onload = function() {
    SB.Utils.initAjax();
    bindListeners();
};

function bindListeners() {
    $("#loginSubmit").click(function(e) {
        loginUser();
    });
}

function loginUser() {

    let data = SB.Utils.readFormData($('#loginUser'));

    $.post("/workinghours/rest/users/login", JSON.stringify(data), function(data, status, xhr) {
        // save data to the local storage
        let token = xhr.getResponseHeader('Authorization');
        SB.Utils.saveUserData(token, data.id, data.userName, data.roleId);
        alert('OK login');
        //window.location.href = "/workinghours/WorktimesRecording.html";
    });
}
