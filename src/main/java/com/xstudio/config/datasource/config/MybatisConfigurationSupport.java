package com.xstudio.config.datasource.config;

import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * mybatis基础配置
 *
 * Created by xiaobiao on 2017/4/7.
 */
@Component("mybatisConfiguration")
public class MybatisConfigurationSupport {

    private final MybatisProperties mybatisProperties;

    private final ResourceLoader resourceLoader;

    private final DatabaseIdProvider databaseIdProvider;

    public MybatisConfigurationSupport(MybatisProperties mybatisProperties, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider) {
        this.mybatisProperties = mybatisProperties;
        this.resourceLoader = resourceLoader;
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
    }

    public void setMapperLocations(String[] mapperLocations){
        this.mybatisProperties.setMapperLocations(mapperLocations);
    }

    public void setTypeAliasesPackage(String typeAliasesPackage){
        this.mybatisProperties.setTypeAliasesPackage(typeAliasesPackage);
    }

    public SqlSessionFactory build(DataSource dataSource, Interceptor[] interceptors) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(mybatisProperties.getConfigLocation())) {
            sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource(mybatisProperties.getConfigLocation()));
        }
        if (mybatisProperties.getConfiguration() != null) {
            Configuration configuration = new Configuration();
            BeanUtils.copyProperties(mybatisProperties.getConfiguration(), configuration);
            configuration.setDefaultExecutorType(mybatisProperties.getExecutorType());
            // log
            configuration.setLogImpl(Slf4jImpl.class);
            sqlSessionFactoryBean.setConfiguration(configuration);
        }
        if (mybatisProperties.getConfigurationProperties() != null) {
            sqlSessionFactoryBean.setConfigurationProperties(mybatisProperties.getConfigurationProperties());
        }
        if (!ObjectUtils.isEmpty(interceptors)) {
            sqlSessionFactoryBean.setPlugins(interceptors);
        }
        if (databaseIdProvider != null) {
            sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
        }
        if (StringUtils.hasLength(mybatisProperties.getTypeAliasesPackage())) {
            sqlSessionFactoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(mybatisProperties.getTypeHandlersPackage())) {
            sqlSessionFactoryBean.setTypeHandlersPackage(mybatisProperties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(mybatisProperties.resolveMapperLocations())) {
            sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
        }
        return sqlSessionFactoryBean.getObject();
    }
}
