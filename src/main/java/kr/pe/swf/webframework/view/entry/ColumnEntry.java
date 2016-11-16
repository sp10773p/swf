package kr.pe.swf.webframework.view.entry;

/**
 * User : sdh
 * Date : 2016-10-04
 * Project Name : Seong`s Web Framework Beta
 * Description  :
 * History : Created on 2016-10-04
 */
public class ColumnEntry {
    private String label;
    private String title;
    private String name;
    private String index;
    private String align;

    private String classes;
    private String datefmt; //"Y-m-d"

    private String defval;
    private String edittype; //text,textarea,select,checkbox,password,button,image,file
    private String sorttype; //int,integer,float,number,currency,date,text,function

    private int width;

    private boolean sortable = true;
    private boolean editable = false;
    private boolean hidden = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getDatefmt() {
        return datefmt;
    }

    public void setDatefmt(String datefmt) {
        this.datefmt = datefmt;
    }

    public String getDefval() {
        return defval;
    }

    public void setDefval(String defval) {
        this.defval = defval;
    }

    public String getEdittype() {
        return edittype;
    }

    public void setEdittype(String edittype) {
        this.edittype = edittype;
    }

    public String getSorttype() {
        return sorttype;
    }

    public void setSorttype(String sorttype) {
        this.sorttype = sorttype;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "ColumnEntry{" +
                "label='" + label + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", index='" + index + '\'' +
                ", align='" + align + '\'' +
                ", classes='" + classes + '\'' +
                ", datefmt='" + datefmt + '\'' +
                ", defval='" + defval + '\'' +
                ", edittype='" + edittype + '\'' +
                ", sorttype='" + sorttype + '\'' +
                ", width=" + width +
                ", sortable=" + sortable +
                ", editable=" + editable +
                ", hidden=" + hidden +
                '}';
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
