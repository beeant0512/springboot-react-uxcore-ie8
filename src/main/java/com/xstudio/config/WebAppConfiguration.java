package com.xstudio.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
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
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 默认所有的Long型都使用 ToStringSerializer
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        // 在convert中添加配置信息.
        converter.setSupportedMediaTypes(fastMediaTypes);
        converter.setFastJsonConfig(fastJsonConfig);


        converters.add(converter);
    }

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
