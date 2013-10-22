package org.kemptonfarms.substances.util;

import java.io.FileInputStream;
import java.util.Properties;

public abstract class CassandraHsdbDataUtilFactory {
    public static IHsdbDataUtil getInstance() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("config.properties"));
        } catch (Exception e) {
            return new AstyanaxHsdbDataUtil();
        }
        if(props.getProperty("cassandra.provider") != null && props.getProperty("cassandra.provider").equals("DataStax")) {
            return new DataStaxHsdbDataUtil();
        }
        return new AstyanaxHsdbDataUtil();
    }
}
