package com.sse.dynamicssedropbox;

import com.sse.dynamicssedropbox.resources.Tree;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Tree tree() {
        return new Tree();
    }

}
