package kr.pe.swf.webframework.view;

/**
 * User : sdh
 * Date : 2016-10-10
 * Project Name : Seong`s Web Framework Beta
 * Description  :
 * History : Created on 2016-10-10
 */
public class ButtonsFactory {
    private String function;
    private String btnName;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

}
