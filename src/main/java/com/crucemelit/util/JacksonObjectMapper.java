package com.crucemelit.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

@Component
public class JacksonObjectMapper extends ObjectMapper {

    public JacksonObjectMapper() {
        Hibernate4Module hbm = new Hibernate4Module();
        hbm.disable(Feature.USE_TRANSIENT_ANNOTATION);
        registerModule(hbm);
    }

}
