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
            List<Map<String, String>> list = null;
            if(StringUtils.isNotEmpty(data.get("selectQKey"))){
                list = null; // TODO 조회하여 list 생성

            }else{
                list = (List<Map<String, String>>)data.get("list");

            }


            input = getSelectTag(id, name, style, list, className);
        }else{
            if(type.equals("autocomplete")){
                input  = getInputTag(type    , id + "_VIEW", name, length, style, className);
                input += getInputTag("hidden", id          , name, 0     , ""   , "");

            }else{
                input  = getInputTag(type, id, name, length, style, className);
            }
        }

        return input;
    }

    public static String createCheckNRadio(Map data, String className){
        String id    = StringUtils.trimStr(data.get("id"));
        String type  = StringUtils.trimStr(data.get("type"));
        String style = StringUtils.trimStr(data.get("style"));

        List<Map<String, String>> list = null;
        if(StringUtils.isNotEmpty(data.get("selectQKey"))){
            list = null; // TODO 조회하여 list 생성

        }else{
            list = (List<Map<String, String>>)data.get("list");

        }

        int idx = 0;
        StringBuffer sb = new StringBuffer();
        for(Map<String, String> map : list){

            String code  = map.get("code");
            String label = map.get("label");

            String checked = null;
            if(map.containsKey("checked") && map.get("checked").equals("true")){
                checked = "checked";

            }

            String inp = HtmlUtil.getInputTag(type, id+(++idx), id, 0, style, className, code, checked);

            sb.append(inp);

            Map<String, String> labelData = new HashMap<String, String>();
            labelData.put("title"    , label);
            labelData.put("className", "w3-validate");
            labelData.put("style"    , "margin-left: 3px; margin-right: 3px;");

            sb.append(createLabel(labelData));

        }

        return sb.toString();
    }

    private static String getSelectTag(String id, String name, String style, List<Map<String, String>> list, String className){
        String att = getAttribute("id", id);

        if(StringUtils.isNotEmpty(style)){
            att += getAttribute("style", style);
        }

        return "<select class=\"" + className + "\"" + att + ">"
                + LF
                + getOptions(list)
                + "</select>"
                + LF;
    }

    private static String getOptions(List<Map<String, String>> list) {
        StringBuffer sb = new StringBuffer();

        for(Map<String, String> map : list){
            String code  = map.get("code");
            String label = map.get("label");

            String att = getAttribute("value", code);
            if(map.containsKey("selected") && map.get("selected").equals("true")){
                att += " selected";
            }

            sb.append("\t<option ").
                    append(att).
                    append(">").
                    append(label).
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

        if(StringUtils.isNotEmpty(value)){
            att += getAttribute("value", value);
        }

        if(StringUtils.isNotEmpty(checked)){
            att += getAttribute("checked", checked);
        }

        if(type.equals("date") || type.equals("duedate")){
            type = "input";
            style = "width: 100px; text-align: center;" + String.valueOf(style); // 날짜 타입

        }

        if(StringUtils.isNotEmpty(style)){
            att += getAttribute("style", style);
        }

        if(StringUtils.isNotEmpty(className)){
            att += getAttribute("class", className);
        }

        att += getAttribute("type", type);

        return "<input" + att + "/>" + LF;
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

}
