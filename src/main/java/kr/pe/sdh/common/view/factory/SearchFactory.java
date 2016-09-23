package kr.pe.sdh.common.view.factory;

import kr.pe.sdh.common.view.SearchEntry;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created by seongdonghun on 2016. 9. 20..
 */
public abstract class SearchFactory {
    protected List<SearchEntry> searchEntries = null;
    protected int searchColSize = 3; //default column count

    public static SearchFactory getSearchFactory(String classname, List<SearchEntry> searchEntries){
        SearchFactory factory = null;
        try{
            Class cls = Class.forName(classname);
            Constructor constructor = cls.getConstructor(new Class[]{Class.forName("java.util.List")});
            factory = (SearchFactory)constructor.newInstance(new Object[]{searchEntries});

        }catch (ClassNotFoundException e){
            System.err.println("클래스 " + classname + " 이 발견되지 않습니다");
        }catch (Exception e){
            e.printStackTrace();
        }

        return factory;
    }

    public SearchFactory(List<SearchEntry> searchEntries){
        this.searchEntries = searchEntries;
    }

    public void setSearchColSize(int searColSize){
        this.searchColSize = searColSize;
    }

    public Map<String , String> drawSearch() throws Exception {
        Map<String , String> codeMap = new HashMap<String, String>();

        if(this.searchEntries == null){
            throw new Exception("SearchEntry is null");
        }

        StringBuffer buffer = new StringBuffer();


        SearchRow searchRow = this.createRow();
        for(int i=0; i < this.searchEntries.size(); i++) {
            SearchEntry data = this.searchEntries.get(i);

            if (i == 0 || (i % this.searchColSize) == 0) {
                searchRow.makeRow();
            }

            SearchCol searchCol = this.createCol(data);
            searchCol.setColSize(this.searchColSize);

            searchRow.appendCol(searchCol);

            if(((i+1)%this.searchColSize) == 0 || (i+1) == this.searchEntries.size()){
                searchRow.closeRow();
            }

            // bindComponent Script 생성
            buffer.append(bindComponent(data));
        }

        codeMap.put("bindScript" , buffer.toString());
        codeMap.put("search"     , searchRow.output());

        return codeMap;

    }

    public abstract String bindComponent(SearchEntry searchEntry) throws Exception;

    public abstract SearchRow createRow();
    public abstract SearchCol createCol(SearchEntry data);

}
