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
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ClientSpoof {
   public static void main(String[] args) {
      String line, jsonStr = "";
      String mongo, dbName, collName = "", clientLog = "", user;
      int port, delay, count = 0;
      JSONObject config, msg;
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
         
         logger = Logger.getLogger("org.mongodb.driver");
         logger.setLevel(Level.OFF);

         client = new MongoClient(mongo);
         db = client.getDatabase(dbName);
         coll = db.getCollection(collName);

         
         //coll.insertOne(Document.parse(MessageGen.nextMessage().toString()));
         System.out.println("************* STARTUP DIAGNOSTICS *************");
         System.out.println();
         DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         Date date = new Date();
         System.out.println(df.format(date));
         System.out.println();
         System.out.println("MongoDB server connection details:");
         System.out.println("   client: " + mongo);
         System.out.println("   port: " + port);
         System.out.println();
         System.out.println("   database: " + dbName);
         System.out.println("   collection: " + collName);
         System.out.println();
         System.out.println("collection size: " + coll.count());


         System.out.println();
         System.out.println("***********************************************");

         while(true) {
            try {
               Thread.sleep(1000 * delay);
            } catch(InterruptedException ex) {
               Thread.currentThread().interrupt();
            }
            msg = MessageGen.nextMessage();
            df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            date = new Date();
            System.out.println();
            System.out.println(df.format(date));
            coll.insertOne(Document.parse(msg.toString()));
            System.out.println(msg.toString(3));
            count++;
            if(count == 40) {
               System.out.println();
               System.out.println("***********************************");
               System.out.println();
               System.out.println("collection size: " + coll.count());
               user = msg.getString("user");
               System.out.println("total number of messages sent by user " 
                + user + ": " + coll.count(new Document("user", user)));
               System.out.println();
               System.out.println("***********************************");
               count = 0;
            }
         }
      }
      catch (Exception e) {
         e.printStackTrace();
         System.exit(-1);
      }
      


   }
}

