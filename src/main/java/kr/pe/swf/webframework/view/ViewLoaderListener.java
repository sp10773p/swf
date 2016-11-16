package kr.pe.swf.webframework.view;

import kr.pe.swf.webframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by seongdonghun on 2016. 9. 28..
 */
public class ViewLoaderListener extends ViewLoader implements ServletContextListener {

    Logger LOGGER = LoggerFactory.getLogger(ViewLoaderListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        ContextLoaderListener contextLoaderListener = new ContextLoaderListener();
        ApplicationContext applicationContext = contextLoaderListener.initWebApplicationContext(servletContext);

        String[] test = applicationContext.getBeanDefinitionNames();

        for(String s : test){
            System.out.println(">>>>> " + s);
        }

        /***
         * web.xml Listener parameter
         */
        String viewPath   = servletContext.getInitParameter("viewPath");
        String gridType   = servletContext.getInitParameter("gridType");
        String searchType = servletContext.getInitParameter("searchType");
        String detailType = servletContext.getInitParameter("detailType");

        servletContext.log("Initializing Seong`s Webframework View Loader");
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("View Loader Listener : initialization started");
        }

        if(viewPath == null && StringUtils.isEmpty(viewPath)){
            throw new RuntimeException("::: viewPath context-param이 존재하지 않습니다.");
        }


        if(searchType == null && StringUtils.isEmpty(searchType)){
            searchType = "kr.pe.swf.webframework.view.factory.w3factory.W3SearchBuilder";
        }

        if(detailType == null && StringUtils.isEmpty(detailType)){
            detailType = "kr.pe.swf.webframework.view.factory.w3factory.W3DetailBuilder";
        }

        if(gridType == null && StringUtils.isEmpty(gridType)){
            gridType = "jqgrid";
        }
        LOGGER.info("View config path : {}", viewPath);
        LOGGER.info("Layout package : {}", searchType);

        initViewLoader((ViewInfoFactory)applicationContext.getBean("viewInfoFactory"),viewPath, searchType, detailType, gridType);

        LOGGER.info("Start View Loading");

        load();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
