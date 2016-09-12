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
    private String select;
    private String selectQKey;
    private String check;
    private String checkQKey;
    private String radio;
    private String radioQKey;

    private boolean isMand = false;

    private int length = 0;
    private int index  = 0;

    private List<EventEntry> eventEntries;

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
        map.put("index"  , index);
        map.put("style"  , style);
        map.put("from"   , fromDate);
        map.put("to"     , toDate);
        map.put("default", defaultDate);

        // 쿼리 id를 우선으로 처리
        if(selectQKey != null){
            map.put("selectQKey", selectQKey);
        }else{
            map.put("select", select);
        }

        // 쿼리 id를 우선으로 처리
        if(checkQKey != null){
            map.put("checkQKey", checkQKey);
        }else{
            map.put("check", check);
        }

        // 쿼리 id를 우선으로 처리
        if(radioQKey != null){
            map.put("radioQKey", radioQKey);
        }else{
            map.put("radio", radio);
        }

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

    public void setType(String type) {
        // 유형 체크
        try{
            TYPE.valueOf(type);
            this.type = type;
        }catch (IllegalArgumentException e){
            System.err.print("::: <seach> Tag의 'type(" + type + ")속성의 값이 유효하지 않습니다");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getSelectQKey() {
        return selectQKey;
    }

    public void setSelectQKey(String selectQKey) {
        this.selectQKey = selectQKey;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCheckQKey() {
        return checkQKey;
    }

    public void setCheckQKey(String checkQKey) {
        this.checkQKey = checkQKey;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getRadioQKey() {
        return radioQKey;
    }

    public void setRadioQKey(String radioQKey) {
        this.radioQKey = radioQKey;
    }

    public boolean isMand() {
        return isMand;
    }

    public void setMand(boolean mand) {
        isMand = mand;
    }

    public void setMand(String mandStr) {
        if(mandStr != null && mandStr.length() > 0){
            System.err.print("::: <seach> Tag의 'isMand(" + mandStr + ")속성의 값이 유효하지 않습니다");
        }else{
            isMand = "Y".equals(mandStr) ? true : false;
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
                System.err.print("::: <seach> Tag의 'length(" + lengthStr + ")속성의 값이 유효하지 않습니다");
            }else{
                this.length = Integer.parseInt(lengthStr);
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public void setIndex(String indexStr) {
        if(indexStr != null && indexStr.length() > 0){
            if(!StringUtils.isNum(indexStr)){
                System.err.print("::: <seach> Tag의 'index(" + indexStr + ")속성의 값이 유효하지 않습니다");
            }else{
                this.length = Integer.parseInt(indexStr);
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

    @Override
    public String toString() {
        return "SearchEntry{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", style='" + style + '\'' +
                ", select='" + select + '\'' +
                ", selectQKey='" + selectQKey + '\'' +
                ", check='" + check + '\'' +
                ", checkQKey='" + checkQKey + '\'' +
                ", radio='" + radio + '\'' +
                ", radioQKey='" + radioQKey + '\'' +
                ", isMand=" + isMand +
                ", length=" + length +
                ", index=" + index +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", defaultDate='" + defaultDate + '\'' +
                ", eventEntries=" + (eventEntries == null ? "" : eventEntries.toString())+
                '}';
    }
}


enum TYPE{
    text,select,checkbox,date,duedate,year,dueyear,month,duemonth,radio,autocomplete,spinner
}