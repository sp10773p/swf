<%--
  Created by IntelliJ IDEA.
  User: seongdonghun
  Date: 2016. 9. 12.
  Time: 오후 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
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

        $(document).ready(function () {
        });

        /*
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
        */
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
            <h2 class="w3-text-teal"></h2>
        </div>
    </div>

    <!-- Search Area -->
    <div class="w3-border w3-round w3-margin-left w3-margin-right" style="background: #f0f0f0">
        <div id="searchArea">
        </div>
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
