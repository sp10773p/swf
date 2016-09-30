package kr.pe.swf.webframework.view.factory.model;

/**
 * Created by seongdonghun on 2016. 9. 30..
 */
public class AutocompleteModel extends AbstractModel {
    @Override
    public void createModel() {
        String orgId = getId();

        InputModel inputModel = new InputModel();

        setId(orgId + PREFIX_VIEW_ID);
        inputModel.initAttribute(getInitAttributeMap());
        inputModel.setType("text");

        buffer.append(inputModel.draw());

        InputModel hiddenModel = new InputModel();

        setId(orgId);
        hiddenModel.initAttribute(getInitAttributeMap());
        hiddenModel.setType("hidden");

        buffer.append(hiddenModel.draw());
    }
}
