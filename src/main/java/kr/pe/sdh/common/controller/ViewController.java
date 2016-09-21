package kr.pe.sdh.common.controller;

import kr.pe.sdh.common.util.StringUtils;
import kr.pe.sdh.common.view.SwfViewBuilder;
import org.apache.velocity.app.VelocityEngine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
@Controller
public class ViewController {
    @Autowired
    SwfViewBuilder swfViewBuilder;

    @Resource(name="velocityEngine")
    private VelocityEngine velocityEngine;

    @RequestMapping("/index.do")
    public String view(HttpServletRequest request, HttpServletResponse response){
        return "main";
    }

    @RequestMapping("/mainView.do")
    public void mainView(@RequestParam(value = "view") String viewId,
                         @RequestParam(value = "param") String param, HttpServletResponse response) throws IOException {

        JSONObject returnData = new JSONObject();
        response.setCharacterEncoding("UTF-8");

        try {
            JSONParser jsonParser;
            JSONObject params;
            if(StringUtils.isNotEmpty(param)){
                jsonParser = new JSONParser();
                params     = (JSONObject) jsonParser.parse(param);
            }


            if(!swfViewBuilder.isExists(viewId)){
                throw new Exception("존재하지 않는 view : " + viewId);
            }

            Map map = new HashMap();
            map.put("title"     , swfViewBuilder.getTitle(viewId));
            map.put("bindScript", swfViewBuilder.getSearchInfoScript(viewId));

            // searchs
            map.putAll(swfViewBuilder.getSearchInfoHtml(viewId));

            StringWriter writer = new StringWriter();

            String vm = swfViewBuilder.getViewType(viewId);

            if(swfViewBuilder.getViewVm(viewId) == null){
                // velocity
                VelocityEngineUtils.mergeTemplate(velocityEngine, "vm/"+vm+".vm", "UTF-8", map, writer);
                swfViewBuilder.setViewVm(viewId, writer.toString());
            }

            returnData.put("code", "S");
            returnData.put("data", swfViewBuilder.getViewVm(viewId));

        }catch(Exception e){
            e.printStackTrace();
            returnData.put("code", "E");
            returnData.put("msg" , e.getMessage());

        }finally {
            response.getWriter().write(returnData.toJSONString());
        }
    }




}
