package kr.pe.swf.webframework.view.factory.w3factory;

import kr.pe.swf.webframework.util.HtmlUtil;
import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.entry.EventEntry;
import kr.pe.swf.webframework.view.entry.SearchEntry;
import kr.pe.swf.webframework.view.factory.SearchCol;
import kr.pe.swf.webframework.view.factory.model.*;

import java.util.List;

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

        AbstractModel titleModel = new LabelModel();
        titleModel.initAttribute(searchEntry.toMap());

        colBuffer.append(HtmlUtil.openDiv(colDivClass));
        colBuffer.append("\t").append(HtmlUtil.openDiv(thClass));
        colBuffer.append("\t\t").append(titleModel.draw());
        colBuffer.append("\t").append(HtmlUtil.closeDiv());
        colBuffer.append("\t").append(HtmlUtil.openDiv(tdClass));

        // 조회조건 입력란
        String type  = StringUtils.trimStr(searchEntry.getType());
        AbstractModel model;

        if("autocomplete".equals(type)){
            model = new AutocompleteModel();
            model.setClassName(inputClass);

        }else if("radio".equals(type)){
            model = new RadioModel(searchEntry.getList());
            model.setClassName(radioClass);

        }else if("checkbox".equals(type)){
            model = new CheckboxModel(searchEntry.getList());
            model.setClassName(checkboxClass);

        }else if("select".equals(type)){
            model = new SelectModel(searchEntry.getList());
            model.setClassName(inputClass);

        }else if(type.indexOf("date") > -1){
            model = new DatepickerModel();
            model.setClassName(dateClass);

        }else{
            model = new InputModel();
            model.setClassName(inputClass);

        }

        model.initAttribute(searchEntry.toMap());

        List<EventEntry> eventEntryList = searchEntry.getEventEntries();
        for(EventEntry eventEntry : eventEntryList){
            String ev = eventEntry.getName();
            String fn = eventEntry.getFnName();
            model.appendAttribute(ev, fn);
        }

        colBuffer.append("\t\t").append(model.draw());

        colBuffer.append("\t").append(HtmlUtil.closeDiv()); // close Td Div
        colBuffer.append(HtmlUtil.closeDiv()); // close Col Div

    }

}
