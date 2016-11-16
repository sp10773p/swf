package kr.pe.swf.webframework.view.factory;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public abstract class DetailRow {
    protected int rowSize= 0;
    protected int rowPos = 1;

    protected StringBuffer rowBuffer = new StringBuffer();

    public DetailRow(int rowSize){
        this.rowSize = rowSize;
        this.rowPos = 1; // position 초기화
    }

    public abstract void makeRow();

    public abstract void appendCol(DetailCol col);

    public abstract void closeRow();

    public String output(){
        return this.rowBuffer.toString();
    }
}
