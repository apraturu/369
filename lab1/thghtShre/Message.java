public class Message {
   private int messageId;
   private String user;
   private String recipient;
   private String text;
   //private int in-response;

   public Message (int messageId, String user, String recipient, String text) {
    this.messageId = messageId;
    this.user = user;
    this.recipient = recipient;
    this.text = text;
   }
}