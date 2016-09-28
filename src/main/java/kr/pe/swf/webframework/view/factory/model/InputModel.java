package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.StringUtils;

/**
 * Created by seongdonghun on 2016. 9. 28..
 */
public class InputModel extends AbstractModel {

    public void createModel() {
        appendAttribute("id", getId());
        if(StringUtils.isNotEmpty(getName())){
            appendAttribute("name", getName());
        }

        if(getLength() > 0){
            appendAttribute("length", String.valueOf(getLength()));
        }

        if(StringUtils.isNotEmpty(getValue())){
            appendAttribute("value", getValue());
        }
    }
}
