import org.json.*;
import java.io.*;

public class beFuddledStats {
   public static void main(String[] args) {
      JSONObject obj;
      String line;
      int numGames = 0;
      int numCompletedGames = 0;
      int numWins = 0;
      
      if (args.length != 1 && args.length != 2) {
         System.out.println("USAGE: java beFuddledGen <inputFileName> " + 
          "[OPTIONAL]<numObjects>");
         System.exit(-1);
      }
      
      try {
         BufferedReader br = new BufferedReader(new FileReader(args[0]));

         try {
            line = br.readLine(); //read through open bracket
            line = br.readLine();
            String aType;
            JSONObject action;

            while (line != null && !line.startsWith("]")) {
               obj = new JSONObject(line);
               action = obj.getJSONObject("action");   
               aType = action.getString("actionType");
               if (aType.equals("GameStart")) {
                  numGames++;
               }
               else if (aType.equals("GameEnd")) {
                  numCompletedGames++;
                  if(action.getString("gameStatus").equals("Win")) {
                     numWins++;
                  }
               }

               line = br.readLine();
            }
            System.out.println("STATS");
            System.out.println("Started games: " + numGames);
            System.out.println("Completed games: " 
             + numCompletedGames);
            System.out.println("Wins: " + numWins);
            System.out.println("Losses: " + (numCompletedGames - numWins));
         }
         catch(JSONException j) {
            System.err.println("ERROR: could not read JSON");
            System.exit(-1);
         }
         br.close();
      }
      catch(IOException e) {
         System.err.println("ERROR: could not open or read from input file");
      }
      
   }
}
