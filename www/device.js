var exec = require('cordova/exec');
var channel = require('cordova/channel');


/**
 * 事件说明
 * onLogin
 * onWakeup
 * onRelation
 */

var Base = function() {
    this.channels = {};
};

Base.prototype = {
    _eventHandler: function(event) {
      if (event && (event.type in this.channels)) {
          this.channels[event.type].fire(event);
      }
    },
    on: function(type, func) {
        if (!(type in this.channels)) {
            this.channels[type] = channel.create(type);
        }
        this.channels[type].subscribe(func);
        return this;
    },
};

var YSDK = function() { Base.call(this); };
YSDK.prototype = new Base();

YSDK.init = function() {
  var options = {};
  var base = new YSDK();
  var cb = function(eventname) {
    base._eventHandler(eventname);
  };
  exec(cb, cb, 'TencentYSDKPlugin', 'initYSDK', [options]);
  return base;
}

YSDK.login = function() {
  var options = {};
  var base = new YSDK();
  var cb = function(eventname) {
    base._eventHandler(eventname);
  };
  exec(cb, cb, 'TencentYSDKPlugin', 'doLogin', [options]);
  return base;
}


module.exports = {
    YSDK: YSDK
};