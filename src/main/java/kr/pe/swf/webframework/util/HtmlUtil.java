package kr.pe.swf.webframework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public class HtmlUtil {
    private static String LF = "\r\n";
    private String inputClass    = "search-input";

    public static String openDiv(String className){
        return "<div" + getAttribute("class", className) + ">" + LF;
    }

    public static String getAttribute(String key, String value){
        return " " + key + "=\"" + value + "\" ";
    }


    public static List<String[]> getStringArray(String select){
        select = select.trim().replaceAll("\\[", "").replaceAll("]", "");

        List<String[]> resultList = new ArrayList<String[]>();

        String[] selectArr = select.split(",");
        for(String s : selectArr){
            String[] arr = s.trim().replaceAll("'", "").split(":");
            resultList.add(arr);
        }

        return resultList;
    }

    public static String closeDiv() {
        return "</div>"+LF;
    }

    public static String getBindScript(String id, String type){
        StringBuffer scriptBuffer = new StringBuffer();
        if(type.equals("D") || type.equals("Y")){
            scriptBuffer.append("$('#").append(id).append("').datepicker({").append(LF);
            scriptBuffer
                    .append(createJqueryOption("showOn", "both"))
                    .append(createJqueryOption("changeMonth", true))
                    .append(createJqueryOption("changeYear", true))
                    .append(createJqueryOption("dateFormat", "yy-mm-dd", true));
            scriptBuffer.append("});").append(LF);
        }

        return scriptBuffer.toString();
    }

    public static String createJqueryOption(String key, Object value){
        return createJqueryOption(key, value, true);
    }

    public static String createJqueryOption(String key, Object value, boolean isComman){
        String valStr = "'" + String.valueOf(value) +"'";

        if(!(value instanceof String)){
            valStr = String.valueOf(value);
        }

        return "\t" + key + ":" + valStr + (isComman ? "," : "") +LF;
    }

    public static String closeTr(){
        return "</tr>" + LF;
    }

    public static String openTr(String className){
        className = (StringUtils.isNotEmpty(className) ? getAttribute("class", className) : "");
        return "<tr" + className + ">" + LF;
    }

    public static String openTh(boolean isMand, String thWidth, int rowSpan, int colSpan){
        thWidth = (StringUtils.isNotEmpty(thWidth) ? getAttribute("width", thWidth) : "");
        String att = thWidth + (rowSpan > 1 ? getAttribute("rowspan", String.valueOf(rowSpan)) : "")
                             + (colSpan > 1 ? getAttribute("colSpan", String.valueOf(colSpan)) : "");
        if(isMand){
            att = getAttribute("style", "color: red;") + att;
        }

        return "<th" + att + ">" + LF;
    }

    public static String closeTh(){
        return "</th>" + LF;
    }

    public static String openTd(String tdWidth, int rowSpan, int colSpan){
        tdWidth = (StringUtils.isNotEmpty(tdWidth) ? getAttribute("width", tdWidth) : "");
        String att = tdWidth + (rowSpan > 1 ? getAttribute("rowspan", String.valueOf(rowSpan)) : "")
                + (colSpan > 1 ? getAttribute("colSpan", String.valueOf(colSpan)) : "");

        return "<td" + att + ">" + LF;
    }

    public static String closeTd(){
        return "</td>" + LF;
    }

    public static String openHtml(String className){
        className = (StringUtils.isNotEmpty(className) ? getAttribute("class", className) : "");
        return "<table" + className + ">" + LF;
    }

    public static String closeHtml(){
        return "</table>" + LF;
    }

    public static String createHidden(String  id, String value){
        return "<input id\"" + id +"\" type=\"hidden\" value=\"" + value +"\" />" + LF;
    }
}
