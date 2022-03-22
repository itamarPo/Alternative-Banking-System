package database.client;

import database.loan.LoansImpl;

public interface Client {
    void changeBalance(int amount);
    double getBalance();
    String getName();
    void addLoanToClient(LoansImpl loan, boolean lenderOrBorrower);
}
