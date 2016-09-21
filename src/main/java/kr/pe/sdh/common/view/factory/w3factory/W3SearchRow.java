package kr.pe.sdh.common.view.factory.w3factory;

import kr.pe.sdh.common.util.HtmlUtil;
import kr.pe.sdh.common.view.factory.SearchCol;
import kr.pe.sdh.common.view.factory.SearchRow;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public class W3SearchRow extends SearchRow {
    public W3SearchRow(int rowSize) {
        super(rowSize);
    }

    public void appendCol(SearchCol col) {
        col.makeCol();
        rowBuffer.append(col.ouput());
    }

    public void makeRow() {
        String className;
        if(rowSize == 1){
            className = "w3-padding search-one";

        }else if(rowSize == 2){
            if(rowPos == 1){
                className = "w3-row-padding search-twin-top";

            }else{
                className = "w3-row-padding search-twin-bottom";

            }
        }else{
            if(rowPos == 1){
                className = "w3-row-padding search-top";

            }else if(rowSize == rowPos){
                className = "w3-row-padding search-bottom";

            }else{
                className = "w3-row-padding search-middle";

            }
        }

        rowPos++;
        rowBuffer.append(HtmlUtil.openDiv(className));
    }

    public void closeRow() {
        rowBuffer.append(HtmlUtil.closeDiv());
    }
}
