package database.client;

import database.Engine;
import database.loan.Loans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer implements CustomerInterface, Serializable {
    private String name;
    private double balance;
    private List<Loans> lenderList;
    private List<Loans> borrowerList;
    private List<AccountTransaction> transactions;
    private List<PaymentNotification> notifications;



    public Customer(String clientName, double balance){
        name = clientName;
        lenderList = new ArrayList<>();
        borrowerList = new ArrayList<>();
        transactions = new ArrayList<>();
        this.balance = balance;
        notifications = new ArrayList<>();
    }

    @Override
    public void addMoney(double amount) {
        balance += amount;
        addTransaction(amount);
    }

    @Override
    public void drawMoney(double amount){
        if(balance-amount < 0)
            balance = 0;
        else
            balance -= amount;
        addTransaction((-1*amount));
    }
    @Override
    public double getBalance() { return balance; }

    @Override
    public String getName() { return name; }

    public List<Loans> getBorrowerList() {
        return borrowerList;
    }

    public List<PaymentNotification> getNotifications() {return notifications;}

    @Override
    public void addLoanToClient(Loans loan, boolean lenderOrBorrower) {
        if(lenderOrBorrower) // lender
           lenderList.add(loan);
        else
            borrowerList.add(loan);

    }
    public List<Loans> getLenderList() {
        return lenderList;
    }

    public List<AccountTransaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(double transaction){
        char incomeOrExpense;
        if(transaction<0)
            incomeOrExpense = '-';
        else
            incomeOrExpense = '+';
        transactions.add(new AccountTransaction(Engine.getTime(), abs(transaction), incomeOrExpense, balance - transaction, balance));
    }

    public void addNotification(String loanID, int notificationYaz, int sumOfPayment){
        notifications.add(0, new PaymentNotification(loanID,notificationYaz,sumOfPayment));
    }
}
