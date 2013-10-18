package org.kemptonfarms.substances.util;


import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;
import org.kemptonfarms.substances.model.Substance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AstyanaxHsdbDataUtil implements IHsdbDataUtil {
    private static EntityManager<Substance, String> getSubstanceEntityManager() {
        return new DefaultEntityManager.Builder<Substance, String>()
                .withEntityType(Substance.class)
                .withKeyspace(getKeyspace())
                .withColumnFamily(getColumnFamily())
                .build();
    }

    @Override
    public List<Substance> getSubstances() {
        ArrayList<Substance> substances = new ArrayList<Substance>();
        try {
            Keyspace keyspace = getKeyspace();

            Rows<String, String> result = null;
            result = keyspace.prepareQuery(getColumnFamily())
                    .getAllRows()
                    .execute()
                    .getResult();

            final EntityManager<Substance, String> entityManager = getSubstanceEntityManager();
            for (Row<String, String> row : result) {
                substances.add(entityManager.get(row.getKey()));
            }
        } catch (ConnectionException e) {
            System.out.println("Error creating Substance objects from Cassandra lookup");
            e.printStackTrace();
        }
        return substances;
    }

    @Override
    public Substance getSubstance(String key) {
        try {
            final EntityManager<Substance, String> entityManager = getSubstanceEntityManager();
            return entityManager.get(key);
        } catch (Exception e) {
            System.out.println("Error creating a Substance object from Cassandra lookup");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void putSubstances(List<Substance> substances) {
        try {
            Keyspace keyspace = getKeyspace();
            ColumnFamily<String, String> columnFamily = initializeColumnFamily();

            int i=0;
            final Map<String, Substance> entities = new HashMap<String, Substance>();
            for(Substance substance: substances)
            {
                i++;
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

    @Override
    public void putSubstance(Substance substance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private static final ColumnFamily<String, String> CF_SUBSTANCES = ColumnFamily
            .newColumnFamily("HazardousSubstances", StringSerializer.get(),
                    StringSerializer.get());

    private static AstyanaxContext createConnection() {
        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forKeyspace("hazards")
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                        .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
                        .setCqlVersion("3.0.0")
                        .setTargetCassandraVersion("1.2")
                )
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool")
                        .setPort(9160)
                        .setMaxConnsPerHost(1)
                        .setSeeds("127.0.0.1:9160")
                )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        return context;
    }

    private static Keyspace getKeyspace() {
        AstyanaxContext<Keyspace> ctx = createConnection();
        ctx.start();
        Keyspace keyspace = ctx.getClient();

        return keyspace;
    }

    private static ColumnFamily<String, String> initializeColumnFamily() {
        Keyspace keyspace = getKeyspace();

        try {
            keyspace.dropColumnFamily(CF_SUBSTANCES);
        } catch (Exception e) {
            // Continue even if column family drop fails due to non-existing family
        }
        try {
            keyspace.createColumnFamily(CF_SUBSTANCES, null);
        } catch (ConnectionException e) {
            System.out.println("Error creating column family");
            return null;
        }
        return CF_SUBSTANCES;
    }

    private static ColumnFamily<String, String> getColumnFamily() {
        return CF_SUBSTANCES;
    }
}
