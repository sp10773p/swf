package kr.pe.swf.webframework.view.factory;

import kr.pe.swf.webframework.util.HtmlUtil;
import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.entry.CompEntry;
import kr.pe.swf.webframework.view.entry.DetailEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public abstract class DetailBuilder {
    protected List<DetailEntry> detailEntries = null;
    protected int detailColSize = 3; //default column count

    private static Logger LOGGER = LoggerFactory.getLogger(DetailBuilder.class);
    private Map params = new HashMap();

    public static DetailBuilder getDetailFactory(String classname, List<DetailEntry> detailEntries){
        DetailBuilder factory = null;
        try{
            Class cls = Class.forName(classname);
            Constructor constructor = cls.getConstructor(new Class[]{Class.forName("java.util.List")});
            factory = (DetailBuilder)constructor.newInstance(new Object[]{detailEntries});

        }catch (ClassNotFoundException e){
            LOGGER.error("클래스 {} 이 발견되지 않습니다", classname);
        }catch (Exception e){
            e.printStackTrace();
        }

        return factory;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public DetailBuilder(List<DetailEntry> detailEntries){
        this.detailEntries = detailEntries;
    }

    public void setDetailColSize(int detailColSize){
        this.detailColSize = detailColSize;
    }

    public Map<String , String> drawDetail(Map data) throws Exception {
        Map<String , String> codeMap = new HashMap<String, String>();

        if(this.detailEntries == null){
            throw new NullPointerException("DetailEntry is null");
        }

        StringBuffer buffer       = new StringBuffer();
        StringBuffer hiddenBuffer = new StringBuffer();

        int colIdx = 0;
        int preRowspan = 0;

        DetailRow detailRow = this.createRow();
        for(int i=0; i < this.detailEntries.size() || colIdx > 0; i++) {
            DetailEntry detailEntry = null;
            if(i < this.detailEntries.size()){
                detailEntry = this.detailEntries.get(i);

            }else{
                detailEntry = new DetailEntry();
                detailEntry.setColspan(String.valueOf((this.detailColSize*2) - colIdx));
            }

            if("hidden".equals(detailEntry.getType())){
                String hiddenId = StringUtils.trimStr(detailEntry.getId());

                if(this.params.containsKey(hiddenId)){
                    detailEntry.setValue((String)this.params.get(hiddenId));
                }

                hiddenBuffer.append(HtmlUtil.createHidden(detailEntry.getId(), detailEntry.getValue())).append("\r\n");
                continue;
            }

            if (colIdx == 0) {
                detailRow.makeRow();
                if(preRowspan > 0){
                    colIdx++;
                    preRowspan--;
                }
            }

            DetailCol detailCol = this.createCol(detailEntry);
            detailCol.setColSize(this.detailColSize);
            detailCol.bindValue(data);

            detailRow.appendCol(detailCol);

            preRowspan += (StringUtils.isEmpty(detailEntry.getRowspan()) ? 0 : Integer.parseInt(detailEntry.getRowspan()) - 1);

            colIdx += (StringUtils.isEmpty(detailEntry.getColspan()) ? 1 : Integer.parseInt(detailEntry.getColspan()));

            if(colIdx == (this.detailColSize*2)){
                detailRow.closeRow();
                colIdx = 0;
            }

            // bindComponent Script 생성
            for(CompEntry compEntry : detailEntry.getCompEntries()){
                CompEntry copyEntry = (CompEntry) compEntry.clone();
                String id = copyEntry.getId();
                if(data!= null && data.containsKey(id)){
                    copyEntry.setValue(String.valueOf(data.get(id)));
                }

                buffer.append(bindComponent(copyEntry));
            }
        }

        StringBuffer tableBuffer = new StringBuffer();
        tableBuffer.append(HtmlUtil.openHtml("detail-table"));
        tableBuffer.append("<tbody>").append("\r\n");
        tableBuffer.append(detailRow.output());
        tableBuffer.append("</tbody>").append("\r\n");
        tableBuffer.append(HtmlUtil.closeHrml());

        codeMap.put("bindScript" , buffer.toString());
        codeMap.put("detail"     , tableBuffer.toString());
        codeMap.put("hidden"     , hiddenBuffer.toString());

        return codeMap;

    }

    public abstract String bindComponent(CompEntry compEntry) throws Exception;

    public abstract DetailRow createRow();
    public abstract DetailCol createCol(DetailEntry data);

}
