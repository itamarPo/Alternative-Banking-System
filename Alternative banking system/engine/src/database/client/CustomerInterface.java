package database.client;

import database.loan.Loans;

public interface CustomerInterface {
    void changeBalance(int amount);
    double getBalance();
    String getName();
    void addLoanToClient(Loans loan, boolean lenderOrBorrower);
}
