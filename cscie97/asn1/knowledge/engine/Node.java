//Node.java
// Brian Mason brm3153@g.harvard.edu
//9/17/2024

package cscie97.asn1.knowledge.engine;

/*
    The public class Node provides a constructor to retrieve
    the identifier.
 */
public class Node {

    private final String identifier;
    private final long createDate;

    public Node(String identifier) {
        this.identifier = identifier;
        this.createDate = System.currentTimeMillis() / 1000L;
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getCreateDate() {
        return createDate;
    }
}
