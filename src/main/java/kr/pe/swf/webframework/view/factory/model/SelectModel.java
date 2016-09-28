package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.HtmlUtil;
import kr.pe.swf.webframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 29..
 */
public class SelectModel extends AbstractModel {
    private List<Map<String, String>> list;

    public SelectModel(List<Map<String, String>> list) {
        this.list = list;
    }

    public void createModel() {
        appendAttribute("id", getId());

        if(StringUtils.isNotEmpty(getStyle())){
            appendAttribute("style", getStyle());
        }

        buffer.append("<select class=\"").append(getClassName()).append("\"")
                .append(getAttribute()).append(">").append(LF);

        for(Map<String, String> map : list){
            String code  = map.get("code");
            String label = map.get("label");

            String att = getAttribute("value", code);
            if(map.containsKey("selected") && map.get("selected").equals("true")){
                att += " selected";
            }

            buffer.append("\t<option ").
                    append(att).
                    append(">").
                    append(label).
                    append("</option>").
                    append(LF);
        }

        buffer.append("</select>").append(LF);
    }
}
