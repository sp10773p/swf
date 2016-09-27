package kr.pe.sdh.common.view.factory.w3factory;

import kr.pe.sdh.common.util.HtmlUtil;
import kr.pe.sdh.common.util.StringUtils;
import kr.pe.sdh.common.view.SearchEntry;
import kr.pe.sdh.common.view.factory.SearchCol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.pe.sdh.common.util.HtmlUtil.createLabel;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public class W3SearchCol extends SearchCol {
    private String colDivClass   = "w3-third";
    private String thClass       = "w3-col m4 th3";
    private String tdClass       = "w3-col m8";
    private String inputClass    = "search-input";
    private String dateClass     = "search-date";
    private String checkboxClass = "w3-check";
    private String radioClass    = "w3-radio";

    public W3SearchCol(SearchEntry searchEntry) {
        super(searchEntry);
    }

    public void makeCol() {
        switch (colSize){
            case 1:
                colDivClass = "w3-all";
                break;

            case 2:
                colDivClass = "w3-half";
                break;

            case 4:
                colDivClass = "w3-quarter";
                break;

            default:
                colDivClass = "w3-third";
                break;
        }

        colBuffer.append(HtmlUtil.openDiv(colDivClass));
        colBuffer.append("\t").append(HtmlUtil.openDiv(thClass));
        colBuffer.append("\t\t").append(createLabel(searchEntry.toMap()));
        colBuffer.append("\t").append(HtmlUtil.closeDiv());
        colBuffer.append("\t").append(HtmlUtil.openDiv(tdClass));

        // 조회조건 입력란
        String orgId = searchEntry.getId();
        String type  = searchEntry.getType();
        if("duedate".equals(type) || "dueyearmonth".equals(type)){
            searchEntry.setId("from_" + orgId);

            colBuffer.append("\t\t").append(HtmlUtil.createInput(searchEntry.toMap(), dateClass));
            colBuffer.append(" ~ ");

            searchEntry.setId("to_" + orgId);
            colBuffer.append("\t\t").append(HtmlUtil.createInput(searchEntry.toMap(), dateClass));

            searchEntry.setId(orgId);

        }else if("checkbox".equals(searchEntry.getType())){
            colBuffer.append("\t\t").append(HtmlUtil.createCheckNRadio(searchEntry.toMap(), checkboxClass));

        }else if("radio".equals(searchEntry.getType())){
            colBuffer.append("\t\t").append(HtmlUtil.createCheckNRadio(searchEntry.toMap(), radioClass));

        }else{
            colBuffer.append("\t\t").append(HtmlUtil.createInput(searchEntry.toMap(), inputClass));

        }

        colBuffer.append("\t").append(HtmlUtil.closeDiv()); // close Td Div
        colBuffer.append(HtmlUtil.closeDiv()); // close Col Div

    }

}
