package kr.pe.swf.webframework.view;

import kr.pe.swf.webframework.view.entry.DetailEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * User : sdh
 * Date : 2016-10-06
 * Project Name : Seong`s Web Framework Beta
 * Description  :
 * History : Created on 2016-10-06
 */
public class DetailsFactory {
    private String id;
    private String title;
    private String qKey;

    private int colSize = 4;

    private List<DetailEntry> detailEntries = new ArrayList<DetailEntry>();
    // For Cache
    private StringBuffer detailsHtml;

    private StringBuffer detailsScript = new StringBuffer();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getqKey() {
        return qKey;
    }

    public void setqKey(String qKey) {
        this.qKey = qKey;
    }

    public int getColSize() {
        return colSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public void addDetail(DetailEntry detailEntry){
        this.detailEntries.add(detailEntry);
    }

    public List<DetailEntry> getDetails(){
        return this.detailEntries;
    }

    public List<String> getDetailsIds(){
        List<String> detailIds = new ArrayList<String>();

        for(DetailEntry detailEntry : this.detailEntries){
            detailIds.add((String)detailEntry.getId());
        }

        return detailIds;
    }

    public void clearDetailHtml(){
        if(this.detailsHtml != null){
            this.detailsHtml = new StringBuffer();
        }
    }

    public void appednDetailHtml(String html){
        if(this.detailsHtml == null){
            this.detailsHtml = new StringBuffer();
        }
        this.detailsHtml.append(html);
    }

    public String getDetailHtml(){
        return (this.detailsHtml != null ? this.detailsHtml.toString() : null);
    }

    public void appendDetailScript(String script){
        this.detailsScript.append(script);
    }

    public String getDetailScript(){
        return this.detailsScript.toString();
    }

    public void clearDetailScript(){
        this.detailsScript = new StringBuffer();
    }
}
