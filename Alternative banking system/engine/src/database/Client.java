package database;

public interface Client {
    void changeMoneyAmount(int amount);
    double showMoney();
    int getId();
    String getName();
    void addLoanToClient(LoansImpl loan, boolean lenderOrBorrower);
}
