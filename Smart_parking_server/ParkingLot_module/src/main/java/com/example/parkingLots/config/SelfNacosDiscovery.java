package com.example.parkingLots.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class SelfNacosDiscovery {
    @Resource
    private AbstractAutoServiceRegistration abstractAutoServiceRegistration;

    @EventListener(ContextClosedEvent.class)
    public void doDeregister() {
        log.info("nacos补偿注销流程，开始");
        try {
            abstractAutoServiceRegistration.destroy();
        }catch (Exception e){
        }
        log.info("nacos补偿注销流程，结束");
    }
}
