package kr.pe.swf.webframework.view;

import kr.pe.swf.webframework.util.DOMUtil;
import kr.pe.swf.webframework.util.FileUtil;
import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.entry.EventEntry;
import kr.pe.swf.webframework.view.entry.SearchEntry;
import kr.pe.swf.webframework.view.entry.ViewEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 21..
 */
public class ViewLoader {
    static final Logger LOGGER = LoggerFactory.getLogger(ViewLoader.class);

    private final String SEARCHS_ID = "SEARCHS";
    private String viewPath;

    private ViewInfoFactory viewInfoFactory;

    public void initViewLoader(String viewPath, String layout){
        initViewLoader(null, viewPath, layout);
    }

    public void initViewLoader(ViewInfoFactory viewInfoFactory, String viewPath, String layout) {
        this.viewPath = viewPath;
        this.viewInfoFactory = viewInfoFactory;
        this.viewInfoFactory.setLayout(layout);
    }

    public void load() throws RuntimeException {
        LOGGER.info("### Seong`s Webframework View load Start...");

        if(viewPath.startsWith("classpath:")){
            viewPath = Thread.currentThread().getContextClassLoader().getResource(viewPath.replace("classpath:", "")).getPath();
        }

        File f = new File(viewPath);
        if(!f.exists()){
            throw new RuntimeException("::: Seong`s Webframework View Path를 확인하세요.");
        }
        String[] filenames = FileUtil.filenameFilesInDirectory(f);

        for(String filename : filenames){
            try{
                buildViewInfo(filename);

            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("::: View 파일 파싱중 에러가 발생 하였습니다. - " + e.getMessage());
            }
        }
    }

    void buildViewInfo(String filename) throws Exception {
        Document viewDoc = DOMUtil.parse(filename);
        Element root = viewDoc.getDocumentElement();

        String viewId    = DOMUtil.getAttribute(root, "id");
        String viewType  = DOMUtil.getAttribute(root, "type");
        String viewTitle = DOMUtil.getAttribute(root, "title");

        // 화면 정보
        LOGGER.info("### View Building - {}", viewId);
        ViewEntry viewEntry = new ViewEntry(viewId, viewType, viewTitle);

        //조회 정보
        viewEntry.setSearchsFactory(buildSearchInfo(root));

        //SCRIPT
        String script = DOMUtil.getElementTextByPath(root, "script");
        viewEntry.setScript(script);

        viewInfoFactory.setViewEntryMap(viewId, viewEntry);
        viewInfoFactory.setViewLoadManage(viewId, filename);
    }

    private SearchsFactory buildSearchInfo(Element root) throws Exception {
        SearchsFactory searchsFactory = new SearchsFactory();

        List<Element> searchsList = DOMUtil.getChildrenByPath(root, "searchs");

        int idx = 0;
        for(Element searchsEle : searchsList){

            // searchs id
            String searchId = null;
            if(StringUtils.isEmpty(searchId)){
                if(searchsList.size() == 1){
                    searchId = SEARCHS_ID;

                }else{
                    searchId = SEARCHS_ID + (++idx);
                }
            }

            //[필수 속성]select Query key
            String qKeyAtt = DOMUtil.getAttribute(searchsEle, "qKey");
            if(StringUtils.isEmpty(qKeyAtt)){
                String viewId = DOMUtil.getAttribute(root, "id");
                throw new Exception("::: <searchs> Tag의 qKey는 필수 속성입니다. [" + viewId +"]");
            }
            searchsFactory.setqKey(qKeyAtt);

            // search column size
            String colSizeAtt = DOMUtil.getAttribute(searchsEle, "colSize");
            if(StringUtils.isNotEmpty(colSizeAtt)){
                searchsFactory.setColSize(Integer.parseInt(colSizeAtt));
            }

            // function
            String functionAtt = DOMUtil.getAttribute(searchsEle, "function");
            if(StringUtils.isNotEmpty(functionAtt)){
                searchsFactory.setFunction(functionAtt);
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

            LOGGER.info("### Append Search Infomation - {}", searchId);

            searchsFactory.addSearch(searchId, searchEntryList);
        }

        return searchsFactory;
    }

    private SearchEntry getSearchEntry(Element searchEle) {
        SearchEntry searchEntry = new SearchEntry();

        //속성 저장
        String id     = DOMUtil.getAttribute(searchEle, "id");
        String type   = DOMUtil.getAttribute(searchEle, "type");
        String title  = DOMUtil.getAttribute(searchEle, "title");
        String isMand = DOMUtil.getAttribute(searchEle, "isMand");
        String isMult = DOMUtil.getAttribute(searchEle, "isMult");
        String length = DOMUtil.getAttribute(searchEle, "length");
        String from   = DOMUtil.getAttribute(searchEle, "from");
        String to     = DOMUtil.getAttribute(searchEle, "to");
        String def    = DOMUtil.getAttribute(searchEle, "default");
        String style  = DOMUtil.getElementTextByPath(searchEle, "style");

        searchEntry.setId(id);
        searchEntry.setType(type);
        searchEntry.setMand(isMand);
        searchEntry.setMand(isMult);
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
        if("select".equals(type) || "checkbox".equals(type) || "radio".equals(type) || "autocomplete".equals(type)){
            List<Element> itemList = DOMUtil.getChildrenByPath(searchEle, "list/item");
            String selectQKey = DOMUtil.getElementTextByPath(searchEle, "selectQKey");

            if(itemList == null && StringUtils.isEmpty(selectQKey)){
                LOGGER.error("::: type 이 select/checkbox/radio/autocomplete 일때 '<list>' 나 '<selectQKey>' Tag정의가 있어야 합니다.");
                return null;
            }

            if(StringUtils.isNotEmpty(selectQKey)){
                selectQKey = selectQKey.trim();
                searchEntry.setSelectQKey(selectQKey);

                try {
                    List<Map<String, String>> list = this.viewInfoFactory.getSqlMapClient().queryForList(selectQKey, null);
                    searchEntry.setList(list);
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }else{
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                for(Element ele : itemList){
                    String code     = DOMUtil.getAttribute(ele, "code");
                    String selected = DOMUtil.getAttribute(ele, "selected");
                    String checked  = DOMUtil.getAttribute(ele, "checked");
                    String label    = DOMUtil.getElementText(ele);

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("code"    , code);
                    map.put("label"   , label);
                    map.put("selected", selected);
                    map.put("checked" , checked);

                    list.add(map);
                }

                searchEntry.setList(list);
            }
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
            LOGGER.error("::: <seach> Tag의 필수 속성이 존재 하지 않습니다. [{}]", attStr.substring(0, attStr.length() - 1));
        }

        return bool;
    }

    // 필수 속성
    enum SEARCH{
        id,type
    }

    enum TARGET {
        //DEFAULT_LIST 일때
        SEARCHS("SEARCHS_GRID"),

        // DUE_LIST ~ 일때
        SEARCHS1("SEARCHS_GRID1"),
        SEARCHS2("SEARCHS_GRID2"),
        SEARCHS3("SEARCHS_GRID3"),
        SEARCHS4("SEARCHS_GRID4"),
        SEARCHS5("SEARCHS_GRID5");

        private String key;
        private TARGET(String key){
            this.key = key;
        }

        public String getTarget(){
            return this.key;
        }

    }

}
