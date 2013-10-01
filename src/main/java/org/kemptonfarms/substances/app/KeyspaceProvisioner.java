package org.kemptonfarms.substances.app;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

import com.google.common.collect.ImmutableMap;

public class KeyspaceProvisioner {

    public static ColumnFamily<String, String> CF_STANDARD1 = ColumnFamily
            .newColumnFamily("Standard", StringSerializer.get(),
                    StringSerializer.get());

    public static void main(String[] args) {
        try {
            AstyanaxContext<Keyspace> ctx = new AstyanaxContext.Builder()
                    .forKeyspace(args[0]+"Keyspace").buildKeyspace(ThriftFamilyFactory.getInstance());
            ctx.start();
            Keyspace keyspace = ctx.getClient();
            keyspace.createKeyspace(ImmutableMap.<String, Object>builder()
                    .put("strategy_options", ImmutableMap.<String, Object>builder()
                            .put("replication_factor", "1")
                            .build())
                    .put("strategy_class","SimpleStrategy")
                    .build()
            );

            keyspace.createColumnFamily(CF_STANDARD1, null);
        } catch (Exception e) {
            // some exception occured
            e.printStackTrace();
        }
    }
}