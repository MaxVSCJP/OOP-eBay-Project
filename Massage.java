
import java.time.*;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
public class Massage {
    private User sender;
    private User recipient;
    private String content;
    private String status;
    private BufferedImage image;
    private Timestamp timestamp;
    

    public Massage(User sender, User recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = Timestamp.from(Instant.now()); 
    }

    public String getstatus() {
        return status;
    }
    public void setstatus(String status) {
        this.status = status;
    }

    public User getSender() {
        return sender;
    }
    public User getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public BufferedImage getfile(){
        return image;
    }
    public void setimage(BufferedImage image){
        this.image = image;
    }
   public Timestamp gettime(){
        return timestamp;
   }

}

