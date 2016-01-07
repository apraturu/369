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

   public static void main (String[] args) {
      JSONArray jArray = new JSONArray();
      JSONObject jObject;
      //read in the output file and the number of JSON objects to generate
      Scanner sc = new Scanner(System.in);
      String outputFileName = sc.next();
   	  int jsonNum = sc.nextInt();

   	  int currMessageId = -1;

   	  while (jsonNum != 0) {
   	 	 //get the message id and make sure it is greater than currMessageId
   	   	 //get the userid which is a string u + num up to 10000
   	   	 int messageId = generateMessageID(currMessageId);
   	   	 String user = generateUser();
   	   	 //Certain status' have more weight
   	   	 String status = messageStatus[random.nextInt(3)];
   	   	 //certain rectrictions
   	   	 String reciever = recipient[random.nextInt(3)];
   	   	 //Have a function to generate the message text
   	   	 String text = generateMessageText();

         Message m = new Message(messageId, user, status, reciever, text);
         //should you add in-response? Do a rand on 1
         //0 -> don't add the field 1->yes add the field
         jObject = new JSONObject(m, names);
         jArray.put(jObject);
   	     jsonNum--;
   	  }
   	  printToFile(outputFileName, jsonNum, jArray);
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

   public static String generateMessageText() {
      return "Placeholder";
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
		 catch (IOException e) {
			e.printStackTrace();
		}
   }
}