package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.HtmlUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 30..
 */
public class CheckboxModel extends AbstractModel {
    private List<Map<String, String >> list;

    public CheckboxModel(List<Map<String, String >> list) {
        this.list = list;
    }

    public void createModel() {
        int idx = 0;
        for(Map<String, String> map : list){
            String code  = map.get("code");
            String label = map.get("label");

            String checked = null;
            if(map.containsKey("checked") && map.get("checked").equals("true")){
                checked = "checked";

            }

            setChecked(checked);
            setValue(code);

            Map<String, Object> attMap = getInitAttributeMap();
            attMap.put("name" , getId());
            attMap.put("id"   , getId() + (++idx));

            InputModel inputModel = new InputModel();
            inputModel.initAttribute(attMap);

            // input
            buffer.append(inputModel.draw());

            Map<String, Object> labelMap = new HashMap();
            labelMap.put("title"    , label);
            labelMap.put("className", labelClassName);
            labelMap.put("style"    , labelStyle);

            Model labelModel = new LabelModel();
            labelModel.initAttribute(labelMap);

            // title
            buffer.append(labelModel.draw());

        }
    }
}
