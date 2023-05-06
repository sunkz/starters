package com.sunkz.starters.hello.service;

import com.sunkz.starters.hello.model.HelloProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelloTemplate {

    private HelloProperties properties;

    public String hello() {
        return properties.getYear();
    }
}
