package kr.pe.swf.webframework.view;

import kr.pe.swf.webframework.util.DOMUtil;
import kr.pe.swf.webframework.util.FileUtil;
import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.entry.*;
import kr.pe.swf.webframework.view.factory.ButtonsFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
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
    private final String DETAILS_ID = "DETAILS";
    private final String LISTS_ID   = "LISTS";

    private final String DEFAULT_LIST_ROWNUM  = "10";
    private final String DEFAULT_LIST_ROWLIST = "10,30,50";


    private String viewPath;

    private ViewInfoFactory viewInfoFactory;

    public ViewLoader(){}

    public ViewLoader(ViewInfoFactory viewInfoFactory){
        this.viewInfoFactory = viewInfoFactory;
    }


    public void initViewLoader(ViewInfoFactory viewInfoFactory, String viewPath, String searchType, String detailType, String gridType) {
        this.viewPath = viewPath;
        this.viewInfoFactory = viewInfoFactory;
        this.viewInfoFactory.setSearchType(searchType);
        this.viewInfoFactory.setDetailType(detailType);
        this.viewInfoFactory.setGridType(gridType);
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

        // 그리드 정보
        viewEntry.setListsFactory(buildListInfo(root, viewEntry.getSearchsFactory()));

        // 상세정보
        viewEntry.setDetailsFactory(buildDetailInfo(root));

        // 버튼 정보
        viewEntry.setButtonsFactory(buildButtonInfo(root));

        viewInfoFactory.setViewEntryMap(viewId, viewEntry);
        viewInfoFactory.setViewLoadManage(viewId, filename);
    }

    private List<ButtonsFactory> buildButtonInfo(Element root){
        List<ButtonsFactory> buttonsFactories = new ArrayList<ButtonsFactory>();

        List<Element> buttonList = DOMUtil.getChildrenByPath(root, "button");

        for(Element buttonEle : buttonList){
            ButtonsFactory buttonsFactory = new ButtonsFactory();

            // id
            String idAtt = DOMUtil.getAttribute(buttonEle, "id");
            buttonsFactory.setId(idAtt);

            // function
            String functionAtt = DOMUtil.getAttribute(buttonEle, "function");
            buttonsFactory.setFunction(functionAtt);

            // 버튼명
            String btnName = DOMUtil.getElementText(buttonEle);
            buttonsFactory.setBtnName(btnName);

            buttonsFactories.add(buttonsFactory);
        }

        return buttonsFactories;
    }

    private List<DetailsFactroy> buildDetailInfo(Element root) throws InvocationTargetException, IllegalAccessException, SQLException {
        List<DetailsFactroy> detailsFactories = new ArrayList<DetailsFactroy>();

        List<Element> detailsList = DOMUtil.getChildrenByPath(root, "details");

        int idx = 0;
        for(Element detailEle : detailsList) {
            DetailsFactroy detailsFactroy = new DetailsFactroy();

            // details id
            detailsFactroy.setId(DETAILS_ID + (++idx));

            // title
            String titleAtt = DOMUtil.getAttribute(detailEle, "title");
            if(StringUtils.isNotEmpty(titleAtt)){
                detailsFactroy.setTitle(titleAtt);
            }

            // 로드시 조회 쿼리키
            String qKeyAtt = DOMUtil.getAttribute(detailEle, "qKey");
            if(StringUtils.isNotEmpty(qKeyAtt)){
                detailsFactroy.setqKey(qKeyAtt);
            }

            // detail column size
            String colSizeAtt = DOMUtil.getAttribute(detailEle, "colSize");
            if(StringUtils.isNotEmpty(colSizeAtt)){
                detailsFactroy.setColSize(Integer.parseInt(colSizeAtt));
            }

            List<Element> detailChildList = DOMUtil.getChildren(detailEle);
            DetailEntry detailEntry = null;
            for(Element ele : detailChildList){
                detailEntry = new DetailEntry();

                if(ele.getTagName().equals("title")){
                    String title = ele.getTextContent();
                    String colspan = DOMUtil.getAttribute(ele, "colspan");
                    String rowspan = DOMUtil.getAttribute(ele, "rowspan");

                    detailEntry.setTitle(title);
                    detailEntry.setColspan(colspan);
                    detailEntry.setRowspan(rowspan);

                    detailsFactroy.addDetail(detailEntry);

                }else if(ele.getTagName().equals("hidden")){
                    String id    = DOMUtil.getAttribute(ele, "id");
                    String value = DOMUtil.getAttribute(ele, "value");

                    detailEntry.setId(id);
                    detailEntry.setValue(value);

                    detailsFactroy.addDetail(detailEntry);

                }else if(ele.getTagName().equals("detail")){
                    String colspan = DOMUtil.getAttribute(ele, "colspan");
                    String rowspan = DOMUtil.getAttribute(ele, "rowspan");

                    detailEntry.setColspan(colspan);
                    detailEntry.setRowspan(rowspan);

                    // 필수여부
                    boolean isMand = false;

                    List<Element> compList = DOMUtil.getChildren(ele, "comp");
                    for(Element compEle : compList){
                        CompEntry compEntry = new CompEntry();
                        Map map = getAttributeElementToMap(compEle);
                        BeanUtils.populate(compEntry, map);

                        if(!isMand){
                            isMand = compEntry.isMand();
                        }

                        // event 저장
                        List<EventEntry> eventEntryList = getEventElement(compEle);
                        compEntry.setEventEntries(eventEntryList);

                        //style 저장
                        compEntry.setStyle(DOMUtil.getElementTextByPath(compEle, "style"));

                        String type = StringUtils.trimStr(compEntry.getType());

                        // type에 따른 유형 처리
                        if("select".equals(type) || "checkbox".equals(type) || "radio".equals(type) || "autocomplete".equals(type)){
                            List list = getItemList(compEle);
                            if(list == null){
                                return null;
                            }
                            String selectQKey = DOMUtil.getElementTextByPath(compEle, "selectQKey");
                            if(StringUtils.isNotEmpty(selectQKey)){
                                compEntry.setSelectQKey(selectQKey.trim());
                            }

                            compEntry.setList(list);
                        }

                        detailEntry.addCompEntries(compEntry);
                    }

                    //comp 중에 필수인 comp가 있으면 이전 태그인 th에 mandatory 표시
                    if(isMand){
                        List<DetailEntry> tmpList = detailsFactroy.getDetails();
                        for(int i = (tmpList.size() - 1); i >= 0; i--){
                            DetailEntry tmpDetailEntry = tmpList.get(i);
                            if(StringUtils.isNotEmpty(tmpDetailEntry.getTitle())){
                                tmpDetailEntry.setMand(isMand);
                                break;
                            }
                        }
                    }

                    detailsFactroy.addDetail(detailEntry);

                }
            }

            detailsFactories.add(detailsFactroy);
            LOGGER.info("### Append Detail Infomation - {}", detailsFactroy.getId());
        }

        return detailsFactories;
    }
    private List<SearchsFactory> buildSearchInfo(Element root) throws Exception {
        List<SearchsFactory> searchsFactories = new ArrayList<SearchsFactory>();

        List<Element> searchsList = DOMUtil.getChildrenByPath(root, "searchs");

        int idx = 0;
        for(Element searchsEle : searchsList){
            SearchsFactory searchsFactory = new SearchsFactory();
            // searchs id
            searchsFactory.setId(SEARCHS_ID + (++idx));

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
            for(int i=0; i<searchList.size(); i++){
                Element searchEle = searchList.get(i);

                // 필수값 체크
                if (!validMandantary(searchEle)) continue;

                SearchEntry searchEntry = getSearchEntry(searchEle);
                if (searchEntry == null) continue;

                searchsFactory.addSearch(searchEntry);

            }

            searchsFactories.add(searchsFactory);
            LOGGER.info("### Append Search Infomation - {}", searchsFactory.getId());

        }

        return searchsFactories;
    }

    private List<ListsFactory> buildListInfo(Element root, List<SearchsFactory> searchsFactories) throws InvocationTargetException, IllegalAccessException {
        List<ListsFactory> retListsFactory = new ArrayList<ListsFactory>();

        List<Element> listsList = DOMUtil.getChildrenByPath(root, "lists");
        int idx = 0;
        for(Element listEle : listsList){
            ListsFactory listsFactory = new ListsFactory();

            // lists id
            listsFactory.setId(LISTS_ID + (++idx));

            String rowNumAtt = DOMUtil.getAttribute(listEle, "rowNum");
            if(StringUtils.isEmpty(rowNumAtt)){
                rowNumAtt = DEFAULT_LIST_ROWNUM;
            }
            listsFactory.setRowNum(rowNumAtt);

            String rowListAtt = DOMUtil.getAttribute(listEle, "rowList");
            if(StringUtils.isEmpty(rowListAtt)){
                rowNumAtt = DEFAULT_LIST_ROWLIST;
            }
            listsFactory.setRowList(rowListAtt);

            // 디폴트 소팅 컬럼
            String sortname = DOMUtil.getAttribute(listEle, "sortname");
            if(StringUtils.isEmpty(sortname)){
                sortname = "";
            }
            listsFactory.setSortname(sortname);

            // 디폴트 소팅 타입
            String sortorder = DOMUtil.getAttribute(listEle, "sortorder");
            if(StringUtils.isEmpty(sortorder)){
                sortorder = "";
            }
            listsFactory.setSortorder(sortorder);

            // 조회 쿼리
            String qKey = DOMUtil.getAttribute(listEle, "qKey");
            if(StringUtils.isEmpty(qKey)){
                // 쿼리키가 없을때 같은 차수의 조회영역  쿼리키를 셋팅한다. (조회버튼으로 조회시 대상인 리스트임)
                if(searchsFactories.size() >= idx){
                    SearchsFactory searchsFactory = searchsFactories.get(idx-1);
                    qKey = searchsFactory.getqKey();
                }
            }
            listsFactory.setqKey(qKey);

            //화면로드시 조회할지 여부
            String isFirstLoad = DOMUtil.getAttribute(listEle, "isFirstLoad");
            if(StringUtils.isNotEmpty(isFirstLoad)){
                listsFactory.setFirstLoad("Y".equals(isFirstLoad.toUpperCase()) ? true :false);
            }

            String layout = DOMUtil.getAttribute(listEle, "layout");
            if(StringUtils.isNotEmpty(layout)){
                listsFactory.setLayout(layout);
            }

            // 컬럼정보 셋팅
            String colModel = DOMUtil.getElementTextByPath(listEle, "colModel");
            listsFactory.setLists(makeColumEntryList(colModel));

            // 컬럼정보 문자열로 저장
            listsFactory.setColModel(colModel.replaceAll("&#44;", ",").replaceAll("&#59;", ";").replaceAll("&#39;", "'"));

            // event 저장
            List<Element> eventList = DOMUtil.getChildrenByPath(listEle, "event");
            for(Element eventEle : eventList){
                String name   = DOMUtil.getAttribute(eventEle, "name");
                String fnName = DOMUtil.getElementText(eventEle);

                EventEntry eventEntry = new EventEntry();
                eventEntry.setFnName(fnName);
                eventEntry.setFnName(name);

                listsFactory.addEventEntry(eventEntry);
            }

            retListsFactory.add(listsFactory);

        }

        return retListsFactory;
    }

    private SearchEntry getSearchEntry(Element searchEle) throws InvocationTargetException, IllegalAccessException, SQLException {
        SearchEntry searchEntry = new SearchEntry();

        //속성 저장
        BeanUtils.populate(searchEle, getAttributeElementToMap(searchEle));

        // event 저장
        searchEntry.setEventEntries(getEventElement(searchEle));

        // style 저장
        searchEntry.setStyle(DOMUtil.getElementTextByPath(searchEle, "style"));

        String type = StringUtils.trimStr(searchEntry.getType());

        // type에 따른 유형 처리
        if("select".equals(type) || "checkbox".equals(type) || "radio".equals(type) || "autocomplete".equals(type)){
            List list = getItemList(searchEle);
            if(list == null){
                return null;
            }

            String selectQKey = DOMUtil.getElementTextByPath(searchEle, "selectQKey");
            if(StringUtils.isNotEmpty(selectQKey)){
                searchEntry.setSelectQKey(selectQKey.trim());

            }

            searchEntry.setList(list);
        }

        return searchEntry;
    }

    private Map getAttributeElementToMap(Element element){
        String[] attArr = {"id", "type", "title", "isMand", "isMult", "length", "from", "to", "default", "value"};

        Map<String, Object> retMap = new HashMap();
        for(String arrId : attArr){
            String key = arrId;
            if("from".equals(arrId) || "to".equals(arrId) || "default".equals(arrId)){
                key += "Date";

            }else if("isMand".equals(arrId) || "isMult".equals(arrId)){
                key = arrId.replace("is", "").toLowerCase();
            }

            retMap.put(key, DOMUtil.getAttribute(element, arrId));
        }

        retMap.put("style", DOMUtil.getElementTextByPath(element, "style"));

        return retMap;
    }

    private List<EventEntry> getEventElement(Element element){
        List<EventEntry> eventEntries = new ArrayList<EventEntry>();
        List<Element> eventList = DOMUtil.getChildrenByPath(element, "event");
        for(Element eventEle : eventList){
            String name   = DOMUtil.getAttribute(eventEle, "name");
            String fnName = DOMUtil.getElementText(eventEle);

            EventEntry eventEntry = new EventEntry();
            eventEntry.setName(name);
            eventEntry.setFnName(fnName);

            eventEntries.add(eventEntry);
        }

        return eventEntries;
    }


    private List<Map<String, String>> getItemList(Element element) throws SQLException{
        String selectQKey = DOMUtil.getElementTextByPath(element, "selectQKey");
        List<Element> itemList = DOMUtil.getChildrenByPath(element, "list/item");

        if(itemList == null && StringUtils.isEmpty(selectQKey)){
            LOGGER.error("::: type 이 select/checkbox/radio/autocomplete 일때 '<list>' 나 '<selectQKey>' Tag정의가 있어야 합니다.");
            return null;
        }

        List<Map<String ,String>> list = new ArrayList<Map<String, String>>();
        if(StringUtils.isNotEmpty(selectQKey)){
            selectQKey = selectQKey.trim();

            list = this.viewInfoFactory.getSqlMapClient().queryForList(selectQKey, null);
        }else {
            for (Element ele : itemList) {
                String code = DOMUtil.getAttribute(ele, "code");
                String selected = DOMUtil.getAttribute(ele, "selected");
                String checked = DOMUtil.getAttribute(ele, "checked");
                String label = DOMUtil.getElementText(ele);

                Map<String, String> map = new HashMap<String, String>();
                map.put("code", code);
                map.put("label", label);
                map.put("selected", selected);
                map.put("checked", checked);

                list.add(map);
            }
        }

        return list;
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

    private List<ColumnEntry> makeColumEntryList(String s) throws InvocationTargetException, IllegalAccessException {
        List<ColumnEntry> columnEntries = new ArrayList<ColumnEntry>();
        StringBuffer colModel = new StringBuffer(s.trim().replaceAll("\\[", "").replaceAll("]", ""));

        while(colModel.indexOf("{") > -1){
            ColumnEntry columnEntry = new ColumnEntry();
            int colStartIdx = colModel.indexOf("{");
            int colEndIdx   = colModel.indexOf("}") + 1;

            String column = colModel.substring(colStartIdx, colEndIdx).toString();
            column = column.replaceAll("\\{", "").replaceAll("}", "");

            // &#44;(쉼표), &#59;(세미콜론), &#39;(작은따옴표)
            String[] arr = column.split(",");
            Map<String, Object> option = new HashMap();
            for(String op : arr){
                String key = op.split(":")[0].trim();
                String val = op.split(":")[1].trim().replaceAll("'", "");
                val = val.replaceAll("&#44;", ",").replaceAll("&#59;", ";").replaceAll("&#39;", "'");

                option.put(key, val);

            }

            BeanUtils.populate(columnEntry, option);
            colModel.delete(0, colEndIdx);

            LOGGER.debug("Lists Column Entry Load : {}", columnEntry.toString());

            columnEntries.add(columnEntry);
        }

        return columnEntries;
    }

    // 필수 속성
    enum SEARCH{
        id,type
    }

    enum TARGET {

        SEARCHS1("LIST1"),
        SEARCHS2("LIST2"),
        SEARCHS3("LIST3"),
        SEARCHS4("LIST4"),
        SEARCHS5("LIST5");

        private String key;
        private TARGET(String key){
            this.key = key;
        }

        public String getTarget(){
            return this.key;
        }

    }

}
