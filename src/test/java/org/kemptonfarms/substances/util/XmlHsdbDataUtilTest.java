package org.kemptonfarms.substances.util;

import org.junit.Before;
import org.junit.Test;
import org.kemptonfarms.substances.app.DataLoader;
import org.kemptonfarms.substances.model.Substance;

import java.util.List;

public class XmlHsdbDataUtilTest {
    private IHsdbDataUtil hsdbDataUtil;

    @Before
    public void setUp() {
        hsdbDataUtil = new XmlHsdbDataUtil();
        DataLoader.main(new String[0]);
    }

    @Test
    public void testGetSubstances() throws Exception {
        List<Substance> substances = hsdbDataUtil.getSubstances();
        assert(substances.size() == 117);
        HsdbTestHelper.verifySubstances(substances);
    }

    @Test
    public void testGetSubstance() throws Exception {
        HsdbTestHelper.verifySubstance(hsdbDataUtil.getSubstance("108-87-2"));
    }
}
