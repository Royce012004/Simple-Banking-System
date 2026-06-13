package BankingSystem;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        AccountDAO dao = new AccountDAOImplementation();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== BANK SYSTEM ===");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. View All Accounts");
            System.out.println("4. Update Account");
            System.out.println("5. Delete Account");
            System.out.println("6. Deposit");
            System.out.println("7. Withdraw");
            System.out.println("0. Exit");

            System.out.print("Choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Balance: ");
                    double bal = sc.nextDouble();

                    dao.create(new Account(name, bal));
                    break;

                case 2:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();

                    Account acc = dao.getById(id);
                    if (acc != null)
                        System.out.println(acc.getId() + " | " + acc.getName() + " | " + acc.getBalance());
                    else
                        System.out.println("Account not found!");
                    break;

                case 3:
                    dao.getAll().forEach(a ->
                            System.out.println(a.getId() + " | " + a.getName() + " | " + a.getBalance()));
                    break;

                case 4:
                    System.out.print("ID: ");
                    int uid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New Name: ");
                    String newName = sc.nextLine();

                    System.out.print("New Balance: ");
                    double newBal = sc.nextDouble();

                    dao.update(new Account(uid, newName, newBal));
                    break;

                case 5:
                    System.out.print("ID: ");
                    dao.delete(sc.nextInt());
                    break;
                case 6:
                    System.out.print("Enter ID: ");
                    int depId = sc.nextInt();

                    System.out.print("Amount to deposit: ");
                    double depAmount = sc.nextDouble();

                    dao.deposit(depId, depAmount);
                    break;
                case 7:
                    System.out.print("Enter ID: ");
                    int wdId = sc.nextInt();

                    System.out.print("Amount to withdraw: ");
                    double wdAmount = sc.nextDouble();

                    dao.withdraw(wdId, wdAmount);
                    break;

                case 0:
                    System.exit(0);
            }
        }
    }
}
