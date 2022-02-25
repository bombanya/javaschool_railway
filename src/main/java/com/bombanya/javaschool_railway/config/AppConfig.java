package com.bombanya.javaschool_railway.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan(basePackages = "com.bombanya.javaschool_railway")
@EnableWebMvc
public class AppConfig {

    @PostConstruct
    public void test(){
        System.out.println("alive");
    }
}
