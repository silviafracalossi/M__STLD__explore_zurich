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

### Data preparation - steps followed:
- Create the folder "resources/data/02_elaborated/"
- Execute the command _python python_elaboration\main.py_ from the main directory. The operation takes roughly 5 minutes. (see result in: resources/data/02_python_elaborated)
- Transform the generated CSV files into SQL (tool: https://sqlizer.io/?utm_source=csv2sql_blog#/)
- Transform the generated JSON files into SQL (tool: https://sqlizer.io/json-to-mysql/?utm_source=json2sql_blog#/)
- Execute the SQL commands into an H2 database (see resources/database/h2_database.\*.db)
- Create the ontology (see resources/ontology/ontology.ttl)
- Create the mapping (see resources/mapping/mapping.ttl)
- Configurate the .properties file so that Ontop can connect to the H2 Database (see resources/database/db_connection.properties)
- Configurate the Ontop installation location in the bash file (see generate_rdf.sh)
- Execute the bash file (make sure the H2 server is active but any connection is closed)(see generate_rdf.sh)
- Open the project in Intellij IDEA
- Open the Maven view with _View > Tool Windows > Maven_.
- In the Maven view, under _Plugins > dependency_, double-click the `dependency:unpack` goal. This will unpack the native libraries into $USER_HOME/.arcgis.
- In the Maven view, run the `compile` phase under _Lifecycle_ and then the `exec:java` goal to run the app.
