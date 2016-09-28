package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.HtmlUtil;
import kr.pe.swf.webframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 29..
 */
public class CheckNRadioModel extends AbstractModel {
    private String type;
    private List<Map<String, String>> list;

    public CheckNRadioModel(String type, List<Map<String, String>> list) {
        this.type = type;
        this.list = list;
    }

    public void createModel() {
        int idx = 0;
        StringBuffer sb = new StringBuffer();
        for(Map<String, String> map : list){
            String code  = map.get("code");
            String label = map.get("label");

            String checked = null;
            if(map.containsKey("checked") && map.get("checked").equals("true")){
                checked = "checked";

            }

            appendAttribute("id", getId());

            if(StringUtils.isNotEmpty(code)){
                appendAttribute("value", code);
            }

            if(StringUtils.isNotEmpty(getName())){
                appendAttribute("name", getName());
            }

            if(StringUtils.isNotEmpty(checked)){
                appendAttribute("checked", checked);
            }

            if(StringUtils.isNotEmpty(getStyle())){
                appendAttribute("style", getStyle());
            }

            if(StringUtils.isNotEmpty(getClassName())){
                appendAttribute("class", getClassName());
            }

            appendAttribute("type", type);


            // input
            buffer.append("<input").append(getAttribute()).append("/>").append(LF);

            Map<String, String> labelData = new HashMap<String, String>();
            labelData.put("title"    , label);
            labelData.put("className", "w3-validate"); // TODO: 2016. 9. 29. 호출하는곳에서 className 넣어줘야함 , getClassName()으로 변경
            labelData.put("style"    , "margin-left: 3px; margin-right: 3px;");

            Model labelModel = new LabelModel();
            labelModel.initAttribute(labelData);
            labelModel.createModel();

            // title
            buffer.append(labelModel.draw());
        }
    }
}
