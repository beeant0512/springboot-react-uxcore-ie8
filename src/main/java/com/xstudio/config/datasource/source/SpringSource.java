package com.xstudio.config.datasource.source;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.xstudio.aop.dbchange.DBChangeInterceptor;
import com.xstudio.config.datasource.config.DruidDataSourceUtil;
import com.xstudio.config.datasource.config.MyOffsetLimitInterceptor;
import com.xstudio.config.datasource.config.MybatisConfigurationSupport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * carbond 数据库配置
 * <p>
 * Created by xiaobiao on 2017/4/7.
 */

@Configuration
@MapperScan(annotationClass = Mapper.class, basePackages = {"com.xstudio.spring.mapper"}, sqlSessionFactoryRef = "springSqlSessionFactory")
public class SpringSource {
    private static final Logger logger = LoggerFactory.getLogger(SpringSource.class);

    @Autowired
    private MybatisConfigurationSupport mybatisConfiguration;

    @Bean(name = "springDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource springDataSource() {
        DruidDataSource dataSource = DruidDataSourceUtil.getMysqlDataSource();
        List<Filter> filters = new ArrayList<>();
        filters.add(DruidDataSourceUtil.statFilter());
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    @Bean(name = "springSqlSessionFactory")
    @Primary
    public SqlSessionFactory springSqlSessionFactory(@Qualifier("springDataSource") DruidDataSource dataSource) throws Exception {
        mybatisConfiguration.setTypeAliasesPackage("com.xstudio.spring.model");
        mybatisConfiguration.setMapperLocations(new String[]{"classpath*:mybatis/mysql/spring/**/*.xml"});
        Interceptor[] interceptors = new Interceptor[]{mysqlInterceptor(), dbChangeInterceptor()};
//        Interceptor[] interceptors = new Interceptor[]{mysqlInterceptor()};
        return mybatisConfiguration.build(dataSource, interceptors);
    }

    @Bean
    public Interceptor dbChangeInterceptor() {
        return new DBChangeInterceptor();
    }

    @Bean
    public Interceptor mysqlInterceptor() {
        MyOffsetLimitInterceptor offsetLimitInterceptor = new MyOffsetLimitInterceptor();
        Properties props = new Properties();
        props.setProperty("dialectClass", "com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect");
        offsetLimitInterceptor.setProperties(props);
        return offsetLimitInterceptor;
    }

}
