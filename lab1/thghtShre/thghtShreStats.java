import java.util.*;
import java.awt.*;
import org.json.*;
import java.io.*;

public class thghtShreStats {
   public static Stats stats = new Stats();
   public static void main(String[] args) {
      JSONObject obj;
      JSONTokener tok;
      ArrayList<String> users = new ArrayList<String>();

      /*TR3. Basic Stats*/
      ArrayList<Integer> numWords = new ArrayList<Integer>();
      ArrayList<Integer> numCharacters = new ArrayList<Integer>();

      /*TR5. Stats for subsets of messages*/
      //1
      ArrayList<Integer> numWordsPublicStatus = new ArrayList<Integer>();
      ArrayList<Integer> numCharactersPublicStatus = new ArrayList<Integer>();
      ArrayList<Integer> numWordsProtectedStatus = new ArrayList<Integer>();
      ArrayList<Integer> numCharactersProtectedStatus = new ArrayList<Integer>();
      ArrayList<Integer> numWordsPrivateStatus = new ArrayList<Integer>(); 
      ArrayList<Integer> numCharactersPrivateStatus = new ArrayList<Integer>(); 
      //2
      ArrayList<Integer> numWordsAllRecipient = new ArrayList<Integer>();
      ArrayList<Integer> numCharactersAllRecipient = new ArrayList<Integer>();
      ArrayList<Integer> numWordsSelfRecipient = new ArrayList<Integer>();
      ArrayList<Integer> numCharactersSelfRecipient = new ArrayList<Integer>();
      ArrayList<Integer> numWordsSubscribersRecipient = new ArrayList<Integer>();
      ArrayList<Integer> numCharactersSubscribersRecipient = new ArrayList<Integer>();
      ArrayList<Integer> numWordsUserIdRecipient = new ArrayList<Integer>();
      ArrayList<Integer> numCharactersUserIdRecipient = new ArrayList<Integer>();
      //3
      ArrayList<Integer> numWordsResponse = new ArrayList<Integer>();
      ArrayList<Integer> numCharactersResponse = new ArrayList<Integer>();
      ArrayList<Integer> numWordsNotResponse = new ArrayList<Integer>();
      ArrayList<Integer> numCharactersNotResponse = new ArrayList<Integer>();

      /*TR6.*/
      //1
      ArrayList<String> publicRecipients = new ArrayList<String>();
      int publicResponse = 0;
      ArrayList<String> protectedRecipients = new ArrayList<String>();
      int protectedResponse = 0;
      ArrayList<String> privateRecipients = new ArrayList<String>();
      int privateResponse = 0;
      int numAllResponse = 0;
      int numSelfResponse = 0;
      int numSubscribersResponse = 0;
      int numUserIdResponse = 0;

      if (args.length != 1 && args.length != 2) {
         System.out.println("USAGE: java thghtShreStats <inputFileName> " + 
          "[OPTIONAL]<outputFileName>");
         System.exit(-1);
      }
      
      try {
         tok = new JSONTokener(new FileReader(args[0]));
         JSONObject message;
         Boolean isResponse;
         String userId;
         String status;
         String recipient;
         String messageText;
         int messageWords;
         int messageCharacters;

         while (tok.skipTo('{') != 0) {
            message = new JSONObject(tok); 
            stats.numMessages++;  
            //MESSAGE PARSING
            messageText = message.getString("text");
            messageWords = numOfWords(messageText);
            messageCharacters = messageText.length();
            numWords.add(messageWords);
            numCharacters.add(messageCharacters);
            //USER PARSING
            userId = message.getString("user");
            if(!users.contains(userId)) {
               users.add(userId);
            }
            //STATUS PARSING
            status = message.getString("status");
            if(status.equals("public")) {
               stats.numPublicMessages++;
               numWordsPublicStatus.add(messageWords);
               numCharactersPublicStatus.add(messageCharacters);
            }
            else if (status.equals("protected")) {
               stats.numProtectedMessages++;
               numWordsProtectedStatus.add(messageWords);
               numCharactersProtectedStatus.add(messageCharacters);
            }
            else {
               stats.numPrivateMessages++;
               numWordsPrivateStatus.add(messageWords);
               numCharactersPrivateStatus.add(messageCharacters);
            }
            //RECIPIENT PARSING
            recipient = message.getString("recipient");
            if (recipient.equals("all")) {
               stats.numMessagesToAll++;
               numWordsAllRecipient.add(messageWords);
               numCharactersAllRecipient.add(messageCharacters);
            }
            else if (recipient.equals("self")) {
               stats.numMessagesToSelf++;
               numWordsSelfRecipient.add(messageWords);
               numCharactersSelfRecipient.add(messageCharacters);
            }
            else if (recipient.equals("subscribers")) {
               stats.numMessagesToSubscribers++;
               numWordsSubscribersRecipient.add(messageWords);
               numCharactersSubscribersRecipient.add(messageCharacters);
            }
            else {
               stats.numMessagesToUserId++;
               numWordsUserIdRecipient.add(messageWords);
               numCharactersUserIdRecipient.add(messageCharacters);
            }
            //IN-RESPONSE
            if(message.has("in-response")) {
               isResponse = true;
               stats.numResponseMessages++;
               numWordsResponse.add(messageWords);
               numCharactersResponse.add(messageCharacters);
            }
            else {
               isResponse = false;
               stats.numNotResponseMessages++;
               numWordsNotResponse.add(messageWords);
               numCharactersNotResponse.add(messageCharacters);
            }
            incrementFrequencyStatus_Recipient(status, recipient);
            incrementFrequencyStatus_Response(status, isResponse);
            incrementFrequencyRecipient_Response(recipient, isResponse);
         }            
      }
      catch(Exception e) {
         System.err.println("ERROR: could not parse file");
      }
      stats.numUniqueUsers = users.size();
      stats.avgLengthCharacters = calcAvg(numCharacters);
      stats.avgLengthCharactersStandardDeviation = calcStdDev(numCharacters, stats.avgLengthCharacters);
      stats.avgLengthWords = calcAvg(numWords);
      stats.avgLengthWordsStandardDeviation = calcStdDev(numWords, stats.avgLengthWords);

      stats.avgLengthWordsPublic = calcAvg(numWordsPublicStatus);
      stats.avgLengthWordsPublicStandardDeviation = calcStdDev(numWordsPublicStatus, stats.avgLengthWordsPublic);
      stats.avgLengthCharactersPublic = calcAvg(numCharactersPublicStatus);
      stats.avgLengthCharactersPublicStandardDeviation = calcStdDev(numCharactersPublicStatus, stats.avgLengthCharactersPublic);
      stats.avgLengthWordsProtected = calcAvg(numWordsProtectedStatus);;
      stats.avgLengthWordsProtectedStandardDeviation = calcStdDev(numWordsProtectedStatus, stats.avgLengthWordsProtected);
      stats.avgLengthCharactersProtected = calcAvg(numCharactersProtectedStatus);
      stats.avgLengthCharactersProtectedStandardDeviation = calcStdDev(numCharactersProtectedStatus, stats.avgLengthCharactersProtected);
      stats.avgLengthWordsPrivate = calcAvg(numWordsPrivateStatus);
      stats.avgLengthWordsPrivateStandardDeviation = calcStdDev(numWordsPrivateStatus, stats.avgLengthWordsPrivate);
      stats.avgLengthCharactersPrivate = calcAvg(numCharactersPrivateStatus);
      stats.avgLengthCharactersPrivateStandardDeviation = calcStdDev(numCharactersPrivateStatus, stats.avgLengthCharactersPrivate);

      stats.avgLengthWordsAll = calcAvg(numWordsAllRecipient);
      stats.avgLengthWordsAllStandardDeviation = calcStdDev(numWordsAllRecipient, stats.avgLengthWordsAll);
      stats.avgLengthCharactersAll = calcAvg(numCharactersAllRecipient);
      stats.avgLengthCharactersAllStandardDeviation = calcStdDev(numCharactersAllRecipient, stats.avgLengthCharactersAll);
      stats.avgLengthWordsSelf = calcAvg(numWordsSelfRecipient);
      stats.avgLengthWordsSelfStandardDeviation = calcStdDev(numWordsSelfRecipient, stats.avgLengthWordsSelf);
      stats.avgLengthCharactersSelf = calcAvg(numCharactersSelfRecipient);
      stats.avgLengthCharactersSelfStandardDeviation = calcStdDev(numCharactersSelfRecipient, stats.avgLengthCharactersSelf);
      stats.avgLengthWordsSubscribers = calcAvg(numWordsSubscribersRecipient);
      stats.avgLengthWordsSubscribersStandardDeviation = calcStdDev(numWordsSubscribersRecipient, stats.avgLengthWordsSubscribers );
      stats.avgLengthCharactersSubscribers = calcAvg(numCharactersSubscribersRecipient);
      stats.avgLengthCharactersSubscribersStandardDeviation = calcStdDev(numCharactersSubscribersRecipient, stats.avgLengthCharactersSubscribers); 
      stats.avgLengthWordsUserId = calcAvg(numWordsUserIdRecipient);
      stats.avgLengthWordsUserIdStandardDeviation = calcStdDev(numWordsUserIdRecipient, stats.avgLengthCharactersSubscribers);
      stats.avgLengthCharactersUserId = calcAvg(numCharactersUserIdRecipient);
      stats.avgLengthCharactersUserIdStandardDeviation = calcStdDev(numCharactersUserIdRecipient, stats.avgLengthCharactersUserId); 

      stats.avgLengthWordsResponse = calcAvg(numWordsResponse);
      stats.avgLengthWordsResponseStandardDeviation = calcStdDev(numWordsResponse, stats.avgLengthWordsResponse);
      stats.avgLengthCharactersResponse = calcAvg(numCharactersResponse);
      stats.avgLengthCharactersResponseStandardDeviation = calcStdDev(numCharactersResponse, stats.avgLengthCharactersResponse);
      stats.avgLengthWordsNotResponse = calcAvg(numWordsNotResponse);
      stats.avgLengthWordsNotResponseStandardDeviation = calcStdDev(numWordsNotResponse, stats.avgLengthWordsNotResponse);
      stats.avgLengthCharactersNotResponse = calcAvg(numCharactersNotResponse);
      stats.avgLengthCharactersNotResponseStandardDeviation = calcStdDev(numCharactersNotResponse, stats.avgLengthCharactersNotResponse);

      stats.print();
      //There is a name of the output file to write to
      if(args.length == 2) {
         printToFile(args[1]);
      }
      //convertToJSONObjects();
   }
    public static int numOfWords(String messageText) {
      int words = 0;
      String[] split = messageText.split("\\s+");
      words = split.length - 1;
      if (words >= 2 && words <=6) {
         stats.messageFrequency2_6++;
      }
      else if (words >= 7 && words <=11) {
         stats.messageFrequency7_11++;
      }
      else if (words >= 12 && words <=16) {
         stats.messageFrequency12_16++;
      }
      else {
         stats.messageFrequency17_20++;
      }
      return words;
    }

