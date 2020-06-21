import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Lab02Test {

    public static void main(String[] args) throws IOException {

        // String inputFilePath = "D:/Uni/repositories/rdf4j_example/turtle_files/L2_01_silvia.ttl";

        String inputFilePath = "D:/Uni/repositories/rdf4j_example/turtle_files/silvia.owl";
        InputStream inputStream = new FileInputStream(inputFilePath);

        String baseIRI = "file://" + inputFilePath;
        Model model = Rio.parse(inputStream, baseIRI, RDFFormat.TURTLE);


        ValueFactory factory = SimpleValueFactory.getInstance();
        IRI bob = factory.createIRI("http://example.org/bob");
        Statement typeStatement = factory.createStatement(bob,
                factory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                FOAF.PERSON);
        //Statement typeStatement = factory.createStatement(bob, RDF.TYPE, FOAF.PERSON);
        model.add(typeStatement);

        IRI name = factory.createIRI("http://example.org/name");
        Literal bobsName = factory.createLiteral("Bob");
        //Statement nameStatement =  factory.createStatement(bob, name, bobsName);
        //model.add(nameStatement);
        model.add(bob, name, bobsName);

        for(Statement statement : model) {
            System.out.println(statement.toString());
        }

        String turtleOutputFile = "D:/Uni/repositories/rdf4j_example/turtle_files/L2_output.ttl";
        Rio.write(model, new FileOutputStream(turtleOutputFile), RDFFormat.TURTLE);

        String xmlOutputFile = "D:/Uni/repositories/rdf4j_example/turtle_files/L2_output.xml";
        Rio.write(model, new FileOutputStream(xmlOutputFile), RDFFormat.RDFXML);
    }
}
