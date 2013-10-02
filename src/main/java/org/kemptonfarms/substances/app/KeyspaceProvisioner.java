package org.kemptonfarms.substances.app;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;

import com.google.common.collect.ImmutableMap;
import org.kemptonfarms.substances.util.CassandraConnection;

public class KeyspaceProvisioner {

    public static void main(String[] args) {
        try {
            AstyanaxContext<Keyspace> ctx = CassandraConnection.createConnection();
            ctx.start();
            Keyspace keyspace = ctx.getClient();
            keyspace.createKeyspace(ImmutableMap.<String, Object>builder()
                    .put("strategy_options", ImmutableMap.<String, Object>builder()
                            .put("replication_factor", "1")
                            .build())
                    .put("strategy_class","SimpleStrategy")
                    .build()
            );
        } catch (Exception e) {
            // some exception occured
            e.printStackTrace();
        }
    }
}