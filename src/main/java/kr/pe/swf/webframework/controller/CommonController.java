package kr.pe.swf.webframework.controller;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.engine.type.IntegerTypeHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by seongdonghun on 2016. 9. 27..
 */
@Controller
public class CommonController {

    @Autowired
    SqlMapClient sqlMapClient;

    Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping("/commonGridSelectList.do")
    public void commonGridSelectList(@RequestParam(value = "_search") String _search,
                                     @RequestParam(value = "nd") String nd,
                                     @RequestParam(value = "rows") String rows, // 페이지당 데이터수
                                     @RequestParam(value = "page") String page, // 현재페이지
                                     @RequestParam(value = "sidx") String sidx, // 소팅 컬럼 id
                                     @RequestParam(value = "sord") String sord, // 소팅 구분(asc, desc)
                                     @RequestParam(required=false, value = "formData") String p,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {

        JSONObject jsonObject = new JSONObject();
        String result = new String();

        try {
            Map params = (Map)(new JSONParser().parse(URLDecoder.decode(p, "UTF-8")));
            String selectQKey = (String)params.get("selectQKey");

            int startRow = 0;
            if(Integer.parseInt(page) > 1){
                startRow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
            }

            params.put("rows", rows);
            params.put("page", page);
            params.put("sidx", sidx);
            params.put("sord", sord);
            params.put("startRow", startRow);

            System.out.println("parameter : " + params);

            int totCnt = 0;
            if(params.containsKey("isPaging") && "true".equals(params.get("isPaging"))){
                try{
                    totCnt = (Integer)sqlMapClient.queryForObject(selectQKey + "ForCount", params);
                }catch(SqlMapException se){
                    LOGGER.error(se.getLocalizedMessage());
                    throw new SqlMapException("Count 쿼리가 존재하지 않습니다. [" + selectQKey + "ForCount" +"]");
                }
            }

            List<Map<String, String>> list = sqlMapClient.queryForList(selectQKey, params);

            int total = (int)Math.ceil(totCnt/Double.parseDouble(rows));
            total = (total < 1 ? 1 : total);

            jsonObject.put("page"   , page);
            jsonObject.put("total"  , String.valueOf(total));
            jsonObject.put("records", String.valueOf(list.size()));
            jsonObject.put("data"   , list);

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

            jsonObject.put("data", list);

            result = jsonObject.toJSONString();
            System.out.println("result : " + result);

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            response.getWriter().write(result);
        }
    }

    @RequestMapping("/selectSubMenu.do")
    public void sselectSubMenu(@RequestParam(value = "menuId") String menuId, HttpServletResponse response) throws IOException{
        JSONObject jsonObject = new JSONObject();
        String result = new String();

        try {

            System.out.println("menuid : " + menuId);

            List<Map<String, Object>> list = sqlMapClient.queryForList("Common.selectSubMenuList", menuId);

            List subMenuList = new ArrayList();
            String menuArr = menuId + ",";
            for(Map<String, Object> pMap : list){
                int lvl = (Integer)pMap.get("MENU_LVL");
                if(lvl == 1){
                    continue;
                }

                String pMenuId = (String)pMap.get("P_MENU_ID");

                if(menuArr.indexOf(pMenuId) > -1){
                    menuArr += (String)pMap.get("MENU_ID");

                    subMenuList.add(pMap);
                }
            }

            jsonObject.put("data", subMenuList);

            result = jsonObject.toJSONString();
            System.out.println("MENU : " + result);

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            response.getWriter().write(result);
        }
    }

    @RequestMapping("/commonSaveData.do")
    public void commonSaveData(@RequestParam(value = "formData") String p, HttpServletResponse response) throws IOException{
        JSONObject jsonObject = new JSONObject();
        try {
            Map params = (Map)(new JSONParser().parse(URLDecoder.decode(p, "UTF-8")));

            //count 쿼리
            int cnt = 0;
            if(params.containsKey("cQKey")){
                String cQKey = (String)params.get("cQKey");
                cnt = Integer.parseInt(String.valueOf(sqlMapClient.queryForObject(cQKey, params)));

            }

            // Update
            if(cnt > 0){
                if(!params.containsKey("uQKey")){
                    throw new Exception("Update 쿼리 Key가 존재하지 않습니다.");
                }

                String uQKey = (String) params.get("uQKey");
                sqlMapClient.update(uQKey, params);

            // Insert
            }else{
                if(!params.containsKey("iQKey")){
                    throw new Exception("Insert 쿼리 Key가 존재하지 않습니다.");
                }

                String iQKey = (String) params.get("iQKey");
                sqlMapClient.insert(iQKey, params);
            }

            jsonObject.put("code", "S");

        }catch(Exception e){
            jsonObject.put("code", "E");
            e.printStackTrace();

        }finally {
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

}
