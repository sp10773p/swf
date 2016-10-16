package kr.pe.swf.webframework.view.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sdh on 2016-10-15.
 */
public class DetailEntry extends Entry {
	private String colspan;
	private String rowspan;

	private List<CompEntry> compEntries = new ArrayList<CompEntry>();

	private List<Map<String ,String>> list;

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

	public List<CompEntry> getCompEntries() {
		return compEntries;
	}

	public void setCompEntries(List<CompEntry> compEntries) {
		this.compEntries = compEntries;
	}

	public void addCompEntries(CompEntry compEntry){
		this.compEntries.add(compEntry);
	}

	public List<Map<String, String>> getList() {
		return list;
	}

	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

	public String print() {
		return "DetailEntry{" +
				"colspan='" + colspan + '\'' +
				", rowspan='" + rowspan + '\'' +
				", compEntries=" + (compEntries == null ? "" : compEntries.toString())+
				", list=" + (list == null ? "" : list.toString()) +
				'}' + super.print();
	}
}
