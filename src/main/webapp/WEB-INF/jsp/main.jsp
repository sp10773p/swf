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
    <title>Seong`s Web Framework Beta</title>
    <%@include file="import/commonHeader.jsp"%>

    <script type="text/javascript">


        function fn_subMenu(menuId) {
            var params = "view=" + menuId;
            new ajaxUtil.Request("/mainView.do", params, "gfn_drawMain");

        }

        var mySidenav, overlayBg;

        $(document).ready(function () {
            mySidenav = document.getElementById("mySidenav");
            overlayBg = document.getElementById("myOverlay");

        });

        function w3_open() {
            if (mySidenav.style.display === 'block') {
                mySidenav.style.display = 'none';
                overlayBg.style.display = "none";
            } else {
                mySidenav.style.display = 'block';
                overlayBg.style.display = "block";
            }
        }

        function w3_close() {
            mySidenav.style.display = "none";
            overlayBg.style.display = "none";
        }



    </script>
<body>

<!-- top menu -->
<ul class="w3-navbar w3-theme w3-top w3-left-align w3-large" style="z-index:4;">
    <li class="w3-opennav w3-right w3-hide-large">
        <a class="w3-hover-white w3-large w3-theme-l1" href="javascript:void(0)" onclick="w3_open()"><i class="fa fa-bars"></i></a>
    </li>
    <li><a href="#" class="w3-theme-l1">SWF</a></li>
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
    <a href="#" class="w3-hover-black" onclick="fn_subMenu('sampleDefaultList')">Default List</a>
    <a href="#" class="w3-hover-black" onclick="fn_subMenu('sam1pleDueList')">Due List</a>
    <a href="#" class="w3-hover-black">Link</a>
    <a href="#" class="w3-hover-black">Link</a>
</nav>

<!-- Overlay effect when opening sidenav on small screens -->
<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- Main content: shift it to the right by 250 pixels when the sidenav is visible -->
<form id="mainForm">
    <div class="w3-main" style="margin-left:250px" id="mainFrame">
    </div>
</form>
</body>
</html>
