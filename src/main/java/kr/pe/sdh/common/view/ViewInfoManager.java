package kr.pe.sdh.common.view;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
public class ViewInfoManager {
    private static ViewInfoManager viewInfoManager = new ViewInfoManager();

    private List<Document> documentList = new ArrayList<Document>();
    private Map<String, Element> viewPath = new HashMap<String, Element>();
    private Map<String, String> viewTitle = new HashMap<String, String>();

    private Map<String, List<SearchEntry>> moduelSearchInfo = new HashMap<String, List<SearchEntry>>();

    public static ViewInfoManager newInstance(){
        return viewInfoManager;
    }

    public void setView(String module, Element root){
        viewPath.put(module, root);
    }

    public Element getView(String module){
        return viewPath.get(module);
    }

    public void setModuleSearchInfo(String module, List<SearchEntry> searchEntries){
        moduelSearchInfo.put(module, searchEntries);
    }

    public List<SearchEntry> getSearchEntry(String module){
        return moduelSearchInfo.get(module);
    }

    public String getViewTitle(String module){
        return viewTitle.get(module);
    }

    public void setViewTitle(String module, String title){
        viewTitle.put(module, title);
    }
}
