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
      Game currentGame;
      ArrayList<String> users = new ArrayList<String>(10000);
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
      numGames = (numObjects / 40) + 1;
      games = new ArrayList<Game>(numGames);

      initUsers(users);
      //create games
      for (int i = 0; i < numGames; i++) {
         int rand = random.nextInt(100);
         if (rand < 5) {
            games.add(new Game(9 + random.nextInt(11)));
         }
         else if (rand < 15) {
            games.add(new Game(20 + random.nextInt(10)));
         }
         else if (rand < 32) {
            games.add(new Game(30 + random.nextInt(10)));
         }
         else if (rand < 68) {
            games.add(new Game(40 + random.nextInt(10)));
         }
         else if (rand < 85) {
            games.add(new Game(50 + random.nextInt(10)));
         }
         else if (rand < 95) {
            games.add(new Game(60 + random.nextInt(10)));
         }
         else if (rand < 97) {
            games.add(new Game(70 + random.nextInt(10)));
         }
         else if (rand < 99) {
            games.add(new Game(80 + random.nextInt(10)));
         }
         else if (rand < 100) {
            games.add(new Game(90 + random.nextInt(11)));
         }
      }

      try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(args[0]), "utf-8"))) {
         try {
            //writer.write("[\n");
            while (numObjects > 0 && !games.isEmpty()) {
               currentGame = games.get(random.nextInt(games.size()));
               record = makeMove(currentGame, numStarted, users);
               if (record != null) {
                  if (record.getAction().getActionType().equals("GameStart")) {
                     numStarted++;
                  }
                  if (record.getAction().getActionType().equals("GameEnd")) {
                     games.remove(currentGame);
                  }
                  recordJSON = new JSONObject(record);
                  writer.write(recordJSON.toString(3));
                  numObjects--;
                  //if(numObjects > 0) { writer.write(","); }
                  writer.write("\n");
               }
            }
            while (numObjects > 0) {
               currentGame = new Game(50);

               record = makeMove(currentGame, numStarted++,  users);
               recordJSON = new JSONObject(record);
               writer.write(recordJSON.toString(3));
               numObjects--;
               //if(numObjects > 0) { writer.write(","); }
               writer.write("\n");

               while (record != null && numObjects > 0 &&
                !record.getAction().getActionType().equals("GameEnd")) {
                  record = makeMove(currentGame, numStarted, users);
                  recordJSON = new JSONObject(record);
                  writer.write(recordJSON.toString(3));
                  numObjects--;
                  //if(numObjects > 0) { writer.write(","); }
                  writer.write("\n");
               }
            }
         }
         catch (JSONException j) {
            System.err.println("ERROR: could not convert to JSON");
            System.exit(-1);
         }
         //writer.write("]\n");
         writer.close();
      }
      catch (IOException e) {
         System.err.print("ERROR: could not write to file");
         System.exit(-1);
      }
   }

   public static void initUsers(ArrayList<String> users) {
      for (int i = 0; i <10000; i++) {
         users.add("u" + Integer.toString(i+1));
      }
   }
   
   public static Record makeMove(Game g, int numStarted, ArrayList<String> users) {
      if (g.getMoves() == g.getMaxMoves()) {
         return null;
      }
      if (g.getMoves() == 0 && users.isEmpty()) {
         return null;
      }
      g.setMoves();
      if (g.getMoves() == 1) {
         g.setGameID(numStarted + 1);
         g.setUser(generateUser(users));

         return new Record(g.getUser(), g.getGameID(), 
          new Action("GameStart", 1, null, null, null, null, null));
      }
      if (g.getMoves() == g.getMaxMoves()) {
         users.add(g.getUser());
         return new Record(g.getUser(), g.getGameID(),
          new Action("GameEnd", g.getMoves(), null, null, g.getPoints(),
          (random.nextInt(2) == 0 ? "Win" : "Loss"), null));
      }
      
      String aType = generateActionType(g), move = null;
      Location loc = null;
      Integer pAdded = null, pTotal = null;

      if (aType.equals("Move")) {
         loc = generateLocation();
      }
      else {
         move = generateGameMove(g);
         if (move.equals("Shuffle")) pAdded = new Integer(50);
         else if (move.equals("Clear")) pAdded = new Integer(-50);
         else if (move.equals("Invert")) pAdded = new Integer(30);
         else if (move.equals("Rotate")) pAdded = new Integer(-40);
      }
         
      if (pAdded == null) pAdded = new Integer(getRandomNumber(-20, 20));
      g.setPoints(pAdded.intValue());
      pTotal = new Integer(g.getPoints() + pAdded.intValue());

      return new Record(g.getUser(), g.getGameID(), 
       new Action(aType, g.getMoves(), loc, pAdded, pTotal, null, move));
   }

   public static String generateActionType(Game game) {
      int randomInt = getRandomNumber(1,20);
      if (randomInt == 1) {
         if (game.canMakeSpecialMove()) {
            return "SpecialMove";
         }
         return "Move";
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
   public static String generateGameMove(Game game) {
      boolean loop = true;
      String move = generateMove();
      while (loop) {
         if (move.equals("Shuffle")) {
            if (!game.isShuffled()) {
               game.setShuffled();
               return move;
            }
         }
         else if (move.equals("Invert")) {
            if (!game.isInverted()) {
               game.setInverted();
               return move;
            }
         }
         else if (move.equals("Clear")) {
            if (!game.isCleared()) {
               game.setCleared();
               return move;
            }
         }
         else {
            if (!game.isRotated()) {
               game.setRotated();
               return move;
            }
         }
         move = generateMove();
      }
      //figure out a better way to keep looping
      return move;
   }

   public static Location generateLocation() {
      int x, y, temp, randX = random.nextInt(100) + 1,
       randY = random.nextInt(100) + 1;

      if (randX == 1 || randX == 2 || randX == 3 || randX == 98 || randX == 99
       || randX == 100) {
         temp = random.nextInt(6);
         switch(temp) {
            case 0:
               x = 1;
               break;
            case 1:
               x = 2;
               break;
            case 2:
               x = 3;
               break;
            case 3:
               x =18;
               break;
            case 4:
               x = 19;
               break;
            default:
               x = 20;
               break;
         }
      }
      else if (randX < 8 || randX > 93) {
         temp = random.nextInt(4);
         switch(temp) {
            case 0:
               x = 4;
               break;
            case 1:
               x =5;
               break;
            case 2:
               x = 16;
               break;
            default:
               x = 17;
               break;
         }
      }
      else if (randX < 11 || randX > 90) {
         x = random.nextInt(2) == 0 ? 6 : 15;
      }
      else if (randX < 15 || randX > 86) {
         x = random.nextInt(2) == 0 ? 7 : 14;
      }
      else if (randX < 22 || randX > 79) {
         x = random.nextInt(2) == 0 ? 8 : 13;
      }
      else if (randX < 34 || randX > 67) {
         x = random.nextInt(2) == 0 ? 9 : 12;
      }
      else {
         x = random.nextInt(2) == 0 ? 10 : 11;
      }

      if (randY == 1 || randY == 2 || randY == 3 || randY == 98 || randY == 99
       || randY == 100) {
         temp = random.nextInt(6);
         switch(temp) {
            case 0:
               y = 1;
               break;
            case 1:
               y = 2;
               break;
            case 2:
               y = 3;
               break;
            case 3:
               y =18;
               break;
            case 4:
               y = 19;
               break;
            default:
               y = 20;
               break;
         }
      }
      else if (randY < 8 || randY > 93) {
         temp = random.nextInt(4);
         switch(temp) {
            case 0:
               y = 4;
               break;
            case 1:
               y =5;
               break;
            case 2:
               y = 16;
               break;
            default:
               y = 17;
               break;
         }
      }
      else if (randY < 11 || randY > 90) {
         y = random.nextInt(2) == 0 ? 6 : 15;
      }
      else if (randY < 15 || randY > 86) {
         y = random.nextInt(2) == 0 ? 7 : 14;
      }
      else if (randY < 22 || randY > 79) {
         y = random.nextInt(2) == 0 ? 8 : 13;
      }
      else if (randY < 34 || randY > 67) {
         y = random.nextInt(2) == 0 ? 9 : 12;
      }
      else {
         y = random.nextInt(2) == 0 ? 10 : 11;
      }
      
      return new Location(x, y);
   }

   public static String generateUser(ArrayList<String> users) {
      int index = random.nextInt(users.size());
      String user = users.get(index);
      users.remove(index);
      return user;
   }

   public static int getRandomNumber(int min, int max) {
      return (random.nextInt((max-min) + 1) + min);
   }
}
