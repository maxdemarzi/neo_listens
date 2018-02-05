package com.maxdemarzi;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.event.LabelEntry;
import org.neo4j.graphdb.event.TransactionData;

import java.util.HashSet;
import java.util.Set;

public class SuspectRunnable implements Runnable {

    private static TransactionData td;
    private static GraphDatabaseService db;

    public SuspectRunnable (TransactionData transactionData, GraphDatabaseService graphDatabaseService) {
        td = transactionData;
        db = graphDatabaseService;
    }

    @Override
    public void run() {
        try (Transaction tx = db.beginTx()) {
            Set<Node> suspects = new HashSet<>();
            for (Node node : td.createdNodes()) {
                if (node.hasLabel(Labels.Suspect)) {
                    suspects.add(node);
                    //GmailSender.sendEmail("maxdemarzi@gmail.com", "A new Suspect has been created in the System!", "boo-yeah");
                    System.out.println("A new Suspect has been created!");
                }
            }

            for (LabelEntry labelEntry : td.assignedLabels()) {
                if (labelEntry.label().name().equals(Labels.Suspect.name()) && !suspects.contains(labelEntry.node())) {
                    System.out.println("A new Suspect has been identified!");
                    suspects.add(labelEntry.node());
                }
            }

            for (Relationship relationship : td.createdRelationships()) {
                if (relationship.isType(RelationshipTypes.KNOWS)) {
                    for (Node user : relationship.getNodes()) {
                        if (user.hasLabel(Labels.Suspect)) {
                            System.out.println("A new direct relationship to a Suspect has been created!");
                        }

                        for (Relationship knows : user.getRelationships(Direction.BOTH, RelationshipTypes.KNOWS)) {
                            Node otherUser = knows.getOtherNode(user);
                            if (otherUser.hasLabel(Labels.Suspect) && !otherUser.equals(relationship.getOtherNode(user))) {
                                System.out.println("A new indirect relationship to a Suspect has been created!");
                            }
                        }
                    }
                }
            }
        }
    }
}
