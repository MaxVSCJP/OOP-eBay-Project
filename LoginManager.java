import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginManager {

    private static HashMap<SignUp, User> users = new HashMap<>();
    private static User ActiveUser = null;
    static Scanner scanner = new Scanner(System.in);

    public static void addUsers (SignUp userlogin, User userInfo) {
        users.put(userlogin, userInfo);
    }


    public static User getActiveUser() {
        return ActiveUser;
    }

    

    public static void userLogin(){
        System.out.println("Enter Username");
        String uname = scanner.nextLine();

        System.out.println("Enter Password");
        String pass = scanner.nextLine();
        
        for (Map.Entry<SignUp,User> userEntry : users.entrySet()) {

            if(userEntry.getKey().getUsername().equals(uname)){
                if(userEntry.getKey().getPassword().equals(pass)){
                    ActiveUser = userEntry.getValue();
                    System.out.println("Logged In.");
                    System.out.println("Welcome " + ActiveUser.getName());
                    break;
                }
            }
        }
    }



    public static void main(String[] args) {

        for(int i=0; i<2; i++){
            System.out.println("Enter Username");
            String username = scanner.nextLine();

            System.out.println("Enter Name");
            String name = scanner.nextLine();

            System.out.println("Enter Email");
            String email = scanner.nextLine();

            System.out.println("Enter Password");
            String password = scanner.nextLine();

            System.out.println("Enter Initial Balance");
            double balance = scanner.nextDouble();

            SignUp login = new SignUp(username, password);
            User user = new User(username, name, email, balance);

            addUsers(login, user);
            System.out.println("\n");
        }
        
        userLogin();

        scanner.close();
        
    }


}
