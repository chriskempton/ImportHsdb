package org.kemptonfarms.substances.app;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.BadRequestException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;

import org.kemptonfarms.substances.model.*;
import org.kemptonfarms.substances.util.CassandraConnection;

import java.util.ArrayList;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class DataLoader {

    public static final ColumnFamily<String, String> CF_SUBSTANCES = ColumnFamily
            .newColumnFamily("HazardousSubstances", StringSerializer.get(),
                    StringSerializer.get());

    public static void main(String[] args) {
        try {
            AstyanaxContext<Keyspace> ctx = CassandraConnection.createConnection();
            ctx.start();
            Keyspace keyspace = ctx.getClient();

            //Initialize column family
            try {
                keyspace.dropColumnFamily(CF_SUBSTANCES);
            } catch (BadRequestException e) {
                // Continue even if column family drop fails due to non-existing family
            }
            keyspace.createColumnFamily(CF_SUBSTANCES, null);
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
                System.out.println("Substance #"+i);
                System.out.println("Name="+substance.getName());

                MutationBatch m = keyspace.prepareMutationBatch();

                m.withRow(CF_SUBSTANCES, k)
                        .putColumn("name", substance.getName(), null)
                        .putColumn("meltingpoint", substance.getMeltingPoint(), null)
                        .putColumn("boilingpoint", substance.getBoilingPoint(), null)
                        .putColumn("molecularformula", substance.getMolecularFormula(), null);

                try {
                    OperationResult<Void> result = m.execute();

                } catch (ConnectionException e) {
                }
            }

        } catch (Exception e) {
            // some exception occurred
            e.printStackTrace();
        }
    }
}