package kr.pe.sdh.common.view;

import kr.pe.sdh.common.util.DOMUtil;
import kr.pe.sdh.common.util.FileUtil;
import kr.pe.sdh.common.util.StringUtils;
import kr.pe.sdh.common.view.factory.SearchFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by seongdonghun on 2016. 9. 21..
 */
public class SwfViewBuilder {
    private String viewPath;
    private String layout;
    private ViewInfoManager viewInfoManager = new ViewInfoManager();

    private Map<String, String> viewVm = new HashMap<String, String>();

    public SwfViewBuilder(String viewPath, String layout) {
        this.viewPath = viewPath;
        this.layout   = layout;

        build();
    }

    private void build() {
        System.out.println("### SWeb Framework View Build Start...");
        File f = new File(viewPath);

        String[] filenames = FileUtil.filenameFilesInDirectory(f);

        for(String filename : filenames){
            try{
                buildViewInfo(filename);

            }catch (Exception e){
                e.printStackTrace();
                System.err.println("::: View 파일 파싱중 에러가 발생 하였습니다." + e.getMessage());
            }
        }
    }

    private void buildViewInfo(String filename) throws Exception {
        Document viewDoc = DOMUtil.parse(filename);
        Element root = viewDoc.getDocumentElement();

        String viewId    = DOMUtil.getAttribute(root, "id");
        String viewType  = DOMUtil.getAttribute(root, "type");
        String viewTitle = DOMUtil.getAttribute(root, "title");

        // 화면 정보
        System.out.println("### View Building - " + viewId);
        ViewEntry viewEntry = new ViewEntry(viewId, viewType, viewTitle);

        //조회 정보
        viewEntry.setSearchsInfo(buildSearchInfo(root));

        viewInfoManager.setViewEntryMap(viewId, viewEntry);
        viewInfoManager.setViewLoadManage(viewId, filename);
    }

    private SearchsInfo buildSearchInfo(Element root) {
        SearchsInfo searchsInfo = new SearchsInfo();

        List<Element> searchsList = DOMUtil.getChildrenByPath(root, "searchs");

        int idx = 0;
        for(Element searchsEle : searchsList){

            // searchs id
            String searchId = null;
            if(StringUtils.isEmpty(searchId)){
                if(searchsList.size() == 1){
                    searchId = "searchs";

                }else{
                    searchId = "searchs" + (++idx);
                }
            }

            // search column size
            String colSizeAtt = DOMUtil.getAttribute(searchsEle, "colSize");
            if(StringUtils.isNotEmpty(colSizeAtt)){
                searchsInfo.setColSize(Integer.parseInt(colSizeAtt));
            }

            List<Element> searchList = DOMUtil.getChildrenByPath(searchsEle, "search");

            // 조회조건 영역 파싱
            List<SearchEntry> searchEntryList = new ArrayList<SearchEntry>();
            for(int i=0; i<searchList.size(); i++){
                Element searchEle = searchList.get(i);

                // 필수값 체크
                if (!validMandantary(searchEle)) continue;

                SearchEntry searchEntry = getSearchEntry(searchEle);
                if (searchEntry == null) continue;

                searchEntryList.add(searchEntry);

            }

            System.out.println("### Append Search Infomation - " + searchId);

            searchsInfo.addSearch(searchId, searchEntryList);
        }

        return searchsInfo;
    }

    private SearchEntry getSearchEntry(Element searchEle) {
        SearchEntry searchEntry = new SearchEntry();

        //속성 저장
        String id     = DOMUtil.getAttribute(searchEle, "id");
        String type   = DOMUtil.getAttribute(searchEle, "type");
        String title  = DOMUtil.getAttribute(searchEle, "title");
        String isMand = DOMUtil.getAttribute(searchEle, "isMand");
        String length = DOMUtil.getAttribute(searchEle, "length");
        String from   = DOMUtil.getAttribute(searchEle, "from");
        String to     = DOMUtil.getAttribute(searchEle, "to");
        String def    = DOMUtil.getAttribute(searchEle, "default");
        String style  = DOMUtil.getElementTextByPath(searchEle, "style");

        searchEntry.setId(id);
        searchEntry.setType(type);
        searchEntry.setMand(isMand);
        searchEntry.setTitle(title);
        searchEntry.setLength(length);
        searchEntry.setStyle(style);
        searchEntry.setFromDate(from);
        searchEntry.setToDate(to);
        searchEntry.setDefaultDate(def);

        // event 저장
        List<Element> eventList = DOMUtil.getChildrenByPath(searchEle, "event");
        for(Element eventEle : eventList){
            String name   = DOMUtil.getAttribute(eventEle, "name");
            String fnName = DOMUtil.getElementText(eventEle, "fnName");

            EventEntry eventEntry = new EventEntry();
            eventEntry.setName(name);
            eventEntry.setFnName(fnName);

            searchEntry.addEventEntry(eventEntry);
        }

        // type에 따른 유형 처리
        if("select".equals(type)){
            String selectStr  = DOMUtil.getElementTextByPath(searchEle, "select");
            String selectQKey = DOMUtil.getElementTextByPath(searchEle, "selectQKey");

            if(selectStr == null && selectQKey == null){
                System.err.println("::: type 이 select 일때 '<select>' 나 '<selectQKey>' Tag정의가 있어야 합니다.");
                return null;
            }

            searchEntry.setSelect(selectStr);
            searchEntry.setSelectQKey(selectQKey); // TODO selectQKey 쿼리 실행후 select의 배열처리해서 set 할것

        }else if("checkbox".equals(type)){
            String checkStr  = DOMUtil.getElementTextByPath(searchEle, "check");
            String checkQKey = DOMUtil.getElementTextByPath(searchEle, "checkQKey");

            if(checkStr == null && checkQKey == null){
                System.err.println("::: type 이 checkbox 일때 '<check>' 나 '<checkQKey>' Tag정의가 있어야 합니다.");
                return null;
            }

            searchEntry.setCheck(checkStr);
            searchEntry.setCheckQKey(checkQKey);

        }else if("radio".equals(type)){
            String radioStr  = DOMUtil.getElementTextByPath(searchEle, "radio");
            String radioQKey = DOMUtil.getElementTextByPath(searchEle, "radioQKey");

            if(radioStr == null && radioQKey == null){
                System.err.println("::: type 이 radio 일때 '<radio>' 나 '<radioQKey>' Tag정의가 있어야 합니다.");
                return null;
            }

            searchEntry.setRadio(radioStr);
            searchEntry.setRadioQKey(radioQKey);

        }

        return searchEntry;
    }

