package org.mitre.synthea.exporters;

import org.mitre.synthea.export.Exporter.ExporterRuntimeOptions;
import org.mitre.synthea.export.PatientExporter;
import org.mitre.synthea.world.agents.Person;

// note that it is possible for a class to implement both interfaces,
// but when loaded in synthea there will be 2 separate instances
public class SamplePatientExporter implements PatientExporter {

  public SamplePatientExporter() {
  }
  
  @Override
  public void export(Person person, long stopTime, ExporterRuntimeOptions options) {
    System.out.println("Exporting " + person.attributes.get(Person.NAME));
    // fill in details here...
  }
}
