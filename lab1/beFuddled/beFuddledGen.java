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


      Record record = new Record("Anusha", 1, new Action("move", 4, 
       new Location(5, 11), -5, 22, "djhsdf", "some move"));

      if (args.length != 2) {
         System.out.println("USAGE: java beFuddledGen <outputFileName> " + 
          "<numObjects>");
         System.exit(-1);
      }
      
      numObjects = Integer.parseInt(args[1]);
      numGames = numObjects / 45;
      games = new ArrayList<Game>(numGames);
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
            JSONObject recordJSON = new JSONObject(record);
            writer.write(recordJSON.toString(3) + "\n");
            writer.write("[\n");









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
}
