public class User {
    private String Username;
    private String name;
    private String email;
    private double balance;
    private int notificationCount = 0;

    public User(String Username, String name, String email, double balance){
        this.Username = Username;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public String getUsername() {
        return Username;
    }

    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public double getBalance() {
        return balance;
    }

    public void depositMoney(double balance) {
        this.balance += balance;
    }

    public void pay(double balance){
        this.balance -= balance;
    }

    public void addNotification(){
        notificationCount++;
    }

    public void decreaseNotification(){
        if(notificationCount > 0){
            notificationCount--;
        }
    }
}