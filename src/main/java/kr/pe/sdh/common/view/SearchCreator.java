package kr.pe.sdh.common.view;


import kr.pe.sdh.common.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 19..
 */
public class SearchCreator {
    private String colDivClass = "w3-third";
    private String thClass = "w3-col m4 th3";
    private String tdClass = "w3-col m8";

    private String inputClass    = "search-input";
    private String dateClass     = "search-date";
    private String checkboxClass = "w3-check";
    private String radioClass    = "w3-radio";

    private int rowSize = 0;
    private int rowPos = 0;

    // 컬럼 열수
    private int searchColSize = 3;

    private String LF = "\r\n";

    private String closeDiv = "</div>";
    private String closeLabel = "</label>";

    StringBuffer resultBuffer = new StringBuffer();

    List<SearchEntry> searchEntries = null;

    public SearchCreator(List<SearchEntry> searchEntries){
        new SearchCreator(searchEntries, 3);
    }

    public SearchCreator(List<SearchEntry> searchEntries, int searchColSize){
        this.searchEntries = searchEntries;

        double d = Double.valueOf(searchEntries.size())/Double.valueOf(this.searchColSize);
        this.rowSize = (int)Math.ceil(d);

        this.setSearchColSize(searchColSize); // default column size
    }

    public void setSearchColSize(int searchColSize) {
        this.searchColSize = searchColSize;

        //  컬럼 css
        if(this.searchColSize == 4) {
            this.colDivClass = "w3-quarter";

        }else if(this.searchColSize == 3) {
            this.colDivClass = "w3-third";

        }
    }

    public String drawSearch() throws Exception{
        if(this.searchEntries == null){
            throw new Exception("SearchEntry is null");
        }

        for(int i=0; i < this.searchEntries.size(); i++){
            SearchEntry data = this.searchEntries.get(i);

            if(i == 0 || (i%this.searchColSize) == 0){
                this.rowPos++;
                this.createRow();
            }

            String orgId = data.getId();

            // 컬럼 생성
            this.createCol();

            // 조회조건 타이틀
            this.createTh();
            this.createLabel(data.toMap());

            this.resultBuffer.append(this.closeDiv); // close Th Div

            // 조회조건 입력란
            this.createTd();

            if("duedate".equals(data.getType())){
                data.setId("from_" + orgId);
                this.createInput(data.toMap());

                this.resultBuffer.append(" ~ ");

                data.setId("to_" + orgId);
                this.createInput(data.toMap());

                data.setId(orgId);

            }else if("checkbox".equals(data.getType()) || "radio".equals(data.getType())){
                this.createCheckNRadio(data.toMap());

            }else{
                this.createInput(data.toMap());
            }

            this.resultBuffer.append(this.closeDiv); // close Td Div
            this.resultBuffer.append(this.closeDiv); // close Col Div

            if(((i+1)%this.searchColSize) == 0 || (i+1) == this.searchEntries.size()){
              this.resultBuffer.append(this.closeDiv); // close Row Div
            }
        }

        return this.resultBuffer.toString();
    }

    public String bindComponent(){
        StringBuffer componentBuffer = new StringBuffer();

        for(int i=0; i < this.searchEntries.size(); i++){
            SearchEntry data = this.searchEntries.get(i);

            if(data.getType().equals("date")){
                componentBuffer.append(this.createDatepicker(data.getId(), data.getDefaultDate()));

            }else if(data.getType().equals("duedate")){
                componentBuffer.append(this.createDatepicker(data.getId(), data.getFromDate()));
                componentBuffer.append(this.createDatepicker(data.getId(), data.getToDate()));

            }else if(data.getType().equals("spinner")){
                componentBuffer.append("$('#").append(data.getId()).append("').spinner()").append(LF);
            }
        }

        return componentBuffer.toString();
    }

    private String createDatepicker(String id, String defaultDate) {
        return "$('#" + id + "').datepicker({" + LF
                + "dateFormat: 'yy-mm-dd'" + LF
                + "});" + LF;
    }

    public String createRow(){
        String className;
        if(this.rowSize == 1){
            className = "w3-padding search-one";

        }else if(this.rowSize == 2){
            if(this.rowPos == 1){
                className = "w3-row-padding search-twin-top";
            }else{
                className = "w3-row-padding search-twin-bottom";
            }
        }else{
            if(this.rowPos == 1){
                className = "w3-row-pdding search-top";
            }else if(this.rowSize == rowPos){
                className = "w3-row-pdding search-bottom";
            }else{
                className = "w3-row-pdding search-middle";
            }
        }

        String row = this.getDivStartTag(className);
        this.resultBuffer.append(row);

        return row;
    }

    public String createCol(){
        String col = this.getDivStartTag(this.colDivClass);
        this.resultBuffer.append(col);

        return col;
    }

    public String createTh(){
        String th = this.getDivStartTag(this.thClass);
        this.resultBuffer.append(th);

        return th;
    }

    public String createTd(){
        String td = this.getDivStartTag(this.tdClass);
        this.resultBuffer.append(td);

        return td;
    }

    public String createLabel(Map data){
        String title     = StringUtils.trimStr(data.get("title"));
        String className = StringUtils.trimStr(data.get("className"));
        String style     = StringUtils.trimStr(data.get("style"));

        boolean isMand = data.get("isMand") == null ? false : (Boolean)data.get("isMand");

        return createLabel(title, className, style, isMand);
    }

