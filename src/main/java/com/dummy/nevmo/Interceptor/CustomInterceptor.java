package com.dummy.nevmo.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public class CustomInterceptor implements HandlerInterceptor {

    static final Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.clear();
        MDC.put("context", request.getMethod()+ "-" + request.getRequestURI().split("/")[2]);
        logger.info("Request received at {}", request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        logger.info("Request completed at {}", request.getRequestURI());
    }
}
