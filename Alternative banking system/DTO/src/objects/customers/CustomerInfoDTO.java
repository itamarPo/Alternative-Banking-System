package objects.customers;


import objects.customers.loanInfo.LoanInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class CustomerInfoDTO {
  private  List<AccountTransactionDTO> transactionDTOS;
  private  List<LoanInfoDTO> lenderList;
  private  List<LoanInfoDTO> borrowerList;
  private String name;
  private double balance;

    public List<AccountTransactionDTO> getTransactionDTOS() {
        return transactionDTOS;
    }

    public List<LoanInfoDTO> getLenderList() {
        return lenderList;
    }

    public List<LoanInfoDTO> getBorrowerList() {
        return borrowerList;
    }

    public CustomerInfoDTO(String name, double balance) {
        this.transactionDTOS = new ArrayList<>();
        this.lenderList = new ArrayList<>();
        this.borrowerList = new ArrayList<>();
        this.name = name;
        this.balance = balance;
    }

    public void print(){
        System.out.println("\r\nCustomer's name: " + name);
        System.out.println("Current balance: " + balance);
        System.out.println("Customer's transactions: ");
        for(AccountTransactionDTO transaction: transactionDTOS)
            transaction.print();
        System.out.println("The loans that the costumer is lending to: ");
        for(LoanInfoDTO lenderLoan: lenderList)
            lenderLoan.print();
        System.out.println("The loans that the costumer is borrowing from: ");
        for(LoanInfoDTO borrowerLoan: borrowerList)
            borrowerLoan.print();
        System.out.println();
    }
}
