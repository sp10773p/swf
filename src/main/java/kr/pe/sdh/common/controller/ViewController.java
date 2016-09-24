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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            // searchs (bindComponent 자동추가)
            map.putAll(swfViewBuilder.getSearchInfoHtml(viewId));


            swfViewBuilder.mergeLayout(viewId, velocityEngine, map);

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

    @RequestMapping("/commonCode.do")
    public void commonCode(@RequestParam(value = "term") String term,
                         @RequestParam(value = "selectQKey") String selectQKey, HttpServletResponse response) throws IOException {

        JSONObject jsonObject = new JSONObject();
        String result = new String();
        response.setCharacterEncoding("UTF-8");

        try {

            System.out.println("term : " + term);
            System.out.println("select : " + selectQKey);
            //TODO 조회 / 조회후 배열 처리
            // sample
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Map<String, String> data = new HashMap<String, String>();
            data.put("label", "Oh");
            data.put("value", "K");

            list.add(data);

            data = new HashMap<String, String>();
            data.put("label", "Seong");
            data.put("value", "S");
            list.add(data);

            //jsonObject.put("data", StringUtils.listMapToString(list));
            jsonObject.put("data", list);

            result = jsonObject.toJSONString();
            System.out.println("result : " + result);

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            response.getWriter().write(result);
        }
    }




}
