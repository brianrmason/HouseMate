package cscie97.asn2.housemate.test;

// TestDriver.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

import cscie97.asn2.housemate.model.CommandLineInterface;
import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn3.housemate.controller.ControllerService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Scanner;

/*
 * Public class with main method to test the HouseMateModelService
 */
public class TestDriver {
    // Method to generate a 20-character authToken
    public static String generateAuthToken() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder authToken = new StringBuilder(20);

        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(charSet.length());
            authToken.append(charSet.charAt(index));
        }

        return authToken.toString();
    }

    /*
     * Public main method to get an instance of the KnowledgeGraph and
     * take in the commands from the housesetup.txt file.
     */
    public static void main(String[] args) {
        String authToken = generateAuthToken();
        System.out.println("Generated authToken: " + authToken);

        // Create an instance of KnowledgeGraph
        KnowledgeGraph knowledgeGraph = KnowledgeGraph.getInstance();

        // Pass the KnowledgeGraph instance to HouseMateModelService
        HouseMateModelService modelService = new HouseMateModelService(knowledgeGraph);
        CommandLineInterface cli = new CommandLineInterface(modelService, authToken);
        ControllerService controllerService = new ControllerService(modelService);

        // Attach ControllerService as an observer to HMMS
        modelService.attach(controllerService);

        // Use Scanner to ask the user for the file name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the setup file name (e.g., housesetup.txt): ");
        String fileName = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Pass each line of the file to the CLI for execution
                try {
                    cli.executeCommand(line);
                } catch (Exception e) {
                    System.out.println("Error executing command: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
            e.printStackTrace();
        }
    }
}


