package BankingSystem;
import java.util.List;
public interface AccountDAO {
    void create(Account account);
    Account getById(int id);
    List<Account> getAll();
    void update(Account account);
    void delete(int id);
    void deposit(int id, double amount);
    void withdraw(int id, double amount);
}
