
var responseCallbacks = {};
var uniqueId = 1;

function call(action,params,responseCallback){
    var message = {
        action:action,
        data:params
    }
    if (responseCallback) {
        var callbackId = 'cb_' + (uniqueId++) + '_' + new Date().getTime();
        responseCallbacks[callbackId] = responseCallback;
        message.callbackId = callbackId;
    }
    window.JsBridge.call(JSON.stringify(message),"Android_Callback");
}

window.Android_Callback = function(callbackId,data){
    alert(callbackId);
    var responseCallback = responseCallbacks[callbackId];
    if(!responseCallback){
        return;
    }
    responseCallback(data);
}


