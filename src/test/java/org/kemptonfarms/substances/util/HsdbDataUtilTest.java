package org.kemptonfarms.substances.util;

import org.junit.Before;
import org.junit.Test;
import org.kemptonfarms.substances.app.DataLoader;
import org.kemptonfarms.substances.model.Substance;

import java.util.ArrayList;

public class HsdbDataUtilTest {

    @Before
    public void setUp() {
        DataLoader.main(new String[0]);
    }

    @Test
    public void testGetSubstancesFromXml() throws Exception {
        ArrayList<Substance> substances = HsdbDataUtil.getSubstancesFromXml();
        assert(substances.size() == 117);
        verifySubstances(substances);
    }

    @Test
    public void testGetSubstancesFromCassandra() throws Exception {
        ArrayList<Substance> substances = HsdbDataUtil.getSubstancesFromCassandra();
        assert(substances.size() == 117);
        verifySubstances(substances);
    }

    @Test
    public void testGetSubstanceFromCassandra() throws Exception {
        verifySubstance(HsdbDataUtil.getSubstanceFromCassandra("108-87-2"));
    }

    private void verifySubstances(ArrayList<Substance> substances) {
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
