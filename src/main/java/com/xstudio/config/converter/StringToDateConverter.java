package com.xstudio.config.converter;

import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.*;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/10/2
 */
public class StringToDateConverter extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory<DateTimeFormat>{

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        Set<Class<?>> fieldTypes = new HashSet<Class<?>>(4);
        fieldTypes.add(Date.class);
        fieldTypes.add(Calendar.class);
        fieldTypes.add(Long.class);
        FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
    }


    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }

    protected Formatter<Date> getFormatter(DateTimeFormat annotation, Class<?> fieldType) {
        DateFormatter formatter = new DateFormatter();
        formatter.setStylePattern(resolveEmbeddedValue(annotation.style()));
        formatter.setIso(annotation.iso());
        formatter.setPattern(resolveEmbeddedValue(annotation.pattern()));
        return formatter;
    }
}
