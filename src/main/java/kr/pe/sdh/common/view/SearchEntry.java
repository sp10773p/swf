package kr.pe.sdh.common.view;

import kr.pe.sdh.common.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
public class SearchEntry {
    private String id;
    private String type;
    private String title;
    private String style;
    private String selectQKey;

    private boolean isMult = false;
    private boolean isMand = false;

    private int length = 0;

    private List<EventEntry> eventEntries;

    private List<Map<String, String>> list;

    /* 날짜관련 속성 */
    // due ~
    private String fromDate;
    private String toDate;

    // date, year, month
    private String defaultDate;

    public Map toMap(){
        Map<String, Object> map = new HashMap();
        map.put("id"     , id);
        map.put("type"   , type);
        map.put("title"  , title);
        map.put("isMand" , isMand);
        map.put("length" , length);
        map.put("style"  , style);
        map.put("from"   , fromDate);
        map.put("to"     , toDate);
        map.put("default", defaultDate);

        // 쿼리 id를 우선으로 처리
        if(selectQKey != null){
            map.put("selectQKey", selectQKey);
        }else{
            map.put("list", list);
        }

        // 쿼리 id를 우선으로 처리
        List list = new ArrayList();
        if(eventEntries != null){
            for(EventEntry eventEntry : eventEntries){
                list.add(eventEntry.toMap());
            }
        }

        map.put("event", list);

        return map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        // 유형 체크
        try{
            TYPE.valueOf(type);
            this.type = type;
        }catch (IllegalArgumentException e){
            System.err.print("::: <seach> Tag의 'type(" + type + ")속성의 값이 유효하지 않습니다");
        }
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getStyle() {
        return style;
    }

    void setStyle(String style) {
        this.style = style;
    }

    String getSelectQKey() {
        return selectQKey;
    }

    void setSelectQKey(String selectQKey) {
        this.selectQKey = selectQKey;
    }

    boolean isMand() {
        return isMand;
    }

    void setMand(boolean mand) {
        isMand = mand;
    }

    void setMand(String mandStr) {
        if(StringUtils.isNotEmpty(mandStr)){
            if(!"Y".equals(mandStr.toUpperCase()) && !"N".equals(mandStr.toUpperCase())){
                System.err.print("::: <search> Tag의 'isMand(" + mandStr + ")속성의 값이 유효하지 않습니다");
            }

            isMand = "Y".equals(mandStr) ? true : false;
        }
    }


    boolean isMult() {
        return isMult;
    }

    void setMult(boolean mult) {
        isMult = mult;
    }

    void setMult(String multStr) {
        if(StringUtils.isNotEmpty(multStr)){
            if(!"Y".equals(multStr.toUpperCase()) && !"N".equals(multStr.toUpperCase())){
                System.err.print("::: <search> Tag의 'isMult(" + multStr + ")속성의 값이 유효하지 않습니다");
            }

            isMult = "Y".equals(multStr) ? true : false;
        }
    }

    int getLength() {
        return length;
    }

    void setLength(int length) {
        this.length = length;
    }

    void setLength(String lengthStr) {
        if(lengthStr != null && lengthStr.length() > 0){
            if(!StringUtils.isNum(lengthStr)){
                System.err.print("::: <search> Tag의 'length(" + lengthStr + ")속성의 값이 유효하지 않습니다");
            }else{
                this.length = Integer.parseInt(lengthStr);
            }
        }
    }

    List<EventEntry> getEventEntries() {
        return eventEntries;
    }

    void setEventEntries(List<EventEntry> eventEntries) {
        this.eventEntries = eventEntries;
    }

    void addEventEntry(EventEntry eventEntry) {
        if(this.eventEntries == null)
            this.eventEntries = new ArrayList<EventEntry>();

        this.eventEntries.add(eventEntry);
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDefaultDate() {
        return defaultDate;
    }

    public void setDefaultDate(String defaultDate) {
        this.defaultDate = defaultDate;
    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }

    String print() {
        return "SearchEntry{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", style='" + style + '\'' +
                ", selectQKey='" + selectQKey + '\'' +
                ", isMand=" + isMand +
                ", isMult=" + isMult +
                ", length=" + length +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", defaultDate='" + defaultDate + '\'' +
                ", list=" + (list == null ? "" : list.toString())+
                ", eventEntries=" + (eventEntries == null ? "" : eventEntries.toString())+
                '}';
    }
}


enum TYPE{
    text,select,checkbox,date,duedate,year,dueyear,month,duemonth,radio,autocomplete,spinner
}