package kr.pe.swf.webframework.view.factory;

import kr.pe.swf.webframework.view.entry.SearchEntry;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public abstract class SearchCol {
    protected StringBuffer colBuffer;
    protected SearchEntry searchEntry;

    protected int colSize = 3;

    public SearchCol(SearchEntry searchEntry) {
        this.searchEntry = searchEntry;
        colBuffer = new StringBuffer();
    }


    /***
     * 조회조건의 한 로우의 한컬럼을 생성(Title : Input)
     */
    public abstract void makeCol();

    /***
     * 생성된 컬럼의 코드를 리턴
     * @return
     */
    public String ouput(){
        return colBuffer.toString();
    }

    public int getColSize() {
        return colSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }
}
