//Predicate.java
// Brian Mason brm3153@g.harvard.edu
//9/17/2024

package cscie97.asn1.knowledge.engine;

/*
    The public class Predicate provides a constructor to retrieve
    the identifier.
 */
public class Predicate {

    private final String identifier;
    private final long createDate;

    public Predicate(String identifier) {
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
