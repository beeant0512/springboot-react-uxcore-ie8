package com.xstudio.config.converter;

import org.springframework.format.Formatter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/10/2
 */
public class DateFormatter implements Formatter<Date> {

    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private static final Map<DateTimeFormat.ISO, String> ISO_PATTERNS;

    static {
        Map<DateTimeFormat.ISO, String> formats = new HashMap<>(4);
        formats.put(DateTimeFormat.ISO.DATE, "yyyy-MM-dd");
        formats.put(DateTimeFormat.ISO.TIME, "HH:mm:ss.SSSZ");
        formats.put(DateTimeFormat.ISO.DATE_TIME, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        ISO_PATTERNS = Collections.unmodifiableMap(formats);
    }


    private String pattern;

    private int style = DateFormat.DEFAULT;

    private String stylePattern;

    private DateTimeFormat.ISO iso;

    private TimeZone timeZone;

    private boolean lenient = false;


    /**
     * Create a new default DateFormatter.
     */
    public DateFormatter() {
    }

    /**
     * Create a new DateFormatter for the given date pattern.
     */
    public DateFormatter(String pattern) {
        this.pattern = pattern;
    }


    /**
     * Set the pattern to use to format date values.
     * <p>If not specified, DateFormat's default style will be used.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Set the ISO format used for this date.
     *
     * @param iso the {@link DateTimeFormat.ISO} format
     * @since 3.2
     */
    public void setIso(DateTimeFormat.ISO iso) {
        this.iso = iso;
    }

    /**
     * Set the style to use to format date values.
     * <p>If not specified, DateFormat's default style will be used.
     *
     * @see DateFormat#DEFAULT
     * @see DateFormat#SHORT
     * @see DateFormat#MEDIUM
     * @see DateFormat#LONG
     * @see DateFormat#FULL
     */
    public void setStyle(int style) {
        this.style = style;
    }

    /**
     * Set the two character to use to format date values. The first character used for
     * the date style, the second is for the time style. Supported characters are
     * <ul>
     * <li>'S' = Small</li>
     * <li>'M' = Medium</li>
     * <li>'L' = Long</li>
     * <li>'F' = Full</li>
     * <li>'-' = Omitted</li>
     * <ul>
     * This method mimics the styles supported by Joda-Time.
     *
     * @param stylePattern two characters from the set {"S", "M", "L", "F", "-"}
     * @since 3.2
     */
    public void setStylePattern(String stylePattern) {
        this.stylePattern = stylePattern;
    }

    /**
     * Set the TimeZone to normalize the date values into, if any.
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Specify whether or not parsing is to be lenient. Default is false.
     * <p>With lenient parsing, the parser may allow inputs that do not precisely match the format.
     * With strict parsing, inputs must match the format exactly.
     */
    public void setLenient(boolean lenient) {
        this.lenient = lenient;
    }


    @Override
    public String print(Date date, Locale locale) {
        return getDateFormat(locale).format(date);
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if(null == text || "".equals(text)){
            return null;
        }
        try {
            getDateFormat(locale).parse(text);
        } catch (Exception e) {
            getSimpleDateFormat(locale).parse(text);
        }
        return getDateFormat(locale).parse(text);
    }

    protected DateFormat getSimpleDateFormat(Locale locale) {
        DateFormat dateFormat = createSimpleDateFormat(locale);
        if (this.timeZone != null) {
            dateFormat.setTimeZone(this.timeZone);
        }
        dateFormat.setLenient(this.lenient);
        return dateFormat;
    }

    protected DateFormat getDateFormat(Locale locale) {
        DateFormat dateFormat = createDateFormat(locale);
        if (this.timeZone != null) {
            dateFormat.setTimeZone(this.timeZone);
        }
        dateFormat.setLenient(this.lenient);
        return dateFormat;
    }

    private DateFormat createSimpleDateFormat(Locale locale) {
        // todo no pattern format and other pattern format
//        if (StringUtils.hasLength(this.pattern)) {
        switch (this.pattern) {
            case "yyyy-MM-dd HH:mm:ss.S":
            case "yyyy-MM-dd HH:mm:ss":
                this.pattern = "yyyy-MM-dd";
                break;
        }
        return new SimpleDateFormat(this.pattern, locale);
//        }
    }

    private DateFormat createDateFormat(Locale locale) {
        if (StringUtils.hasLength(this.pattern)) {
            return new SimpleDateFormat(this.pattern, locale);
        }
        if (this.iso != null && this.iso != DateTimeFormat.ISO.NONE) {
            String pattern = ISO_PATTERNS.get(this.iso);
            if (pattern == null) {
                throw new IllegalStateException("Unsupported ISO format " + this.iso);
            }
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setTimeZone(UTC);
            return format;
        }
        if (StringUtils.hasLength(this.stylePattern)) {
            int dateStyle = getStylePatternForChar(0);
            int timeStyle = getStylePatternForChar(1);
            if (dateStyle != -1 && timeStyle != -1) {
                return DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
            }
            if (dateStyle != -1) {
                return DateFormat.getDateInstance(dateStyle, locale);
            }
            if (timeStyle != -1) {
                return DateFormat.getTimeInstance(timeStyle, locale);
            }
            throw new IllegalStateException("Unsupported style pattern '" + this.stylePattern + "'");

        }
        return DateFormat.getDateInstance(this.style, locale);
    }

    private int getStylePatternForChar(int index) {
        if (this.stylePattern != null && this.stylePattern.length() > index) {
            switch (this.stylePattern.charAt(index)) {
                case 'S':
                    return DateFormat.SHORT;
                case 'M':
                    return DateFormat.MEDIUM;
                case 'L':
                    return DateFormat.LONG;
                case 'F':
                    return DateFormat.FULL;
                case '-':
                    return -1;
            }
        }
        throw new IllegalStateException("Unsupported style pattern '" + this.stylePattern + "'");
    }
}