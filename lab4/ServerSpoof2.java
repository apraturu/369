/**
 * Varsha Roopreddy and Anusha Praturu
 * CPE 369 - Secion 03
 * Lab 4
 * ServerSpoof.java
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.*;
import java.util.*;
import com.mongodb.Block;
import com.mongodb.client.MongoIterable;

public class ServerSpoof2 {
    public static MongoCollection<Document> coll;
    public static MongoCollection<Document> monColl;
    public static MongoIterable<String> distUsers;
    public static Iterator userIter;
    public static long uniqueUsers, numNewMsg;
    public static void main(String[] args) {
      Logger logger;
      MongoClient client;
      MongoDatabase db;
      String line, wordLine,jsonStr = "";
      ArrayList<String> words = new ArrayList<String>();
      ArrayList<Long> msgTotals, userTotals, newMsgTotals;
      MonitorTotals mt;
      String[] names = {"recordType", "msgTotals", "userTotals", "newMsgTotals"};
      String mongo, dbName, collName = "", monName = "", wordFilter = "", serverLog = "";
      int port, delay, newMessages = 0, checkpoint = 1;
      long uniqueMessages, publicNum, protectedNum, privateNum, allNum, selfNum;
      long subscribersNum, userIdNum; 
      JSONObject config;

      if(args.length != 1) {
         System.err.println("USAGE: java -cp "
          + "'.:org.json-20120521.jar:mongo-java-driver-3.2.0.jar' ServerSpoof"
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
            wordFilter = config.getString("wordFilter");
            if(wordFilter.equals("")) {
               throw new IllegalArgumentException();
            }
         } catch (Exception e) {
            System.err.println("Error: must specify valid word filter file to "
             + "generate messages.");
            System.exit(-1);
         }
         try {
            serverLog = config.getString("serverLog");
            if(serverLog.equals("")) {
               throw new IllegalArgumentException();
            }
         }
         catch (Exception e) {
            System.err.println("Error: must specify valid file name to "
             + "output server log.");
            System.exit(-1);
         }

      //Establish the connection with MongoDB server
         logger = Logger.getLogger("org.mongodb.driver");
         logger.setLevel(Level.OFF);

         client = new MongoClient(mongo);
         db = client.getDatabase(dbName);
         coll = db.getCollection(collName);

         System.out.println("===XXXXXXXXXX==========");
         
         long collectionCount = coll.count();
         System.out.println("Collection Count: " + collectionCount);
      
         //Wipe the contents of monitor.
         monColl = db.getCollection(monName);
         monColl.drop();

         //read contents if word filter into data struct
         try {
            FileReader fileReader = new FileReader(wordFilter);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
               words.add(line);
            }
         }    
         catch (Exception e) {
            System.out.println(e);
            System.exit(1);
         }
         //print startup diognostics
         DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         Date date = new Date();
         System.out.println(df.format(date));
         System.out.println();
         System.out.println("MongoDB server connection details:");
         System.out.println("   client: " + mongo);
         System.out.println("   port: " + port);
         System.out.println();
         System.out.println("   database: " + dbName);
         System.out.println("   collection: " + collName);
         System.out.println();
         System.out.println("collection size: " + coll.count());
         //Main loop   
         while(true) {
            Thread.sleep(3000 * delay);
            uniqueMessages = coll.count();
            distUsers = (MongoIterable<String>)(coll.distinct("user", String.class));
            userIter = distUsers.iterator();
            for(uniqueUsers = 0; userIter.hasNext(); ++uniqueUsers) {
               userIter.next();
            }
            publicNum = coll.count(new Document("status", "public"));
            privateNum = coll.count(new Document("status", "private"));
            protectedNum = coll.count(new Document("status", "protected"));
            allNum = coll.count(new Document("recipient", "all"));
            selfNum = coll.count(new Document("recipient", "self"));;
            subscribersNum = coll.count(new Document("recipient", "subscribers"));

            userIdNum = coll.count(new Document("recipient", new Document("$nin", new BsonArray(Arrays.asList(new BsonString("all"), new BsonString("self"), new BsonString("subscribers"))))));
            
            String monitorString = "{" +
                                    "\"time\": " + "\""+ df.format(date) + "\""+
                                    ",\"messages\": " + uniqueMessages +
                                    ",\"users\": " + uniqueUsers +
                                    ",\"new\": " + newMessages +
                                    ",\"statusStats\": [" + "{\"public\":" + publicNum + "}," +
                                                            "{\"private\":" + privateNum + "}," +
                                                            "{\"protected\":" + protectedNum + "}]" +
                                    ",\"recipientStats\": [" + "{\"all\":" + allNum + "}," +
                                                            "{\"self\":" + selfNum + "}," +
                                                            "{\"subscribers\":" + subscribersNum + "},"+
                                                            "{\"userId\":" + userIdNum + "}]}"; 
            System.out.println(monitorString);
            JSONObject monitor = new JSONObject(monitorString);

            monColl.insertOne(Document.parse(monitor.toString()));
            System.out.println(monitor.toString(3));
            //word search
            //TODO: how to use regex to go through all the new messages
            //TODO: how do we get the new messages via checkpoints??
            if(checkpoint == 1) {
               msgTotals = new ArrayList<Long>();
               msgTotals.add(uniqueMessages);
               userTotals = new ArrayList<Long>();
               userTotals.add(uniqueUsers);
               newMsgTotals = new ArrayList<Long>();
               numNewMsg = coll.count();
               newMsgTotals.add(numNewMsg);
               mt = new MonitorTotals("monitor totals", msgTotals, userTotals, newMsgTotals);
               //create new monitor total JSON object and add to collection
               JSONObject monitorTotals = new JSONObject(mt, names);
               System.out.println(monitorTotals.toString(3));
               monColl.insertOne(Document.parse(monitorTotals.toString()));
             }
             else {
               //every checkpoint after get the record and call updateMonitorTotals
               FindIterable<Document> result = monColl.find(new Document("recordType", "monitor totals"));
               result.forEach(new Block<Document>() {        // print each retrieved document
                @Override
                  public void apply(final Document d) {
                     JSONObject newMonTotals;
                     JSONArray tempArr;
                     long newCollSize = coll.count();
                     try {
                        JSONObject j = new JSONObject(d.toJson());
                        newMonTotals = new JSONObject();
                        tempArr = j.getJSONArray("msgTotals");
                        tempArr.put(newCollSize);
                        newMonTotals.put("recordType", "monitor totals");
                        newMonTotals.put("msgTotals", tempArr);
                        tempArr = j.getJSONArray("userTotals");
                        distUsers = (MongoIterable<String>)(coll.distinct("user", String.class));
                        userIter = distUsers.iterator();
                        for(uniqueUsers = 0; userIter.hasNext(); ++uniqueUsers) {
                           userIter.next();
                        }
                        tempArr.put(uniqueUsers); 
                        newMonTotals.put("userTotals", tempArr);
                        tempArr = j.getJSONArray("newMsgTotals");
                        tempArr.put(newCollSize - numNewMsg);
                        numNewMsg = newCollSize;
                        newMonTotals.put("newMsgTotals", tempArr);

                        monColl.replaceOne(new Document("recordType", "monitor totals"), Document.parse(newMonTotals.toString(3)));
                        //monColl.insertOne(Document.parse(newMonTotals.toString()));

                        System.out.println(newMonTotals.toString(3));
                     }
                     catch(Exception e) {
                        System.out.println(e);
                        System.exit(1);
                     }
                  }

               });
             }
             checkpoint++;
         }   
      }
      catch (Exception e) {
         e.printStackTrace();
         System.exit(-1);
      }

   }

   public static void updateMonTotals(JSONObject obj) {
      
   }
}
