public class Message {
   public int messageId;
   public String user;
   public String status;
   public String recipient;
   public String text;
   //private int in-response;

   public Message (int messageId, String user, String status, String recipient, String text) {
    this.messageId = messageId;
    this.user = user;
    this.status = status;
    this.recipient = recipient;
    this.text = text;
   }
}