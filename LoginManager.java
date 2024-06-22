import java.util.HashMap;
import java.util.Map;

public class LoginManager {
        
    private static User ActiveUser = null;

    public static User getActiveUser() {
        return ActiveUser;
    }

    public static void setActiveUser(User activeUser){
        ActiveUser = activeUser; 
    }


    


}
