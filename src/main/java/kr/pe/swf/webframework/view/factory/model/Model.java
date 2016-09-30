package kr.pe.swf.webframework.view.factory.model;

import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 28..
 */
public interface Model {
    public abstract void initAttribute(String type, String id, String name, String title, String style, String className, String value, int length, String checked, boolean isMand);
    public abstract void initAttribute(String type, String id, String name);
    public abstract void initAttribute(String type, String id, String name, int length, String style, String className);
    public abstract void initAttribute(Map<String, Object> map);

    Map<String, Object>getInitAttributeMap();

    public abstract void createModel();
    public abstract String draw();

}
