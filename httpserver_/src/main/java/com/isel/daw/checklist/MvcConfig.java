package com.isel.daw.checklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor( new Interceptor());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(AuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new OAuth2ClientContextFilter(),
                        AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAfter(OpenIdConnectFilter(),
                        OAuth2ClientContextFilter.class)
                .httpBasic()
               .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

}


