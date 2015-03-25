Neo_Listens
===========

Sample Event Listener / Triggers


1. Build it:

        mvn clean package

2. Copy target/triggers-1.0.jar to the plugins/ directory of your Neo4j server.

3. Start your Neo4j Server

4. Run these queries, tailing the graph.db/log/console.log file:

        CREATE (max:User {name:"Max"}) RETURN max;

        CREATE (al:Suspect {name:"Al Capone"}) RETURN al;

        MATCH (max:User),(al:Suspect)
        WHERE max.name = "Max" AND al.name = "Al Capone"
        CREATE (max)-[r:KNOWS]->(al)
        RETURN r;

        CREATE (monica:User {name:"Monica"}) RETURN monica;

        MATCH (max:User),(monica:User)
        WHERE max.name = "Max" AND monica.name = "Monica"
        CREATE (max)-[r:KNOWS]->(monica)
        RETURN r;

        MATCH (monica:User)
        WHERE monica.name = "Monica"
        SET monica :Suspect
        RETURN monica;

5. You should see:

        A new Suspect has been created!
        A new direct relationship to a Suspect has been created!
        A new indirect relationship to a Suspect has been created!
        A new Suspect has been identified!

