//KnowledgeGraph.java
// Brian Mason brm3153@g.harvard.edu
//9/17/2024

package cscie97.asn1.knowledge.engine;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
    Public class for managing the set of active Triples
 */
public class KnowledgeGraph {

    //Singleton instance
    private static KnowledgeGraph instance;

    //Maps to store
    private final Map<String, Node> nodeMap;
    private final Map<String, Predicate> predicateMap;
    private final Map<String, Triple> tripleMap;
    private final Map<String, Set<Triple>> queryMapSet;

    private KnowledgeGraph() {
        this.nodeMap = new HashMap<>();
        this.predicateMap = new HashMap<>();
        this.tripleMap = new HashMap<>();
        this.queryMapSet = new HashMap<>();
    }

    /*
        Public method for initializing a single instance of the
        KnowledgeGraph
     */
    public static synchronized KnowledgeGraph getInstance() {
        if (instance == null) {
            instance = new KnowledgeGraph();
        }
        return instance;
    }

    /*
        Public method for adding a Triple to the KnowledgeGraph.
     */
    public void importTriple(String subject, String predicate, String object) {
        Node subjectNode = getNode(subject.toLowerCase());
        Predicate predicateEdge = getPredicate(predicate.toLowerCase());
        Node objectNode = getNode(object.toLowerCase());

        Triple triple = getTriple(subjectNode, predicateEdge, objectNode);
        tripleMap.put(triple.getIdentifier(), triple);
        System.out.println("Triple created at: " + triple.getCreateDate() +
                " for " + " " + subject + " " + predicate + " " + object);
    }

    /*
        Public method that checks if the query exists in the queryMapSet.
     */
    public Set<Triple> executeQuery(String subject, String predicate, String object) {
        String queryKey = (subject == null ? "?" : subject.toLowerCase()) + " " +
                        (predicate == null ? "?" : predicate.toLowerCase()) + " " +
                        (object == null ? "?" : object.toLowerCase());

        queryKey = queryKey.toLowerCase();

        assert object != null;
        if (object.equals("?")) {
            for (String key : queryMapSet.keySet()) {
                if (key.startsWith(subject + " " + predicate)) {
                    return queryMapSet.get(key);  // Return the matching set of triples
                }
            }
        }

        return queryMapSet.get(queryKey);
    }

    /*
        Public method to get the node object from the nodeMap
     */
    public Node getNode(String attribute) {
        return nodeMap.computeIfAbsent(attribute.toLowerCase(), Node::new);
    }
    /*
        Public method to get the predicate object from the predicateMap
     */
    public Predicate getPredicate(String attribute) {
        return predicateMap.computeIfAbsent(attribute.toLowerCase(), Predicate::new);
    }

    /*
        Public method retrieve an existing triple object if it exists or creating
        a new triple object if it does not exist. The method generates all possible
        query key permutations related to the triple wildcard and then stores
        each permutation in the queryMapSet.
     */
    public Triple getTriple(Node subject, Predicate predicate, Node object) {
        String tripleIdentifier = subject.getIdentifier() + "-" + predicate.getIdentifier() + "-" + object.getIdentifier();

        Triple triple = tripleMap.get(tripleIdentifier);

        if (triple == null) {
            triple = new Triple(subject, predicate, object);
            tripleMap.put(tripleIdentifier, triple);
        }
        String[] permutations = {
                subject.getIdentifier() + " " + predicate.getIdentifier() + " " + object.getIdentifier(),
                subject.getIdentifier() + " " + predicate.getIdentifier() + " ?",
                subject.getIdentifier() + " ? " + object.getIdentifier(),
                subject.getIdentifier() + " ? ?",
                "? " + predicate.getIdentifier() + " " + object.getIdentifier(),
                "? " + predicate.getIdentifier() + " ?",
                "? ? " + object.getIdentifier(),
                "? ? ?"
        };

        for (String key : permutations) {
            queryMapSet.computeIfAbsent(key, k -> new HashSet<>()).add(triple);
        }

        return triple;
    }

    public void changeLightState(String deviceName, String state) {
        String subject = deviceName;
        String predicate = "light_status";
        String object = state;

        importTriple(subject, predicate, object);
    }


    public void updateFireStatus(String smokeDetector, boolean detectedFire) {
        String subject = smokeDetector;
        String predicate = "detected_fire";
        String object = detectedFire ? "true" : "false";

        importTriple(subject, predicate, object);
    }

    public void updateDoorStatus(String deviceName, String door, String state) {
        String subject = deviceName;
        String predicate = door;
        String object = state;

        importTriple(subject, predicate, object);
    }

    public String queryOccupantLocation(String occupantName) {
        String subject = occupantName.toLowerCase();
        String predicate = "lives_in";
        String object = "?";

        // Execute the query to find the location of the occupant
        Set<Triple> resultTriples = executeQuery(subject, predicate, object);

        if (resultTriples != null && !resultTriples.isEmpty()) {
            // Assume we only need the first result if multiple locations are returned
            Triple locationTriple = resultTriples.iterator().next();
            return locationTriple.toString();
        }

        return null;

    }

    public void updateOccupantPresence(String occupantName, String location) {
        clearOccupantPresence(occupantName);

        String subject = occupantName.toLowerCase();
        String predicate = "is_in";
        String object = location.toLowerCase();

        importTriple(subject, predicate, object);

        System.out.println("Updated presence: " + occupantName + " is now in " + location);
    }

    public void clearOccupantPresence(String occupantName) {
        String subject = occupantName.toLowerCase();
        String predicate = "is_in";
        
        queryMapSet.entrySet().removeIf(entry ->
                entry.getKey().startsWith(subject + " " + predicate)
        );

        System.out.println("Cleared location for: " + occupantName);
    }

    public void updateApplianceStatus(String applianceName, String cooking, String complete) {
        String subject = applianceName.toLowerCase();
        String predicateCooking = "cooking_status";
        String objectCooking = cooking.toLowerCase();

        importTriple(subject, predicateCooking, objectCooking);

        String predicateCompletion = "completion_status";
        String objectCompletion = complete.toLowerCase();

        importTriple(subject, predicateCompletion, objectCompletion);

        System.out.println("Updated appliance status: " + applianceName +
                " is " + cooking + " and completion status is " + complete);
    }

    public void updateBeerCountStatus(int currentCount) {
        String subject = "fridge";
        String predicate = "beer_count";
        String object = String.valueOf(currentCount);

        importTriple(subject, predicate, object);

        queryMapSet.entrySet().removeIf(entry ->
                entry.getKey().startsWith(subject + " " + predicate) &&
                        !entry.getValue().contains(new Triple(getNode(subject),
                                getPredicate(predicate), getNode(object)))
        );

        System.out.println("Updated beer count to: " + currentCount);
    }
}
