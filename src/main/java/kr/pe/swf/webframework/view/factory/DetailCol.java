package kr.pe.swf.webframework.view.factory;

import kr.pe.swf.webframework.view.entry.DetailEntry;

import java.util.Map;

/**
 * Created by sdh on 2016-10-15.
 */
public abstract class DetailCol {
	protected StringBuffer colBuffer;
	protected DetailEntry detailEntry;

	protected int colSize = 3;

	public DetailCol(DetailEntry detailEntry) {
		this.detailEntry = detailEntry;
		colBuffer = new StringBuffer();
	}

	/***
	 * 조회조건의 한 로우의 한컬럼을 생성(Title : Input)
	 */
	public abstract void makeCol();

	/***
	 * 생성된 컬럼의 코드를 리턴
	 */
	public String ouput() {
		return colBuffer.toString();
	}

	public int getColSize() {
		return colSize;
	}

	public void setColSize(int colSize) {
		this.colSize = colSize;
	}

	public abstract void bindValue(Map data);
}