    private String createLabel(String title, String className, String style, boolean isMand) {
        String styleStr = "";

        if(isMand){
            styleStr = "color: red;";
        }

        if(StringUtils.isNotEmpty(style)){
            styleStr = styleStr + style;
        }

        String label = getLabelTag(title, className, styleStr);

        resultBuffer.append(label);

        return label;
    }

    public String createInput(Map data){
        String id    = StringUtils.trimStr(data.get("id"));
        String name  = StringUtils.trimStr(data.get("name"));
        String type  = StringUtils.trimStr(data.get("type"));
        String style = StringUtils.trimStr(data.get("style"));
        int length   = data.get("length") == null ? 0 : (Integer)data.get("length");

        String input;

        if(type.equals("select")){
            String select = StringUtils.isEmpty(data.get("selectQKey")) ? (String)data.get("select") : (String)data.get("selectQKey");
            input = getSelectTag(id, name, style, select);
        }else{
            input = getInputTag(type, id, name, length, style);
        }

        resultBuffer.append(input);

        return input;
    }

    public String createCheckNRadio(Map data){
        String id    = StringUtils.trimStr(data.get("id"));
        String type  = StringUtils.trimStr(data.get("type"));
        String style = StringUtils.trimStr(data.get("style"));

        StringBuffer sb = new StringBuffer();

        String childKey = type.substring(0, 5);

        //check or radio Node TODO QKey는 쿼리실행후 받아와야하며 받아온 내용을 변환
        String child = StringUtils.isEmpty(data.get(childKey + "QKey")) ? (String)data.get(childKey) : (String)data.get(childKey + "QKey");

        int idx = 0;

        List<String[]> objArrList = getStringArray(child);

        for(String[] objArr : objArrList){
            if(objArr.length < 2){
                System.err.println("<"+childKey+"> or <"+childKey+"QKey> 내용을 확인하세요");
                return null;
            }

            String value = objArr[0];
            String label = objArr[1];
            String checked = (objArr.length > 2 && objArr[2].equals("C") ? "checked" : "" );

            String inp = getInputTag(type, id+(++idx), id, 0, style, value, checked);

            Map<String, String> labelData = new HashMap<String, String>();
            labelData.put("title"    , label);
            labelData.put("className", "w3-validate");
            labelData.put("style"    , "margin-left: 3px; margin-right: 3px;");

            String lb = createLabel(labelData);

        }

        resultBuffer.append(sb.toString());

        return sb.toString();
    }

    private String getSelectTag(String id, String name, String style, String select){
        String att = getAttribute("id", id);

        if(StringUtils.isNotEmpty(name)){
            att += getAttribute("name", name);
        }

        if(StringUtils.isNotEmpty(style)){
            att += getAttribute("style", style);
        }

        return "<select" + att + ">"
                + LF
                + getOptions(select)
                + "</select>"
                + LF;
    }

    private String getOptions(String select) {
        StringBuffer sb = new StringBuffer();

        List<String[]> objArrList = getStringArray(select);

        for(String[] objArr : objArrList){
            if(objArr.length < 2){
                System.err.println("<select> or <sekectKey> 내용을 확인하세요");
                return null;
            }

            String code  = objArr[0];
            String value = objArr[1];

            String att = getAttribute("value", code);
            if(objArr.length > 2 && objArr[2].equals("S")){
                att += " selected";
            }

            sb.append("\t<option ").
                    append(att).
                    append(">").
                    append(value).
                    append("</option>").
                    append(LF);

        }

        return sb.toString();
    }

    private List<String[]> getStringArray(String select){
        select = select.trim().replaceAll("\\[", "").replaceAll("]", "");

        List<String[]> resultList = new ArrayList<String[]>();

        String[] selectArr = select.split(",");
        for(String s : selectArr){
            String[] arr = s.trim().replaceAll("'", "").split(":");
            resultList.add(arr);
        }

        return resultList;
    }

    private String getInputTag(String type, String id, String name, int length, String style){
        return getInputTag(type, id, name, length, style, null, null);

    }

    private String getInputTag(String type, String id, String name, int length, String style, String value, String checked){
        String att  = getAttribute("id", id);
               att += getAttribute("type", type);

        if(StringUtils.isNotEmpty(name)){
            att += getAttribute("name", name);
        }

        if(length > 0){
            att += getAttribute("length", String.valueOf(length));
        }

        if(StringUtils.isNotEmpty(style)){
            att += getAttribute("style", style);
        }

        if(StringUtils.isNotEmpty(value)){
            att += getAttribute("value", value);
        }

        if(StringUtils.isNotEmpty(checked)){
            att += getAttribute("checked", checked);
        }

        String className = inputClass;
        if(type.equals("date") || type.equals("duedate")){
            className = this.dateClass;
        }else if(type.equals("checkbox")){
            className = this.checkboxClass;
        }else if(type.equals("radio")){
            className = this.radioClass;
        }

        att += getAttribute("class", className);

        return "<input" + att + "/>" + LF;
    }

    public String getDivStartTag(String className){
        return "<div" + getAttribute("class", className) + "/>" + LF;
    }

    public String getLabelTag(String title, String className, String style){
        String att = "";

        if(StringUtils.isNotEmpty(className)){
            att += getAttribute("class", className);
        }

        if(StringUtils.isNotEmpty(style)){
            att += getAttribute("style", style);
        }

        return "<label" + att + ">" + title + "</label>" + LF;

    }

    public String getAttribute(String key, String value){
        return " " + key + "\"" + value + "\" ";
    }

}
