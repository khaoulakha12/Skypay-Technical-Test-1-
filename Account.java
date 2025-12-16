import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements AccountService {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Clock clock;
    private final List<Transaction> transactions = new ArrayList<>();

    private int balance = 0;

    public Account() {
        this(Clock.systemDefaultZone());
    }

    public Account(Clock clock) {
        if (clock == null) {
            throw new IllegalArgumentException("clock cannot be null");
        }
        this.clock = clock;
    }

    @Override
    public void deposit(int amount) {
        deposit(amount, LocalDate.now(clock));
    }

    public void deposit(int amount, LocalDate date) {
        validateAmount(amount);
        LocalDate txDate = requireDate(date);
        balance = safeAdd(balance, amount);
        transactions.add(new Transaction(txDate, amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        withdraw(amount, LocalDate.now(clock));
    }

    public void withdraw(int amount, LocalDate date) {
        validateAmount(amount);
        LocalDate txDate = requireDate(date);
        int debit = -Math.abs(amount);
        if (safeAdd(balance, debit) < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("balance underflow");
        }
        balance = safeAdd(balance, debit);
        transactions.add(new Transaction(txDate, debit, balance));
    }

    @Override
    public void printStatement() {
        String statement = buildStatement();
        System.out.print(statement);
    }

    public String buildStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date || Amount || Balance");

        List<Transaction> copy = new ArrayList<>(transactions);
        Collections.reverse(copy);
        for (Transaction tx : copy) {
            sb.append(System.lineSeparator())
                    .append(formatDate(tx.date()))
                    .append(" || ")
                    .append(tx.amount())
                    .append(" || ")
                    .append(tx.balanceAfter());
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    private void validateAmount(int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("amount cannot be zero");
        }
    }

    private LocalDate requireDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return date;
    }

    private int safeAdd(int left, int right) {
        long result = (long) left + (long) right;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("amount causes overflow");
        }
        return (int) result;
    }

    private String formatDate(LocalDate date) {
        return DATE_FORMAT.format(date);
    }
}
