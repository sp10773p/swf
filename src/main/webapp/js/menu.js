/**
 * Created by sdh on 2016-10-13.
 */

/***
 * 화면 호출
 * @param menuId
 * @param params
 * @param saveMode
 */
function gfn_mainView(menuId, params, saveMode) {
    var requestParam = {};
    if(params == null || params == ''){
        params = {};
    }
    if(saveMode){
        params["saveMode"] = saveMode;
    }

    requestParam["view"] = menuId;
    requestParam["param"] = encodeURIComponent((JSON.stringify(params)).split( "null").join(''));
    new ajaxUtil.Request("/mainView.do", requestParam, "gfn_drawMain", "POST");

}

/***
 * Side 메뉴 생성
 * @param pMenuId
 * @param pMenuNm
 */
function gfn_subMenu(pMenuId, pMenuNm){
    $('#subMenuTitle').html(pMenuNm);

    var params = "menuid=" + pMenuId;
    var data = new ajaxUtil.Request("/selectSubMenu.do",params, null, null, true);
    var arr = data['data'];

    var subMenuDiv = document.getElementById("subMenu");
    subMenuDiv.innerHTML = "";

    var contentDiv = null;
    var contentLinkDiv = null;
    for(var i=0; i < arr.length; i++){
        var menuId = arr[i].MENU_ID;
        var menuNm = arr[i].MENU_NM;
        var url    = arr[i].URL;
        var leafYn = arr[i].LEAFYN;

        if(leafYn == 'N'){
            if(contentDiv != null){
                var dropDownDiv = document.createElement("div");
                dropDownDiv.setAttribute("class", "w3-dropdown-hover");

                dropDownDiv.appendChild(contentLinkDiv);
                dropDownDiv.appendChild(contentDiv);

                subMenuDiv.appendChild(dropDownDiv);

                console.log(subMenuDiv.innerHTML);

                contentDiv = null;
                contentLinkDiv = null;

            }

            contentDiv = document.createElement("div");
            contentDiv.setAttribute("class", "w3-dropdown-content w3-white w3-card-4");

            contentLinkDiv = document.createElement("a");
            var iObj = document.createElement("i");
            iObj.setAttribute("class", "fa fa-caret-down");

            contentLinkDiv.innerHTML = menuNm;
            contentLinkDiv.appendChild(iObj);

        }else{
            var aObj = document.createElement("a");
            aObj.setAttribute("href", "#");
            aObj.setAttribute("class", "w3-hover-black");
            aObj.setAttribute("onclick", "gfn_mainView('"+menuId+"')");
            aObj.innerHTML = menuNm;

            if(contentDiv != null){
                contentDiv.appendChild(aObj);
            }else{
                subMenuDiv.appendChild(aObj);
            }
        }

        if(arr.length == (i+1)){
            if(contentDiv != null){
                var dropDownDiv = document.createElement("div");
                dropDownDiv.setAttribute("class", "w3-dropdown-hover");

                dropDownDiv.appendChild(contentLinkDiv);
                dropDownDiv.appendChild(contentDiv);

                subMenuDiv.appendChild(dropDownDiv);
            }
        }
    }
    //$('#subMenuList').html(pMenuNm);
}
