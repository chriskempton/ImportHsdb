package org.kemptonfarms.substances.util;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.BadRequestException;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;

import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

public class CassandraConnectionUtil {
    private static final ColumnFamily<String, String> CF_SUBSTANCES = ColumnFamily
            .newColumnFamily("HazardousSubstances", StringSerializer.get(),
                    StringSerializer.get());

    public static AstyanaxContext createConnection() {
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

    public static Keyspace getKeyspace() {
        AstyanaxContext<Keyspace> ctx = createConnection();
        ctx.start();
        Keyspace keyspace = ctx.getClient();

        return keyspace;
    }

    public static ColumnFamily<String, String> initializeColumnFamily() {
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

    public static ColumnFamily<String, String> getColumnFamily() {
        return CF_SUBSTANCES;
    }
}
