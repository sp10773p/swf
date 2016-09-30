package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.StringUtils;
import org.apache.commons.collections.map.HashedMap;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 28..
 */
public abstract class AbstractModel implements Model {
    final String mandStyle = "color: red;";
    final String PREFIX_VIEW_ID = "_VIEW";
    final String labelClassName = "w3-validate";
    final String labelStyle = "margin-left: 3px; margin-right: 3px;";

    final String dateStyle   = "width: 100px; text-align: center;";
    final String PREFIX_FROM = "from_";
    final String PREFIX_TO   = "to_";

    String LF = "\r\n";

    private String id;
    private String name;
    private String style;
    private String className;
    private String title;
    private String value;
    private String type;
    private String checked;

    private boolean isMand;

    private int length;

    StringBuffer buffer = new StringBuffer();
    StringBuffer attBuffer = new StringBuffer();

    public void initAttribute(String type, String id, String name, String title,
                              String style, String className, String value,
                              int length, String checked, boolean isMand) {
        this.type      = type;
        this.id        = id;
        this.name      = name;
        this.title     = title;
        this.style     = style;
        this.className = className;
        this.value     = value;
        this.length    = length;
        this.checked   = checked;
        this.isMand    = isMand;
    }

    public void initAttribute(String type, String id, String name) {
        initAttribute(type, id, name, 0, null, null);
    }

    public void initAttribute(String type, String id, String name, int length, String style, String className) {
        initAttribute(type, id, name, null, null, className, null, length, null, false);

    }

    public void initAttribute(Map<String, Object> map) {
        this.type      = StringUtils.trimStr(map.get("type"));
        this.id        = StringUtils.trimStr(map.get("id"));
        this.name      = StringUtils.trimStr(map.get("name"));
        this.title     = StringUtils.trimStr(map.get("title"));
        this.style     = StringUtils.trimStr(map.get("style"));
        this.className = StringUtils.trimStr(map.get("className"));
        this.value     = StringUtils.trimStr(map.get("value"));
        this.checked   = StringUtils.trimStr(map.get("checked"));
        this.length    = (map.get("length") == null ? 0     : (Integer)map.get("length"));
        this.isMand    = (map.get("isMand") == null ? false : (Boolean)map.get("isMand"));
    }

    public Map<String, Object> getInitAttributeMap() {
        Map<String, Object> map = new HashedMap();

        map.put("type"     , type);
        map.put("id"       , id);
        map.put("name"     , name);
        map.put("title"    , title);
        map.put("style"    , style);
        map.put("className", className);
        map.put("value"    , value);
        map.put("length"   , length);
        map.put("checked"  , checked);
        map.put("isMand"   , isMand);

        return map;
    }

    public abstract void createModel();

    public String draw() {
        if(buffer.length() == 0){
            createModel();
        }

        return buffer.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isMand() {
        return isMand;
    }

    public void setMand(boolean mand) {
        isMand = mand;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String openDiv(String className){
        return "<div" + getAttribute("class", className) + ">" + LF;
    }

    public static String getAttribute(String key, String value){
        return " " + key + "=\"" + value + "\" ";
    }

    public String getAttribute(){
        return attBuffer.toString();
    }

    public void appendAttribute(String key, String value){
        attBuffer.append(" ").append(key).append("=\"").append(value).append("\" ");
    }

}
