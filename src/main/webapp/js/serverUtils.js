/**
 * Created by seongdonghun on 2016. 9. 27..
 */
var ajaxUtil = {};
var serverUtil = {};

serverUtil = function (formId, targetId, selectQKey, url, callback) {
    this.params = {};
    this.mandValidItems = [];
    this.formId = formId;
    this.selectQKey = selectQKey;
    this.targetId = targetId;
    this.url = url;
    this.callback = callback;

    if(this.formId){
        this.setAllParam();
    }
}

serverUtil.prototype = {
    setAllParam: function () {
        this.params = {};

        var formObj = $('#' + this.formId);
        var searchData = formObj[0];

        for(var i=0; i<searchData.length; i++){
            var id  = formObj[0][i].id;
            var obj = $('#' + id);
            var val = obj.val();
            var type = obj.attr("type");

            // 필수체크필요 필드
            if(obj.attr("mandatory")){
                var title = obj.attr("mandatory");
                //console.log(title + "," + id);
                this.mandValidItems[this.mandValidItems.length] = {"id":id, "title":title};
            }

            /*if(type == "checkbox" || type == "radio"){*/
            if(type == "checkbox"){
                var checkedStr = "";
                var name = obj.attr("name");
                $('input:'+type+'[name="'+name+'"]').each(function () {
                    if(this.checked){
                        checkedStr += "'" + this.value +"',";
                    }
                });

                if(checkedStr.length > 0){
                    checkedStr = checkedStr.substring(0, checkedStr.length-1);
                    this.params[name] = checkedStr;
                }

                if($('input:'+type+'[id="'+id+'"]').is(":checked") == false){
                    val = null;
                }

            }else if(type == "radio"){
                var name = obj.attr("name");
                this.params[name] = $(':radio[name="' + name + '"]:checked').val();
                val = null;
            }

            if(val != null && val != ""){
                this.params[id] = val;
            }
        }
    },
    setParam: function (key, value) {
        this.params[key] = value;
    },
    getParam: function (key) {
        return this.params[key];
    },
    encodeURI: function () {
        return encodeURIComponent((JSON.stringify(this.params)).split( "null").join(''));
    },
    sendSync: function () {
        return new ajaxUtil.Request(this.url, {formData: this.encodeURI()}, null, "POST", true);
    },
    send: function () {
        return new ajaxUtil.Request(this.url, {formData: this.encodeURI()}, this.callback, "POST", false);
    },
    mandValidation: function () {
        var validStr = "";
        for(var i=0; i < this.mandValidItems.length; i++){
            var m = this.mandValidItems[i];
            var id = m["id"];
            var val = $('#' + id).val();
            var title = m["title"];

            //console.log("[" + title + "]" + id + "::" + val);

            if(val == null || val == ''){
                validStr += title + "은(는) 필수사항입니다.\r\n";
            }
        }

        if(validStr.length > 0){
            alert(validStr);
            return false;
        }

        return true;
    },
    loadGrid: function () {
        if(!this.mandValidation()) return;

        this.params["selectQKey"] = this.selectQKey;

        $("#"+this.targetId).clearGridData();  // 이전 데이터 삭제
        $("#"+this.targetId).jqGrid("setGridParam",
            {
                url: this.url,
                datatype: 'json',
                mtype: 'post',
                postData: {formData: this.encodeURI()}
            }).trigger("reloadGrid");
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
 * 조회조건의 페이징그리드 조회시 기본으로 호출되는 함수
 */
function gfn_pagingGridSelectList(formId, targetId, selectQKey) {
    var server = new serverUtil(formId, targetId, selectQKey, 'commonGridSelectList.do');
    server.setParam("isPaging", "true");
    server.loadGrid();
}

/***
 * 상세화면에서 호출되는 저장함수
 */
function gfn_saveData(formId, url, cQKey, iQkey, uQKey) {
    url = (url == null ? 'commonSaveData.do' : url);
    var server = new serverUtil(formId, null, null, url, 'gfn_saveDataCallback');
    server.setParam("cQkey", cQKey);
    server.setParam("iQkey", iQkey);
    server.setParam("uQKey", uQKey);
    server.send();
}

/***
 * 공통 콜백
 * @param data
 */
function gfn_saveDataCallback(data) {
    if(data['code'] && data['code'] == 'S'){
        alert('성공적으로 저장하였습니다.');
    }
}


/***
 * 상세화면에서 호출되는 저장함수
 */
function gfn_selectData(formId, selectQKey) {
    var server = new serverUtil(formId, null, selectQKey, null);
    //server.loadGrid();
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
