package kr.pe.swf.webframework.view.factory.w3factory;

import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.util.UIComponentsUtils;
import kr.pe.swf.webframework.view.entry.CompEntry;
import kr.pe.swf.webframework.view.entry.DetailEntry;
import kr.pe.swf.webframework.view.factory.DetailBuilder;
import kr.pe.swf.webframework.view.factory.DetailCol;
import kr.pe.swf.webframework.view.factory.DetailRow;

import java.util.List;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public class W3DetailBuilder extends DetailBuilder {

    public W3DetailBuilder(List<DetailEntry> detailEntries) {
        super(detailEntries);
    }

    public String bindComponent(CompEntry compEntry) throws Exception {
        StringBuffer scriptBuffer = new StringBuffer();
        String type = StringUtils.trimStr(compEntry.getType());
        if(type.startsWith("due")) {
            String orgId = compEntry.getId();

            compEntry.setId("from_" + orgId);
            scriptBuffer.append(UIComponentsUtils.getBindScript(compEntry));

            compEntry.setId("to_" + orgId);
            scriptBuffer.append(UIComponentsUtils.getBindScript(compEntry));

        }else{
            scriptBuffer.append(UIComponentsUtils.getBindScript(compEntry));

        }

        return scriptBuffer.toString();
    }

    public DetailRow createRow() {
        double d = Double.valueOf(detailEntries.size())/Double.valueOf(detailColSize);
        int rowSize = (int)Math.ceil(d);
        return new W3DetailRow(rowSize);
    }

    public DetailCol createCol(DetailEntry data) {
        return new W3DetailCol(data);
    }

}
