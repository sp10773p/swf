package kr.pe.swf.webframework.controller;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 27..
 */
@Controller
public class SelectController {

    @Autowired
    SqlMapClient sqlMapClient;

    @RequestMapping("/commonGridSelectList.do")
    public void commonGridSelectList(@RequestParam(value = "params") String params, HttpServletResponse response) throws IOException {

        JSONObject jsonObject = new JSONObject();
        String result = new String();

        try {
            JSONObject jsonParam = (JSONObject)(new JSONParser().parse(params));

            System.out.println("params : " + jsonParam.toString());

            //TODO 조회 / 조회후 배열 처리
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for(int i=0; i<10; i++){
                Map<String, String> data = new HashMap<String, String>();
                for(int k=0; k<10; k++){
                    data.put("COL"+i, String.valueOf(i));
                }

                list.add(data);
            }

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
