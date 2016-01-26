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
import org.json.*;

public class ClientSpoof {
   public static void main(String[] args) throws JSONException {
      System.out.println(MessageGen.nextMessage().toString(3));
   }
}

