package kr.pe.sdh.common.view.factory.w3factory;

import kr.pe.sdh.common.view.SearchEntry;
import kr.pe.sdh.common.view.factory.SearchCol;
import kr.pe.sdh.common.view.factory.SearchFactory;
import kr.pe.sdh.common.view.factory.SearchRow;

import java.util.List;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public class W3SearchFactory extends SearchFactory {

    public W3SearchFactory(List<SearchEntry> searchEntries) {
        super(searchEntries);
    }

    public String bindComponent() throws Exception {
        return "";
    }

    public SearchRow createRow() {
        double d = Double.valueOf(searchEntries.size())/Double.valueOf(searchColSize);
        int rowSize = (int)Math.ceil(d);
        return new W3SearchRow(rowSize);
    }

    public SearchCol createCol(SearchEntry data) {
        return new W3SearchCol(data);
    }

}
