package com.sgai.cxmc.config;

import com.sgai.cxmc.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: MVC配置
 * @Author: 李锐平
 * @Date: 2019/7/31 11:34
 * @Version 1.0
 */

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册自定义的登录校验拦截器，设置拦截的URL以及排除的URL
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/error","/error/**","/mc/app/**","/user/login","/static/**","/webjars/**");

        //注册自定义的权限拦截器，
//        registry.addInterceptor(new AclHandlerInterceptor()).addPathPatterns("/admin/**");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //由于自己注册了自定义的拦截器，因此静态资源需要加入排除URL，并且在此方法里添加资源处理？
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/webjars/");
        registry.addResourceHandler("/webjars/**") .addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}
