package org.kemptonfarms.substances.app;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnList;

import org.kemptonfarms.substances.model.*;
import org.kemptonfarms.substances.util.CassandraConnectionUtil;
import org.kemptonfarms.substances.util.HsdbDataUtil;

public class DataReader {

    public static void main(String[] args) {
        try {
            Keyspace keyspace = CassandraConnectionUtil.getKeyspace();

            for(Substance substance: HsdbDataUtil.getSubstancesFromXml())
            {
                String k = substance.getId();
                OperationResult<ColumnList<String>> result =
                        keyspace.prepareQuery(CassandraConnectionUtil.getColumnFamily())
                                .getKey(k)
                                .execute();
                ColumnList<String> columns = result.getResult();

                for (Column<String> c : result.getResult()) {
                    System.out.println(c.getName());
                    System.out.println(c.getStringValue());
                }
            }

        } catch (Exception e) {
            // some exception occurred
            e.printStackTrace();
        }
    }
}