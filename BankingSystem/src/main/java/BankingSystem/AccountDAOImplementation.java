package BankingSystem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AccountDAOImplementation implements AccountDAO {
    Connection conn = DBConnection.getConnection();

    @Override
    public void create(Account account) {

        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Connection is NULL. Check DB!");
            return;
        }

        String sql = "INSERT INTO accounts (account_name, balance) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getName());
            ps.setDouble(2, account.getBalance());
            ps.executeUpdate();
            System.out.println("Account created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getById(int id) {
        String sql = "SELECT * FROM accounts WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("account_name"),
                        rs.getDouble("balance")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAll() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Account(
                        rs.getInt("id"),
                        rs.getString("account_name"),
                        rs.getDouble("balance")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE accounts SET account_name=?, balance=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getName());
            ps.setDouble(2, account.getBalance());
            ps.setInt(3, account.getId());
            ps.executeUpdate();
            System.out.println("Account updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deposit(int id, double amount) {

        Connection conn = DBConnection.getConnection();

        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Deposit successful!");
            else
                System.out.println("Account not found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void withdraw(int id, double amount) {

        Connection conn = DBConnection.getConnection();

        String checkSql = "SELECT balance FROM accounts WHERE id = ?";
        String updateSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";

        try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, id);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");

                if (balance >= amount) {

                    try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                        ps.setDouble(1, amount);
                        ps.setInt(2, id);
                        ps.executeUpdate();
                        System.out.println("Withdraw successful!");
                    }

                } else {
                    System.out.println("Insufficient balance!");
                }

            } else {
                System.out.println("Account not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM accounts WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Account deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
