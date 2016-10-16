package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.StringUtils;

/**
 * Created by seongdonghun on 2016. 9. 28..
 */
public class InputModel extends AbstractModel {

    public void createModel() {
        appendAttribute("id"  , getId());
        appendAttribute("type", getType());

        if(StringUtils.isNotEmpty(getName())){
            appendAttribute("name", getName());
        }

        if(StringUtils.isNotEmpty(getType())){
            appendAttribute("type", getType());
        }

        if(StringUtils.isNotEmpty(getStyle())){
            appendAttribute("style", getStyle());
        }

        if(StringUtils.isNotEmpty(getClassName())){
            appendAttribute("class", getClassName());
        }

        if(StringUtils.isNotEmpty(getChecked())){
            appendAttribute("checked", getChecked());
        }

        if(getLength() > 0){
            appendAttribute("length", String.valueOf(getLength()));
        }

        if(StringUtils.isNotEmpty(getValue())){
            appendAttribute("value", getValue());
        }

        if(isMand()){
            appendAttribute("mandatory", getTitle());
        }

        buffer.append("<input ").append(getAttribute()).append("/>").append(LF);
    }
}
