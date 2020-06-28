## Explore Zurich:
Developed as a project for the "Semantic Technologies and Linked Data" course, “Explore Zurich” is an offline application to discover the tourist attractions of Zurich. Without indicating a specific location, the user can look for bars, restaurants, museums, shops and tourist attractions by district. Furthermore, a brief description of the points of interest is displayed, along with opening hours and address. Moreover, public transportation data is included, allowing the user to discover the location of the train stations and the stops offered by the city. The information can be visualized in a map in the homepage, which contains also a filter panel on the side, to indicate which district and what types of facilities to display.

## Project configuration
### Requirements - Java Application
- IntelliJ (or any other IDE that support Maven projects)
- Java 11

### Requirements - Python Script (not necessary to run the application)
- csv library
- json library
- shapely library
- geopandas library

### Installation steps:
- Open the project in Intellij IDEA
- Select _File > Project Structure..._ and ensure that the Project SDK and language level are set to use Java 11.
- Open the Maven view with _View > Tool Windows > Maven_.
- In the Maven view, under _Plugins > dependency_, double-click the `dependency:unpack` goal. This will unpack the native libraries into $USER_HOME/.arcgis.
- In the Maven view, run the `compile` phase under _Lifecycle_ and then the `exec:java` goal to run the app.
- Run the main method in the src/main/java/Main.java file
