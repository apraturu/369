public class Game {
   private String user;
   private int gameID;
   private int moves;
   private boolean hasShuffled;
   private boolean hasCleared;
   private boolean hasInverted;
   private boolean hasRotated;
   private int points;
   private int maxMoves;

   public Game(String uid, int numMoves) {
      user = uid;
      moves = 0;
      hasShuffled = false;
      hasCleared = false;
      hasInverted = false;
      hasRotated = false;
      points = 0;
      maxMoves = numMoves;
   }
}
