package kr.pe.swf.webframework.view;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.entry.ViewEntry;
import kr.pe.swf.webframework.view.factory.ButtonsFactory;
import kr.pe.swf.webframework.view.factory.DetailBuilder;
import kr.pe.swf.webframework.view.factory.ListBuilder;
import kr.pe.swf.webframework.view.factory.SearchBuilder;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
public class ViewInfoFactory {
    static final Logger LOGGER = LoggerFactory.getLogger(ViewInfoFactory.class);

    // view id, view info
    private Map<String, ViewEntry> viewEntryMap = new HashMap<String, ViewEntry>();

    private Map<String, String> viewLoadManage = new HashMap<String, String>();

    private Map<String, String> viewVmLoadManage = new HashMap<String, String>();

    private String searchType;
    private String detailType;
    private String gridType;

    private SqlMapClient sqlMapClient;

    private VelocityEngine velocityEngine;

    private ViewInfoFactory() {}

    private ViewInfoFactory(SqlMapClient sqlMapClient, VelocityEngine velocityEngine){
        this.sqlMapClient = sqlMapClient;
        this.velocityEngine = velocityEngine;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public ViewEntry getViewEntry(String viewId) {
        return viewEntryMap.get(viewId);
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setViewEntryMap(String viewId, ViewEntry viewEntry) {
        viewEntryMap.put(viewId, viewEntry);
    }

    public void setViewLoadManage(String viewId, String fileName){
        String fileInfo = fileName+"@"+String.valueOf(new File(fileName).lastModified());
        viewLoadManage.put(viewId, fileInfo);
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
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
        return getViewEntry(viewId).getType();
    }


    public void mergeLayout(String viewId, Map map){
        String layout = getViewType(viewId) + ".vm";

        Template template = velocityEngine.getTemplate(layout, "UTF-8");

        // 첫 로드이거나 layout이 변경되었을때  Auto Reload
        //if(viewVmLoadManage.get(viewId) == null || template.requiresChecking()){
        //    LOGGER.info("### View Layout Reload : {}", viewId);

            StringWriter writer = new StringWriter();
            VelocityEngineUtils.mergeTemplate(velocityEngine, layout, "UTF-8", map, writer);

            System.out.println(writer.toString());

            viewVmLoadManage.put(viewId, writer.toString());
        //}
    }

    public String getViewVmManage(String viewId){
        return viewVmLoadManage.get(viewId);
    }

    public void setViewVmManage(String viewId, String vm){
        viewVmLoadManage.put(viewId, vm);
    }

    public boolean isExists(String viewId) {
        if(getViewEntry(viewId) == null){
            return false;

        }else{

            // 수정여부 확인
            if(!checkViewInfo(viewId)){

                String filename = getViewInfoFile(viewId);
                try {
                    viewVmLoadManage.remove(viewId);
                    removeViewInfo(viewId);

                    LOGGER.info("### ViewInfo Reload Start. - {}", viewId);
                    (new ViewLoader(this)).buildViewInfo(filename);

                } catch (Exception e) {
                    LOGGER.error("::: ViewInfo Reload Fail !!!");
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public Map<String, Object> getBindSourceCode(String viewId) throws Exception{
        Map<String, Object> bindSourceMap = new HashMap<String, Object>();

        //view Parameter
        Map viewParam = this.getViewEntry(viewId).getViewParam();

        String saveMode = null;
        if(!viewParam.isEmpty() && viewParam.containsKey("saveMod")){
            saveMode = (String)viewParam.get("saveMode");
        }

        StringBuffer bindScriptBuffer = new StringBuffer();

        //조회영역
        List<SearchsFactory> searchsFactories = getViewEntry(viewId).getSearchsFactory();
        for(SearchsFactory searchsFactory : searchsFactories){
            if(searchsFactory.getSearchHtml() == null){
                SearchBuilder searchBuilder = SearchBuilder.getSearchFactory(searchType, searchsFactory.getSearchs());

                searchBuilder.setSearchsFactory(searchsFactory);

                // view Parameter
                if(viewParam != null && !viewParam.isEmpty()){
                    searchBuilder.setParams(viewParam);
                }

                // 조회 함수 지정
                String targetObjId = ViewLoader.TARGET.valueOf(searchsFactory.getId()).getTarget();
                if(StringUtils.isNotEmpty(searchsFactory.getFunction())){
                    String function = searchsFactory.getFunction() + "('" + searchsFactory.getId() + "' , '" + targetObjId + "', '" + searchsFactory.getqKey() + "')";
                    searchsFactory.setFunction(function);

                }else{
                    searchsFactory.setFunction("gfn_gridSelectList('" + searchsFactory.getId() + "' , '" + targetObjId + "', '" + searchsFactory.getqKey() + "')");
                }

                // set column size
                searchBuilder.setSearchColSize(searchsFactory.getColSize());

                // html code 생성
                Map<String, String> codeMap = searchBuilder.drawSearch();

                searchsFactory.appendSearchHtml(codeMap.get("search"));

                // Search Area Component 초기화 Script
                searchsFactory.appendSearchScript(codeMap.get("bindScript"));

            }

            bindScriptBuffer.append(searchsFactory.getSearchScript());

            bindSourceMap.put(searchsFactory.getId() + "_SELECT_FN", searchsFactory.getFunction());
            bindSourceMap.put(searchsFactory.getId() + "_FORM"     , searchsFactory.getId());
            bindSourceMap.put(searchsFactory.getId()               , searchsFactory.getSearchHtml());
        }

        //상세 영역
        StringBuffer detailHtmlBuffer = new StringBuffer(); //상세영역 html 코드
        StringBuffer hiddenHtmlBuffer = new StringBuffer(); //상세영역 hidden html 코드
        List<DetailsFactroy> detailsFactries = getViewEntry(viewId).getDetailsFactory();

        Map data = null;
        for(DetailsFactroy detailsFactroy : detailsFactries){
            DetailBuilder detailBuilder = DetailBuilder.getDetailFactory(detailType, detailsFactroy.getDetails());

            // view Parameter
            if(viewParam != null && !viewParam.isEmpty()){
                detailBuilder.setParams(viewParam);
            }

            // set column size
            detailBuilder.setDetailColSize(detailsFactroy.getColSize());

            // 수정모드일때 조회쿼리 실행
            if(saveMode != null && "U".equals(saveMode)){
                String selectQKey = detailsFactroy.getqKey();
                if(StringUtils.isNotEmpty(selectQKey)){
                    data = (Map)this.sqlMapClient.queryForObject(selectQKey, viewParam);

                }
            }else{
                data = null;
            }

            // html code 생성
            Map<String, String> codeMap = detailBuilder.drawDetail(data);

            detailsFactroy.clearDetailHtml();
            detailsFactroy.appendDetailHtml(codeMap.get("detail")); // Detail Area HTML Code

            // Detail Area Component 초기화 Script
            detailsFactroy.clearDetailScript();

            // hidden 필드
            hiddenHtmlBuffer.append(codeMap.get("hidden"));

            bindScriptBuffer.append(detailsFactroy.getDetailsScript());

            // title이 있을때만 title을 표시
            if(StringUtils.isNotEmpty(detailsFactroy.getTitle())){
                detailHtmlBuffer.append("<h4>").append(detailsFactroy.getTitle()).append("</h4>\r\n");
            }

            detailHtmlBuffer.append(detailsFactroy.getDetailsHtml());
        }

        detailHtmlBuffer.append(hiddenHtmlBuffer.toString());
        bindSourceMap.put("DETAIL_HTML", detailHtmlBuffer.toString()); // Detail Area HTML Code

        //bindComponent Script
        bindSourceMap.put("bindScript"    , bindScriptBuffer.toString());
        bindSourceMap.put("customerScript", getViewEntry(viewId).getScript());

        // 리스트 영역
        StringBuffer bindGridScriptBuffer = new StringBuffer();
        List<ListsFactory> listsFactories = getViewEntry(viewId).getListsFactory();
        for(int i=0; i < listsFactories.size(); i++){
            ListsFactory listsFactory = listsFactories.get(i);
            if(listsFactory.getListsScript() == null){
                ListBuilder listBuilder = new ListBuilder(gridType, listsFactory, this.velocityEngine);

                String targetSelectQkey = null;
                String targetUrl        = null;

                if(listsFactories.size() > (i+1)){
                    targetSelectQkey = listsFactories.get(i+1).getqKey();
                    targetUrl        = listsFactories.get(i+1).getUrl();
                }

                Map param = this.getViewEntry(viewId).getViewParam();
                bindGridScriptBuffer.append(listBuilder.createBindGrid(listsFactories.size(), i, targetSelectQkey, targetUrl, listsFactory.getLayout(), param));

            }
        }

        bindSourceMap.put("bindGridScript", bindGridScriptBuffer.toString());

        // 버튼 영역
        List<Map<String ,String>> btnObjectList = new ArrayList<Map<String, String>>();
        List<ButtonsFactory> buttonsFactories = getViewEntry(viewId).getButtonsFactory();
        for(int i=0; i < buttonsFactories.size(); i++) {
            ButtonsFactory buttonsFactory = buttonsFactories.get(i);
            Map<String, String> btnObject = new HashMap<String, String>();

            btnObject.put("BUTTONS_FN"  , buttonsFactory.getFunction() + "(this)");
            btnObject.put("BUTTONS_ID"  , buttonsFactory.getId());
            btnObject.put("BUTTONS_NAME", buttonsFactory.getBtnName());

            btnObjectList.add(btnObject);
        }

        bindSourceMap.put("BUTTONS_LIST", btnObjectList);


        return bindSourceMap;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getGridType() {
        return gridType;
    }

    public void setGridType(String gridType) {
        this.gridType = gridType;
    }
}