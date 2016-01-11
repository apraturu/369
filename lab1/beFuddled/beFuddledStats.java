import org.json.*;
import java.util.ArrayList;
import java.io.*;

public class beFuddledStats {
   public static void main(String[] args) {
      JSONObject obj;
      JSONTokener tok;
      int numGames = 0;
      int numCompletedGames = 0;
      int numWins = 0;
      ArrayList<Integer> pointsWon = new ArrayList<Integer>();
      ArrayList<Integer> pointsLost = new ArrayList<Integer>();

      if (args.length != 1 && args.length != 2) {
         System.out.println("USAGE: java beFuddledGen <inputFileName> " + 
          "[OPTIONAL]<numObjects>");
         System.exit(-1);
      }
      
      try {
         tok = new JSONTokener(new FileReader(args[0]));
         String aType;
         JSONObject action;

         while (tok.skipTo('{') != 0) {
            obj = new JSONObject(tok);
            action = obj.getJSONObject("action");   
            aType = action.getString("actionType");
            if (aType.equals("GameStart")) {
               numGames++;
            }
            else if (aType.equals("GameEnd")) {
               numCompletedGames++;
               if(action.getString("gameStatus").equals("Win")) {
                  numWins++;
                  //pointsWon.add(new Integer(action.
               }
            }
         }
         System.out.println("STATS");
         System.out.println("Started games: " + numGames);
         System.out.println("Completed games: " 
          + numCompletedGames);
         System.out.println("Wins: " + numWins);
         System.out.println("Losses: " + (numCompletedGames - numWins));
         System.out.println("\nPOINTS");
            
      }
      catch(Exception e) {
         System.err.println("ERROR: could not parse file");
      }
      
   }
}
