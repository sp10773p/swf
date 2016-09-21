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
    private Map<String, String> searchsScript = new HashMap<String, String>();

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

    void setSearchScript(String searchsId, String script){
        this.searchsScript.put(searchsId, script);
    }

    String getSearchScript(String searchsId){
        return this.searchsScript.get(searchsId);
    }
}
