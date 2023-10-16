package com.hmall.gateway.routes;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class RouteConfigLoader {

    private final NacosConfigManager nacosConfigManager;

    private final RouteDefinitionWriter writer;

    private final static String DATAID = "gateway-routes.json";
    private final static String GROUP = "DEFAULT_GROUP";

    private Set<String> routeIds = new HashSet<>();

    @PostConstruct
    public void initRouteConfiguration() throws NacosException {
        //第一次启动时加载路由配置,并且添加监听器
        String configInfo = nacosConfigManager.getConfigService().getConfigAndSignListener(DATAID, GROUP, 1000, new Listener() {
            @Override
            public Executor getExecutor() {
                return Executors.newSingleThreadExecutor();
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                //监听到路由变更,更新路由表
                updateRouteConfigInfo(configInfo);
            }
        });
        //2.写入路由表
        updateRouteConfigInfo(configInfo);
    }

    private void updateRouteConfigInfo(String configInfo) {
        //解析
        List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);
        //删除旧的
        for (String routeId : routeIds) {
            writer.delete(Mono.just(routeId)).subscribe();
        }
        //判断是否有新路由
        if (routeDefinitions == null || routeDefinitions.isEmpty()) {
            //无新路由
            return;
        }
        routeIds.clear();
        //更新路由
        for (RouteDefinition routeDefinition : routeDefinitions) {
            writer.save(Mono.just(routeDefinition)).subscribe();
            routeIds.add(routeDefinition.getId());
        }
    }

}
