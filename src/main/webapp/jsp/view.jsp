<%--
  Created by IntelliJ IDEA.
  User: seongdonghun
  Date: 2016. 9. 12.
  Time: 오후 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%
    String result = (String)request.getAttribute("result");
%>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="css/w3.css">
    <link rel="stylesheet" href="css/w3-theme-black.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">

    <link rel="stylesheet" href="css/jquery-ui.css">
    <script src="js/jquery.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/jquery-ui.min.js"></script>

    <!-- jqgrid-->
    <link rel="stylesheet" type="text/css" media="screen" href="css/jqgrid/ui.jqgrid.css" />
    <script src="js/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="js/jqgrid/i18n/grid.locale-kr.js" type="text/javascript"></script>

    <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Roboto", sans-serif}
        .w3-sidenav a,.w3-sidenav h4{padding:12px;}
        .w3-navbar a{padding-top:12px !important;padding-bottom:12px !important;}

        .th3 {padding-top: 2px; font-size: 14px; font-weight:bold; color: #111d1a;}

        /* 조회조건이 한 로우일때 */
        .search-one {padding-top: 15px; padding-bottom: 15px;}

        /* 조회조건이 3로우 이상일때 */
        .search-top {padding-top: 15px;}
        .search-middel {padding-top: 5px; padding-bottom: 5px;}
        .search-bottom {padding-bottom: 15px;}

        /* 조회조건이 2 로우일때 */
        .search-twin-top {padding-top: 15px; padding-bottom: 5px;}
        .search-twin-bottom {padding-bottom: 15px;}

        .search-input {width: 98%; height: 24px; font-size: 14px; font-weight: normal}
        .search-date  {width: 100px; height: 24px; font-size: 14px; font-weight: normal; text-align: center}

        .ui-jqgrid .ui-jqgrid-view {z-index: 0}

        input[type=checkbox].w3-check {top: 3px}
        input[type=radio].w3-radio {top: 3px}

    </style>

    <script type="text/javascript">
        var searchColSize = 4; // 조회조건 한로우의 컬럼수

        var inputClass    = "search-input";
        var dateClass     = "search-date";
        var checkboxClass = "w3-check";
        var radioClass    = "w3-radio";

        function gfn_createRow(rowSize, rowPos) {
            var divRow = document.createElement("div");

            if(rowSize == 1){
                divRow.className = "w3-padding search-one";

            }else if(rowSize == 2){
                if(rowPos == 1){
                    divRow.className = "w3-row-padding search-twin-top";
                }else{
                    divRow.className = "w3-row-padding search-twin-bottom";
                }
            }else{
                if(rowPos == 1){
                    divRow.className = "w3-row-pdding search-top";
                }else if(rowSize == rowPos){
                    divRow.className = "w3-row-pdding search-bottom";
                }else{
                    divRow.className = "w3-row-pdding search-middle";
                }
            }

            return divRow;
        }

        function gfn_createQuarter(){
            var divQuarter = document.createElement("div");

            divQuarter.className = "w3-quarter";

            return divQuarter;
        }

        function gfn_createTh() {
            var divTh = document.createElement("div");

            divTh.className = "w3-col m4 th3";

            return divTh;

        }

        function gfn_createLabel(data) {
            var thLabel = document.createElement("label");

            var styleStr = "";
            var className = data.className;
            if(className){
                thLabel.className = className;
            }

            var isMand = data.isMand;
            if(isMand){
                styleStr = "color: red;";
            }

            var style = data.style;
            if(style){
                styleStr = styleStr + style;
            }

            if(styleStr != "" && styleStr.length > 0){
                thLabel.setAttribute("style", styleStr);
            }

            thLabel.innerHTML = data.title;

            return thLabel;

        }

        function gfn_createTd() {
            var divTd = document.createElement("div");

            divTd.className = "w3-col m8";

            return divTd;
        }

        function gfn_createInput(data) {
            var id = data.id;
            var name = data.name;
            var type = data.type;
            var isMand = data.isMand;
            var length = data.length;
            var style = data.style

            var inputType = (type == "select" ? type : "input");

            var inp = document.createElement(inputType);
            inp.setAttribute("id", id);

            if(name != null){
                inp.setAttribute("name", name);
            }

            if(length != null){
                inp.setAttribute("length", length);
            }

            if(style != null){
                inp.setAttribute("style", style);
            }

            // input tag일때
            if(inputType == "input"){
                inp.setAttribute("type", type);
                if(type == "text"){
                    inp.className = inputClass;

                }else if(type == "date" || type == "duedate"){
                    inp.className = dateClass;

                }else if(type == "checkbox"){
                    inp.className = checkboxClass;

                }else if(type == "radio"){
                    inp.className = radioClass;

                }

            }else if(inputType == "select"){
                var select = data.selectQKey;
                if(select == null){
                    select = data.select;

                    var arr = eval("(" + select + ")");
                    for(var i=0; i<arr.length; i++){
                        var optionStr = arr[i].split(":");
                        inp.add(new Option(optionStr[1], optionStr[0]));

                        // default selected
                        if(optionStr[2] && optionStr[2] == "S"){
                            inp.selectedIndex = i;
                        }
                    }
                }
            }

            return inp;
        }

        function gfn_createDatepicker(id, defaultDate) {
            $('#' + id).datepicker({
                dateFormat: 'yy-mm-dd'
                //TODO defaultdate 추가해야함
            })
        }

        function gfn_createCheckNRadio(data, divTd) {
            var obj = evel("data." + data.type.substring(0, 5) + "QKey");
            if(obj == null){
                obj = evel("data." + data.type.substring(0, 5));
            }

            var arr = eval("(" + obj + ")");
            for(var i=0; i<arr.length; i++){
                var objStr = arr[i].split(":");

                //code:value 유효성 검사
                if(objStr.length < 2){
                    alert(data.title + "의 <" + data.type.substring(0, 5) + "> 내용을 확인하세요.");
                    return;
                }

                var id = data.id + ("" + i);

                var objData = {type: data.type, id: id, name: data.id};
                var inp = gfn_createInput(objData);

                inp.setAttribute("value", objStr[1]);

                //default check
                if(objStr[2] && objStr[2] == "C"){
                    inp.setAttribute("checked", "checked");
                }

                divTd.appendChild(inp);

                var labelData = {title: objStr[1], className: "w3-validate", style: "margin-left:3px; margin-right:3px;"};
                var lab = gfn_createLabel(labelData);

                divTd.appendChild(lab);
            }

        }

        $(document).ready(function () {
           var views = eval(<%= '(' + result + ')'%>);

            //화면제목
            var viewTitle = views["title"];

            $('#viewTtitle').html(viewTitle);

            // 조회영역
            var search = views["search"];
            var rowPos = 0;
            var rowSize = Math.ceil(search.length/searchColSize);
            var divRow;
            for(var i=0; i<search.length; i++){
                var data = search[i];

                if(i == 0 || (i%searchColSize) == 0){
                    divRow = gfn_createRow(rowSize, rowPos);
                }

                var divCol = gfn_createQuarter();

                var divTh = gfn_createTh();
                var label = gfn_createLabel(data);

                divTh.appendChild(label);

                var divTd = gfn_createTd();


                var orgId = data.id;
                if(data.type == "duedate"){
                    data.id = "from_" + orgId;
                    var inp = gfn_createInput(data);

                    divTd.appendChild(inp);

                    data.id = "to_" + orgId;
                    var inp = gfn_createInput(data);

                    divTd.appendChild(inp);

                    data.id = orgId;

                }else if(data.type == "checkbox" || data.type == "radio"){
                    gfn_createCheckNRadio(data, divTd);

                }else{
                    var inp = gfn_createInput(data);

                    divTd.appendChild(inp);
                }

                divCol.appendChild(divTh);
                divCol.appendChild(divTd);

                divRow.appendChild(divCol);

                if(i == 0 || (i%searchColSize) == 0){
                    var divSearchArea = document.getElementById("searchArea");
                    divSearchArea.appendChild(divRow);
                }
            }

            // 입력종류에 따른 component화 처리
            for(var i=0; i<search.length; i++){
                var data = search[i];
                var type = data.type;

                // 날짜 계열
                if(type == "date"){
                    gfn_createDatepicker(data.id, data.default);

                }else if(type == "duedate"){
                    gfn_createDatepicker("from_"+data.id, data.from);
                    gfn_createDatepicker("to_"+data.id  , data.to);
                }
            }
        });


        $(function () {
            $("#list").jqGrid({
                colNames: ["검색버튼을 눌러주세요."],
                colModel: [
                    { name: "invid", width: "100%", sortable: false, align: "left" }
                ],
                gridview: true,
                autowidth: true,
                autoencode: true
            });
        });
    </script>
<body>

<!-- top menu -->
<ul class="w3-navbar w3-theme w3-top w3-left-align w3-large" style="z-index:4;">
    <li class="w3-opennav w3-right w3-hide-large">
        <a class="w3-hover-white w3-large w3-theme-l1" href="javascript:void(0)" onclick="w3_open()"><i class="fa fa-bars"></i></a>
    </li>
    <li><a href="#" class="w3-theme-l1">Logo</a></li>
    <li class="w3-hide-small"><a href="#" class="w3-hover-white">About</a></li>
    <li class="w3-hide-small"><a href="#" class="w3-hover-white">Values</a></li>
    <li class="w3-hide-small"><a href="#" class="w3-hover-white">News</a></li>
    <li class="w3-hide-small"><a href="#" class="w3-hover-white">Contact</a></li>
    <li class="w3-hide-medium w3-hide-small"><a href="#" class="w3-hover-white">Clients</a></li>
    <li class="w3-hide-medium w3-hide-small"><a href="#" class="w3-hover-white">Partners</a></li>
</ul>

<!-- left menu -->
<nav class="w3-sidenav w3-collapse w3-theme-l5 w3-animate-left" style="z-index:3;width:250px;margin-top:51px;" id="mySidenav">
    <a href="javascript:void(0)" onclick="w3_close()" class="w3-right w3-xlarge w3-padding-large w3-hover-black w3-hide-large" title="close menu">
        <i class="fa fa-remove"></i>
    </a>
    <h4><b>Menu</b></h4>
    <a href="#" class="w3-hover-black">Link</a>
    <a href="#" class="w3-hover-black">Link</a>
    <a href="#" class="w3-hover-black">Link</a>
    <a href="#" class="w3-hover-black">Link</a>
</nav>

<!-- Overlay effect when opening sidenav on small screens -->
<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- Main content: shift it to the right by 250 pixels when the sidenav is visible -->
<div class="w3-main" style="margin-left:250px">

    <!-- Title -->
    <div class="w3-row w3-padding-64" style="padding-bottom: 10px!important">
        <div class="w3-container">
            <h2 class="w3-text-teal">S/R List</h2>
        </div>
    </div>

    <!-- Search Area -->
    <div class="w3-border w3-round w3-margin-left w3-margin-right" style="background: #f0f0f0">
        <div id="searchArea">
        </div>
    </div>

    <!-- Pagination -->
    <div class="w3-center w3-padding-32 w3-margin-left w3-margin-right" style="z-index: 1">
        <table id="list"><tr><td></td></tr></table>
        <div id="pager"></div>
    </div>


    <!-- END MAIN -->
</div>

<script>
    // Get the Sidenav
    var mySidenav = document.getElementById("mySidenav");

    // Get the DIV with overlay effect
    var overlayBg = document.getElementById("myOverlay");

    // Toggle between showing and hiding the sidenav, and add overlay effect
    function w3_open() {
        if (mySidenav.style.display === 'block') {
            mySidenav.style.display = 'none';
            overlayBg.style.display = "none";
        } else {
            mySidenav.style.display = 'block';
            overlayBg.style.display = "block";
        }
    }

    // Close the sidenav with the close button
    function w3_close() {
        mySidenav.style.display = "none";
        overlayBg.style.display = "none";
    }
</script>


</body>
</html>
