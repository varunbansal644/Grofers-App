package com.upgrad.Grofers.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfig<AuthenticationInterceptor> extends WebMvcConfigurerAdapter {

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;

    /*Add all paths that require authentication checks here*/
    String[] pathPatternToIntercept = {"/order/coupon/{coupon_name}", "/order", "/customer/logout", "/customer",
            "/customer/password", "/address", "/address/customer", "/address/{address_id}"};
    List<String> pathsToIntercept = new ArrayList<String>(Arrays.asList(pathPatternToIntercept));

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor) authenticationInterceptor).addPathPatterns(pathsToIntercept);
    }
}
