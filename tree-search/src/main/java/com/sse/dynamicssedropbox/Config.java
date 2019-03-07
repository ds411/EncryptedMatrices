package com.sse.dynamicssedropbox;

import com.sse.dynamicssedropbox.resources.GetAndPutHashMap;
import com.sse.dynamicssedropbox.resources.Tree;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Configuration
public class Config {

    String filename = "/FILENAME WOULD GO HERE";

    @Bean
    public GetAndPutHashMap trees() {
        if(Files.exists(Paths.get(filename))) {
            try {
                return (GetAndPutHashMap) new ObjectInputStream(new FileInputStream(filename)).readObject();
            }
            catch(IOException | ClassNotFoundException e) {}
        }
        return new GetAndPutHashMap();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
