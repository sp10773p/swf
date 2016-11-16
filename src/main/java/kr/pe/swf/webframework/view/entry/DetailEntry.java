package kr.pe.swf.webframework.view.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
public class DetailEntry extends Entry{
    private String colspan;
    private String rowspan;

    private List<CompEntry> compEntries = new ArrayList<CompEntry>();

    private List<Map<String, String>> list;

    public Map toMap(){
        Map<String, Object> map = super.toMap();
        map.put("colspan", colspan);
        map.put("rowspan", rowspan);

        return map;
    }

    public String getColspan() {
        return colspan;
    }

    public void setColspan(String colspan) {
        this.colspan = colspan;
    }

    public String getRowspan() {
        return rowspan;
    }

    public void setRowspan(String rowspan) {
        this.rowspan = rowspan;
    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }

    public List<CompEntry> getCompEntries() {
        return compEntries;
    }

    public void setCompEntries(List<CompEntry> eventEntries) {
        this.compEntries = compEntries;
    }

    public void addCompEntry(CompEntry compEntry) {
        this.compEntries.add(compEntry);
    }

    public String print() {
        return "DetailEntry{" +
                "colspan='" + colspan + '\'' +
                ", rowspan='" + rowspan + '\'' +
                ", compEntries=" + compEntries +
                ", list=" + list +
                '}' + super.print();
    }
}
