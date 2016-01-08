public class Game {
   private String user;
   private int gameID;
   private int moves;
   private boolean shuffled;
   private boolean cleared;
   private boolean inverted;
   private boolean rotated;
   private int points;
   private int maxMoves;

   public Game(String uid, int numMoves) {
      user = uid;
      moves = 0;
      shuffled = false;
      cleared = false;
      inverted = false;
      rotated = false;
      points = 0;
      maxMoves = numMoves;
   }

   public void setGameID(int gid) {
      gameID = gid;
   }

   public void setMoves() {
      moves = moves + 1;
   }

   public void setShuffled() {
      shuffled = true;
   }

   public void setCleared() {
      cleared = true;
   }

   public void setInverted() {
      inverted = true;
   }

   public void setRotated() {
      rotated = true;
   }

   public void setPoints(int point) {
      points += point;
   }

   public String getUser() {
      return user;
   }

   public int getGameID() {
      return gameID;
   }

   public int getMoves() {
      return moves;
   }

   public boolean isShuffled() {
      return shuffled;
   }

   public boolean isCleared() {
      return cleared;
   }

   public boolean isInverted() {
      return inverted;
   }

   public boolean isRotated() {
      return rotated;
   }

   public int getPoints() {
      return points;
   }

   public int getMaxMoves() {
      return maxMoves;
   }

   public boolean canMakeSpecialMove() {
      return !(shuffled && cleared && rotated && inverted)
   }
}
