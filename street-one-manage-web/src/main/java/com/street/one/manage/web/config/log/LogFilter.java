package com.street.one.manage.web.config.log;

import com.alibaba.fastjson.JSON;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.enums.BaseResponseCodeEnum;
import com.street.one.manage.common.utils.BaseResponseUtil;
import com.street.one.manage.common.utils.JsonFormatUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName:  xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: LogFilter
 * @Author: tjl
 * @Description: 打印请求日志
 * @Date: 2024/5/13 16:40
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public class LogFilter implements Filter {


    /**
     * 日志打印需要排除的请求类
     */
    private final List<String> excludeRequestURI = Lists.newArrayList("/api/v1/report/excel/download_excel_template");


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try{
            String contentType = request.getContentType();
            log.info("===================contentType=======" + contentType);
            if (contentType != null && contentType.contains("multipart/form-data")) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
            //过滤url
            if(excludeRequestURI.contains(request.getRequestURI())){
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
            String method = request.getMethod();
            String signUrl = request.getRequestURI();
            String requestParam;
            RequestWrapper requestWrapper = new RequestWrapper(request);
            ResponseWrapper responseWrapper = new ResponseWrapper(response);
            if (CommonConstants.METHOD_TYPE.equals(method)) {
                requestParam = getData(requestWrapper);
            } else {
                requestParam = requestWrapper.getBody();
            }
            printReqLog(null, signUrl, method, JSON.toJSONString(resolveHeader(requestWrapper)), requestParam);
            chain.doFilter(requestWrapper, responseWrapper);
            byte[] content = responseWrapper.getContent();
            String resultParams = "";
            if (content.length > 0) {
                resultParams = new String(content, CommonConstants.CHARSET);
            }
            if (responseWrapper.isUsingWriter()) {
                printRespLog(null, JSON.toJSONString(BaseResponseUtil.fail(BaseResponseCodeEnum.E401)));
                return;
            }
            printRespLog(null, resultParams);
            response.getOutputStream().write(resultParams.getBytes());
        }catch (Exception e) {
            log.error("LogFilter -> " + e.getMessage(), e);
            response.getOutputStream().write(JSON.toJSONString(BaseResponseUtil.fail(BaseResponseCodeEnum.E9999,
                    "系统异常,请稍后再试")).getBytes());
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
    }

    /***
     * 解析请求头参数
     * @param request
     * @return
     */
    private Map<String, String> resolveHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> header = new HashMap<>(48);
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            header.put(key, value);
        }
        return header;
    }


    private String getData(HttpServletRequest http) {
        Map<String, String[]> parameterMap = http.getParameterMap();
        return JSON.toJSONString(parameterMap);
    }


    private void printReqLog(String requestId, String path, String method, String header, String params) {
        StringBuilder sb = new StringBuilder("");
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("#--------------------------------------------------------------------------------#");
//        sb.append(JsonFromatUtils.NEWLINE);
////        sb.append("#" + requestId);
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("# REQUEST PATH " + path);
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("# REQUEST METHOD " + method);
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("# REQUEST HEADER");
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append(JsonFormatUtils.format(header));
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("# REQUEST PARAMS");
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append(JsonFormatUtils.format(params));
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("#--------------------------------------------------------------------------------#");
        log.info(sb.toString());
    }

    private void printRespLog(String requestId, String params) {
        StringBuilder sb = new StringBuilder();
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("#--------------------------------------------------------------------------------#");
        //sb.append(JsonFromatUtils.NEWLINE);
        //sb.append("#" + requestId);
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("# RESPONSE PARAMS");
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append(JsonFormatUtils.format(params));
        sb.append(JsonFormatUtils.NEWLINE);
        sb.append("#--------------------------------------------------------------------------------#");
        log.info(sb.toString());
    }

}
