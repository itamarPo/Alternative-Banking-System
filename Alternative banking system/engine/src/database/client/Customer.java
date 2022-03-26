package database.client;

import database.loan.Loans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements CustomerInterface, Serializable {
    private String name;
   // private final int ID;
    double balance;
    private List<Loans> lenderList;
    private List<Loans> borrowerList;

    public Customer(String clientName, double balance){
        name = clientName;
      //  ID = clientId;
        lenderList = new ArrayList<Loans>(); //need to implemet List<LoansImpl>
        borrowerList = new ArrayList<Loans>();
        this.balance = balance;
    }

    @Override
    public void changeBalance(int amount) {
        if(balance + amount < 0)
            balance = 0;
        else
            balance += amount;
    }

    @Override
    public double getBalance() { return balance; }

    @Override
    public String getName() { return name; }

    public List<Loans> getBorrowerList() {
        return borrowerList;
    }

    @Override
    public void addLoanToClient(Loans loan, boolean lenderOrBorrower) {
        if(lenderOrBorrower) // lender
           lenderList.add(loan);
        else
            borrowerList.add(loan);

    }


}
