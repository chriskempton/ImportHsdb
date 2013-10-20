package org.kemptonfarms.substances.util;

import java.util.List;
import org.kemptonfarms.substances.model.Substance;

public interface IHsdbDataUtil {
    public Substance getSubstance(String key);

    public List<Substance> getSubstances();

    public void putSubstances(List<Substance> substances) throws Exception;
}
