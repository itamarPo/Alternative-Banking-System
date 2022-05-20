package objects.customers;


import objects.customers.loanInfo.LoanInfoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerInfoDTO {
  private  List<AccountTransactionDTO> transactionDTOS;
  private  List<LoanInfoDTO> lenderList;
  private  List<LoanInfoDTO> borrowerList;
  private String name;
  private double balance;
  private Integer newBorrower;
  private Integer pendingBorrower;
  private Integer activeBorrower;
  private Integer riskBorrower;
  private Integer finishedBorrower;
  private Integer pendingLender;
  private Integer activeLender;
  private Integer riskLender;
  private Integer finishedLender;

    public double getBalance() {
        return balance;
    }
    public List<AccountTransactionDTO> getTransactionDTOS() {
        return transactionDTOS;
    }
    public List<LoanInfoDTO> getLenderList() {
        return lenderList;
    }
    public List<LoanInfoDTO> getBorrowerList() {
        return borrowerList;
    }
    public String getName() {
        return name;
    }
    public Integer getNewBorrower() {return newBorrower;}
    public Integer getPendingBorrower() {return pendingBorrower;}
    public Integer getActiveBorrower() {return activeBorrower;}
    public Integer getRiskBorrower() {return riskBorrower;}
    public Integer getFinishedBorrower() {return finishedBorrower;}
    public Integer getPendingLender() {return pendingLender;}
    public Integer getActiveLender() {return activeLender;}
    public Integer getRiskLender() {return riskLender;}
    public Integer getFinishedLender() {return finishedLender;}

    public void setLoansAmounts(){
        newBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("New")).collect(Collectors.toList()).size();
        pendingBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("Pending")).collect(Collectors.toList()).size();
        activeBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("Active")).collect(Collectors.toList()).size();
        riskBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("Risk")).collect(Collectors.toList()).size();
        finishedBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("Finished")).collect(Collectors.toList()).size();
        pendingLender = lenderList.stream().filter(x -> x.getStatus().equals("Pending")).collect(Collectors.toList()).size();
        activeLender = lenderList.stream().filter(x -> x.getStatus().equals("Active")).collect(Collectors.toList()).size();
        riskLender = lenderList.stream().filter(x -> x.getStatus().equals("Risk")).collect(Collectors.toList()).size();
        finishedLender = lenderList.stream().filter(x -> x.getStatus().equals("finished")).collect(Collectors.toList()).size();
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
        System.out.println("Current balance: " + String.format("%.2f", balance));
        System.out.println("Customer's transactions: ");
        for(AccountTransactionDTO transaction: transactionDTOS)
            transaction.print();
        System.out.println("The loans that the costumer is lending to: ");
        for(LoanInfoDTO lenderLoan: lenderList)
            lenderLoan.print();
        System.out.println("-------");
        System.out.println("The loans that the costumer is borrowing from: ");
        for(LoanInfoDTO borrowerLoan: borrowerList)
            borrowerLoan.print();
        System.out.println("----------------------------------------------");
    }

}
