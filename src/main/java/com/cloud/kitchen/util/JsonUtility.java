package com.cloud.kitchen.util;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature ;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.cloud.kitchen.models.Order;

public class JsonUtility {

    private final static Logger logger = LogManager.getLogger(JsonUtility.class);

    public static ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static List<Order> readOrders(){
        List<Order> orders = new ArrayList<>();
        try {
            InputStream inputStream = JsonUtility.class.getClassLoader().getResourceAsStream(com.cloud.kitchen.constants.StringConstants.ORDERS_FILE);
            if (inputStream == null) {
                logger.error("File not found!");
                return orders;
            }
            orders = getObjectMapper().readValue(inputStream, new TypeReference<>() {
            });
        }catch (Exception exception) {
            logger.error("errorMessage={}. stackTrace={}",exception, ExceptionUtils.getMessage(exception));
        }
        return orders;
    }
}
