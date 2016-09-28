package kr.pe.swf.webframework.view;

import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.entry.ViewEntry;
import kr.pe.swf.webframework.view.factory.SearchBuilder;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
public class ViewInfoFactory {
    private static  ViewInfoFactory viewInfoFactory = new ViewInfoFactory();

    static final Logger LOGGER = LoggerFactory.getLogger(ViewInfoFactory.class);

    // view id, view info
    private Map<String, ViewEntry> viewEntryMap = new HashMap<String, ViewEntry>();

    private Map<String, String> viewLoadManage = new HashMap<String, String>();

    private Map<String, String> viewVmLoadManage = new HashMap<String, String>();

    private String layout;

    private ViewInfoFactory() {}

    public static ViewInfoFactory getInstance(){
        return viewInfoFactory;
    }

    public ViewEntry getViewEntry(String viewId) {
        return viewEntryMap.get(viewId);
    }

    public void setViewEntryMap(String viewId, ViewEntry viewEntry) {
        viewEntryMap.put(viewId, viewEntry);
    }

    public void setViewLoadManage(String viewId, String fileName){
        String fileInfo = fileName+"@"+String.valueOf(new File(fileName).lastModified());
        viewLoadManage.put(viewId, fileInfo);
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    boolean checkViewInfo(String viewId){
        String[] fileManager = getFileManager(viewId);
        String fileName = fileManager[0];
        long saveTime = Long.valueOf(fileManager[1]);

        if(saveTime < new File(fileName).lastModified()){
            return false;

        }

        return true;
    }

    String getViewInfoFile(String viewId){
        return getFileManager(viewId)[0];
    }

    private String[] getFileManager(String viewId){
        return viewLoadManage.get(viewId).split("@");
    }

    void removeViewInfo(String viewId){
        viewEntryMap.remove(viewId);
        viewLoadManage.remove(viewId);
    }

    public String getTitle(String viewId) {
        return getViewEntry(viewId).getTitle();
    }

    public String getViewId(String viewId) {
        return getViewEntry(viewId).getId();
    }

    public String getViewType(String viewId) {
        return viewInfoFactory.getViewEntry(viewId).getType();
    }


    public void mergeLayout(String viewId, VelocityEngine velocityEngine, Map map){
        String layout = getViewType(viewId) + ".vm";

        Template template = velocityEngine.getTemplate(layout, "UTF-8");

        // 첫 로드이거나 layout이 변경되었을때  Auto Reload
        if(viewVmLoadManage.get(viewId) == null || template.requiresChecking()){
            LOGGER.info("### View Layout Reload : {}", viewId);

            StringWriter writer = new StringWriter();
            VelocityEngineUtils.mergeTemplate(velocityEngine, layout, "UTF-8", map, writer);

            viewVmLoadManage.put(viewId, writer.toString());
        }
    }

    public String getViewVmManage(String viewId){
        return viewVmLoadManage.get(viewId);
    }

    public void setViewVmManage(String viewId, String vm){
        viewVmLoadManage.put(viewId, vm);
    }

    public boolean isExists(String viewId) {
        if(viewInfoFactory.getViewEntry(viewId) == null){
            return false;

        }else{

            // 수정여부 확인
            if(!viewInfoFactory.checkViewInfo(viewId)){

                String filename = viewInfoFactory.getViewInfoFile(viewId);
                try {
                    viewVmLoadManage.remove(viewId);
                    viewInfoFactory.removeViewInfo(viewId);

                    LOGGER.info("### ViewInfo Reload Start. - {}", viewId);
                    (new ViewLoader()).buildViewInfo(filename);

                } catch (Exception e) {
                    LOGGER.error("::: ViewInfo Reload Fail !!!");
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public Map<String, String> getSearchInfoHtml(String viewId) throws Exception{
        Map<String, String> htmlMap = new HashMap<String, String>();

        SearchsFactory searchsFactory = getViewEntry(viewId).getSearchsFactory();

        for(String searchsId : searchsFactory.getSearchsIds()){
            if(searchsFactory.getSearchHtml(searchsId) == null){
                SearchBuilder searchBuilder = SearchBuilder.getSearchFactory(layout, searchsFactory.getSearch(searchsId));

                // set column size
                searchBuilder.setSearchColSize(searchsFactory.getColSize());

                // html code 생성
                Map<String, String> codeMap = searchBuilder.drawSearch();

                searchsFactory.appendScript(codeMap.get("bindScript"));

                searchsFactory.setSearchHtml(searchsId, codeMap.get("search"));

                // 조회 함수 지정
                String targetObjId = ViewLoader.TARGET.valueOf(searchsId).getTarget();
                if(StringUtils.isNotEmpty(searchsFactory.getFunction())){
                    String function = searchsFactory.getFunction() + "('" + searchsId + "' , '" + targetObjId + "', '" + searchsFactory.getqKey() + "')";
                    searchsFactory.setFunction(function);

                }else{
                    searchsFactory.setFunction("gfn_gridSelectList('" + searchsId + "' , '" + targetObjId + "', '" + searchsFactory.getqKey() + "')");
                }
            }

            htmlMap.put("selectFn", searchsFactory.getFunction());
            htmlMap.put(searchsId + "_FORM", searchsId); // form id
            htmlMap.put(searchsId, searchsFactory.getSearchHtml(searchsId));
        }

        //bindComponent Script
        htmlMap.put("bindScript", searchsFactory.getSearchScript());
        htmlMap.put("script"    , getViewEntry(viewId).getScript());


        return htmlMap;

    }


}