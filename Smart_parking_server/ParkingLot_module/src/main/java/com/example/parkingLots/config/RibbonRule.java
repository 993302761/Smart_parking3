package com.example.parkingLots.config;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonRule {

    //通过配置将Ribbon均衡负载策略设置为优先选择相同集群中的实例，失败则访问其他集群
    @Bean
    public IRule  LoadRules (){
        //随机策略
//        return new RandomRule();
        //根据nacos的设置优先选择本地集群
        return new NacosRule();
        //根据nacos页面设置的权重（0-1）来分配，为0则不再会有请求被分配到那台服务器上
        //可用于停机更新
    }
}
