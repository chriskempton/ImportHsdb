package org.kemptonfarms.substances.app;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.entitystore.EntityManager;
import com.netflix.astyanax.entitystore.DefaultEntityManager;

import com.netflix.astyanax.model.ColumnFamily;
import org.kemptonfarms.substances.model.*;
import org.kemptonfarms.substances.util.CassandraConnectionUtil;
import org.kemptonfarms.substances.util.HsdbUtil;

import java.util.Map;
import java.util.HashMap;

public class DataLoader {

    public static void main(String[] args) {
        try {
            Keyspace keyspace = CassandraConnectionUtil.getKeyspace();
            ColumnFamily<String, String> columnFamily = CassandraConnectionUtil.initializeColumnFamily();

            int i=0;
            final Map<String, Substance> entities = new HashMap<String, Substance>();
            for(Substance substance: HsdbUtil.getSubstances())
            {
                i++;
                System.out.println("Substance #"+i);
                System.out.println("Name="+substance.getName());
                entities.put(Integer.toString(i), substance);
            }
            EntityManager<Substance, String> entityPersister = new DefaultEntityManager.Builder<Substance, String>()
                    .withEntityType(Substance.class)
                    .withKeyspace(keyspace)
                    .withColumnFamily(columnFamily)
                    .build();
            entityPersister.put(entities.values());
        } catch (Exception e) {
            // some exception occurred
            e.printStackTrace();
        }
    }
}