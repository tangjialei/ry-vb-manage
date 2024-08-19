package com.street.one.manage.common.utils;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: JSONUtil
 * @Author: tjl
 * @Description: json 工具类
 * @Date: 2024/5/13 16:50
 * @modified modify person name
 * @Version: 1.0
 */
public class JSONUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        mapper.registerModule(javaTimeModule);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(JSONObject jobj, String propertyName, T defaultValue) {
        if(!jobj.containsKey(propertyName)) {
            return defaultValue;
        }
        try {
            return (T) jobj.get(propertyName);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(JSONArray jarr, int index) {
        try {
            return (T) jarr.get(index);
        } catch (JSONException e) {
            return null;
        }
    }

    public static void setValue(JSONObject jobj, String propertyName, Object defaultValue) {
        try {
            jobj.put(propertyName, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject CreateJsonObject(String text) {
        return cn.hutool.json.JSONUtil.parseObj(text);
    }

    public static JSONArray CreateJsonArray(String jsonstring) {
        JSONArray result = null;
        try{
            result = cn.hutool.json.JSONUtil.parseArray(jsonstring);
        } catch (Exception e){
            System.out.print("JSONUtil JSONArray解析错误：");
            System.out.println(jsonstring);
            throw e;
        }
        return result;
    }

    /**
     * 解析是否是有效的JSON字符串 （Alibaba fastjson）
     *
     * @param test
     * @return
     */
    public final static boolean isJSONValid(String test) {
        try {
            cn.hutool.json.JSONUtil.parseObj(test);
        } catch (JSONException | ClassCastException ex) {
            try {
                cn.hutool.json.JSONUtil.parseArray(test);
            } catch (JSONException | ClassCastException ex1) {
                return false;
            }
        }
        return true;
    }


    /**
     * 解析是否是有效的JSONObject字符串 （Alibaba fastjson）
     *
     * @param json
     * @return
     */
    public final static boolean isCanConvertJSONObjectValid(String json) {
        try {
            cn.hutool.json.JSONUtil.parseObj(json);
        } catch (JSONException | ClassCastException ex) {
            return false;
        }
        return true;
    }


    /**
     * 解析是否是有效的JSONObject字符串 （Alibaba fastjson）
     *
     * @param json
     * @return
     */
    public final static boolean isCanConvertJSONArrayValid(String json) {
        try {
            cn.hutool.json.JSONUtil.parseArray(json);
        } catch (JSONException | ClassCastException ex) {
            return false;
        }
        return true;
    }

    public static <T> String bean2Json(T obj) {
        try {
            if(obj == null) {
                return null;
            }
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parse(String s, Class<T> c) {
        try {
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(s, c);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            if(StringUtils.isEmpty(jsonStr)) {
                return null;
            }
            return mapper.readValue(jsonStr, objClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> json2List(String jsonStr, Class<T> beanClass) {
        JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, beanClass);
        try {
            if(StringUtils.isEmpty(jsonStr)) {
                return null;
            }
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            List<T> list = mapper.readValue(jsonStr, javaType);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String list2Json(List<T> list) {
        try {
            if(list == null) {
                return null;
            }
            return mapper.writeValueAsString(list);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> json2List(String jsonStr, List<T> beanClass) {
        try {
            if(StringUtils.isEmpty(jsonStr)) {
                return null;
            }
            @SuppressWarnings("unchecked")
            List<T> list = mapper.readValue(jsonStr, beanClass.getClass());
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T json2Bean(String jsonStr, String subKey, Class<T> objClass) {
        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(jsonStr);
            JsonNode treekey2value = rootNode.findPath(subKey);
            return json2Bean(treekey2value.toString(), objClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJson(String jsonStr, String subKey) {
        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(jsonStr);
            JsonNode treekey2value = rootNode.findPath(subKey);
            return String.valueOf(treekey2value.asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
