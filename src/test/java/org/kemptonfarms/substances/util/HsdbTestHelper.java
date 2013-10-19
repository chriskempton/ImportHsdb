package org.kemptonfarms.substances.util;

import org.kemptonfarms.substances.model.Substance;

import java.util.List;

public class HsdbTestHelper {

    public static void verifySubstances(List<Substance> substances) {
        for(Substance substance:substances) {
            verifySubstance(substance);
        }
    }

    public static void verifySubstance(Substance substance) {
        assert(substance.getId().length() > 0);
        assert(substance.getName().length() > 0);
        assert(substance.getMolecularFormula().length() > 0);
        assert(substance.getMeltingPoint() != null);
        assert(substance.getBoilingPoint() != null);
    }

}
