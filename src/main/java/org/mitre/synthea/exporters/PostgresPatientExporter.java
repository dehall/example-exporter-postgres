package org.mitre.synthea.exporters;

import static org.mitre.synthea.export.ExportHelper.dateFromTimestamp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.mitre.synthea.export.Exporter.ExporterRuntimeOptions;
import org.mitre.synthea.export.PatientExporter;
import org.mitre.synthea.helpers.Config;
import org.mitre.synthea.world.agents.Person;
import org.mitre.synthea.world.concepts.HealthRecord;
import org.mitre.synthea.world.concepts.HealthRecord.Encounter;
import org.mitre.synthea.world.concepts.HealthRecord.Entry;

public class PostgresPatientExporter implements PatientExporter {

  private final String jdbcConnectionString;
  private final String username;
  private final String password;
  
  public PostgresPatientExporter() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new Error(e);
    }
    
    jdbcConnectionString = Config.get("exporter.postgres.connection_string"); // something like jdbc:postgresql://localhost/
    username = Config.get("exporter.postgres.username");
    password = Config.get("exporter.postgres.password");
  }
  
  private Connection getConnection() throws SQLException {
    Properties props = new Properties();
    props.setProperty("user", username);
    props.setProperty("password", password);
    return DriverManager.getConnection(jdbcConnectionString, props);
  }
  
  @Override
  public void export(Person person, long stopTime, ExporterRuntimeOptions options) {
    System.out.println("Exporting " + person.attributes.get(Person.NAME));
    try (Connection conn = getConnection()) {
      
      patient(person, stopTime, conn);

      for (Encounter encounter : person.record.encounters) {
        for (HealthRecord.Entry condition : encounter.conditions) {
            condition((String)person.attributes.get(Person.ID), encounter.fullUrl, condition, conn);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void patient(Person person, long time, Connection conn) throws SQLException {
    PreparedStatement ps = conn.prepareStatement("INSERT INTO patients (ID, BIRTHDATE, FIRSTNAME, LASTNAME)  VALUES (?, ?, ?, ?)");
    
    int index = 1; // note sql always starts at index 1 for some reason
    
    ps.setString(index++, (String) person.attributes.get(Person.ID));
    ps.setString(index++, dateFromTimestamp((long) person.attributes.get(Person.BIRTHDATE)));
    ps.setString(index++, (String) person.attributes.get(Person.FIRST_NAME));
    ps.setString(index++, (String) person.attributes.get(Person.LAST_NAME));
    
    ps.executeUpdate();
    ps.close();
  }
  
  private void condition(String personID, String encounterID, Entry condition, Connection conn) throws SQLException {
    PreparedStatement ps = conn.prepareStatement("INSERT INTO conditions (START_DATE, STOP_DATE, PATIENT, CODE)  VALUES (?, ?, ?, ?)");
    
    int index = 1; // note sql always starts at index 1 for some reason
    
    ps.setString(index++, dateFromTimestamp(condition.start));
    ps.setString(index++, dateFromTimestamp(condition.stop));
    ps.setString(index++, personID);
    ps.setString(index++, condition.codes.get(0).code);
    
    ps.executeUpdate();
    ps.close();
  }
  
}
