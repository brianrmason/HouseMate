//Importer.java
// Brian Mason brm3153@g.harvard.edu
//9/17/2024

package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
    The class Importer contains the importTripleFile method
    which takes in a file and imports it to the KnowledgeGraph
 */
public class Importer {

    private final String filename;

    //Constructor
    public Importer(String filename) {
            this.filename = filename;
    }

    /*
        Public method for reading the lines of an input file, then validating
        the file before importing the file to the knowledgeGraph. The method
        throws an ImportException if invalid triple format is detected.
     */
    public void importTripleFile() throws ImportException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            KnowledgeGraph knowledgeGraph = KnowledgeGraph.getInstance();

            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase();
                String[] words = line.split("\\s");

                if (line.isEmpty()) {
                    continue;
                }

                if (words.length != 3) {
                    throw new ImportException("Invalid triple format in file: " + line);
                }

                String subject = words[0];
                String predicate = words[1];
                String object = words[2];

                knowledgeGraph.importTriple(subject, predicate, object);
            }

        } catch (FileNotFoundException e) {
            throw new ImportException("File not found: " + filename, e);
        } catch (IOException e) {
            throw new ImportException("Error while reading the query file: " + filename, e);
        }
    }

}

