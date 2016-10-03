/**
 * Created by seongdonghun on 2016. 9. 27..
 */
var ajaxUtil = {};
var serverUtil = {};

serverUtil = function (formId, targetId, selectQKey) {
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
    },
    loadGrid: function () {
        $("#"+this.targetId).clearGridData();  // 이전 데이터 삭제
        $("#"+this.targetId).setGridParam({url:"commonGridSelectList.do",
                                     datatype:"json",
                                     postData: this.encodeURI }).trigger("reloadGrid");

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
    $("#"+targetId).clearGridData();  // 이전 데이터 삭제

    //var server = new serverUtil(formId, targetId, selectQKey);
    //server.loadGrid();


    /*$("#" + targetId).jqGrid({
        url: "example.php",
        datatype: "json",
        mtype: "POST",
        colNames: ["Inv No", "Date", "Amount", "Tax", "Total", "Notes"],
        colModel: [
            { name: "invid", width: 55 },
            { name: "invdate", width: 90 },
            { name: "amount", width: 80, align: "right" },
            { name: "tax", width: 80, align: "right" },
            { name: "total", width: 80, align: "right" },
            { name: "note", width: 150, sortable: false }
        ],
        pager: "#pager",
        rowNum: 10,
        rowList: [10, 20, 30],
        sortname: "invid",
        sortorder: "desc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        caption: "My first grid"
    });*/

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

