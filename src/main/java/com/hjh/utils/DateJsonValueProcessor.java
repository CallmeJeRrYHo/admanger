package com.hjh.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private DateFormat dateFormat;

    public DateJsonValueProcessor(String datePattern) {
        if (null == datePattern) {
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            this.dateFormat = new SimpleDateFormat(datePattern);
        }

    }

    public DateJsonValueProcessor() {
    }

    public Object processArrayValue(Object arg0, JsonConfig arg1) {
        return this.process(arg0);
    }

    public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
        return this.process(arg1);
    }

    private Object process(Object value) {
        return value != null ? this.dateFormat.format((Date)value) : null;
    }
}
