package com.baidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ClassName RunZuulServerApplication
 * @Description: TODO
 * @Author hexiangshen
 * @Date 2020/12/23
 * @Version V1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class RunZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunZuulServerApplication.class);
    }
}
