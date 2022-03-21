package database.client;

import database.loan.LoansImpl;

import java.util.ArrayList;
import java.util.List;

public class ClientImp  implements Client{
    private String name;
    private final int ID;
    double balance;
    private List<LoansImpl> lenderList;
    private List<LoansImpl> borrowerList;

    public ClientImp(String clientName, int clientId){
        name = clientName;
        ID = clientId;
        lenderList = new ArrayList<LoansImpl>(); //need to implemet List<LoansImpl>
        borrowerList = new ArrayList<LoansImpl>();
        balance = 0;
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
    public int getID() { return ID; }

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
