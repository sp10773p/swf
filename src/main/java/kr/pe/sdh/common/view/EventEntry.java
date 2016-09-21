package kr.pe.sdh.common.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
class EventEntry {
    private String name;
    private String fnName;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getFnName() {
        return fnName;
    }

    void setFnName(String fnName) {
        this.fnName = fnName;
    }

    Map toMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("fnName", fnName);

        return map;
    }

    String print() {
        return "EventEntry{" +
                "name='" + name + '\'' +
                ", fnName='" + fnName + '\'' +
                '}';
    }
}
