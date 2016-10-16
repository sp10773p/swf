package kr.pe.swf.webframework.view;

import kr.pe.swf.webframework.view.entry.ColumnEntry;
import kr.pe.swf.webframework.view.entry.EventEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sdh on 2016-10-15.
 */
public class ListsFactory {
	private String id;
	private String rowNum;
	private String rowList;
	private String sortname;
	private String sortorder;
	private String colModel;
	private String qKey;
	private String url;
	private String layout;

	private boolean isFirstLoad = false;

	private List<ColumnEntry> lists = new ArrayList<ColumnEntry>();
	private List<EventEntry> eventEntries = new ArrayList<EventEntry>();

	private StringBuffer listsScript;

	public void appendScript(String script){
		if(this.listsScript == null){
			this.listsScript = new StringBuffer();
		}

		this.listsScript.append(script);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getRowList() {
		return rowList;
	}

	public void setRowList(String rowList) {
		this.rowList = rowList;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public String getColModel() {
		return colModel;
	}

	public void setColModel(String colModel) {
		this.colModel = colModel;
	}

	public String getqKey() {
		return qKey;
	}

	public void setqKey(String qKey) {
		this.qKey = qKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public boolean isFirstLoad() {
		return isFirstLoad;
	}

	public void setFirstLoad(boolean firstLoad) {
		isFirstLoad = firstLoad;
	}

	public void addList(ColumnEntry columnEntry){
		this.lists.add(columnEntry);
	}

	public List<ColumnEntry> getLists() {
		return lists;
	}

	public void setLists(List<ColumnEntry> lists) {
		this.lists = lists;
	}

	public void addEventEntry(EventEntry eventEntry) {
		this.eventEntries.add(eventEntry);
	}

	public List<EventEntry> getEventEntries() {
		return eventEntries;
	}

	public void setEventEntries(List<EventEntry> eventEntries) {
		this.eventEntries = eventEntries;
	}

	public String getListsScript() {
		return (listsScript != null ? listsScript.toString() : null);
	}

	public void setListsScript(StringBuffer listsScript) {
		this.listsScript = listsScript;
	}

	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap();

		map.put("GRID_ID"    , id);
		map.put("rowNum"     , rowNum);
		map.put("rowList"    , rowList);
		map.put("sortname"   , sortname);
		map.put("sortorder"  , sortorder);
		map.put("colModel"   , colModel);
		map.put("qKey"       , qKey);
		map.put("url"        , url);
		map.put("layout"     , layout);
		map.put("isFirstLoad", isFirstLoad);

		List eventList = new ArrayList();
		if(eventEntries != null){
			for(EventEntry eventEntry : eventEntries){
				eventList.add(eventEntry.toMap());
			}
		}

		map.put("event", eventList);

		return map;
	}
}
