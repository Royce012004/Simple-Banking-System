package Banking;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO dao = new UserDAO();
        System.out.println("=== BANKING SYSTEM ===");
        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            if (choice == 1) {
                System.out.print("Name: ");
                sc.nextLine();
                String name = sc.nextLine();
                System.out.print("4-digit PIN: ");
                String pin = sc.next();
                dao.createUser(name, pin);
            } else if (choice == 2) {
                System.out.print("Name: ");
                sc.nextLine();
                String name = sc.nextLine();
                System.out.print("PIN: ");
                String pin = sc.next();
                User user = dao.login(name, pin);
                if (user == null) {
                    System.out.println("Invalid login!");
                    continue;
                }
                System.out.println("Welcome " + user.name);

                boolean loggedIn = true;

                while (loggedIn) {

                    System.out.println("\n1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Update Name");
                    System.out.println("4. Delete Account");
                    System.out.println("5. View Balance");
                    System.out.println("6. Transfer Money");
                    System.out.println("7. Logout");
                    System.out.print("Choose: ");

                    int options = sc.nextInt();

                    switch (options) {

                        case 1 -> {
                            System.out.print("Amount: ");
                            dao.deposit(user.id, sc.nextDouble());
                        }

                        case 2 -> {
                            System.out.print("Amount: ");
                            dao.withdraw(user.id, sc.nextDouble());
                        }

                        case 3 -> {
                            System.out.print("New Name: ");
                            sc.nextLine();
                            String newName = sc.nextLine();
                            dao.updateName(user.id, newName);
                        }

                        case 4 -> {
                            dao.deleteUser(user.id);
                            System.out.println("Account deleted.");
                            loggedIn = false;
                        }

                        case 5 -> {
                            dao.viewBalance(user.id);
                        }

                        case 6 -> {
                            sc.nextLine();

                            System.out.print("Receiver Account Number: ");
                            String accountNumber = sc.nextLine();

                            System.out.print("Amount: ");
                            double amount = sc.nextDouble();

                            dao.transferMoney(user.id, accountNumber, amount);
                        }

                        case 7 -> {
                            System.out.println("Logged Out Successfully");
                            loggedIn = false;
                        }

                        default -> System.out.println("Invalid option.");
                    }
                }
            }
        }
    }
}
