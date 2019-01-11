package com.kenzan.hiring.customersapi.listener;

import com.kenzan.hiring.customersapi.util.DataLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/*
    This component will listen to application events and, in this case, when
    the application context starts or gets refreshed json data gets loaded again
*/
@Component
public class ApplicationListener {

    @Autowired
    private DataLoader dataLoader;

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationListener.class);

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        LOG.info("ApplicationListener on event ContextRefreshedEvent, about to load json data");
        dataLoader.loadJsonData();
    }
}