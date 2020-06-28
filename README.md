## Explore Zurich
Project for the "Semantic Technologies and Linked Data" course.

## Project configuration
### Requirements
- Make sure you have the following python libraries installed:
   - csv
   - json
   - shapely
   - geopandas
- The project was developed in Intellij. It was necessary to:
    - set Project SDK and language level to Java 11.

### Installation steps:
- Open the project in Intellij IDEA
- Open the Maven view with _View > Tool Windows > Maven_.
- In the Maven view, under _Plugins > dependency_, double-click the `dependency:unpack` goal. This will unpack the native libraries into $USER_HOME/.arcgis.
- In the Maven view, run the `compile` phase under _Lifecycle_ and then the `exec:java` goal to run the app.
- Run the main method in the src/main/java/Main.java file
