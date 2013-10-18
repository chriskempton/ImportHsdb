package org.kemptonfarms.substances.app;

import org.kemptonfarms.substances.util.AstyanaxHsdbDataUtil;
import org.kemptonfarms.substances.util.IHsdbDataUtil;
import org.kemptonfarms.substances.util.XmlHsdbDataUtil;

public class DataLoader {

    /*
        see HsdbDataUtilTest.setUp();
     */
    public static void main(String[] args) {
        try {
            IHsdbDataUtil outputDataUtil = new AstyanaxHsdbDataUtil();
            IHsdbDataUtil inputDataUtil = new XmlHsdbDataUtil();

            outputDataUtil.putSubstances(inputDataUtil.getSubstances());
        } catch (Exception e) {
            // some exception occurred
            e.printStackTrace();
        }
    }
}