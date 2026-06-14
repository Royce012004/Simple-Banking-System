package Banking;

public class User {
    int id;
    String name;
    String pin;
    double balance;
    String accountNumber;
    String email;
    String address;
    String phoneNumber;

    public User(int id, String name, String pin, double balance,
                String accountNumber, String email,
                String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}