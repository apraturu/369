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
      double winAvg = 0.0;
      double lossAvg = 0.0;

      if (args.length != 1 && args.length != 2) {
         System.out.println("USAGE: java beFuddledGen <inputFileName> " + 
          "[OPTIONAL]<numObjects>");
         System.exit(-1);
      }
      
      try {
         tok = new JSONTokener(new FileReader(args[0]));
         JSONObject action;
         String aType;

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
                  pointsWon.add(new Integer(action.getInt("points")));
               }
               else {
                  pointsLost.add(new Integer(action.getInt("points")));
               }
            }
         }
         System.out.println("STATS");
         System.out.println("Games started: " + numGames);
         System.out.println("Games completed: " 
          + numCompletedGames);
         System.out.println("Wins: " + numWins);
         System.out.println("Losses: " + (numCompletedGames - numWins));
         System.out.println("\nPOINTS");
         //TODO add total # of points
         System.out.printf("Average number of points in games won: %.2f\n",
          calcAvg(pointsWon));
         System.out.printf("Average number of points in games lost: %.2f\n",
          calcAvg(pointsLost));
            
         
      }
      catch(Exception e) {
         System.err.println("ERROR: could not parse file");
      }
      
   }

   public static double calcAvg(ArrayList<Integer> ints) {
      double sum = 0.0;
      for(Integer i : ints) {
         sum += i;
      }
      return sum / (double)ints.size();
   }
}
