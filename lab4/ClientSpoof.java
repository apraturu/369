/**
 * Varsha Roopreddy and Anusha Praturu
 * CPE 369 - Secion 03
 * Lab 4
 * ClientSpoof.java
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ClientSpoof {
   public static void main(String[] args) {
      String line, jsonStr = "", mongo, dbName, collName = "", clientLog = "",
       user;
      int port, delay, count = 0;
      long collSize, userMsgCount;
      JSONObject config, msg;
      Logger logger;
      MongoClient client;
      MongoDatabase db;
      MongoCollection<Document> coll;
      DateFormat df;
      Date date;
      Writer writer;

      if(args.length != 1) {
         System.err.println("USAGE: java -cp "
          + "'.:org.json-20120521.jar:mongo-java-driver-3.2.0.jar' ClientSpoof"
          + " <configFile>");
         System.exit(-1);
      }

      try {
         BufferedReader reader = new BufferedReader(new FileReader(args[0]));
         line = reader.readLine();
         while (line != null) {
            jsonStr += line;
            line = reader.readLine();
         }
         config = new JSONObject(jsonStr);
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
         } catch (Exception e) {
            System.err.println("Error: must specify collection name in "
             + "configuration file.");
            System.exit(-1);
         }
         try {
            delay = config.getInt("delay");
         } catch (JSONException e) {
            delay = 10;
         }
         delay = (delay == 0 ? 10 : delay);
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
         
         writer = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(clientLog), "utf-8"));
         logger = Logger.getLogger("org.mongodb.driver");
         logger.setLevel(Level.OFF);

         client = new MongoClient(mongo);
         db = client.getDatabase(dbName);
         coll = db.getCollection(collName);
         collSize = coll.count();
         df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         date = new Date();

         printStartup(writer, df.format(date), mongo, port, dbName, collName, collSize);
         
         while(true) {
            try {
               Thread.sleep(1000 * delay);
            } catch(InterruptedException ex) {
               Thread.currentThread().interrupt();
            }
            msg = MessageGen.nextMessage();
            df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            date = new Date();
            System.out.println("\n" + df.format(date));
            coll.insertOne(Document.parse(msg.toString()));
            System.out.println(msg.toString(3));

            writer.write("\n" + df.format(date) + "\n");
            writer.write(msg.toString(3) + "\n");
            writer.flush();

            count++;
            if(count == 40) {
               collSize = coll.count();
               user = msg.getString("user");
               userMsgCount = coll.count(new Document("user", user));
               
               System.out.println("\n***********************************\n");
               System.out.println("collection size: " + collSize);
               System.out.println("total number of messages sent by user " 
                + user + ": " + userMsgCount);
               System.out.println("\n***********************************");

               writer.write("\n***********************************\n\n");
               writer.write("collection size: " + collSize + "\n");
               writer.write("total number of nessages sent by user " + user +
                ": " + userMsgCount + "\n\n");
               writer.write("***********************************\n");
               writer.flush();

               count = 0;
            }
         }
      }
      catch (Exception e) {
         e.printStackTrace();
         System.exit(-1);
      }
   }
   
   public static void printStartup(Writer writer, String date, String mongo, 
    int port, String db, String coll, long size) {
      System.out.println("************* STARTUP DIAGNOSTICS *************\n");
      System.out.println(date + "\n");
      System.out.println("MongoDB server connection details:");
      System.out.println("   client: " + mongo);
      System.out.println("   port: " + port + "\n");
      System.out.println("   database: " + db);
      System.out.println("   collection: " + coll + "\n");
      System.out.println("collection size: " + size + "\n");
      System.out.println("***********************************************");
      
      try {
         writer.write("************* STARTUP DIAGNOSTICS *************\n\n");
         writer.write(date + "\n\n");
         writer.write("MongoDB server connection details:\n");
         writer.write("   client: " + mongo + "\n");
         writer.write("   port: " + port + "\n\n");
         writer.write("   database: " + db + "\n");
         writer.write("   collection: " + coll + "\n\n");
         writer.write("collection size: " + size + "\n\n");
         writer.write("***********************************************\n");
         writer.flush();
      }
      catch (IOException e) {
         System.err.println("Error: could not write to log file.");
         System.exit(-1);
      }
   }
}

