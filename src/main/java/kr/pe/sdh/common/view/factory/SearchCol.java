package kr.pe.sdh.common.view.factory;

import kr.pe.sdh.common.view.SearchEntry;

import java.util.HashMap;
import java.util.Map;

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


    public abstract void makeCol();

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
