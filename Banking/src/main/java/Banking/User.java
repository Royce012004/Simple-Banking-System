package Banking;

public class User {
    int id;
    String name;
    String pin;
    double balance;
    String accountNumber;
    public User(int id, String name, String pin, double balance, String accountNumber) {
        this.id = id;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }
}