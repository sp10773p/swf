package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.StringUtils;

/**
 * Created by seongdonghun on 2016. 9. 28..
 */
public class LabelModel extends AbstractModel {


    public void createModel() {
        String styleStr = "";

        if(isMand()){
            styleStr = "color: red;";
        }

        if(StringUtils.isNotEmpty(getStyle())){
            styleStr = styleStr + getStyle();
        }

        if(StringUtils.isNotEmpty(getClassName())){
            appendAttribute("class", getClassName());
        }

        if(StringUtils.isNotEmpty(styleStr)){
            appendAttribute("style", styleStr);
        }

        buffer.append("<label").append(getAttribute()).append(">").append(getTitle()).append("</label>").append(LF);
    }

}
