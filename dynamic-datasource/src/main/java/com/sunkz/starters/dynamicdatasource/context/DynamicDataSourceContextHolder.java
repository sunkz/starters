package com.sunkz.starters.dynamicdatasource.context;

import com.sunkz.starters.dynamicdatasource.constants.DataSourceConstants;

public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<>();

    public static void setContextKey(String key) {
        DATASOURCE_CONTEXT_KEY_HOLDER.set(key);
    }

    public static String getContextKey() {
        String key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        return key == null ? DataSourceConstants.DS_KEY_MASTER : key;
    }

}
