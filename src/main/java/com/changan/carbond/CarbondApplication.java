package com.changan.carbond;

import com.changan.carbond.common.utils.ContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CarbondApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(CarbondApplication.class, args);
        ContextUtil.setContext(cac);
    }
}
