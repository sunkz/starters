package com.sunkz.starters.hello.config;

import com.sunkz.starters.hello.model.HelloProperties;
import com.sunkz.starters.hello.service.HelloTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HelloProperties.class)
public class HelloAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HelloTemplate helloService(HelloProperties helloProperties) {
        return new HelloTemplate(helloProperties);
    }

}