    public static void incrementFrequencyStatus_Recipient(String status, String recipient) {
      if (status.equals("public")) {
         if (recipient.equals("all")) {
            stats.frequencyPublic_All++;
         }
         else if (recipient.equals("self")) {
            stats.frequencyPublic_Self++;
         }
         else if (recipient.equals("subscribers")) {
            stats.frequencyPublic_Subscribers++;
         }
         else {
            stats.frequencyPublic_UserId++;
         }
      }
      else if (status.equals("protected")) {
         if (recipient.equals("all")) {
            stats.frequencyProtected_All++;
         }
         else if (recipient.equals("self")) {
            stats.frequencyProtected_Self++;
         }
         else if (recipient.equals("subscribers")) {
            stats.frequencyProtected_Subscribers++;
         }
         else {
            stats.frequencyProtected_UserId++;
         }
      }
      else {
         if (recipient.equals("all")) {
            stats.frequencyPrivate_All++;
         }
         else if (recipient.equals("self")) {
            stats.frequencyPrivate_Self++;
         }
         else if (recipient.equals("subscribers")) {
            stats.frequencyPrivate_Subscribers++;
         }
         else {
            stats.frequencyPrivate_UserId++;
         }
      }
    }

    public static void incrementFrequencyStatus_Response(String status, Boolean isResponse) {
      if (status.equals("public")) {
         if (isResponse) {
            stats.frequencyPublic_PresentResponseFlag++;
         }
         else {
            stats.frequencyPublic_AbsentResponseFlag++;
         }
      }
      else if (status.equals("protected")) {
         if (isResponse) {
            stats.frequencyProtected_PresentResponseFlag++;
         }
         else {
            stats.frequencyProtected_AbsentResponseFlag++;
         }
      }
      else {
         if (isResponse) {
            stats.frequencyPrivate_PresentResponseFlag++;
         }
         else {
            stats.frequencyPrivate_AbsentResponseFlag++;
         }
      }
    }

