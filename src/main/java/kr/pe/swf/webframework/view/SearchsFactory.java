package kr.pe.swf.webframework.view;

import kr.pe.swf.webframework.view.entry.SearchEntry;

import java.util.*;

/**
 * <searchs id="searchs id"></searchs>
 */
public class SearchsFactory {

    // searchs id, search items
    private List<SearchEntry> searchs = new ArrayList<SearchEntry>();

    // For Cache
    private StringBuffer searchsHtml;
    private StringBuffer searchsScript = new StringBuffer();

    private int colSize = 3;

    private String function;
    private String qKey;
    private String id;          // searchs id

    public void addSearch(SearchEntry searchEntry){
        this.searchs.add(searchEntry);
    }

    public void setSearchs(List<SearchEntry> searchEntries){
        this.searchs = searchEntries;
    }

    public List<SearchEntry> getSearchs(){
        return this.searchs;
    }

    public List<String> getSearchsIds(){
        List<String> searchIds = new ArrayList<String>();

        for(SearchEntry searchEntry : this.searchs){
            searchIds.add((String)searchEntry.getId());
        }

        return searchIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void appednSearchHtml(String html){
        if(this.searchsHtml == null){
            this.searchsHtml = new StringBuffer();
        }
        this.searchsHtml.append(html);
    }

    public String getSearchHtml(){
        return (this.searchsHtml != null ? this.searchsHtml.toString() : null);
    }

    public void appendSearchScript(String script){
        this.searchsScript.append(script);
    }

    public String getSearchScript(){
        return this.searchsScript.toString();
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public int getColSize() {
        return colSize;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getqKey() {
        return qKey;
    }

    public void setqKey(String qKey) {
        this.qKey = qKey;
    }
}
