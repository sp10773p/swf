package kr.pe.sdh.common.view;

import java.util.*;

/**
 * <searchs id="searchs id"></searchs>
 */
class SearchsInfo {

    // searchs id, search items
    private Map<String, List<SearchEntry>> searchs = new HashMap<String, List<SearchEntry>>();

    // searchs html code - searchs id, searchs html code
    private Map<String, String> searchsHtml = new HashMap<String, String>();

    // searchs script code - searchs id, searchs script code
    private StringBuffer searchsScript = new StringBuffer();

    private int colSize = 3;

    void addSearch(String searchId, List<SearchEntry> searchEntries){
        this.searchs.put(searchId, searchEntries);
    }

    List<SearchEntry> getSearch(String searchId){
        return this.searchs.get(searchId);
    }

    Map<String, List<SearchEntry>> getSearchs(){
        return this.searchs;
    }

    List<String> getSearchsIds(){
        List<String> searchIds = new ArrayList<String>();
        Iterator it = this.searchs.keySet().iterator();
        while(it.hasNext()){
            searchIds.add((String)it.next());
        }

        return searchIds;
    }

    void setSearchHtml(String searchsId, String html){
        this.searchsHtml.put(searchsId, html);
    }

    String getSearchHtml(String searchsId){
        return this.searchsHtml.get(searchsId);
    }

    void appendScript(String script){
        this.searchsScript.append(script);
    }

    String getSearchScript(){
        return this.searchsScript.toString();
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public int getColSize() {
        return colSize;
    }
}
