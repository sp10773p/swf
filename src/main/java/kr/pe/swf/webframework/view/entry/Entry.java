package kr.pe.swf.webframework.view.entry;

import kr.pe.swf.webframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sdh on 2016-10-15.
 */
public class Entry implements Cloneable {
	private String id;
	private String type;
	private String title;
	private String style;
	private String value;

	private boolean isMand = false;

	/* 날짜관련 속성 */
	// due ~
	private String fromDate;
	private String toDate;

	// date, year, month
	private String defaultDate;

	List<Map<String, String>> list;

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public Map toMap() {
		Map<String, Object> map = new HashMap();
		map.put("id"     , id);
		map.put("type"   , type);
		map.put("value"  , value);
		map.put("title"  , title);
		map.put("isMand" , isMand);
		map.put("style"  , style);
		map.put("from"   , fromDate);
		map.put("to"     , toDate);
		map.put("default", defaultDate);
		map.put("list"   , list);

		return  map;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isMand() {
		return isMand;
	}

	public void setMand(String mandStr) {
		if(StringUtils.isNotEmpty(mandStr)){
			if(!"Y".equals(mandStr.toUpperCase()) && !"N".equals(mandStr.toUpperCase())){
				System.err.print("::: <search> Tag의 'isMand(" + mandStr + ")속성의 값이 유효하지 않습니다");
			}

			isMand = "Y".equals(mandStr) ? true : false;
		}
	}

	public void setMand(boolean mand) {
		isMand = mand;
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

	public String print() {
		return "Entry{" +
				"id='" + id + '\'' +
				", type='" + type + '\'' +
				", title='" + title + '\'' +
				", style='" + style + '\'' +
				", value='" + value + '\'' +
				", isMand=" + isMand +
				", fromDate='" + fromDate + '\'' +
				", toDate='" + toDate + '\'' +
				", defaultDate='" + defaultDate + '\'' +
				", list=" + (list == null ? "" : list.toString())+
				'}';
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	enum TYPE{
		text,select,checkbox,date,duedate,year,dueyear,month,duemonth,radio,autocomplete,spinner
	}
}
