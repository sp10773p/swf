package kr.pe.swf.webframework.view.entry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
public class EventEntry {
    private String name;
    private String fnName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFnName() {
        return fnName;
    }

    public void setFnName(String fnName) {
        this.fnName = fnName;
    }

    public Map toMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("fnName", fnName);

        return map;
    }

    public String print() {
        return "EventEntry{" +
                "name='" + name + '\'' +
                ", fnName='" + fnName + '\'' +
                '}';
    }
}
