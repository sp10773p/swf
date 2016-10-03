package kr.pe.swf.webframework.controller;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by seongdonghun on 2016. 9. 27..
 */
@Controller
public class SelectController {

    @Autowired
    SqlMapClient sqlMapClient;

    Logger logger = LoggerFactory.getLogger(SelectController.class);

    @RequestMapping("/commonGridSelectList.do")
    public void commonGridSelectList(@RequestParam(value = "_search") String _search,
                                     @RequestParam(value = "nd") String nd,
                                     @RequestParam(value = "rows") String rows,
                                     @RequestParam(value = "page") String page,
                                     @RequestParam(value = "sidx") String sidx,
                                     @RequestParam(value = "sord") String sord,
                                     @RequestParam(required=false, value = "params") String params,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {

        JSONObject jsonObject = new JSONObject();
        String result = new String();

        try {
            Enumeration p = request.getParameterNames();
            while(p.hasMoreElements()){
                String s=  (String)p.nextElement();
                System.out.println(s+ " : " + request.getParameter(s));
            }

            //TODO 조회 / 조회후 배열 처리
            List<Map<String, String>> list = sqlMapClient.queryForList("Common.selectTestList");

            jsonObject.put("page", String.valueOf(1));
            jsonObject.put("total", String.valueOf(2));
            jsonObject.put("records", String.valueOf(13));
            jsonObject.put("data", list);

            result = jsonObject.toJSONString();
            System.out.println("result : " + result);

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            response.getWriter().write(result);
        }
    }

    @RequestMapping("/autoComplete.do")
    public void autoComplete(@RequestParam(value = "term") String term,
                             @RequestParam(value = "selectQKey") String selectQKey, HttpServletResponse response) throws IOException {

        JSONObject jsonObject = new JSONObject();
        String result = new String();

        try {

            System.out.println("term : " + term);
            System.out.println("select : " + selectQKey);
            //TODO 조회 / 조회후 배열 처리
            // sample
            List<Map<String, String>> list = sqlMapClient.queryForList(selectQKey);
            /*Map<String, String> data = new HashMap<String, String>();
            data.put("label", "Oh");
            data.put("value", "K");

            list.add(data);

            data = new HashMap<String, String>();
            data.put("label", "Seong");
            data.put("value", "S");
            list.add(data);*/

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
