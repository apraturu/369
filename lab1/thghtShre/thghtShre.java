import java.util.*;
import java.awt.*;
import org.json.JSONObject;
import org.json.*;
import java.io.*;
import java.util.Random;


public class thghtShre {
   public static String[] messageStatus = {"public", "protected", "private"};
   public static String[] recipient = {"all", "self", "subscribers"};
   public static String[] names = {"messageId", "user", "status", "recipient", "text"};
   public static Random random = new Random();
   //public static ArrayList<String> words = new ArrayList<String>();

   public static void main (String[] args) {
      JSONArray jArray = new JSONArray();
      JSONObject jObject;
      ArrayList<String> words = new ArrayList<String>();
      if (args.length != 2) {
         System.out.println("USAGE: java beFuddledGen <outputFileName> " + 
          "<numObjects>");
         System.exit(-1);
      }
      String outputFileName = args[0];
   	  int jsonNum = Integer.parseInt(args[1]);
        String line = null;
        try {
         FileReader fileReader = new FileReader("sense.txt");
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         while((line = bufferedReader.readLine()) != null) {
            words.add(line);
         }
        }  
      catch (Exception e) {
         System.out.println(e);
      }
   	  int currMessageId = -1;
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(args[0]), "utf-8"))) {
         try {
            writer.write("[\n");
            while (jsonNum != 0) {
          //get the message id and make sure it is greater than currMessageId
             //get the userid which is a string u + num up to 10000
             int messageId = generateMessageID(currMessageId);
             String user = generateUser();
             //Certain status' have more weight
             String status = generateStatus();
             //certain rectrictions
             String reciever = generateRecipient(status);
             //Have a function to generate the message text
             String text = getMessage(words);

         Message m = new Message(messageId, user, status, reciever, text);
         //should you add in-response? Do a rand on 1
         //0 -> don't add the field 1->yes add the field

         jObject = new JSONObject(m, names);
         if (random.nextInt(2) == 1) {
            try {
               jObject = jObject.put("in-response", random.nextInt(m.messageId));
            }
            catch (Exception e) {
               System.out.println(e);
            }
         }
            if((jsonNum-1) == 0) {
               writer.write(jObject.toString(3) + "\n");
            }
            else {
               writer.write(jObject.toString(3) + ",\n");
            }
           jsonNum--;
        }
         }
         catch (JSONException j) {
            System.err.println("ERROR: could not convert to JSON");
            System.exit(-1);
         }
         writer.write("]\n");
         writer.close();
      }
      catch (IOException e) {
         System.err.print("ERROR: could not write to file");
         System.exit(-1);
      }
   }

   public static int generateMessageID(int currMessageId) {
   	  int messageId = random.nextInt();
      while (messageId <= currMessageId) {
         messageId = random.nextInt();
      }
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

   public static void printToFile(String outputFileName, int jsonNum, JSONArray jArray) {
      try {

			File file = new File(outputFileName + ".txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			try {
			   bw.write(jArray.toString(3));
		    }
		    catch (Exception e) {
               System.out.println(e);
            }
			bw.close();
		}
		 catch (Exception e) {
			System.out.println(e);
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