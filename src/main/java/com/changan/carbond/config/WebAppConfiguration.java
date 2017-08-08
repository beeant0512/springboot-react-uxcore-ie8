package com.changan.carbond.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * WebMVC 配置
 *
 * Created by xiaobiao on 2017/3/2.
 */
@Configuration
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        List<StaticResource> staticResources = staticResources();
        for (StaticResource staticResource : staticResources) {
            registry.addResourceHandler(staticResource.getPathPattern()).addResourceLocations(staticResource.getResourceLocations());
        }
        super.addResourceHandlers(registry);
    }

    private List<StaticResource> staticResources() {
        List<StaticResource> staticResources = new ArrayList<>();
        staticResources.add(new StaticResource("/static/**","classpath:/static/"));
        return staticResources;
    }

    public class StaticResource {
        private String pathPattern;

        private String[] resourceLocations;

        public StaticResource(String pathPattern, String resourceLocations) {
            this.pathPattern = pathPattern;
            this.resourceLocations = new String[]{resourceLocations};
        }

        public StaticResource(String pathPattern, String[] resourceLocations) {
            this.pathPattern = pathPattern;
            this.resourceLocations = resourceLocations;
        }

        public String getPathPattern() {
            return pathPattern;
        }

        public String[] getResourceLocations() {
            return resourceLocations;
        }
    }
}
