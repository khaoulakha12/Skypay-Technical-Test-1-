import java.time.LocalDate;

final class Transaction {
    private final LocalDate date;
    private final int amount;
    private final int balanceAfter;

    Transaction(LocalDate date, int amount, int balanceAfter) {
        this.date = date;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    LocalDate date() {
        return date;
    }

    int amount() {
        return amount;
    }

    int balanceAfter() {
        return balanceAfter;
    }
}
