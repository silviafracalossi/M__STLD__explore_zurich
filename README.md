## Explore Zurich
Project for the "Semantic Technologies and Linked Data" course.

## Project configuration
### Requirements
- IntelliJ (or any other IDE that support Maven projects)
- Java 11

#### For the python file execution (which is not necessary to run the application)
- csv library
- json library
- shapely library
- geopandas library

### Installation steps:
- Open the project in Intellij IDEA
- Set Project SDK and language level to Java 11.
- Open the Maven view with _View > Tool Windows > Maven_.
- In the Maven view, under _Plugins > dependency_, double-click the `dependency:unpack` goal. This will unpack the native libraries into $USER_HOME/.arcgis.
- In the Maven view, run the `compile` phase under _Lifecycle_ and then the `exec:java` goal to run the app.
- Run the main method in the src/main/java/Main.java file
