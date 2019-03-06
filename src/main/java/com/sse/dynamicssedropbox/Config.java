package com.sse.dynamicssedropbox;

import com.sse.dynamicssedropbox.resources.GetAndPutHashMap;
import com.sse.dynamicssedropbox.resources.Tree;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;

@Configuration
public class Config {

    @Bean
    public GetAndPutHashMap trees() {
        return new GetAndPutHashMap();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
