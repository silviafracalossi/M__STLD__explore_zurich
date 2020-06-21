
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.*;

public class Lab05Test {

    public static void main(String[] args) throws Exception {

        Repository repo = new SailRepository(
                new ForwardChainingRDFSInferencer(
                        new MemoryStore()
                )
        );
        repo.initialize();

        File ontologyFile = new File("D:/Uni/repositories/rdf4j_example/turtle_files/L4_tourism-owl.ttl");

        try (RepositoryConnection connection = repo.getConnection()) {
            // Loading the data
            connection.add(ontologyFile, "http://example.org/inst/", RDFFormat.TURTLE);

            String sparqlQuery = "PREFIX i: <http://example.org/inst/>\n\n" +
                    "SELECT ?s ?p ?o \n" +
                    "WHERE {\n" +
                    "   ?s ?p ?o .\n" +
                    "   VALUES ?s { i:museion i:chickenHut }\n" +
                    "}";

            System.out.println(sparqlQuery);

            try (TupleQueryResult result = connection.prepareTupleQuery(sparqlQuery).evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    System.out.printf("%s %s %s\n",
                            bindingSet.getBinding("s"),
                            bindingSet.getBinding("p"),
                            bindingSet.getBinding("o"));
                }
            }
        }
    }
}
