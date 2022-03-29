package objects.customers;


import objects.customers.loanInfo.LoanInfoDTO;

import java.util.List;

public class CustomerInfoDTO {
  private  List<AccountTransactionDTO> transactionDTOS;
  private  List<LoanInfoDTO> lenderList;
  private  List<LoanInfoDTO> borrowerList;
  private String name;
  private double balance;

    public CustomerInfoDTO(List<AccountTransactionDTO> transactionDTOS, List<LoanInfoDTO> lenderList, List<LoanInfoDTO> borrowerList, String name, double balance) {
        this.transactionDTOS = transactionDTOS;
        this.lenderList = lenderList;
        this.borrowerList = borrowerList;
        this.name = name;
        this.balance = balance;
    }

    public void print(){
        System.out.println("Customer's name: " + name);
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
    }
}
