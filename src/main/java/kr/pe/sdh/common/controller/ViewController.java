package kr.pe.sdh.common.controller;

import kr.pe.sdh.common.view.SearchEntry;
import kr.pe.sdh.common.view.ViewInfoManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
@Controller
public class ViewController {

    @RequestMapping("/view.do")
    public String view(@RequestParam(value="module") String module, HttpServletRequest request, HttpServletResponse response){
        ViewInfoManager viewInfoManager = ViewInfoManager.newInstance();

        List<SearchEntry> searchEntryList = viewInfoManager.getSearchEntry(module);


        return "view";
    }
}
