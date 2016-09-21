package kr.pe.sdh.common.view.factory;

import kr.pe.sdh.common.view.SearchEntry;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public abstract class SearchCol {
    protected StringBuffer colBuffer;
    protected SearchEntry searchEntry;

    public SearchCol(SearchEntry searchEntry) {
        this.searchEntry = searchEntry;
        colBuffer = new StringBuffer();
    }


    public abstract void makeCol();

    public String ouput(){
        return colBuffer.toString();
    }
}
