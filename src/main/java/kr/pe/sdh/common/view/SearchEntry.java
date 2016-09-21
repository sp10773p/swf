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

    String getSelect() {
        return select;
    }

    void setSelect(String select) {
        this.select = select;
    }

    String getSelectQKey() {
        return selectQKey;
    }

    void setSelectQKey(String selectQKey) {
        this.selectQKey = selectQKey;
    }

    String getCheck() {
        return check;
    }

    void setCheck(String check) {
        this.check = check;
    }

    String getCheckQKey() {
        return checkQKey;
    }

    void setCheckQKey(String checkQKey) {
        this.checkQKey = checkQKey;
    }

    String getRadio() {
        return radio;
    }

    void setRadio(String radio) {
        this.radio = radio;
    }

    String getRadioQKey() {
        return radioQKey;
    }

    void setRadioQKey(String radioQKey) {
        this.radioQKey = radioQKey;
    }

    boolean isMand() {
        return isMand;
    }

    void setMand(boolean mand) {
        isMand = mand;
    }

    void setMand(String mandStr) {
        if(mandStr != null && mandStr.length() > 0){
            if(!"Y".equals(mandStr) && !"N".equals(mandStr)){
                System.err.print("::: <search> Tag의 'isMand(" + mandStr + ")속성의 값이 유효하지 않습니다");
            }
        }else{
            isMand = "Y".equals(mandStr) ? true : false;
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

    String getFromDate() {
        return fromDate;
    }

    void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    String getToDate() {
        return toDate;
    }

    void setToDate(String toDate) {
        this.toDate = toDate;
    }

    String getDefaultDate() {
        return defaultDate;
    }

    void setDefaultDate(String defaultDate) {
        this.defaultDate = defaultDate;
    }

    String print() {
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