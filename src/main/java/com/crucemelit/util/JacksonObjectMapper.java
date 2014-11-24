package com.crucemelit.util;

import javax.annotation.Resource;

import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Component
public class JacksonObjectMapper extends Jackson2ObjectMapperFactoryBean {

    @Resource
    private HandlerInstantiator handlerInstantiator;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Hibernate4Module hbm = new Hibernate4Module();
        hbm.enable(Hibernate4Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
        getObject().registerModule(hbm);
        getObject().setHandlerInstantiator(handlerInstantiator);
        getObject().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
