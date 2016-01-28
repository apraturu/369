/**
 * Varsha Roopreddy and Anusha Praturu
 * CPE 369 - Secion 03
 * Lab 4
 * ClientSpoof.java
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ClientSpoof {
   public static void main(String[] args) {
      String line, jsonStr = "";
      String mongo, dbName, collName = "", monName, words, clientLog = "";
      int port, delay;
      JSONObject config;
      Logger logger;
      MongoClient client;
      MongoDatabase db;
      MongoCollection<Document> coll;

      if(args.length != 1) {
         System.err.println("USAGE: java -cp "
          + "'.:org.json-20120521.jar:mongo-java-driver-3.2.0.jar' ClientSpoof"
          + " <configFile>");
         System.exit(-1);
      }

      //System.out.println(MessageGen.nextMessage().toString(3));
      
      try {
         BufferedReader reader = new BufferedReader(new FileReader(args[0]));
         line = reader.readLine();
         while (line != null) {
            jsonStr += line;
            line = reader.readLine();
         }
         config = new JSONObject(jsonStr);
         //System.out.println(config.toString(3));
         try {
            mongo = config.getString("mongo");
         } catch (JSONException e) {
            mongo = "localhost";
         }
         mongo = (mongo.equals("") ? "localhost" : mongo);
         try {
            port = config.getInt("port");
         } catch (JSONException e) {
            port = 27017;
         }
         try {
            dbName = config.getString("database");
         } catch (JSONException e) {
            dbName = "test";
         }
         try {
            collName = config.getString("collection");
            monName = config.getString("monitor");
            if(collName.equals(monName)) {
               throw new IllegalArgumentException();
            }
         } catch (Exception e) {
            System.err.println("Error: must specify collection and monitor "
             + "names in configuration file. Names must be different.");
            System.exit(-1);
         }
         try {
            delay = config.getInt("delay");
         } catch (JSONException e) {
            delay = 10;
         }
         delay = (delay == 0 ? 10 : delay);
         try {
            words = config.getString("words");
            if(words.equals("")) {
               throw new IllegalArgumentException();
            }
         } catch (Exception e) {
            System.err.println("Error: must specify valid word file to "
             + "generate messages.");
            System.exit(-1);
         }
         try {
            clientLog = config.getString("clientLog");
            if(clientLog.equals("")) {
               throw new IllegalArgumentException();
            }
         }
         catch (Exception e) {
            System.err.println("Error: must specify valid file name to "
             + "output client log.");
            System.exit(-1);
         }
         
         logger = Logger.getLogger(clientLog);
         logger.setLevel(Level.OFF);

         client = new MongoClient(mongo);
         db = client.getDatabase(dbName);
         coll = db.getCollection(collName);

         coll.insertOne(Document.parse(MessageGen.nextMessage().toString()));

         FindIterable<Document> result = coll.find();
         result.forEach(new Block<Document>() {
            @Override
            public void apply(final Document d) {
               System.out.println(d);
            }
         });

         System.out.println("****************** SUCCESS ******************");
      }
      catch (Exception e) {
         e.printStackTrace();
         System.exit(-1);
      }
      


   }
}

