package com.sunkz.starters.log.config;

import com.sunkz.starters.log.aop.LogAspect;
import com.sunkz.starters.log.aop.LogCurlAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogCurlAspect logCurlAspect() {
        return new LogCurlAspect();
    }

}
