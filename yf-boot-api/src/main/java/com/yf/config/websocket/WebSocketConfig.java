package com.yf.config.websocket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Webscoket配置文件
 * @author bool
 * @date 2019-09-26 09:39
 */
@ConditionalOnWebApplication
@Configuration
@EnableWebSocket
public class WebSocketConfig {

    @Bean
    public CustomSpringConfigurator customSpringConfigurator() {
        // 获取容器
        return new CustomSpringConfigurator();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
