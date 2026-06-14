package Banking;
import java.sql.*;

public class UserDAO {
    public void createUser(String name, String pin, String email, String address, String phoneNumber) {
        try (Connection conn = DBConnection.getConnection()) {
            String accountNumber = "ACC" + System.currentTimeMillis();
            String sql = "INSERT INTO users(name, pin, balance, account_number, email, address, phone_number) VALUES (?, ?, 0, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, pin);
            ps.setString(3, accountNumber);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.setString(6, phoneNumber);
            ps.executeUpdate();
            System.out.println("Account Created Successfully!");
            System.out.println("Your Account Number: " + accountNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public User login(String name, String pin) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE name=? AND pin=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, pin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("pin"),
                        rs.getDouble("balance"),
                        rs.getString("account_number"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone_number")
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public void deposit(int id, double amount) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE users SET balance = balance + ? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Deposit successful!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewBalance(int id) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT balance FROM users WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("Current Balance: " + balance);
            } else {
                System.out.println("User not found!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void withdraw(int id, double amount) {
        try (Connection conn = DBConnection.getConnection()) {
            String check = "SELECT balance FROM users WHERE id=?";
            PreparedStatement ps1 = conn.prepareStatement(check);
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");

                if (balance < amount) {
                    System.out.println("Insufficient balance!");
                    return;
                }
            }
            String sql = "UPDATE users SET balance = balance - ? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Withdraw successful!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateName(int id, String newName) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE users SET name=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Updated successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void transferMoney(int senderId, String receiverAccountNumber, double amount) {

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            // Find receiver
            String receiverSql =
                    "SELECT id FROM users WHERE account_number = ?";

            PreparedStatement receiverPs =
                    conn.prepareStatement(receiverSql);

            receiverPs.setString(1, receiverAccountNumber);

            ResultSet receiverRs = receiverPs.executeQuery();

            if (!receiverRs.next()) {
                System.out.println("Account number not found!");
                conn.rollback();
                return;
            }

            int receiverId = receiverRs.getInt("id");

            if (receiverId == senderId) {
                System.out.println("Cannot transfer to your own account!");
                conn.rollback();
                return;
            }

            // Check sender balance
            String balanceSql =
                    "SELECT balance FROM users WHERE id=?";

            PreparedStatement balancePs =
                    conn.prepareStatement(balanceSql);

            balancePs.setInt(1, senderId);

            ResultSet balanceRs = balancePs.executeQuery();

            if (!balanceRs.next()) {
                System.out.println("Sender not found!");
                conn.rollback();
                return;
            }

            double balance = balanceRs.getDouble("balance");

            if (balance < amount) {
                System.out.println("Insufficient balance!");
                conn.rollback();
                return;
            }

            // Deduct sender
            String deductSql =
                    "UPDATE users SET balance = balance - ? WHERE id=?";

            PreparedStatement deductPs =
                    conn.prepareStatement(deductSql);

            deductPs.setDouble(1, amount);
            deductPs.setInt(2, senderId);

            deductPs.executeUpdate();

            // Add receiver
            String addSql =
                    "UPDATE users SET balance = balance + ? WHERE id=?";

            PreparedStatement addPs =
                    conn.prepareStatement(addSql);

            addPs.setDouble(1, amount);
            addPs.setInt(2, receiverId);

            addPs.executeUpdate();

            conn.commit();

            System.out.println("Transfer Successful!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteUser(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Account deleted!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}