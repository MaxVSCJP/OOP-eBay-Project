
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.awt.image.BufferedImage;
public class Massage {
    private User sender;
    private User recipient;
    private String content;
    private String status;
    private String timestamp;
    

    public Massage(User sender, User recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
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

    public String gettime(){
        return timestamp;
   }

}

