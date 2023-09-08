This repo is a toy example of a custom Synthea™ exporter.

Run the following command to produce the jar file:
```sh
./gradlew jar
```

The jar file will be created at `./build/libs/example-exporter-postgres.jar`.

## Usage
For the basics of running Synthea, please refer to [Basic Setup and Running](https://github.com/synthetichealth/synthea/wiki/Basic-Setup-and-Running)
or [Developer Setup and Running](https://github.com/synthetichealth/synthea/wiki/Developer-Setup-and-Running) on the Synthea wiki.

If you are using the "Basic Setup" of Synthea, add your .jar file to the classpath and run Synthea as follows:


```sh
java -jar synthea-with-dependencies.jar -cp path/to/example-exporter-postgres.jar.jar
```

Other options may be appended to the end of the command as usual.


If you are using the "Developer Setup" of Synthea, add example-exporter-postgres.jar to its `./lib/custom/` directory, and run synthea with `./run_synthea`, with other options appended to the end of the command as usual.
