package com.crucemelit.util;

import java.text.FieldPosition;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

@Component
public class JacksonObjectMapper extends Jackson2ObjectMapperFactoryBean {

    @Resource
    private HandlerInstantiator handlerInstantiator;

    @Override
    public void afterPropertiesSet() {
        setDateFormat(new ISO8601DateFormat() {

            private static final long serialVersionUID = 1L;

            @Override
            public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
                String value = ISO8601Utils.format(date, true, TimeZone.getDefault());
                toAppendTo.append(value);
                return toAppendTo;
            }
        });

        super.afterPropertiesSet();

        getObject().setHandlerInstantiator(handlerInstantiator);
    }

}
