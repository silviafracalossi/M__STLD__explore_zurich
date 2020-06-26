
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

    public DataLoader() {
        repo = new SailRepository(
                new ForwardChainingRDFSInferencer(
                        new MemoryStore()
                )
        );
        repo.initialize();

        // Loading data file
        data_file = new File("D:/Uni/repositories/stld_explore_zurich/resources/data/04_RDF/data.rdf");
    }

    // Retrieving all the district areas
    public List<BindingSet> getDistrictAreas() throws Exception {

        // Creating result list variable
        List<BindingSet> result_list;

        // Making sure the connection is working
        try (RepositoryConnection connection = repo.getConnection()) {
            connection.add(data_file, "http://example.org/inst/", RDFFormat.RDFXML);

            // Formulating SPARQL Query
            String sparqlQuery = "\n" +
                    "PREFIX i: <http://example.org/inst/>\n" +
                    "PREFIX : <http://example.org/term/>\n\n" +
                    "SELECT ?d ?d_area \n" +
                    "WHERE {\n" +
                    "   ?d :area ?d_area .\n" +
                    "}" +
                    "\n";

            // Evaluating the query and retrieving the results
            try (TupleQueryResult query_result = connection.prepareTupleQuery(sparqlQuery).evaluate()) {
                result_list = QueryResults.asList(query_result);
            }

            return result_list;
        }
    }
}