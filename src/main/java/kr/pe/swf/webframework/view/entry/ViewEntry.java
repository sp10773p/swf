package kr.pe.swf.webframework.view.entry;

import kr.pe.swf.webframework.view.SearchsFactory;

/**
 * Created by seongdonghun on 2016. 9. 21..
 */
public class ViewEntry {

    private SearchsFactory searchsFactory;
    private String id;      // 화면 id
    private String type;    // View type
    private String title;   // 화면 제목
    private String script;

    public ViewEntry(String id, String type, String title) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    public SearchsFactory getSearchsFactory() {
        return searchsFactory;
    }

    public void setSearchsFactory(SearchsFactory searchsFactory) {
        this.searchsFactory = searchsFactory;
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
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
