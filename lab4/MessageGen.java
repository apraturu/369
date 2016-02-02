/**
 * Varsha Roopreddy and Anusha Praturu
 * CPE 369 - Secion 03
 * Lab 4
 * MessageGen.java
 */

import java.util.Random;
import java.io.*;
import org.json.*;
import java.util.*;
import java.awt.*;

public class MessageGen {
  public static int currMessageId = -1;
  public static Random random = new Random();
  public static JSONObject nextMessage() {
      JSONObject jObject;
      String line = null;
      String[] names = {"messageId", "user", "status", "recipient", "text"};
      ArrayList<String> words = new ArrayList<String>();
      /*Read in the sense file and populate it with all words*/
      try {
         FileReader fileReader = new FileReader("sense.txt");
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         while((line = bufferedReader.readLine()) != null) {
            words.add(line);
         }
      }  
      catch (Exception e) {
         System.out.println(e);
         System.exit(1);
      }
      
      int messageId = generateMessageID();
      String user = generateUser();
      String status = generateStatus();
      String reciever = generateRecipient(status);
      String text = getMessage(words);
      Message m = new Message(messageId, user, status, reciever, text);
      jObject = new JSONObject(m, names);
      if (random.nextInt(2) == 1) {
            try {
               jObject = jObject.put("in-response", getRandomNumber(0, messageId-1));
            }
            catch (Exception e) {
               System.out.println(e);
            }
         }
      return jObject;
   }

   public static int generateMessageID() {
      int messageId = random.nextInt();
      while (messageId <= currMessageId) {
         messageId = random.nextInt();
      }
      currMessageId = messageId;
      return messageId;
   }

   public static String generateUser() {
      int userNum = random.nextInt(10001);
      //userNum cannot be 0
      while (userNum == 0) {
         userNum = random.nextInt(10001);
      }
      String userId = "u" + Integer.toString(userNum);
      return userId;
   }

   public static String generateStatus() {
      int randomInt = random.nextInt(10);
      if(randomInt <=7) {
         return "public";
      }
      else if(randomInt == 8) {
         return "protected";
      }
         return "private";
   }

   public static String generateRecipient(String status) {
      int randomInt;
      /*Messages with the status "private" can have either "self" or a user
      Id as a recepient.*/
      if(status.equals("private")) {
         if(getRandomNumber(0,1) == 0) {
            return "self";
         }
         else {
            return Integer.toString(getRandomNumber(1,10000));
         }
      }
      /*Messages with the status "protected" can have either "self", or
      "subscribers", or a user Id as a recepient.*/
      else if(status.equals("protected")) {
         randomInt = getRandomNumber(1,10);
         if(randomInt <=8) {
            return "subscribers";
         }
         else if(randomInt ==9) {
            return "self";
         }
         else {
            return Integer.toString(getRandomNumber(1,10000));
         }
      }
      /*Messages with the status "public" can have any syntactically legal
      "recepient" value.*/
      else {
         randomInt = getRandomNumber(1,10);
         if(randomInt <=4) {
            return "subscribers";
         }
         else if(randomInt <=8) {
            return "all";
         }
         else if(randomInt == 9) {
            return "self";
         }
         else {
            return Integer.toString(getRandomNumber(1,10000));
         }
      }
   }

    public static String getMessage(ArrayList<String> words) {
      String message = "";
      String line = null;
      int numWords = getRandomNumber(2,20);
      while (numWords != 0) {
         message = message + " " + words.get(random.nextInt(words.size()));
         numWords--;
      }
      return message;
   }

   public static int getRandomNumber(int min, int max) {
      return (random.nextInt((max-min) + 1) + min);
   }
}
