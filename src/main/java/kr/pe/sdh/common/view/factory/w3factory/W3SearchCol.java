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
        colBuffer.append(HtmlUtil.openDiv(colDivClass));
        colBuffer.append("\t").append(HtmlUtil.openDiv(thClass));
        colBuffer.append("\t\t").append(createLabel(searchEntry.toMap()));
        colBuffer.append("\t").append(HtmlUtil.closeDiv());
        colBuffer.append("\t").append(HtmlUtil.openDiv(tdClass));

        // 조회조건 입력란
        String orgId = searchEntry.getId();
        if("duedate".equals(searchEntry.getType())){
            searchEntry.setId("from_" + orgId);

            colBuffer.append("\t\t").append(HtmlUtil.createInput(searchEntry.toMap(), dateClass));
            colBuffer.append(" ~ ");

            searchEntry.setId("to_" + orgId);
            colBuffer.append("\t\t").append(HtmlUtil.createInput(searchEntry.toMap(), dateClass));

            searchEntry.setId(orgId);

        }else if("checkbox".equals(searchEntry.getType())){
            colBuffer.append("\t\t").append(createCheckNRadio(searchEntry.toMap(), checkboxClass));

        }else if("radio".equals(searchEntry.getType())){
            colBuffer.append("\t\t").append(createCheckNRadio(searchEntry.toMap(), radioClass));

        }else{
            colBuffer.append("\t\t").append(HtmlUtil.createInput(searchEntry.toMap(), inputClass));
        }

        colBuffer.append("\t").append(HtmlUtil.closeDiv()); // close Td Div
        colBuffer.append(HtmlUtil.closeDiv()); // close Col Div

    }

    private String createCheckNRadio(Map data, String className){
        String id    = StringUtils.trimStr(data.get("id"));
        String type  = StringUtils.trimStr(data.get("type"));
        String style = StringUtils.trimStr(data.get("style"));

        StringBuffer sb = new StringBuffer();

        String childKey = type.substring(0, 5);

        //check or radio Node TODO QKey는 쿼리실행후 받아와야하며 받아온 내용을 변환
        String child = StringUtils.isEmpty(data.get(childKey + "QKey")) ? (String)data.get(childKey) : (String)data.get(childKey + "QKey");

        int idx = 0;

        List<String[]> objArrList = HtmlUtil.getStringArray(child);

        for(String[] objArr : objArrList){
            if(objArr.length < 2){
                System.err.println("<"+childKey+"> or <"+childKey+"QKey> 내용을 확인하세요");
                return null;
            }

            String value = objArr[0];
            String label = objArr[1];
            String checked = (objArr.length > 2 && objArr[2].equals("C") ? "checked" : "" );

            String inp = HtmlUtil.getInputTag(type, id+(++idx), id, 0, style, className, value, checked);

            sb.append(inp);

            Map<String, String> labelData = new HashMap<String, String>();
            labelData.put("title"    , label);
            labelData.put("className", "w3-validate");
            labelData.put("style"    , "margin-left: 3px; margin-right: 3px;");

            sb.append(createLabel(labelData));

        }

        return sb.toString();
    }
}
