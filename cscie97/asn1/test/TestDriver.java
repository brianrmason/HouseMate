//TestDriver.java
// Brian Mason brm3153@g.harvard.edu
//9/17/2024

package cscie97.asn1.test;

import cscie97.asn1.knowledge.engine.*;

import java.util.Scanner;

/*
    The class TestDriver contains the main method to demonstrate the
    capabilities of the KnowledgeGraph class.
 */
public class TestDriver {

    public static void main(String[] args) throws QueryEngineException, ImportException {
        //Scanner to get the input triple file
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter Input Triple File: ");

        String inputFile = myObj.nextLine();

        Importer importer = new Importer(inputFile);

        importer.importTripleFile();


        //Scanner to get sample query file
        System.out.println("Enter Query File: ");
        String sampleQueryFile = myObj.nextLine();

        QueryEngine queryEngineFile = new QueryEngine(sampleQueryFile);
        queryEngineFile.executeQueryFile();

        myObj.close();
    }

}
