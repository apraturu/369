import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.lang.Math;

public class beFuddledStats {
   public static void main(String[] args) {
      JSONObject obj;
      JSONTokener tok;
      ArrayList<Integer> totalPoints;
      ArrayList<Integer> pointsWon = new ArrayList<Integer>();
      ArrayList<Integer> pointsLost = new ArrayList<Integer>();
      ArrayList<Integer> totalMoves;
      ArrayList<Integer> movesWon = new ArrayList<Integer>();
      ArrayList<Integer> movesLost = new ArrayList<Integer>();
      HashMap<String, Integer> usersStarted = new HashMap<String, Integer>();
      HashMap<String, Integer> usersCompleted = new HashMap<String, Integer>();
      Stats stats = new Stats();
      String[] names = {"gamesSeen","gamesCompleted","totalWins","totalLosses",
       "totalPointsAvg","totalPointsStdDev","gamesWonPointsAvg", 
       "gamesWonPointsStdDev","gamesLostPointsAvg","gamesLostPointsStdDev",
       "totalMovesAvg","totalMovesStdDev","gamesWonMovesAvg",
       "gamesWonMovesStdDev","gamesLostMovesAvg","gamesLostMovesStdDev",
       "moveFrequency0_14","moveFrequency15_29","moveFrequency30_39",
       "moveFrequency40_49","moveFrequency50_59","moveFrequency60_79",
       "moveFrequency80_inf","totalUsersStartedGames",
       "totalUsersCompletedGames","maxGamesStarted","maxGamesStarted_UserID",
       "maxGamesCompleted","maxGamesCompleted_UserIDs","maxWins",
       "maxWins_UserIDs","maxLosses","maxLosses_UserIDs", 
       "longestGame_UserIDs","boardLocation_1","boardLocation_2",
       "boardLocation_3","boardLocation_4","boardLocation_5","boardLocation_6",
       "boardLocation_7","boardLocation_8","boardLocation_9","boardLocation_10",
       "shuffleFrequency","clearFrequency","invertFrequency","rotateFrequency"};

      if (args.length != 1 && args.length != 2) {
         System.out.println("USAGE: java beFuddledGen <inputFileName> " + 
          "[OPTIONAL]<numObjects>");
         System.exit(-1);
      }
      
      try {
         tok = new JSONTokener(new FileReader(args[0]));
         JSONObject action;
         String aType, user;
         int moves;

         while (tok.skipTo('{') != 0) {
            obj = new JSONObject(tok);
            action = obj.getJSONObject("action");   
            aType = action.getString("actionType");
            user = obj.getString("user");

            if (aType.equals("GameStart")) {
               stats.gamesSeen++;
               if(usersStarted.containsKey(user)) {
                  usersStarted.put(user, usersStarted.get(user) + 1);
               }
               else {
                  usersStarted.put(user, 1);
               }
            }
            else if (aType.equals("GameEnd")) {
               stats.gamesCompleted++;
               if(usersCompleted.containsKey(user)) {
                  usersCompleted.put(user, usersCompleted.get(user) + 1);
               }
               else {
                  usersCompleted.put(user, 1);
               }
               moves = action.getInt("actionNumber");
               addToHistogram(stats, moves);
               if(action.getString("gameStatus").equals("Win")) {
                  stats.totalWins++;
                  pointsWon.add(new Integer(action.getInt("points")));
                  movesWon.add(new Integer(moves));
               }
               else {
                  stats.totalLosses++;
                  pointsLost.add(new Integer(action.getInt("points")));
                  movesLost.add(new Integer(moves));
               }
            }
         }

         stats.gamesWonPointsAvg = calcAvg(pointsWon);
         stats.gamesWonPointsStdDev = calcStdDev(pointsWon, 
          stats.gamesWonPointsAvg);
         stats.gamesLostPointsAvg = calcAvg(pointsLost);
         stats.gamesLostPointsStdDev = calcStdDev(pointsLost, 
          stats.gamesLostPointsAvg);
         totalPoints = pointsWon;
         totalPoints.addAll(pointsLost);
         stats.totalPointsAvg = calcAvg(totalPoints);
         stats.totalPointsStdDev = calcStdDev(totalPoints, 
          stats.totalPointsAvg);
         
         stats.gamesWonMovesAvg = calcAvg(movesWon);
         stats.gamesWonMovesStdDev = calcStdDev(movesWon, 
          stats.gamesWonMovesAvg);
         stats.gamesLostMovesAvg = calcAvg(movesLost);
         stats.gamesLostMovesStdDev = calcStdDev(movesLost, 
          stats.gamesLostMovesAvg);
         totalMoves = movesWon;
         totalMoves.addAll(movesLost);
         stats.totalMovesAvg = calcAvg(totalMoves);
         stats.totalMovesStdDev = calcStdDev(totalMoves, stats.totalMovesAvg);

         stats.totalUsersStartedGames = usersStarted.size();
         stats.totalUsersCompletedGames = usersCompleted.size();

         stats.print();
      
      }
      catch(Exception e) {
         System.err.println("ERROR: could not parse file");
         e.printStackTrace();
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

   public static void addToHistogram(Stats stats, int moves) {
      if(moves < 15) {
         stats.moveFrequency0_14++;
      }
      else if(moves < 30) {
         stats.moveFrequency15_29++;
      }
      else if(moves < 40) {
         stats.moveFrequency30_39++;
      }
      else if(moves < 50) {
         stats.moveFrequency40_49++;
      }
      else if(moves < 60) {
         stats.moveFrequency50_59++;
      }
      else if(moves < 80) {
         stats.moveFrequency60_79++;
      }
      else {
         stats.moveFrequency80_inf++;
      }
   }
}
