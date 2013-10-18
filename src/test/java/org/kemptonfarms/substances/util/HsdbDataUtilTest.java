package org.kemptonfarms.substances.util;

import org.junit.Before;
import org.junit.Test;
import org.kemptonfarms.substances.app.DataLoader;
import org.kemptonfarms.substances.model.Substance;

import java.util.List;

public class HsdbDataUtilTest {

    //TODO Create separate test classes for each implementation of IHsdbDataUtil interface
    @Before
    public void setUp() {
        DataLoader.main(new String[0]);
    }

    @Test
    public void testGetSubstancesFromXml() throws Exception {
        IHsdbDataUtil hsdbDataUtil = new XmlHsdbDataUtil();
        List<Substance> substances = hsdbDataUtil.getSubstances();
        assert(substances.size() == 117);
        verifySubstances(substances);
    }

    @Test
    public void testGetSubstancesFromCassandra() throws Exception {
        IHsdbDataUtil hsdbDataUtil = new AstyanaxHsdbDataUtil();
        List<Substance> substances = hsdbDataUtil.getSubstances();
        assert(substances.size() == 117);
        verifySubstances(substances);
    }

    @Test
    public void testGetSubstanceFromCassandra() throws Exception {
        IHsdbDataUtil hsdbDataUtil = new AstyanaxHsdbDataUtil();
        verifySubstance(hsdbDataUtil.getSubstance("108-87-2"));
    }

    private void verifySubstances(List<Substance> substances) {
        for(Substance substance:substances) {
            verifySubstance(substance);
        }
    }

    private void verifySubstance(Substance substance) {
        assert(substance.getId().length() > 0);
        assert(substance.getName().length() > 0);
        assert(substance.getMolecularFormula().length() > 0);
        assert(substance.getMeltingPoint() != null);
        assert(substance.getBoilingPoint() != null);
    }
}
