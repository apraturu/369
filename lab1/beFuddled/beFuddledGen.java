import java.io.*;
import org.json.*;

public class beFuddledGen {
   public static void main(String[] args) {
      int numObjects;
      Record record = new Record("Anusha", 1, new Action("move", 4, 
       new Location(5, 11), -5, 22, "djhsdf", "some move"));

      if (args.length != 2) {
         System.out.println("USAGE: java beFuddledGen <outputFileName> " + 
          "<numObjects>");
         System.exit(-1);
      }
      
      numObjects = Integer.parseInt(args[1]);

      try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(args[0]), "utf-8"))) {
         try {
            JSONObject recordJSON = new JSONObject(record);
            writer.write(recordJSON.toString(3) + "\n");
         }
         catch (JSONException j) {
            System.err.println("ERROR: could not convert to JSON");
            System.exit(-1);
         }
         writer.close();
      }
      catch (IOException e) {
         System.err.print("ERROR: could not write to file");
         System.exit(-1);
      }
   }
}
