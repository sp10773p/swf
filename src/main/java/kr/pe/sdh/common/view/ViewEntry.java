package kr.pe.sdh.common.view;

/**
 * Created by seongdonghun on 2016. 9. 21..
 */
class ViewEntry {

    private SearchsInfo searchsInfo;
    private String id;      // 화면 id
    private String type;    // View type
    private String title;   // 화면 제목

    ViewEntry(String id, String type, String title) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    SearchsInfo getSearchsInfo() {
        return searchsInfo;
    }

    void setSearchsInfo(SearchsInfo searchsInfo) {
        this.searchsInfo = searchsInfo;
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
}
