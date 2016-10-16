package kr.pe.swf.webframework.view;

import kr.pe.swf.webframework.view.entry.DetailEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdh on 2016-10-15.
 */
public class DetailsFactroy {
	private String id;
	private String title;
	private String qKey;

	private int colSize = 4;

	private List<DetailEntry> detailEntries = new ArrayList<DetailEntry>();

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

	public List<DetailEntry> getDetails() {
		return detailEntries;
	}

	public void setDetails(List<DetailEntry> detailEntries) {
		this.detailEntries = detailEntries;
	}

	public String getDetailsHtml() {
		return (detailsHtml != null ? detailsHtml.toString() : null);
	}

	public void setDetailsHtml(StringBuffer detailsHtml) {
		this.detailsHtml = detailsHtml;
	}

	public StringBuffer getDetailsScript() {
		return detailsScript;
	}

	public void setDetailsScript(StringBuffer detailsScript) {
		this.detailsScript = detailsScript;
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

	public void appendDetailHtml(String html){
		if(this.detailsHtml == null){
			this.detailsHtml = new StringBuffer();
		}
		this.detailsHtml.append(html);
	}

	public void clearDetailScript() {
		if(this.detailsScript != null){
			this.detailsScript = new StringBuffer();
		}
	}
}
