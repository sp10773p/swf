package kr.pe.sdh.common.controller;

import kr.pe.sdh.common.view.SearchCreator;
import kr.pe.sdh.common.view.SearchEntry;
import kr.pe.sdh.common.view.ViewInfoManager;
import org.apache.velocity.app.VelocityEngine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
@Controller
public class ViewController {
    ViewInfoManager viewInfoManager = ViewInfoManager.newInstance();

    @Resource(name="velocityEngine")
    private VelocityEngine velocityEngine;

    @RequestMapping("/view.do")
    public String view(HttpServletRequest request, HttpServletResponse response){

        return "main";
    }

    @RequestMapping("/mainView.do")
    public void mainView(@RequestParam(value = "p") String p,  HttpServletRequest request, HttpServletResponse response){
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(p);

            String module = (String)jsonObject.get("module");

            response.setCharacterEncoding("UTF-8");

            Map map = new HashMap();
            map.put("title", viewInfoManager.getViewTitle(module));
            map.put("search", getSearchInfoHtml(module));

            StringWriter writer = new StringWriter();

            // velocity
            VelocityEngineUtils.mergeTemplate(velocityEngine, "vm/module1.vm", "UTF-8", map, writer);
            System.out.println(writer.toString());

            response.getWriter().write(writer.toString());

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private String getSearchInfoScript(String module) throws Exception{
        List<SearchEntry> searchEntries = viewInfoManager.getSearchEntry(module);
        SearchCreator searchCreator = new SearchCreator(searchEntries);

        String script = searchCreator.bindComponent();

        return script;

    }

    private String getSearchInfoHtml(String module) throws Exception{
        List<SearchEntry> searchEntries = viewInfoManager.getSearchEntry(module);
        SearchCreator searchCreator = new SearchCreator(searchEntries);

        String html = searchCreator.drawSearch();

        return html;
    }


}
