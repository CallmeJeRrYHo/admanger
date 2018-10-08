package com.hjh.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class EmptyUtils {
    private EmptyUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        } else if (obj instanceof Collection && ((Collection)obj).isEmpty()) {
            return true;
        } else {
            return obj instanceof Map && ((Map)obj).isEmpty();
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
