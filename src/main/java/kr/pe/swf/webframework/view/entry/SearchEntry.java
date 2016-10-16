package kr.pe.swf.webframework.view.entry;

import kr.pe.swf.webframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
public class SearchEntry extends Entry{
    private String selectQKey;

    private boolean isMult = false;
    private int length = 0;

    private List<EventEntry> eventEntries;

    public Map toMap(){
        Map<String, Object> map = new HashMap();
        map.put("length" , length);

        // 쿼리 id를 우선으로 처리
        //if(selectQKey != null){
            map.put("selectQKey", selectQKey);
        //}else{
            map.put("list", list);
        //}

        // 쿼리 id를 우선으로 처리
        List eventList = new ArrayList();
        if(eventEntries != null){
            for(EventEntry eventEntry : eventEntries){
                eventList.add(eventEntry.toMap());
            }
        }

        map.put("event", eventList);

        return map;
    }

    public String getSelectQKey() {
        return selectQKey;
    }

    public void setSelectQKey(String selectQKey) {
        this.selectQKey = selectQKey;
    }


    public boolean isMult() {
        return isMult;
    }

    public void setMult(boolean mult) {
        isMult = mult;
    }

    public void setMult(String multStr) {
        if(StringUtils.isNotEmpty(multStr)){
            if(!"Y".equals(multStr.toUpperCase()) && !"N".equals(multStr.toUpperCase())){
                System.err.print("::: <search> Tag의 'isMult(" + multStr + ")속성의 값이 유효하지 않습니다");
            }

            isMult = "Y".equals(multStr) ? true : false;
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLength(String lengthStr) {
        if(lengthStr != null && lengthStr.length() > 0){
            if(!StringUtils.isNum(lengthStr)){
                System.err.print("::: <search> Tag의 'length(" + lengthStr + ")속성의 값이 유효하지 않습니다");
            }else{
                this.length = Integer.parseInt(lengthStr);
            }
        }
    }

    public List<EventEntry> getEventEntries() {
        return eventEntries;
    }

    public void setEventEntries(List<EventEntry> eventEntries) {
        this.eventEntries = eventEntries;
    }

    public void addEventEntry(EventEntry eventEntry) {
        if(this.eventEntries == null)
            this.eventEntries = new ArrayList<EventEntry>();

        this.eventEntries.add(eventEntry);
    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }

    public String print() {
        return "SearchEntry{" +
                ", selectQKey='" + selectQKey + '\'' +
                ", isMult=" + isMult +
                ", length=" + length +
                ", list=" + (list == null ? "" : list.toString())+
                ", eventEntries=" + (eventEntries == null ? "" : eventEntries.toString())+
                '}' + super.print();
    }
}
