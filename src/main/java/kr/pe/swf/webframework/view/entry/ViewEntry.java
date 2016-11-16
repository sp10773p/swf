package kr.pe.swf.webframework.view.entry;

import kr.pe.swf.webframework.view.ButtonsFactory;
import kr.pe.swf.webframework.view.DetailsFactory;
import kr.pe.swf.webframework.view.ListsFactory;
import kr.pe.swf.webframework.view.SearchsFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 21..
 */
public class ViewEntry {

    private List<SearchsFactory> searchsFactories = new ArrayList();
    private List<DetailsFactory> detailsFactories = new ArrayList();
    private List<ListsFactory> listsFactories     = new ArrayList();
    private List<ButtonsFactory> buttonsFactories = new ArrayList();
    private String id;      // 화면 id
    private String type;    // View type
    private String title;   // 화면 제목
    private String script;

    private Map<String, String> viewParam = new HashMap<String, String>();

    public ViewEntry(String id, String type, String title) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    public List<ButtonsFactory> getButtonsFactory() {
        return buttonsFactories;
    }

    public void setButtonsFactory(List<ButtonsFactory> buttonsFactories) {
        this.buttonsFactories = buttonsFactories;
    }

    public List<DetailsFactory> getDetailsFactory() {
        return detailsFactories;
    }

    public void setDetailsFactory(List<DetailsFactory> detailsFactories) {
        this.detailsFactories = detailsFactories;
    }

    public List<ListsFactory> getListsFactory() {
        return listsFactories;
    }

    public void setListsFactory(List<ListsFactory> listsFactories) {
        this.listsFactories = listsFactories;
    }

    public List<SearchsFactory> getSearchsFactory() {
        return searchsFactories;
    }

    public void setSearchsFactory(List<SearchsFactory> searchsFactory) {
        this.searchsFactories = searchsFactory;
    }

    public Map<String, String> getViewParam() {
        return viewParam;
    }

    public void setViewParam(Map<String, String> viewParam) {
        this.viewParam = viewParam;
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
