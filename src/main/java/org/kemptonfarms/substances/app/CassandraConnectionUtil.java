package org.kemptonfarms.substances.app;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;

import com.netflix.astyanax.thrift.ThriftFamilyFactory;

public class CassandraConnectionUtil {
      public static AstyanaxContext createConnection() {
          AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                  .forKeyspace("hsdbKeyspace")
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
}
