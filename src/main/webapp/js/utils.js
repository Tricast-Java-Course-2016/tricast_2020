window.SB = window.SB || {};

const userId = "WH_USER_ID";
const userName = "WH_USER_NAME";
const userToken = "WH_USER_TOKEN";
const userRole = "WH_USER_ROLE";

window.SB.Utils = {
        
    saveUserData : function saveUserData(token, id, accountUsername, role) {
        localStorage.setItem(userToken, token);
        localStorage.setItem(userId, id);
        localStorage.setItem(userName, accountUsername);
        localStorage.setItem(userRole, role);
    }, 
        
    getUserId : function getUserId() {
        return localStorage.getItem(userId);
    },
        
    getUsername : function getUsername() {
        return localStorage.getItem(userName);
    },
        
    getToken : function getToken() {
        return localStorage.getItem(userToken);
    },
        
    getUserRole : function getUserRole() {
        return localStorage.getItem(userRole);
    },
    
    readFormData : function readFormData($form) {
        // The FormData interface provides a way to easily construct a set of key/value pairs representing form fields
        // and their values, which can then be easily sent in a http request
        // The FormData.entries() method returns an iterator allowing to go through all key/value pairs contained in
        // this object.
        let data = {};
        for(let val of (new FormData($form.get(0))).entries()) {
            data[val[0]] = val[1];
        }
        return data;
    },
    
    defaultErrorHandling : function defaultErrorHandling(xhr) {
        let errorMsg;
        if (xhr.status == 0) {
            errorMsg = "The server is not responding or is not reachable.";
        } else {
            errorMsg = (xhr.statusText != "")? xhr.responseText : xhr.response;
        }
        console.log(errorMsg);
        alert(errorMsg);
     },
     
    initAjax : function initAjax() {
        
        $.ajaxSetup({
            dataType : 'json',
            contentType : "application/json;charset=utf-8",
            timeout : 20000,
            error : SB.Utils.defaultErrorHandling,
            beforeSend: function(xhr){ xhr.setRequestHeader("Authorization", SB.Utils.getToken()); }
        });
    }
    
};