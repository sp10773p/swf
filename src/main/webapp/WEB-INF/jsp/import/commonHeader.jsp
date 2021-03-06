<%--
  Created by IntelliJ IDEA.
  User: seongdonghun
  Date: 2016. 9. 20.
  Time: 오후 8:23
  To change this template use File | Settings | File Templates.
--%>
<META http-equiv="Expires" content="-1">
<META http-equiv="Pragma" content="no-cache">
<META http-equiv="Cache-Control" content="No-Cache">
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">

<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/jquery-ui.structure.css">
<link rel="stylesheet" href="css/jquery-ui.theme.css">
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid-bootstrap.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid-bootstrap-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />


<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script src="js/date.js"></script>
<script src="js/util.js"></script>
<script src="js/string.js"></script>
<script src="js/serverUtils.js"></script>
<script src="js/jqGridUtil.js"></script>
<script src="js/menu.js"></script>

<style>
    html,body,h1,h2,h3,h4,h5,h6 {font-family: "Roboto", sans-serif}
    .w3-sidenav a,.w3-sidenav h4{padding:12px;}
    .w3-navbar a{padding-top:12px !important;padding-bottom:12px !important;}
    .w3-all {width: 99.99999%}

    .th3 {padding-top: 2px; font-size: 14px; font-weight:bold; color: #111d1a;}

    /* 조회조건이 한 로우일때 */
    .search-one {padding-top: 15px; padding-bottom: 15px;}

    /* 조회조건이 3로우 이상일때 */
    .search-top    {padding-top: 15px;}
    .search-middle {padding-top: 5px;}
    .search-bottom {padding-top: 5px; padding-bottom: 15px;}

    /* 조회조건이 2 로우일때 */
    .search-twin-top {padding-top: 15px; padding-bottom: 5px;}
    .search-twin-bottom {padding-bottom: 15px;}

    .search-input {width: 98%; height: 24px; font-size: 14px; font-weight: normal}
    .search-date  {width: 100px; height: 24px; font-size: 14px; font-weight: normal; text-align: center}
    .searchForm {margin-bottom: 0px;}
    .btn_area {padding-right: 15px; width: 100%; float: right; text-align: right}
    .select_btn, .detail_btn {padding-top: 30px; padding-right: 15px; width: 50%; float: right; text-align: right}

    /*** Override ***/
    input[type=checkbox].w3-check, input[type=radio].w3-radio {top: 3px; width: 18px; height: 18px}
    .w3-border {border:  2px solid #009688 !important;}

    .ui-datepicker {font-size: 12px;  width: 220px;}
    .ui-datepicker select.ui-datepicker-month {width: 30%; font-size: 11px; padding-left: 5px;}
    .ui-datepicker select.ui-datepicker-year  {width: 40%; font-size: 11px; padding-left: 5px;}
    .ui-datepicker-trigger {width: 17px; margin-left: 2px; cursor: pointer}
    .ui-datepicker th {color: white}

    .ui-common-table {font-size: 14px}
    .ui-jqgrid .ui-jqgrid-view {z-index: 0}

    /* detail 관련*/
    .detail-table {border-collapse:collapse;border-spacing:0;width:100%;display:table; border:1px solid #ccc}
    .detail-table tr {border-bottom:1px solid #ddd}
    .detail-table td,.detail-table th{border: 1px solid #ccc; padding:6px 8px;display:table-cell;text-align:left;vertical-align:top;
        font-size: 14px; height: 100%; vertical-align: middle; }
    .detail-table th {background-color: #ddffff!important; }

    .detail-input {width: 98%; font-size: 14px; font-weight: normal}

</style>
