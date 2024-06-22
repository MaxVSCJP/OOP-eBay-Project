import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class chat {
private User seller;
private User buyer;
private static ArrayList<Massage>texts = new ArrayList<>();

    public chat(User buyer,User seller){
        this.buyer = buyer;
        this.seller = seller;
    }

    public static void addmess(Massage mess){
        //add massage to the chat
        texts.add(mess);
    }
   
    //call when the user touches the send button 
    public void send(String content,BufferedImage img){
        Massage temptext = new Massage(seller, buyer, content);
        temptext.setstatus("unread");
        texts.add(temptext);
        MesssageSQL.ADD(temptext);
    }
    //call when the user refreshes the chat
    public void receive(){
        MesssageSQL.retrive();
        for(Massage mess:texts){
            if(mess.getSender().getUsername() == buyer.getUsername() && mess.getRecipient().getUsername() == seller.getUsername()){
                if(mess.getstatus().equals("unread")){
                    System.out.println(mess.getContent() + mess.gettime());
                    mess.setstatus("read");
                }
                
                
            }
        }

    }

  
          
    }