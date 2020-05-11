var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'TelephonyManager', 'coolMethod', [arg0]);
};

exports.getCellId = function(arg0, success, error) {
    exec(success,error, 'TelephonyManager', 'getCellId', [arg0]);
}

