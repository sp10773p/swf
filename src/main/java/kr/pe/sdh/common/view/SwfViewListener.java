package kr.pe.sdh.common.view;

import kr.pe.sdh.common.util.DOMUtil;
import kr.pe.sdh.common.util.FileUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
public class SwfViewListener implements ServletContextListener {
    public SwfViewListener() {
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ViewInfoManager viewInfoManager = ViewInfoManager.newInstance();

        ServletContext context = servletContextEvent.getServletContext();

        String viewPath = context.getInitParameter("viewPath");
        File f = new File(viewPath);

        String[] filenames = FileUtil.filenameFilesInDirectory(f);

        for(String filename : filenames){
            try{
                Document viewDoc = DOMUtil.parse(filename);
                Element root = viewDoc.getDocumentElement();

                String module    = DOMUtil.getAttribute(root, "module");
                String viewTitle = DOMUtil.getAttribute(root, "title");

                List<Element> searchList = DOMUtil.getChildrenByPath(root, "searchs/search");

                // 조회조건 영역 파싱
                SearchEntry[] searchEntries = new SearchEntry[searchList.size()];
                for(Element searchEle : searchList){
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
                        continue;
                    }

                    SearchEntry searchEntry = new SearchEntry();

                    //속성 저장
                    String id     = DOMUtil.getAttribute(searchEle, "id");
                    String type   = DOMUtil.getAttribute(searchEle, "type");
                    String title  = DOMUtil.getAttribute(searchEle, "title");
                    String isMand = DOMUtil.getAttribute(searchEle, "isMand");
                    String length = DOMUtil.getAttribute(searchEle, "length");
                    String index  = DOMUtil.getAttribute(searchEle, "index");
                    String style  = DOMUtil.getAttribute(searchEle, "style");

                    searchEntry.setId(id);
                    searchEntry.setType(type);
                    searchEntry.setMand(isMand);
                    searchEntry.setTitle(title);
                    searchEntry.setLength(length);
                    searchEntry.setIndex(index);
                    searchEntry.setStyle(style);

                    // event 저장
                    List<Element> eventList = DOMUtil.getChildrenByPath(searchEle, "event");
                    for(Element eventEle : eventList){
                        String name   = DOMUtil.getAttribute(eventEle, "name");
                        String fnName = DOMUtil.getAttribute(eventEle, "fnName");

                        EventEntry eventEntry = new EventEntry();
                        eventEntry.setName(name);
                        eventEntry.setFnName(fnName);

                        searchEntry.addEventEntry(eventEntry);
                    }

                    if(searchEntries.length < searchEntry.getIndex()){
                        System.err.println("::: <search> Tag의 index 값이 전체 <search> Tag의 수보다 큽니다 [" + searchEntry.getIndex() + "]");
                        continue;
                    }

                    if(searchEntries[searchEntry.getIndex()-1] != null){
                        System.err.println("::: <search> Tag의 중복된 index 값이 존재합니다 [" + searchEntry.getIndex() + "]");
                        continue;
                    }

                    // type에 따른 유형 처리
                    if("select".equals(type)){
                        String selectStr  = DOMUtil.getElementTextByPath(searchEle, "select");
                        String selectQKey = DOMUtil.getElementTextByPath(searchEle, "selectQKey");

                        if(selectStr == null && selectQKey == null){
                            System.err.println("::: type 이 select 일때 '<select>' 나 '<selectQKey>' Tag정의가 있어야 합니다.");
                            continue;
                        }

                        searchEntry.setSelect(selectStr);
                        searchEntry.setSelectQKey(selectQKey); // TODO selectQKey 쿼리 실행후 select의 배열처리해서 set 할것

                    }else if("checkbox".equals(type)){
                        String checkStr  = DOMUtil.getElementTextByPath(searchEle, "check");
                        String checkQKey = DOMUtil.getElementTextByPath(searchEle, "checkQKey");

                        if(checkStr == null && checkQKey == null){
                            System.err.println("::: type 이 checkbox 일때 '<check>' 나 '<checkQKey>' Tag정의가 있어야 합니다.");
                            continue;
                        }

                        searchEntry.setCheck(checkStr);
                        searchEntry.setCheckQKey(checkQKey);

                    }else if("radio".equals(type)){
                        String radioStr  = DOMUtil.getElementTextByPath(searchEle, "radio");
                        String radioQKey = DOMUtil.getElementTextByPath(searchEle, "radioQKey");

                        if(radioStr == null && radioQKey == null){
                            System.err.println("::: type 이 radio 일때 '<radio>' 나 '<radioQKey>' Tag정의가 있어야 합니다.");
                            continue;
                        }

                        searchEntry.setRadio(radioStr);
                        searchEntry.setRadioQKey(radioQKey);

                    }

                    System.out.println(searchEntry.toString());
                    searchEntries[searchEntry.getIndex() - 1] = searchEntry;

                }

                viewInfoManager.setModuleSearchInfo(module, Arrays.asList(searchEntries));
                viewInfoManager.setView(module, root);
                viewInfoManager.setViewTitle(module, viewTitle);

            }catch (Exception e){
                e.printStackTrace();
                System.err.println("::: View 파일 파싱중 에러가 발생 하였습니다." + e.getMessage());
            }
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

enum SEARCH{

}