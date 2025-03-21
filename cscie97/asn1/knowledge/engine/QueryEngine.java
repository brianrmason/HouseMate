//QueryEngine.java
// Brian Mason brm3153@g.harvard.edu
//9/17/2024

package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class QueryEngine {

    private String sampleQuery;
    private final String sampleQueryFile;

    public QueryEngine(String sampleQuery) {
        this.sampleQuery = sampleQuery;
        this.sampleQueryFile = sampleQuery;
    }

    /*
        Public method for executing a single query on the knowledge graph.
        Checks for non-null and well-formed query string. Throws QueryEngineException
        on error
     */
    public void executeQuery() throws QueryEngineException {
        if (Objects.isNull(sampleQuery) || sampleQuery.isBlank()) {
            throw new QueryEngineException("Query cannot be null or blank" + sampleQuery);
        }

        String[] queryParts = sampleQuery.split("\\s");
        if (queryParts.length != 3) {
            throw new QueryEngineException("Incorrect number of items in query" + sampleQuery);
        }

        String subject = queryParts[0].equals("?") ? null : queryParts[0];
        String predicate = queryParts[1].equals("?") ? null : queryParts[1];
        String object = queryParts[2].equals("?") ? null : queryParts[2];

        KnowledgeGraph knowledgeGraph = KnowledgeGraph.getInstance();
        Set<Triple> result = knowledgeGraph.executeQuery(subject, predicate, object);

        System.out.println(sampleQuery);

        if (result == null || result.isEmpty()) {
            System.out.println("<null>");
        } else {
            for (Triple triple : result) {
                System.out.println(triple);
            }
        }
    }

    /*
        Public method for executing a set of queries read from a file. Checks for valid
        file nam and delegates to executeQuery for processing individual queries. Throws
        a QueryEngineException on error
    */
    public void executeQueryFile() throws QueryEngineException {
        if (Objects.isNull(sampleQueryFile) || sampleQueryFile.isBlank()) {
            throw new QueryEngineException("Query file name cannot be null or blank");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(sampleQueryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sampleQuery = line.trim();
                if (!sampleQuery.isBlank()) {
                    executeQuery();
                }
            }
        } catch (FileNotFoundException e) {
            throw new QueryEngineException("File not found: " + sampleQueryFile, e);
        } catch (IOException e) {
            throw new QueryEngineException("I/O error while reading the query file: " + sampleQueryFile, e);
        }
    }
}
