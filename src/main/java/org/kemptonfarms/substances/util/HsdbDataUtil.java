package org.kemptonfarms.substances.util;


import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import org.kemptonfarms.substances.model.Hsdb;
import org.kemptonfarms.substances.model.Substance;

import javax.xml.bind.*;
import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class HsdbDataUtil {
    private static EntityManager<Substance, String> getSubstanceEntityManager() {
        return new DefaultEntityManager.Builder<Substance, String>()
                .withEntityType(Substance.class)
                .withKeyspace(CassandraConnectionUtil.getKeyspace())
                .withColumnFamily(CassandraConnectionUtil.getColumnFamily())
                .build();
    }

    public static ArrayList<Substance> getSubstancesFromXml() {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Hsdb.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("data/hsdb.xml");

            // this will create Java object - country from the XML file
            Hsdb hazardousSubstancesDb = (Hsdb) jaxbUnmarshaller.unmarshal(XMLfile);

            return hazardousSubstancesDb.getSubstances();
        } catch (JAXBException e) {
            System.out.println("Error creating Substance objects from XML");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Substance> getSubstancesFromCassandra() {
        ArrayList<Substance> substances = new ArrayList<Substance>();
        try {
            Keyspace keyspace = CassandraConnectionUtil.getKeyspace();

            Rows<String, String> result = null;
            result = keyspace.prepareQuery(CassandraConnectionUtil.getColumnFamily())
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

    public static Substance getSubstanceFromCassandra(String key) {
        try {
            final EntityManager<Substance, String> entityManager = getSubstanceEntityManager();
            return entityManager.get(key);
        } catch (Exception e) {
            System.out.println("Error creating a Substance object from Cassandra lookup");
            e.printStackTrace();
        }
        return null;
    }
}
