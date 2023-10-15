package com.hmall.gateway.routes;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class RouteConfigLoader {

    private final NacosConfigManager nacosConfigManager;

    private final static String DATAID = "gateway-routes.json";
    private final static String GROUP = "DEFAULT_GROUP";

    @PostConstruct
    public void initRouteConfiguration() throws NacosException {
    nacosConfigManager.getConfigService().getConfigAndSignListener(DATAID, GROUP, 1000, new Listener() {
        @Override
        public Executor getExecutor() {
            return Executors.newSingleThreadExecutor();
        }

        @Override
        public void receiveConfigInfo(String s) {

        }
    })

    }

}
