/**
 * Created by wang on 15/6/12.
 * 消息分发器，作用于各个页面模块之间信息传递分发。
 * 每个模块定义并注册自己的事件到分发器，同时也注册自己需要订阅的消息。消息分发器会再接收到
 * 消息事件之后将其分发至对应的页面组件中。
 */

//params 必须是一个数组
function EventDispatcher() {
    this._eventMap = {};//结构{'eventName':[fn1,fn2]}
    this.regEvent = function (name, fn) {
        this._eventMap[name] != null ? this._eventMap[name].push(fn) : this._eventMap[name] = [fn];
    };
    //params 必须是一个数组
    this.broadcast = function (eventName, params) {
        var fun = this._eventMap[eventName];
        for (var i = 0; i < fun.length; i++) {
            fun[i].apply(this, params);
        }
    };
};

function IpCheck(ip) {
    var exp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    var reg = ip.match(exp);
    if (reg == null) {
        return false;
    }
    return true;
}

function response_check(data) {
    if (data.status == -1) {
        error.show(data.message)
        return false
    }
    return true
}


var Bus = new EventDispatcher();//消息总线



