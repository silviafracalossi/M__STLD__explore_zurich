import com.taxonic.carml.engine.RmlMapper;
import com.taxonic.carml.logical_source_resolver.CsvResolver;
import com.taxonic.carml.logical_source_resolver.JsonPathResolver;
import com.taxonic.carml.logical_source_resolver.XPathResolver;
import com.taxonic.carml.model.TriplesMap;
import com.taxonic.carml.util.RmlMappingLoader;
import com.taxonic.carml.vocab.Rdf;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Set;

public class Carml {

    public static void main(String[] args) throws IOException {

        Set<TriplesMap> mapping =
                RmlMappingLoader
                        .build()
                        .load(RDFFormat.TURTLE, Paths.get("D:/Uni/repositories/stld_explore_zurich/mapping/mapping.ttl"));

        RmlMapper mapper =
                RmlMapper
                        .newBuilder()
                        // Add the resolvers to suit your need
                        //.setLogicalSourceResolver(Rdf.Ql.JsonPath, new JsonPathResolver())
                        //.setLogicalSourceResolver(Rdf.Ql.XPath, new XPathResolver())
                        .setLogicalSourceResolver(Rdf.Ql.Csv, new CsvResolver())
                        // optional:
                        // specify IRI unicode normalization form (default = NFC)
                        // see http://www.unicode.org/unicode/reports/tr15/tr15-23.html
                        .iriUnicodeNormalization(Normalizer.Form.NFKC)
                        // set file directory for sources in mapping
                        .fileResolver(Paths.get("D:/Uni/repositories/stld_explore_zurich/elaborated/"))
                        // set classpath basepath for sources in mapping
                        .classPathResolver("D:/Uni/repositories/stld_explore_zurich/elaborated/")
                        .build();

        Model result = mapper.map(mapping);



    }
}
