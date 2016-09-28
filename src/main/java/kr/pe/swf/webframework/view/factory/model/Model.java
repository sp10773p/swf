package kr.pe.swf.webframework.view.factory.model;

import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 28..
 */
public interface Model {
    public abstract void initAttribute(String id, String name, String title, String style, String className, String value, int length, boolean isMand);
    public abstract void initAttribute(Map<String, String> map);
    public abstract void createModel();
    public abstract String draw();

}
