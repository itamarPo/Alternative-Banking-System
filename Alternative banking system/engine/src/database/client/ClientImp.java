package database.client;

import database.loan.LoansImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientImp  implements Client, Serializable {
    private String name;
   // private final int ID;
    double balance;
    private List<LoansImpl> lenderList;
    private List<LoansImpl> borrowerList;

    public ClientImp(String clientName, double balance){
        name = clientName;
      //  ID = clientId;
        lenderList = new ArrayList<LoansImpl>(); //need to implemet List<LoansImpl>
        borrowerList = new ArrayList<LoansImpl>();
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

    @Override
    public void addLoanToClient(LoansImpl loan, boolean lenderOrBorrower) {
        if(lenderOrBorrower) // lender
           lenderList.add(loan);
        else
            borrowerList.add(loan);

    }


}
