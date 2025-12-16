import java.time.LocalDate;

public class AccountDemo {
    public static void main(String[] args) {
        Account account = new Account();

        account.deposit(1000, LocalDate.of(2012, 1, 10));
        account.deposit(2000, LocalDate.of(2012, 1, 13));
        account.withdraw(500, LocalDate.of(2012, 1, 14));

        account.printStatement();
    }
}
