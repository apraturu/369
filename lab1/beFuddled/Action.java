

public class Action {
   //components
   private String actionType;
   private int actionNumber;
   //private Location location;
   private int pointsAdded;
   private int points;
   private String gameStatus;
   private String move;

   public Action(String aType, int aNumber, int pAdded, int p, String gStatus, 
                  String m) {
      actionType = aType;
      actionNumber = aNumber;
      pointsAdded = pAdded;
      points = p;
      gameStatus = gStatus;
      move = m;
   }

   public String getActionType() {
      return actionType;
   }

   public int getActionNumber() {
      return actionNumber;
   }

   public int getPointsAdded() {
      return pointsAdded;
   }

   public int getPoints() {
      return points;
   }

   public String getGameStatus() {
      return gameStatus;
   }

   public String getMove() {
      return move;
   }
}
