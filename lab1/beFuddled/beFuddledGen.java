import java.io.*;
import org.json.*;
import java.util.Random;
import java.util.ArrayList;

public class beFuddledGen {
   public static Random random = new Random();
   public static void main(String[] args) {
      int numObjects;
      int numUsers = 0;
      int numGames;
      ArrayList<Game> games;
      int numUnfinished;
      int numStarted = 0;
      Record record;
      JSONObject recordJSON;

      if (args.length != 2) {
         System.out.println("USAGE: java beFuddledGen <outputFileName> " + 
          "<numObjects>");
         System.exit(-1);
      }
      
      numObjects = Integer.parseInt(args[1]);
      numGames = numObjects / 45;
      numUnfinished = numGames;
      games = new ArrayList<Game>(numGames);

      //create games
      for (int i = 0; i < numGames; i++) {
         int rand = random.nextInt(100);
         if (rand < 5) {
            games.add(new Game("u" + Integer.toString(i + 1), 9 + random.nextInt(11)));
         }
         else if (rand < 15) {
            games.add(new Game("u" + Integer.toString(i + 1), 20 + random.nextInt(10)));
         }
         else if (rand < 32) {
            games.add(new Game("u" + Integer.toString(i + 1), 30 + random.nextInt(10)));
         }
         else if (rand < 68) {
            games.add(new Game("u" + Integer.toString(i + 1), 40 + random.nextInt(10)));
         }
         else if (rand < 85) {
            games.add(new Game("u" + Integer.toString(i + 1), 50 + random.nextInt(10)));
         }
         else if (rand < 95) {
            games.add(new Game("u" + Integer.toString(i + 1), 60 + random.nextInt(10)));
         }
         else if (rand < 97) {
            games.add(new Game("u" + Integer.toString(i + 1), 70 + random.nextInt(10)));
         }
         else if (rand < 99) {
            games.add(new Game("u" + Integer.toString(i + 1), 80 + random.nextInt(10)));
         }
         else if (rand < 100) {
            games.add(new Game("u" + Integer.toString(i + 1), 90 + random.nextInt(11)));
         }
      }


      try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(args[0]), "utf-8"))) {
         try {
            writer.write("[\n");
            int temp = 20;
            while (numObjects > 0 && numUnfinished > 0) {
               record = makeMove(games.get(random.nextInt(numGames)), numStarted);
               if (record.getAction().getActionType().equals("GameStart")) {
                  numStarted++;
               }
               if (record.getAction().getActionType().equals("GameEnd")) {
                  numUnfinished--;
               }
               if (record != null) {
                  recordJSON = new JSONObject(record);
                  writer.write(recordJSON.toString(3) + ",\n");
                  numObjects--;
               }
               temp --;
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
   
   public static Record makeMove(Game g, int numStarted) {
      if (g.getMoves() == g.getMaxMoves()) {
         return null;
      }
      if (g.getMoves() == 0) {
         g.setGameID(numStarted + 1);

         return new Record(g.getUser(), g.getGameID(), 
          new Action("GameStart", 1, null, null, null, null, null));
      }
      
      return null;
   }

   public static String generateActionType() {
      int randomInt = getRandomNumber(1,20);
      if (randomInt == 1) {
         return "SpecialMove";
      }
      return "Move";
   }
   public static String generateMove() {
      int randomInt = getRandomNumber(1, 100);
      if (randomInt <= 33) {
         return "Shuffle";
      }
      else if (randomInt <= 58) {
         return "Invert";
      }
      else if (randomInt <= 80) {
         return "Clear";
      }
      else {
         return "Rotate";
      }
   }
   public static String generateGameMove(int gameNum, ArrayList<Game> games) {
      boolean loop = true;
      String move = generateMove();
      while (loop) {
         if (move.equals("Shuffle")) {
            if (!games.get(gameNum).isShuffled()) {
               return move;
            }
         }
         else if (move.equals("Invert")) {
            if (!games.get(gameNum).isInverted()) {
               return move;
            }
         }
         else if (move.equals("Clear")) {
            if (!games.get(gameNum).isCleared()) {
               return move;
            }
         }
         else {
            if (!games.get(gameNum).isRotated()) {
               return move;
            }
         }
         move = generateMove();
      }
      //figure out a better way to keep looping
      return move;
   }
   public static Location generateLocation() {
      Location location;
      int randomInt = getRandomNumber(1, 10);
      //Center Location
      if(randomInt >= 6) {
         location = new Location(getRandomNumber(5,15), getRandomNumber(5,15));
      }
      //edge location
      else {
         //x coord is from 1-4
         if (getRandomNumber(0,1) == 0) {
            //y coord is from 1-4
            if (getRandomNumber(0,1) == 0) {
               location = new Location(getRandomNumber(1,4), getRandomNumber(1,4));
            }
            //y coord is from 16-20
            else {
               location = new Location(getRandomNumber(1,4), getRandomNumber(16,20));
            }
         } 
         //x coors is from 16-20
         else {
            //y coord is from 1-4
            if (getRandomNumber(0,1) == 0) {
               location = new Location(getRandomNumber(16,20), getRandomNumber(1,4));
            }
            //y coord is from 16-20
            else {
               location = new Location(getRandomNumber(16,20), getRandomNumber(16,20));
            }
         }
      }
      return location;
   }
   public static int getRandomNumber(int min, int max) {
      return (random.nextInt((max-min) + 1) + min);
   }
}
