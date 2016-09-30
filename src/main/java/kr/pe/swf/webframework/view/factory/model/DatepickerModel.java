package kr.pe.swf.webframework.view.factory.model;

import kr.pe.swf.webframework.util.StringUtils;
import org.omg.CORBA.ORB;

/**
 * Created by seongdonghun on 2016. 9. 30..
 */
public class DatepickerModel extends AbstractModel {

    @Override
    public void createModel() {
        InputModel inputModel = new InputModel();

        String type = getType();
        setType("text");
        setStyle(dateStyle + StringUtils.blankStr(getStyle()));

        if(type.startsWith("due")){
            String orgId = getId();

            // from date
            setId(PREFIX_FROM + orgId);
            inputModel.initAttribute(getInitAttributeMap());

            buffer.append(inputModel.draw()).append(" ~ ");

            // to date
            inputModel = new InputModel();

            setId(PREFIX_TO + orgId);
            inputModel.initAttribute(getInitAttributeMap());

            buffer.append(inputModel.draw());

        }else{
            inputModel.initAttribute(getInitAttributeMap());

            buffer.append(inputModel.draw());
        }
    }
}
