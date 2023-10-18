package com.hmall.api.config;

import com.hmall.api.client.fallback.ItemClientFallback;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return  Logger.Level.BASIC;
    }

    @Bean
    public RequestInterceptor userInfoRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                //获取我们的登录用户
                Long userInfo = UserContext.getUser();
                if (userInfo == null){
                    return;
                }
                requestTemplate.header("user-info", userInfo.toString());
            }
        };
    }

    @Bean
    public ItemClientFallback itemClientFallback(){
        return new ItemClientFallback();
    }
}
