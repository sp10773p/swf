package kr.pe.swf.webframework.controller;

import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.ViewInfoFactory;
import kr.pe.swf.webframework.view.ViewLoader;
import org.apache.velocity.app.VelocityEngine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
@Controller
public class ViewController {
    @Resource(name="velocityEngine")
    private VelocityEngine velocityEngine;

    @Autowired
    private ViewInfoFactory viewInfoFactory;

    @RequestMapping("/index.do")
    public String view(HttpServletRequest request, HttpServletResponse response){
        return "main";
    }

    @RequestMapping("/mainView.do")
    public void mainView(@RequestParam(value = "view") String viewId,
                         @RequestParam(required = false, value = "param") String param, HttpServletResponse response) throws IOException {

        JSONObject returnData = new JSONObject();

        try {
            JSONParser jsonParser;
            JSONObject params;
            if(StringUtils.isNotEmpty(param)){
                jsonParser = new JSONParser();
                params     = (JSONObject) jsonParser.parse(param);
            }


            if(!viewInfoFactory.isExists(viewId)){
                throw new Exception("존재하지 않는 view : " + viewId);
            }

            Map map = new HashMap();
            map.put("title"     , viewInfoFactory.getTitle(viewId));

            // searchs (bindComponent 자동추가)
            map.putAll(viewInfoFactory.getSearchInfoHtml(viewId));


            viewInfoFactory.mergeLayout(viewId, velocityEngine, map);

            returnData.put("code", "S");
            returnData.put("data", viewInfoFactory.getViewVmManage(viewId));

        }catch(Exception e){
            e.printStackTrace();
            returnData.put("code", "E");
            returnData.put("msg" , e.getMessage());

        }finally {
            response.getWriter().write(returnData.toJSONString());
        }
    }





}
