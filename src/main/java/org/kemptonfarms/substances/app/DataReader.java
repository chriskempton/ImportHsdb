package org.kemptonfarms.substances.app;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnList;

import org.kemptonfarms.substances.model.*;
import org.kemptonfarms.substances.util.CassandraConnection;

import java.util.ArrayList;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class DataReader {

    public static final ColumnFamily<String, String> CF_SUBSTANCES = ColumnFamily
            .newColumnFamily("HazardousSubstances", StringSerializer.get(),
                    StringSerializer.get());

    public static void main(String[] args) {
        try {
            AstyanaxContext<Keyspace> ctx = CassandraConnection.createConnection();
            ctx.start();
            Keyspace keyspace = ctx.getClient();

            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Hsdb.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("data/hsdb.xml");

            // this will create Java object - country from the XML file
            Hsdb hazardousSubstancesDb = (Hsdb) jaxbUnmarshaller.unmarshal(XMLfile);

            ArrayList<Substance> hazardousSubstances = hazardousSubstancesDb.getSubstances();

            int i=0;
            for(Substance substance:hazardousSubstances)
            {
                i++;
                String k = "substance"+i;
                OperationResult<ColumnList<String>> result =
                        keyspace.prepareQuery(CF_SUBSTANCES)
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