package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 28..
 */
public abstract class AbstractModel implements Model {
    String LF = "\r\n";

    private String id;
    private String name;
    private String style;
    private String className;
    private String title;
    private String value;

    private boolean isMand;

    private int length;

    StringBuffer buffer = new StringBuffer();
    StringBuffer attBuffer = new StringBuffer();

    public void initAttribute(String id, String name, String title, String style, String className, String value, int length, boolean isMand){
        this.id = id;
        this.name = name;
        this.style = style;
        this.className = className;
        this.title = title;
        this.isMand = isMand;
        this.length = length;
        this.value = value;
    }

    public void initAttribute(Map<String, String> map) {
        this.id = StringUtils.trimStr(map.get("id"));
        this.name = StringUtils.trimStr(map.get("name"));
        this.style = StringUtils.trimStr(map.get("style"));
        this.className = StringUtils.trimStr(map.get("className"));
        this.title = StringUtils.trimStr(map.get("title"));
        this.value = StringUtils.trimStr(map.get("value"));
    }

    public abstract void createModel();

    public String draw() {
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
