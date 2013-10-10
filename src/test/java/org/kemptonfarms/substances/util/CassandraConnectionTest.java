package org.kemptonfarms.substances.util;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.model.ColumnFamily;

import org.junit.Test;
import org.junit.Assert;

import org.kemptonfarms.substances.util.CassandraConnectionUtil;

public class CassandraConnectionTest {
    @Test
    public void testGetColumnFamily() throws Exception {
        ColumnFamily<String, String> columnFamily = CassandraConnectionUtil.getColumnFamily();
        assert(columnFamily instanceof ColumnFamily);
        assert(columnFamily.getName().equals("HazardousSubstances"));
    }

    @Test
    public void testInitializeColumnFamily() throws Exception {
        ColumnFamily<String, String> columnFamily = CassandraConnectionUtil.initializeColumnFamily();
        assert(columnFamily instanceof ColumnFamily);
        assert(columnFamily.getName().equals("HazardousSubstances"));
    }

    @Test
    public void testGetKeyspace() throws Exception {
        assert(CassandraConnectionUtil.getKeyspace() instanceof Keyspace);
    }
}
