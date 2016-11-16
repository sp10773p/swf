package kr.pe.swf.webframework.controller;

import com.ibatis.sqlmap.client.SqlMapClient;
import kr.pe.swf.webframework.view.ViewInfoFactory;
import org.apache.velocity.app.VelocityEngine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private SqlMapClient sqlMapClient;

    private final String USER_SESSTION_ID = "SWF_USER_SESSTION_ID";
    private final String MENU_SESSTION_ID = "SWF_MENU_SESSTION_ID";

    @RequestMapping("/index.do")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");

        HttpSession session = request.getSession();
        if(session.getAttribute(USER_SESSTION_ID) == null){
            List list = sqlMapClient.queryForList("Common.selectMenuList", null);
            session.setAttribute(MENU_SESSTION_ID, list);
        }

        modelAndView.addObject("S_MENU", session.getAttribute(MENU_SESSTION_ID));

        return modelAndView;
    }

    @RequestMapping("/mainView.do")
    public void mainView(@RequestParam(value = "view") String viewId,
                         @RequestParam(required = false, value = "param") String p, HttpServletResponse response) throws IOException {

        JSONObject returnData = new JSONObject();

        try {

            if(!viewInfoFactory.isExists(viewId)){
                throw new Exception("존재하지 않는 view : " + viewId);
            }

            // Request Parameter 셋팅
            Map viewParam = (Map)(new JSONParser().parse(URLDecoder.decode(p, "UTF-8")));

            System.out.println("viewParam:" + viewParam);

            viewInfoFactory.getViewEntry(viewId).setViewParam(viewParam);

            Map map = new HashMap();
            map.put("title", viewInfoFactory.getTitle(viewId));

            // 화면에 바인드 할 소스코드 생성
            map.putAll(viewInfoFactory.getBindSourceCode(viewId));

            // Velocity Bind
            viewInfoFactory.mergeLayout(viewId, map);

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
