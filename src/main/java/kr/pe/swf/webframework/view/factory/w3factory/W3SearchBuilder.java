package kr.pe.swf.webframework.view.factory.w3factory;

import kr.pe.swf.webframework.util.UIComponentsUtils;
import kr.pe.swf.webframework.view.entry.SearchEntry;
import kr.pe.swf.webframework.view.factory.SearchCol;
import kr.pe.swf.webframework.view.factory.SearchBuilder;
import kr.pe.swf.webframework.view.factory.SearchRow;

import java.util.List;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public class W3SearchBuilder extends SearchBuilder {

    public W3SearchBuilder(List<SearchEntry> searchEntries) {
        super(searchEntries);
    }

    public String bindComponent(SearchEntry searchEntry) throws Exception {
        StringBuffer scriptBuffer = new StringBuffer();
        if(searchEntry.getType().startsWith("due")) {
            String orgId = searchEntry.getId();

            searchEntry.setId("from_" + orgId);
            scriptBuffer.append(UIComponentsUtils.getBindScript(searchEntry));

            searchEntry.setId("to_" + orgId);
            scriptBuffer.append(UIComponentsUtils.getBindScript(searchEntry));

        }else{
            scriptBuffer.append(UIComponentsUtils.getBindScript(searchEntry));

        }

        return scriptBuffer.toString();
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