    private boolean validMandantary(Element searchEle) {
        //필수값 체크
        boolean bool = true;
        StringBuffer attStr = new StringBuffer();
        for(SEARCH search : SEARCH.values()){
            String att = DOMUtil.getAttribute(searchEle, search.toString());
            if(att == null || att.length() == 0){
                bool = false;
            }

            attStr.append(search.toString()+",");
        }

        if(!bool){
            System.err.print("::: <seach> Tag의 필수 속성이 존재 하지 않습니다. [" + attStr.substring(0, attStr.length() - 1) + "]");
        }

        return bool;
    }

    public Map<String, String> getSearchInfoHtml(String viewId) throws Exception{
        Map<String, String> htmlMap = new HashMap<String, String>();

        SearchsInfo searchsInfo = viewInfoManager.getViewEntry(viewId).getSearchsInfo();

        for(String searchsId : searchsInfo.getSearchsIds()){
            if(searchsInfo.getSearchHtml(searchsId) == null){
                SearchFactory searchFactory = SearchFactory.getSearchFactory(layout, searchsInfo.getSearch(searchsId));

                // set column size
                searchFactory.setSearchColSize(searchsInfo.getColSize());

                // html code 생성
                Map<String, String> codeMap = searchFactory.drawSearch();

                searchsInfo.appendScript(codeMap.get("bindScript"));

                searchsInfo.setSearchHtml(searchsId, codeMap.get("search"));
            }

            htmlMap.put(searchsId, searchsInfo.getSearchHtml(searchsId));
        }

        //bindComponent Script
        htmlMap.put("bindScript", searchsInfo.getSearchScript());

        return htmlMap;

    }

    public void mergeLayout(String viewId, VelocityEngine velocityEngine, Map map){
        String layout = getViewType(viewId) + ".vm";

        Template template = velocityEngine.getTemplate(layout, "UTF-8");

        // 첫 로드이거나 layout이 변경되었을때  Auto Reload
        if(viewVm.get(viewId) == null || template.requiresChecking()){
            System.out.println("### View Layout Reload : " + viewId);

            StringWriter writer = new StringWriter();
            VelocityEngineUtils.mergeTemplate(velocityEngine, layout, "UTF-8", map, writer);

            viewVm.put(viewId, writer.toString());
        }
    }

    public String getViewVm(String viewId){
        return viewVm.get(viewId);
    }

    public void setViewVm(String viewId, String vm){
        viewVm.put(viewId, vm);
    }


    public String getTitle(String viewId) {
        return viewInfoManager.getViewEntry(viewId).getTitle();
    }

    public String getViewId(String viewId) {
        return viewInfoManager.getViewEntry(viewId).getId();
    }

    public String getViewType(String viewId) {
        return viewInfoManager.getViewEntry(viewId).getType();
    }

    public boolean isExists(String viewId) {
        if(viewInfoManager.getViewEntry(viewId) == null){
            return false;

        }else{

            // 수정여부 확인
            if(!viewInfoManager.checkViewInfo(viewId)){

                String filename = viewInfoManager.getViewInfoFile(viewId);
                try {
                    viewVm.remove(viewId);
                    viewInfoManager.removeViewInfo(viewId);

                    System.out.println("### ViewInfo Reload Start. - " + viewId);
                    buildViewInfo(filename);

                } catch (Exception e) {
                    System.err.println("::: ViewInfo Reload Fail !!!");
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}

// 필수 속성
enum SEARCH{
    id,type
}