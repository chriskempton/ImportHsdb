package org.kemptonfarms.substances.util;

import org.kemptonfarms.substances.model.Substance;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class DataStaxHsdbDataUtil implements IHsdbDataUtil {
    private Cluster cluster;
    private Session session;

    @Override
    public Substance getSubstance(String key) {
        connect();
        ResultSet results = session.execute("SELECT * FROM hazards2.HazardousSubstances WHERE id = '" + key + "';");
        for (Row row : results) {
            List<String> synList = new ArrayList<String>();
            synList.addAll(row.getSet("synonyms", String.class));

            List<String> useList = new ArrayList<String>();
            useList.addAll(row.getSet("majoruses", String.class));
            Substance substance = new Substance();
            substance.setId(key);
            substance.setName(row.getString("name"));
            substance.setMolecularFormula(row.getString("molecularformula"));
            substance.setBoilingPoint(row.getString("boilingpoint"));
            substance.setMeltingPoint(row.getString("meltingpoint"));
            substance.setSynonyms(synList);
            substance.setMajorUses(useList);
            close();
            return substance;
        }
        close();
        return null;
    }

    @Override
    public List<Substance> getSubstances() {
        connect();
        ResultSet results = session.execute("SELECT * FROM hazards2.HazardousSubstances;");
        List<Substance> substances = new ArrayList<Substance>();
        for (Row row : results) {
            List<String> synList = new ArrayList<String>();
            synList.addAll(row.getSet("synonyms", String.class));

            List<String> useList = new ArrayList<String>();
            useList.addAll(row.getSet("majoruses", String.class));

            Substance substance = new Substance();
            substance.setId(row.getString("id"));
            substance.setName(row.getString("name"));
            substance.setMolecularFormula(row.getString("molecularformula"));
            substance.setBoilingPoint(row.getString("boilingpoint"));
            substance.setMeltingPoint(row.getString("meltingpoint"));
            substance.setSynonyms(synList);
            substance.setMajorUses(useList);
            substances.add(substance);
        }
        close();
        return substances;
    }

    @Override
    public void putSubstances(List<Substance> substances) throws Exception {
        initializeTable();
        connect();
        PreparedStatement statement = session.prepare(
                "INSERT INTO hazards2.HazardousSubstances " +
                        "(id, name, molecularformula, boilingpoint, meltingpoint, synonyms, majoruses) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);");
        for(Substance substance:substances) {
            session.execute(statement.bind(substance.getId(),
                    substance.getName(),
                    substance.getMolecularFormula(),
                    substance.getBoilingPoint(),
                    substance.getMeltingPoint(),
                    new HashSet<String>(substance.getSynonyms()),
                    new HashSet<String>(substance.getMajorUses())));
        }
        close();
    }

    private void initializeTable() {
        connect();
        try {
            session.execute("DROP KEYSPACE hazards2");
            session.execute("DROP TABLE hazards2.HazardousSubstances");
        } catch (Exception e) {
            // Allow for exception if table doesn't already exist
        }
        session.execute("CREATE KEYSPACE hazards2 WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':3};");
        session.execute(
                "CREATE TABLE hazards2.HazardousSubstances (" +
                        "id text PRIMARY KEY," +
                        "name text," +
                        "molecularformula text," +
                        "boilingpoint text," +
                        "meltingpoint text," +
                        "synonyms set<text>," +
                        "majoruses set<text>" +
                        ");");
        close();
    }

    private void connect() {
        cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .build();
        session = cluster.connect();
    }

    private void close() {
        cluster.shutdown();
    }
}
