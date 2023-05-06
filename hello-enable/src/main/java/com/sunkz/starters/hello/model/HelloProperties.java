package com.sunkz.starters.hello.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "hello")
public class HelloProperties {
    private String year;
}
