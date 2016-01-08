public class Action {
   //components
   private String actionType;
   private int actionNumber;
   private Location location;
   private Integer pointsAdded;
   private Integer points;
   private String gameStatus;
   private String move;

   public Action(String aType, int aNumber, Location l, Integer pAdded, 
                  Integer p, String gStatus, String m) {
      actionType = aType;
      actionNumber = aNumber;
      location = l;
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

   public Location getLocation() {
      return location;
   }

   public Integer getPointsAdded() {
      return pointsAdded;
   }

   public Integer getPoints() {
      return points;
   }

   public String getGameStatus() {
      return gameStatus;
   }

   public String getMove() {
      return move;
   }
}
