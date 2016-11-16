package kr.pe.swf.webframework.view.factory.w3factory;

import kr.pe.swf.webframework.util.HtmlUtil;
import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.entry.CompEntry;
import kr.pe.swf.webframework.view.entry.DetailEntry;
import kr.pe.swf.webframework.view.entry.EventEntry;
import kr.pe.swf.webframework.view.factory.DetailCol;
import kr.pe.swf.webframework.view.factory.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public class W3DetailCol extends DetailCol {
    private String inputClass    = "search-input";
    private String dateClass     = "search-date";
    private String checkboxClass = "w3-check";
    private String radioClass    = "w3-radio";

    private Map data;

    public W3DetailCol(DetailEntry detailEntry) {
        super(detailEntry);
    }

    public void makeCol() {
        String thWidth = null;
        String tdWidth = null;
        switch (colSize){
            case 1:
                thWidth = "40%";
                tdWidth = "60%";
                break;

            case 2:
                thWidth = "20%";
                tdWidth = "30%";
                break;

            case 3:
                thWidth = "15%";
                tdWidth = "18%";
                break;

            default:
                thWidth = "10%";
                tdWidth = "15%";
                break;
        }

        int rowSpan = (StringUtils.isEmpty(detailEntry.getRowspan()) ? 1 : Integer.parseInt(detailEntry.getRowspan()));
        int colSpan = (StringUtils.isEmpty(detailEntry.getColspan()) ? 1 : Integer.parseInt(detailEntry.getColspan()));

        // title
        if(StringUtils.isNotEmpty(detailEntry.getTitle())){
            colBuffer.append(HtmlUtil.openTh(detailEntry.isMand(), thWidth, rowSpan, colSpan));
            colBuffer.append(detailEntry.getTitle());
            colBuffer.append(HtmlUtil.closeTh());

        // 입력란
        }else{
            StringBuffer buffer = new StringBuffer();
            // 조회조건 입력란
            List<CompEntry> compEntries = detailEntry.getCompEntries();
            for(CompEntry orgCompEntry : compEntries){
                CompEntry compEntry = null;
                try {
                    compEntry = (CompEntry) orgCompEntry.clone();
                } catch (CloneNotSupportedException e) {}

                String type = StringUtils.trimStr(compEntry.getType());

                List<Map<String, String>> list = orgCompEntry.getList();

                String id = compEntry.getId();

                // 수정모드일때 조회한 값을 셋팅
                if(this.data.containsKey(id)){
                    compEntry.setValue(String.valueOf(this.data.get(id)));
                    if(compEntry.getList() != null && compEntry.getList().size() > 0){
                        list = new ArrayList();
                        for(Map<String, String> orgMap : compEntry.getList()){
                            HashMap<String, String> map = (HashMap<String, String>) ((HashMap<String, String>)orgMap).clone();

                            String code  = map.get("code");
                            if(StringUtils.isNotEmpty(code) && String.valueOf(this.data.get(id)).indexOf(code.trim()) > -1){
                                map.put("checked", "true");
                                map.put("selected", "true");
                            }else{
                                if(map.containsKey("checked")){
                                    map.remove("checked");

                                }
                                if(map.containsKey("selected")){
                                    map.remove("selected");
                                }
                            }

                            list.add(map);
                        }
                    }
                }

                AbstractModel model = null;
                if("autocomplete".equals(type)){
                    model = new AutocompleteModel();
                    model.setClassName(inputClass);

                }else if("radio".equals(type)){
                    model = new RadioModel(list);
                    model.setClassName(radioClass);

                }else if("checkbox".equals(type)){
                    model = new CheckboxModel(list);
                    model.setClassName(checkboxClass);

                }else if("select".equals(type)){
                    model = new SelectModel(list);
                    model.setClassName(inputClass);

                }else if(type.indexOf("date") > -1){
                    model = new DatepickerModel();
                    model.setClassName(dateClass);

                }else if("label".equals(type)){
                    model = new LabelModel();
                    compEntry.setTitle(compEntry.getValue());

                }else{
                    model = new InputModel();
                    model.setClassName(inputClass);

                }

                model.initAttribute(compEntry.toMap());

                List<EventEntry> eventEntryList = compEntry.getEventEntries();
                for(EventEntry eventEntry : eventEntryList){
                    String ev = eventEntry.getName();
                    String fn = eventEntry.getFnName()+"(this)";
                    model.appendAttribute(ev, fn);
                }

                buffer.append(model.draw());
            }

            colBuffer.append(HtmlUtil.openTd(tdWidth, rowSpan, colSpan));
            colBuffer.append(buffer.toString());
            colBuffer.append(HtmlUtil.closeTd()); // close Td
        }
    }

    @Override
    public void bindValue(Map data) {
        if(data == null){
            data = new HashMap();
        }

        this.data = data;
    }
}
