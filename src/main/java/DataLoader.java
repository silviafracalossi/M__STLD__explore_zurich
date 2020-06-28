
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.*;
import java.util.List;

public class DataLoader {

    private static Repository repo;
    private static File data_file;
    private List<BindingSet> districts_result_list = null;
    private RepositoryConnection connection;

    public DataLoader() {

        System.out.println("Configuring the connection to the data file...");

        repo = new SailRepository(
                new ForwardChainingRDFSInferencer(
                        new MemoryStore()
                )
        );
        repo.initialize();

        // Defining the data file
        data_file = new File("D:/Uni/repositories/stld_explore_zurich/resources/data/04_RDF/data.rdf");

        // Connecting to file
        try {
            connection = repo.getConnection();
            connection.add(data_file, "http://example.org/inst/", RDFFormat.RDFXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Returns polygon of specified district
    public BindingSet getDistrictById (int district_id) {

        // Creating result list variable
        List<BindingSet> result_list = null;
        String district_reference = "<http://example.com/base/district/"+district_id+">";

        // Formulating SPARQL Query
        String sparqlQuery = "" +
                "PREFIX : <http://example.org/term/>\n" +
                "SELECT ?d_area \n" +
                "WHERE {\n" +
                    "\t" +district_reference+ " :area ?d_area .\n" +
                "}" + "\n";

        // Evaluating the query and retrieving the results
        System.out.println("\n[INFO] Query in getDistrictById");
        System.out.println(sparqlQuery);
        try (TupleQueryResult query_result = connection.prepareTupleQuery(sparqlQuery).evaluate()) {
            result_list = QueryResults.asList(query_result);
        }

        return (result_list != null) ? result_list.get(0) : null;
    }


    // Retrieving all the district areas
    public List<BindingSet> getDistrictAreas() {

        if (districts_result_list != null) {
            return districts_result_list;
        }

        // Creating result list variable
        List<BindingSet> result_list = null;

        // Formulating SPARQL Query
        String sparqlQuery = "" +
                "PREFIX : <http://example.org/term/>\n" +
                "SELECT ?d ?d_area \n" +
                "WHERE {\n" +
                "   ?d :area ?d_area .\n" +
                "   ?d rdf:type :District .\n" +
                "}" + "\n";


        // Evaluating the query and retrieving the results
        System.out.println("\n[INFO] Query in getDistrictAreas");
        System.out.println(sparqlQuery);
        try (TupleQueryResult query_result = connection.prepareTupleQuery(sparqlQuery).evaluate()) {
            result_list = QueryResults.asList(query_result);
        }

        // Saving the list as global variable to retrieve it without querying data again
        districts_result_list = result_list;

        return result_list;
    }


    // Return the POIs and Facilities requested by the user
    public List<BindingSet> getMarkerData(int district_id, String filters) {

        // Creating result list variable
        List<BindingSet> result_list = null;

        // Formulating SPARQL Query
        String sparqlQuery = "" +
                "PREFIX : <http://example.org/term/>\n" +
                "SELECT ";

        // Filter by district
        String district_reference = "?d";
        if (district_id != 0) {
            district_reference = "<http://example.com/base/district/"+district_id+">";
        }

        // Adding poi condition
        sparqlQuery += "?poi ?nam ?descr ?locat \n" +
                "WHERE { \n" +
                "\t ?poi rdf:type :PointOfInterest . \n" +
                "\t ?poi :poiIn "+district_reference+" . \n" +
                "\t ?poi :location ?locat . \n";

        // Adding district specification
        if (district_id == 0) {
            sparqlQuery += "\t ?d rdf:type :District .\n";
        }

        // TODO!
        // this.filters += (pubtrans_check.isSelected()) ? "t" : "";
        // this.filters += (parking_check.isSelected()) ? "p" : "";

        // Adding class specification based on filters
        if (!filters.contains("r")) sparqlQuery += "\t FILTER NOT EXISTS {?poi rdf:type :Restaurant} \n";
        if (!filters.contains("b")) sparqlQuery += "\t FILTER NOT EXISTS {?poi rdf:type :Bar} \n";
        if (!filters.contains("m")) sparqlQuery += "\t FILTER NOT EXISTS {?poi rdf:type :Museum} \n";
        if (!filters.contains("a")) sparqlQuery += "\t FILTER NOT EXISTS {?poi rdf:type :Attraction} \n";
        if (!filters.contains("s")) sparqlQuery += "\t FILTER NOT EXISTS {?poi rdf:type :Shop} \n";

        // Adding limit of 25
        sparqlQuery += "} \n"
                + "LIMIT 20 \n";

        // Evaluating the query and retrieving the results
        System.out.println("\n[INFO] Query in getMarkerData");
        System.out.println(sparqlQuery);
        try (TupleQueryResult query_result = connection.prepareTupleQuery(sparqlQuery).evaluate()) {
            result_list = QueryResults.asList(query_result);
        }

        return result_list;
    }


    // Retrieving the POI information of the given IRI
    public BindingSet getPoiByIRI(String IRI, String instance_class) {

        // Creating result list variable
        List<BindingSet> result_list = null;
        String reference = "<"+IRI+">";

        // Formulating SPARQL Query
        String sparqlQuery = "" +
                "PREFIX : <http://example.org/term/>\n" +
                "SELECT * \n" +
                "WHERE {\n" +
                "\t " +reference+ " rdf:type :"+instance_class+" .\n" +
                "\t " +reference+ " :name ?nam . \n" +
                "\t OPTIONAL {" +reference+ " :description ?descr } \n" +
                "\t OPTIONAL {" +reference+ " :address ?addr } \n" +
                "\t OPTIONAL {" +reference+ " :openingHours ?oh } \n" +
                "}" +
                "\n";

        // Evaluating the query and retrieving the results
        System.out.println("\n[INFO] Query in getPOIbyIRI");
        System.out.println(sparqlQuery);
        try (TupleQueryResult query_result = connection.prepareTupleQuery(sparqlQuery).evaluate()) {
            result_list = QueryResults.asList(query_result);
        }

        return (result_list != null) ? result_list.get(0) : null;
    }
}
