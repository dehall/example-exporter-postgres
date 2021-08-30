package org.mitre.synthea.exporters;

import org.mitre.synthea.engine.Generator;
import org.mitre.synthea.export.Exporter.ExporterRuntimeOptions;
import org.mitre.synthea.export.PostCompletionExporter;

// note that it is possible for a class to implement both interfaces,
// but when loaded in synthea there will be 2 separate instances
public class SamplePostCompletionExporter implements PostCompletionExporter {

  public SamplePostCompletionExporter() {
  }

  @Override
  public void export(Generator generator, ExporterRuntimeOptions options) {
    System.out.println("running post completion");
    // Fill in details here...
  }
}
