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
    CREATE (helene:User {name:"Helene"}) RETURN helene;
    MATCH (max:User),(helene:User)
    WHERE max.name = "Max" AND helene.name = "Helene"
    CREATE (max)-[r:KNOWS]->(al)
    RETURN r;
    MATCH (helene:User)
    WHERE helene.name = "Helene"
    SET helene :Suspect
    RETURN helene;

