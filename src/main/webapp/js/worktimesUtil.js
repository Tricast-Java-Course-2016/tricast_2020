window.SB = window.SB || {};

window.SB.Utils = {
    
    readWorktimesFormDataList : function readFormDataList($form){
    	let dataList = [];
    	let data = {};
    	const worktimeInputFieldsCount = 4;
    	let nthInputField = 0;
    	
    	for(let val of (new FormData($form.get(0))).entries()) {
            data[val[0]] = val[1];
            nthInputField++;
            if (nthInputField === worktimeInputFieldsCount){
            	dataList.push(data);
            	nthInputField = 0;
            	data = {};
            }
        }
        return dataList;
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
            error : SB.Utils.defaultErrorHandling
        });
    }
};