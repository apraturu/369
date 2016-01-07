/**
 * Class Record holds data that is contained within a record for the game
 * "Befuddled". A record has game status information and information per-
 * taining to a particular move.
 */
public class Record {
   //components
   private String user;
   private int game;
   private Action action;

   public Record(String u, int g, Action a) {
      user = u;
      game = g;
      action = a;
   }

   public String getUser() {
      return user;
   }

   public int getGame() {
      return game;
   }

   public Action getAction() {
      return action;
   }
}
