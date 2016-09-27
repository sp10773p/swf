/**
 * Created by seongdonghun on 2016. 9. 27..
 */
var ajaxUtil = {};
var serverUtil = {};

serverUtil = function (formId, selectQKey, targetId) {
    this.params = {};
    this.formId = formId;
    this.selectQKey = selectQKey;
    this.targetId = targetId;

    this.setAllParam();

}

serverUtil.prototype = {
    setAllParam: function () {
        this.params = {};

        var formObj = $('#' + this.formId);
        var searchData = formObj[0];

        for(var i=0; i<searchData.length; i++){
            var id  = formObj[0][i].id;
            var val = $('#' + id).val();

            if(val != null && val != ""){
                this.params[id] = val;
            }
        }

        this.params["selectQKey"] = this.selectQKey;
    },
    setParam: function (key, value) {
        this.params[key] = value;
    },
    getParam: function (key) {
        return this.params[key];
    },
    encodeURI: function () {
        return "params=" + encodeURIComponent((JSON.stringify(this.params)).split( "null").join(''));
    },
    send: function () {
        return new ajaxUtil.Request("/commonGridSelectList.do", this.encodeURI(), null, "POST", true);
    }
}

ajaxUtil.Request = function (url, params, callback, method, isSync) {
    this.url = url;
    this.params = params;
    this.callback = callback;
    this.method = (method == null ? "GET" : method);
    this.isSync = (isSync == null ? false : isSync);

    if(this.method && this.method.toUpperCase() == "POST"){
        if(this.isSync){
            return this.postSync();
        }else{
            this.post();
        }
    }else{
        if(this.isSync){
            return this.getSync();
        }else{
            this.get();
        }
    }
}


ajaxUtil.Request.prototype = {
    send: function (method) {
        jQuery.ajax({
            type: method,
            url : this.url,
            data: this.params,
            dataType:"JSON",
            callbackFn : this.callback,
            success: function (data) {
                if(this.callbackFn){
                    eval(this.callbackFn + "(data)");
                }else{
                    gfn_callBack(data);
                }
            }
        })
    },
    get: function () {
        this.send("GET");
    },
    post: function () {
        this.send("POST");
    },
    /* var data = new ajaxUtil.Request("/mainView.do", params).postSync();*/
    sendSync: function (method) {
        var response = $.ajax({
                            type: method,
                            url : this.url,
                            data: this.params,
                            dataType:"JSON",
                            async: false
                        }).responseText;

        return eval("(" + response + ")");
    },
    getSync: function () {
        return this.sendSync("GET");
    },
    postSync: function () {
        return this.sendSync("POST");
    }
};

/***
 * 화면 html code
 * @param data
 */
function gfn_drawMain(data) {
    var code = data["code"];
    if(code == "S"){
        $('#mainFrame').html(data["data"]);
    }else{
        alert(data["msg"]);
    }
}

/***
 * 조회조건의 조회시 기본으로 호출되는 함수
 */
function gfn_gridSelectList(formId, targetId, selectQKey) {
    var s = new serverUtil(formId, targetId, selectQKey);
    var data = s.send();

    var row_size = data["data"].length;
    for(var i=0; i < row_size; i++){
        var column = data["data"][i];
        for(var colId in column){
            console.log(column[colId]);
        }
    }

}


/***
 * 공통 콜백
 * @param data
 */
function gfn_callBack(data) {
    alert("gfn_callback : " + data['data'].length);

}

function gnf_autocomplete(term, selectQKey){
    var params = "term=" + term + "&selectQKey=" + selectQKey;
    var data = new ajaxUtil.Request("/autoComplete.do",params, null, null, true);
    return data['data'];
}

