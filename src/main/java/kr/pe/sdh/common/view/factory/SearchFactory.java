package kr.pe.sdh.common.view.factory;

import kr.pe.sdh.common.view.SearchEntry;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public String drawSearch() throws Exception {
        if(this.searchEntries == null){
            throw new Exception("SearchEntry is null");
        }

        SearchRow searchRow = this.createRow();
        for(int i=0; i < this.searchEntries.size(); i++) {
            SearchEntry data = this.searchEntries.get(i);

            if (i == 0 || (i % this.searchColSize) == 0) {
                searchRow.makeRow();
            }

            searchRow.appendCol(this.createCol(data));

            if(((i+1)%this.searchColSize) == 0 || (i+1) == this.searchEntries.size()){
                searchRow.closeRow();
            }
        }

        return searchRow.output();

    }

    public abstract String bindComponent() throws Exception;

    public abstract SearchRow createRow();
    public abstract SearchCol createCol(SearchEntry data);

}
