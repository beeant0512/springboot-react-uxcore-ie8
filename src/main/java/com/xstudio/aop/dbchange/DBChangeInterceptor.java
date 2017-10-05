package com.xstudio.aop.dbchange;

import com.xstudio.common.utils.ContextUtil;
import com.xstudio.spring.model.SqlLog;
import com.xstudio.spring.service.impl.SqlLogServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/9/29
 */


@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class DBChangeInterceptor implements Interceptor {
    static Pattern updatePattern = Pattern.compile("update\\s+\\`{0,1}(\\w*)\\`{0,1}\\s+[\\w\\W]*\\s+update_by\\s*\\=\\s*\\`{0,1}(\\w*)\\`{0,1}[\\s\\w\\W]*");
    static Pattern insertSetPattern = Pattern.compile("insert\\s+into\\s+\\`{0,1}(\\w*)\\`{0,1}\\s+[\\w\\W]*\\s+create_by\\s*\\=\\s*\\`{0,1}(\\w*)\\`{0,1}[\\s\\w\\W]*");
    static Pattern insertValuesPattern = Pattern.compile("insert\\s+into\\s+\\`{0,1}(\\w*)\\`{0,1}\\s*\\(([\\w\\W\\s]*)\\)\\s*values\\s*\\(([\\w\\W\\s]*)\\)");
    static Pattern deletePattern = Pattern.compile("delete\\s+from\\s+\\`{0,1}(\\w*)\\`{0,1}");
    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    private static Logger logger = LoggerFactory.getLogger(DBChangeInterceptor.class);

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql
                .getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration
                    .getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                MetaObject metaObject = configuration
                        .newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql
                                .getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        sql = sql.replaceFirst("\\?", "缺失");
                    } //打印出缺失，提醒该参数缺失并防止错位
                }
            }
        }
        return sql;
    }

    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(
                    DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        // 获取执行的方法
        if (args.length > 1) {
            // 传入的对象
            Object obj = args[1];
            if (obj instanceof SqlLog) {
                // 若是日志对象 则直接跳过
                return invocation.proceed();
            }
            saveSqlLog(invocation, args);
        }

        return invocation.proceed();
    }

    private void saveSqlLog(Invocation invocation, Object[] args) {
        MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        Object parameter = args[PARAMETER_INDEX];
        Configuration configuration = ms.getConfiguration();
        Object target = invocation.getTarget();
        StatementHandler handler = configuration.newStatementHandler((Executor) target, ms, parameter, RowBounds.DEFAULT, null, null);
        BoundSql boundSql = handler.getBoundSql();
        //记录SQL
        String sql = showSql(configuration, boundSql);
        logger.info(sql);
        SqlLog sqlLog = new SqlLog();
        sqlLog.setActorId(0l);
        // todo multi-sql serverInfo ...

        if (sql.trim().startsWith("update")) {
            sqlLog.setMethod("update");
            Matcher matcher = updatePattern.matcher(sql);
            if (matcher.find()) {
                sqlLog.setTableName(matcher.group(1));
                sqlLog.setActorId(Long.valueOf(matcher.group(2)));
            }
        } else if (sql.trim().startsWith("delete")) {
            sqlLog.setMethod("delete");
            Matcher matcher = deletePattern.matcher(sql);
            if (matcher.find()) {
                sqlLog.setTableName(matcher.group(1));
            }
        } else {
            return;
        }
        sqlLog.setStatment(sql);
        SqlLogServiceImpl sqlLogService = ContextUtil.getBean(SqlLogServiceImpl.class);
        sqlLogService.insertSelective(sqlLog);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        return;
    }
}
