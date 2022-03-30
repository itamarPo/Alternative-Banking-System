package database.client;

import database.loan.Loans;

public interface CustomerInterface {
    void changeBalance(double amount);
    double getBalance();
    String getName();
    void addLoanToClient(Loans loan, boolean lenderOrBorrower);
}
