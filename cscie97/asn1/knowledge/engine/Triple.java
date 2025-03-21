//Triple.java
// Brian Mason brm3153@g.harvard.edu
//9/17/2024

package cscie97.asn1.knowledge.engine;

/*
    The class Triple represents the data structure that forms between
    the subject, predicate, and object.
 */
public class Triple {
    private final Node subject;
    private final Predicate predicate;
    private final Node object;
    private final String identifier;
    private final long createDate;

    public Triple(Node subject, Predicate predicate, Node object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
        this.identifier = subject.getIdentifier() + "_" + predicate.getIdentifier() + "_" + object.getIdentifier();
        this.createDate = System.currentTimeMillis() / 1000L;
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String toString() {
        return subject.getIdentifier() + " " + predicate.getIdentifier() + " " + object.getIdentifier();
    }
}
