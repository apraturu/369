import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.Iterator;
import java.io.*;
import java.lang.Math;
//import java.lang.Integer;

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
      HashMap<String, Integer> userWins = new HashMap<String, Integer>();
      HashMap<String, Integer> userLosses = new HashMap<String, Integer>();
      int maxMoves = 0;
      ArrayList<String> maxMoveUsers = new ArrayList<String>();
      HashMap<String, Integer> moveLocations = new HashMap<String, Integer>();
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
         JSONObject location;
         String loc;

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
               if (moves > maxMoves) {
                  maxMoves = moves;
                  maxMoveUsers.clear();
                  maxMoveUsers.add(user);
               }
               else if (moves == maxMoves) {
                  maxMoveUsers.add(user);
               }
               addToHistogram(stats, moves);
               if(action.getString("gameStatus").equals("Win")) {
                  stats.totalWins++;
                  pointsWon.add(new Integer(action.getInt("points")));
                  movesWon.add(new Integer(moves));
                  if(userWins.containsKey(user)) {
                     userWins.put(user, userWins.get(user) + 1);
                  }
                  else {
                     userWins.put(user, 1);
                  }
               }
               else {
                  stats.totalLosses++;
                  pointsLost.add(new Integer(action.getInt("points")));
                  movesLost.add(new Integer(moves));
                  if(userLosses.containsKey(user)) {
                     userLosses.put(user, userLosses.get(user) + 1);
                  }
                  else {
                     userLosses.put(user, 1);
                  }
               }
            }
            else if (aType.equals("Move")) {
               location = action.getJSONObject("location");
               loc = "(" + location.getInt("x") + ", " + location.getInt("y") + 
                ")";
               if (moveLocations.containsKey(loc)) {
                  moveLocations.put(loc, moveLocations.get(loc) + 1);
               }
               else {
                  moveLocations.put(loc, 1);
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
         stats.maxGamesStarted = Collections.max(usersStarted.values());
         stats.maxGamesStarted_UserIDs = getKeysWithValue(usersStarted,
          stats.maxGamesStarted);
         stats.maxGamesCompleted = Collections.max(usersCompleted.values());
         stats.maxGamesCompleted_UserIDs = getKeysWithValue(usersCompleted,
          stats.maxGamesCompleted);
         stats.maxWins = Collections.max(userWins.values());
         stats.maxWins_UserIDs = getKeysWithValue(userWins,
          stats.maxWins);
         stats.maxLosses = Collections.max(userLosses.values());
         stats.maxLosses_UserIDs = getKeysWithValue(userLosses,
          stats.maxLosses);
         stats.longestGame_UserIDs = maxMoveUsers;

         fillBoardLocationInfo(moveLocations, stats);

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
      if (moves < 15) {
         stats.moveFrequency0_14++;
      }
      else if (moves < 30) {
         stats.moveFrequency15_29++;
      }
      else if (moves < 40) {
         stats.moveFrequency30_39++;
      }
      else if (moves < 50) {
         stats.moveFrequency40_49++;
      }
      else if (moves < 60) {
         stats.moveFrequency50_59++;
      }
      else if (moves < 80) {
         stats.moveFrequency60_79++;
      }
      else {
         stats.moveFrequency80_inf++;
      }
   }

   public static ArrayList<String> getKeysWithValue(HashMap<String, Integer> 
    map, Integer val) {
      ArrayList<String> keys = new ArrayList<String>();

      for (Map.Entry<String, Integer> entry : map.entrySet()) {
         if (entry.getValue().equals(val)) {
            keys.add(entry.getKey());
         }
      }

      return keys;
   }

   public static void fillBoardLocationInfo(HashMap<String, Integer> map, 
    Stats stats) {
      Integer curValue;
      ArrayList<String> keys;
      ArrayList<Integer> values = new ArrayList<Integer>(map.values());
      stats.boardLocationKeys = new ArrayList<String>(10);
      stats.boardLocationValues = new ArrayList<Integer>(10);

      while (stats.boardLocationKeys.size() < 10) {
         curValue = new Integer(Collections.max(values));
         values.remove(curValue);
         keys = getKeysWithValue(map, new Integer(curValue));

         for (int i = 0; i < keys.size() && stats.boardLocationKeys.size()
           < 10; i++) {
            stats.boardLocationKeys.add(keys.get(i));
            stats.boardLocationValues.add(curValue);
         }
      }
   }
}
