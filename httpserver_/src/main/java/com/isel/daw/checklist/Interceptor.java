package com.isel.daw.checklist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class Interceptor implements HandlerInterceptor{


    private static final Logger log = LoggerFactory.getLogger(Interceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            String pattern = (String) Optional.ofNullable(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE))
                    .orElse("[unknown]");
            log.info("on preHandle for {}", pattern);
            HandlerMethod hm = (HandlerMethod) handler;
            RequiresAuthentication methodAnnotation = hm.getMethodAnnotation(RequiresAuthentication.class);
            if (methodAnnotation != null) {
                log.info("!!! Requires authentication !!!");
            }
        }
        return true;
    }
}
