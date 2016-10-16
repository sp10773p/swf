package kr.pe.swf.webframework.view.factory;

/**
 * Created by sdh on 2016-10-15.
 */
public abstract class DetailRow {
	protected int rowSize = 0;
	protected int rowPos  = 1;

	protected StringBuffer rowBuffer = new StringBuffer();

	public DetailRow(int rowSize) {
		this.rowSize = rowSize;
		this.rowPos = 1; //position 초기화
	}

	public abstract void makeRow();

	public abstract void appendCol(DetailCol detailCol);

	public abstract void closeRow();

	public String ouput(){
		return this.rowBuffer.toString();
	}
}
