import java.util.ArrayList;

public class Stats {
   //stats
   public int gamesSeen;
   public int gamesCompleted;
   public int totalWins;
   public int totalLosses;

   //points
   public double totalPointsAvg;
   public double totalPointsStdDev;
   public double gamesWonPointsAvg;
   public double gamesWonPointsStdDev;
   public double gamesLostPointsAvg;
   public double gamesLostPointsStdDev;

   //moves
   public double totalMovesAvg;
   public double totalMovesStdDev;
   public double gamesWonMovesAvg;
   public double gamesWonMovesStdDev;
   public double gamesLostMovesAvg;
   public double gamesLostMovesStdDev;
   public int moveFrequency0_14;
   public int moveFrequency15_29;
   public int moveFrequency30_39;
   public int moveFrequency40_49;
   public int moveFrequency50_59;
   public int moveFrequency60_79;
   public int moveFrequency80_inf;

   //users
   public int totalUsersStartedGames;
   public int totalUsersCompletedGames;
   public int maxGamesStarted;
   public ArrayList<String> maxGamesStarted_UserIDs;
   public int maxGamesCompleted;
   public ArrayList<String> maxGamesCompleted_UserIDs;
   public int maxWins;
   public ArrayList<String> maxWins_UserIDs;
   public int maxLosses;
   public ArrayList<String> maxLosses_UserIDs;
   public ArrayList<String> longestGame_UserIDs;

   //histogram for moves
   public int boardLocation_1;
   public int boardLocation_2;
   public int boardLocation_3;
   public int boardLocation_4;
   public int boardLocation_5;
   public int boardLocation_6;
   public int boardLocation_7;
   public int boardLocation_8;
   public int boardLocation_9;
   public int boardLocation_10;
   
   //special moves
   public int shuffleFrequency;
   public int clearFrequency;
   public int invertFrequency;
   public int rotateFrequency;

   public void print() {
      String leftAlignFormatInt = "| %-35s | %-5d |%n";
      String leftAlignFormatDouble = "| %-35s | %-5.2f |%n";


      System.out.println();
      System.out.format("+---------------------------------------------+%n");
      System.out.format("| GAME STATS                                  |%n");
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "Games seen", gamesSeen);
      System.out.format(leftAlignFormatInt, "Games completed", gamesCompleted);
      System.out.format(leftAlignFormatInt, "Wins", totalWins);
      System.out.format(leftAlignFormatInt, "Losses", totalLosses);
      System.out.format("+---------------------------------------------+%n");
      System.out.println();
      
      System.out.format("+---------------------------------------------+%n");
      System.out.format("| POINTS                                      |%n");
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatDouble,
       "Average # of points per game", totalPointsAvg);
      System.out.format(leftAlignFormatDouble, "Standard deviation",
       totalPointsStdDev);
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatDouble,
       "Average # of points for games won", gamesWonPointsAvg);
      System.out.format(leftAlignFormatDouble, "Standard deviation",
       gamesWonPointsStdDev);
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatDouble, 
       "Average # of points for games lost", gamesLostPointsAvg);
      System.out.format(leftAlignFormatDouble, "Standard deviation",
       gamesLostPointsStdDev);
      System.out.format("+---------------------------------------------+%n");
      System.out.println();

      System.out.format("+---------------------------------------------+%n");
      System.out.format("| MOVES                                       |%n");
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatDouble,
       "Average # of moves per game", totalMovesAvg);
      System.out.format(leftAlignFormatDouble, "Standard deviation",
       totalMovesStdDev);
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatDouble,
       "Average # of moves for games won", gamesWonMovesAvg);
      System.out.format(leftAlignFormatDouble, "Standard deviation",
       gamesWonMovesStdDev);
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatDouble, 
       "Average # of moves for games lost", gamesLostMovesAvg);
      System.out.format(leftAlignFormatDouble, "Standard deviation",
       gamesLostMovesStdDev);
      System.out.format("+---------------------------------------------+%n");
      System.out.println();

      System.out.format("+---------------------------------------------+%n");
      System.out.format("| NUMBER OF MOVES HISTOGRAM                   |%n");
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "[0, 15)", moveFrequency0_14);
      System.out.format(leftAlignFormatInt, "[15, 30)", moveFrequency15_29);
      System.out.format(leftAlignFormatInt, "[30, 40)", moveFrequency30_39);
      System.out.format(leftAlignFormatInt, "[40, 50)", moveFrequency40_49);
      System.out.format(leftAlignFormatInt, "[50, 60)", moveFrequency50_59);
      System.out.format(leftAlignFormatInt, "[60, 80)", moveFrequency60_79);
      System.out.format(leftAlignFormatInt, "[80, infinity)", moveFrequency80_inf);
      System.out.format("+---------------------------------------------+%n");
      System.out.println();

      System.out.format("+---------------------------------------------+%n");
      System.out.format("| USERS                                       |%n");
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "# of users who started a game",
       totalUsersStartedGames);
      System.out.format(leftAlignFormatInt, "# of users who completed a game",
       totalUsersCompletedGames);
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "Largest # of games started",
       maxGamesStarted);
      System.out.print("| Users that started max # of games   | ");
      for(int i = 0; i < maxGamesStarted_UserIDs.size() - 1; i++) {
         System.out.print(maxGamesStarted_UserIDs.get(i) + ", ");
      }
      System.out.format("%s |%n", 
       maxGamesStarted_UserIDs.get(maxGamesStarted_UserIDs.size() - 1));
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "Largest # of games completed",
       maxGamesCompleted);
      System.out.print("| Users that completed max # of games | ");
      for(int i = 0; i < maxGamesCompleted_UserIDs.size() - 1; i++) {
         System.out.print(maxGamesCompleted_UserIDs.get(i) + ", ");
      }
      System.out.format("%s |%n", 
       maxGamesCompleted_UserIDs.get(maxGamesCompleted_UserIDs.size() - 1));
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "Largest # of games won",
       maxWins);
      System.out.print("| Users that won the max # of times   | ");
      for(int i = 0; i < maxWins_UserIDs.size() - 1; i++) {
         System.out.print(maxWins_UserIDs.get(i) + ", ");
      }
      System.out.format("%s |%n", 
       maxWins_UserIDs.get(maxWins_UserIDs.size() - 1));
      System.out.format("+---------------------------------------------+%n");
      System.out.format(leftAlignFormatInt, "Largest # of games lost",
       maxLosses);
      System.out.print("| Users that lost the max # of times  | ");
      for(int i = 0; i < maxLosses_UserIDs.size() - 1; i++) {
         System.out.print(maxLosses_UserIDs.get(i) + ", ");
      }
      System.out.format("%s |%n", 
       maxLosses_UserIDs.get(maxLosses_UserIDs.size() - 1));
      System.out.format("+---------------------------------------------+%n");
      System.out.print("| Users that played the longest game  | ");
      for(int i = 0; i < longestGame_UserIDs.size() - 1; i++) {
         System.out.print(longestGame_UserIDs.get(i) + ", ");
      }
      System.out.format("%s |%n", 
       longestGame_UserIDs.get(longestGame_UserIDs.size() - 1));
      System.out.format("+---------------------------------------------+%n");
      System.out.println();

      System.out.format("+---------------------------------------------+%n");
      System.out.format("| LOCATION OF MOVES HISTOGRAM                 |%n");
      System.out.format("+---------------------------------------------+%n");
   }
}
