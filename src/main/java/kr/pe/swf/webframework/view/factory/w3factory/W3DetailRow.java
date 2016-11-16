package kr.pe.swf.webframework.view.factory.w3factory;

import kr.pe.swf.webframework.util.HtmlUtil;
import kr.pe.swf.webframework.view.factory.DetailCol;
import kr.pe.swf.webframework.view.factory.DetailRow;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public class W3DetailRow extends DetailRow {
    public W3DetailRow(int rowSize) {
        super(rowSize);
    }

    public void appendCol(DetailCol col) {
        col.makeCol();
        rowBuffer.append(col.ouput());
    }

    public void makeRow() {
        rowBuffer.append(HtmlUtil.openTr(null));
    }

    public void closeRow() {
        rowBuffer.append(HtmlUtil.closeTr());
    }
}