    public static void incrementFrequencyRecipient_Response(String recipient, Boolean isResponse) {
      if (recipient.equals("all")) {
            if (isResponse) {
               stats.frequencyAll_PresentResponseFlag++;
            }
            else {
               stats.frequencyAll_AbsentResponseFlag++;
            }
         }
         else if (recipient.equals("self")) {
            if (isResponse) {
               stats.frequencySelf_PresentResponseFlag++;
            }
            else {
               stats.frequencySelf_AbsentResponseFlag++;
            }
         }
         else if (recipient.equals("subscribers")) {
            if (isResponse) {
               stats.frequencySubscribers_PresentResponseFlag++;
            }
            else {
               stats.frequencySubscribers_AbsentResponseFlag++;
            }
         }
         else {
            if (isResponse) {
               stats.frequencyUserId_PresentResponseFlag++;
            }
            else {
               stats.frequencyUserId_AbsentResponseFlag++;
            }
         }
    }

   public static double calcAvg(ArrayList<Integer> ints) {
      double sum = 0.0;
      for(Integer i : ints) {
         sum += i;
      }
      return sum / (double)ints.size();
   }
   public static double calcStdDev(ArrayList<Integer> ints, double mean) {
      double sum = 0.0;
      for(Integer i : ints) {
         sum += Math.pow(mean - i, 2);
      }
      return Math.sqrt(sum / (double)ints.size());
   }
   public static void convertToJSONObjects() {
      JSONObject jObject = null;
     try {
         jObject = new JSONObject(stats);
         System.out.println(jObject.toString(3));
      }
      catch (JSONException j) {
            System.err.println("ERROR: could not convert to JSON");
            System.exit(-1);
         }
    }

       public static void printToFile(String outputFileName) {
         JSONObject jObject = null;
      try {

         File file = new File(outputFileName);

         // if file doesnt exists, then create it
         if (!file.exists()) {
            file.createNewFile();
         }

         FileWriter fw = new FileWriter(file.getAbsoluteFile());
         BufferedWriter bw = new BufferedWriter(fw);
         try {
         jObject = new JSONObject(stats);
         bw.write(jObject.toString(3));
      }
      catch (JSONException j) {
            System.err.println("ERROR: could not convert to JSON");
            System.exit(-1);
         }
         bw.close();
      }
       catch (Exception e) {
         System.out.println(e);
      }
   }
 
}