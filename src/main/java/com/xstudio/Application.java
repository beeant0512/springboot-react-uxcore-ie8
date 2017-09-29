package com.xstudio;

import com.xstudio.common.utils.ContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(Application.class, args);
        ContextUtil.setContext(cac);
    }
}
