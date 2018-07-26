package com.isel.daw.checklist;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends GenericFilterBean {
    static final String HEADER_STRING = "Authorization";
    static final String SECRET = "secret";
    static final String TOKEN_PREFIX = "Bearer ";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        Authentication authentication = null;
        //String headerAuth = request.getHeader(


        //SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
