package kr.pe.sdh.common.view;

import kr.pe.sdh.common.util.DateUtil;
import org.w3c.dom.Element;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 12..
 */
class ViewInfoManager {

    // view id, view info
    private Map<String, ViewEntry> viewEntryMap = new HashMap<String, ViewEntry>();
    private Map<String, String> viewLoadManage = new HashMap<String, String>();

    ViewEntry getViewEntry(String viewId) {
        return viewEntryMap.get(viewId);
    }

    void setViewEntryMap(String viewId, ViewEntry viewEntry) {
        viewEntryMap.put(viewId, viewEntry);
    }

    void setViewLoadManage(String viewId, String fileName){
        String fileInfo = fileName+":"+String.valueOf(new File(fileName).lastModified());
        viewLoadManage.put(viewId, fileInfo);
    }

    boolean checkViewInfo(String viewId){
        String[] fileManager = getFileManager(viewId);
        String fileName = fileManager[0];
        long saveTime = Long.valueOf(fileManager[1]);

        if(saveTime < new File(fileName).lastModified()){
            return false;

        }

        return true;
    }

    String getViewInfoFile(String viewId){
        return getFileManager(viewId)[0];
    }

    private String[] getFileManager(String viewId){
        return viewLoadManage.get(viewId).split(":");
    }

    void removeViewInfo(String viewId){
        viewEntryMap.remove(viewId);
        viewLoadManage.remove(viewId);
    }


}