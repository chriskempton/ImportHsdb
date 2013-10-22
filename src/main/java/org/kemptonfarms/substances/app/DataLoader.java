package org.kemptonfarms.substances.app;

import org.kemptonfarms.substances.util.*;

public class DataLoader {

    /*
        see HsdbDataUtilTest.setUp();
     */
    public static void main(String[] args) {
        try {
            IHsdbDataUtil outputDataUtil = CassandraHsdbDataUtilFactory.getInstance();
            IHsdbDataUtil inputDataUtil = new XmlHsdbDataUtil();

            outputDataUtil.putSubstances(inputDataUtil.getSubstances());
        } catch (Exception e) {
            // some exception occurred
            e.printStackTrace();
        }
    }
}