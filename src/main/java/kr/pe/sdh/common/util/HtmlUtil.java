package kr.pe.sdh.common.util;

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

    public static String createLabel(Map data) {
        String title     = StringUtils.trimStr(data.get("title"));
        String className = StringUtils.trimStr(data.get("className"));
        String style     = StringUtils.trimStr(data.get("style"));

        boolean isMand = data.get("isMand") == null ? false : (Boolean)data.get("isMand");

        return createLabel(title, className, style, isMand);
    }

    public static String createLabel(String title, String className, String style, boolean isMand) {
        String styleStr = "";

        if(isMand){
            styleStr = "color: red;";
        }

        if(StringUtils.isNotEmpty(style)){
            styleStr = styleStr + style;
        }

        String label = getLabelTag(title, className, styleStr);

        return label;
    }

    public static String getLabelTag(String title, String className, String style){
        String att = "";

        if(StringUtils.isNotEmpty(className)){
            att += getAttribute("class", className);
        }

        if(StringUtils.isNotEmpty(style)){
            att += getAttribute("style", style);
        }

        return "<label" + att + ">" + title + "</label>" + LF;

    }

    public static String createInput(Map data, String className){
        String id    = StringUtils.trimStr(data.get("id"));
        String name  = StringUtils.trimStr(data.get("name"));
        String type  = StringUtils.trimStr(data.get("type"));
        String style = StringUtils.trimStr(data.get("style"));

        int length   = data.get("length") == null ? 0 : (Integer)data.get("length");

        String input;

        if(type.equals("select")){
            String select = StringUtils.isEmpty(data.get("selectQKey")) ? (String)data.get("select") : (String)data.get("selectQKey");
            input = getSelectTag(id, name, style, select, className);
        }else{
            input = getInputTag(type, id, name, length, style, className);
        }

        return input;
    }

    private static String getSelectTag(String id, String name, String style, String select, String className){
        String att = getAttribute("id", id);

        if(StringUtils.isNotEmpty(name)){
            att += getAttribute("name", name);
        }

        if(StringUtils.isNotEmpty(style)){
            att += getAttribute("style", style);
        }

        return "<select class=\"" + className + "\"" + att + ">"
                + LF
                + getOptions(select)
                + "</select>"
                + LF;
    }

    private static String getOptions(String select) {
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

    public static String getInputTag(String type, String id, String name, int length, String style, String className){
        return getInputTag(type, id, name, length, style, className, null, null);

    }

    public static String getInputTag(String type, String id, String name, int length, String style, String className, String value, String checked){
        String att  = getAttribute("id", id);

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

        if(type.equals("date") || type.equals("duedate")){
            type = "input";

        }

        att += getAttribute("type", type);
        att += getAttribute("class", className);

        return "<input" + att + "/>" + LF;
    }


    public static String closeDiv() {
        return "</div>"+LF;
    }

}
