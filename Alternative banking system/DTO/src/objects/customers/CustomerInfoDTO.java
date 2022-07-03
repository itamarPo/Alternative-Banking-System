package objects.customers;


import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.payments.PaymentNotificationDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerInfoDTO {

//  private List<LoanInfoDTO> lenderList; //remove
//  private List<LoanInfoDTO> borrowerList; //remove
//  private List<LoanInfoDTO> loansForSale; //  might remove
//  private List<PaymentNotificationDTO> notificationDTOS; // remove
   private List<AccountTransactionDTO> transactionDTOS;
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

    //getters
    public double getBalance() {return balance;}
    public List<AccountTransactionDTO> getTransactionDTOS() {return transactionDTOS;}
    public String getName() {return name;}
    public Integer getNewBorrower() {return newBorrower;}
    public Integer getPendingBorrower() {return pendingBorrower;}
    public Integer getActiveBorrower() {return activeBorrower;}
    public Integer getRiskBorrower() {return riskBorrower;}
    public Integer getFinishedBorrower() {return finishedBorrower;}
    public Integer getPendingLender() {return pendingLender;}
    public Integer getActiveLender() {return activeLender;}
    public Integer getRiskLender() {return riskLender;}
    public Integer getFinishedLender() {return finishedLender;}

    //setters
    public void setNewBorrower(Integer newBorrower) {this.newBorrower = newBorrower;}
    public void setPendingBorrower(Integer pendingBorrower) {this.pendingBorrower = pendingBorrower;}
    public void setActiveBorrower(Integer activeBorrower) {this.activeBorrower = activeBorrower;}
    public void setRiskBorrower(Integer riskBorrower) {this.riskBorrower = riskBorrower;}
    public void setFinishedBorrower(Integer finishedBorrower) {this.finishedBorrower = finishedBorrower;}
    public void setPendingLender(Integer pendingLender) {this.pendingLender = pendingLender;}
    public void setActiveLender(Integer activeLender) {this.activeLender = activeLender;}
    public void setRiskLender(Integer riskLender) {this.riskLender = riskLender;}
    public void setFinishedLender(Integer finishedLender) {this.finishedLender = finishedLender;}

    //    public List<LoanInfoDTO> getLenderList() {
//        return lenderList;
//    }
//    public List<LoanInfoDTO> getBorrowerList() {
//        return borrowerList;
//    }
//    public List<LoanInfoDTO> getLoansForSale() {return loansForSale;}

//    public void setLoansAmounts(){
//        newBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("New")).collect(Collectors.toList()).size();
//        pendingBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("Pending")).collect(Collectors.toList()).size();
//        activeBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("Active")).collect(Collectors.toList()).size();
//        riskBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("Risk")).collect(Collectors.toList()).size();
//        finishedBorrower = borrowerList.stream().filter(x -> x.getStatus().equals("Finished")).collect(Collectors.toList()).size();
//        pendingLender = lenderList.stream().filter(x -> x.getStatus().equals("Pending")).collect(Collectors.toList()).size();
//        activeLender = lenderList.stream().filter(x -> x.getStatus().equals("Active")).collect(Collectors.toList()).size();
//        riskLender = lenderList.stream().filter(x -> x.getStatus().equals("Risk")).collect(Collectors.toList()).size();
//        finishedLender = lenderList.stream().filter(x -> x.getStatus().equals("finished")).collect(Collectors.toList()).size();
//    }

    public CustomerInfoDTO(String name, double balance) {
        this.transactionDTOS = new ArrayList<>();
//        this.lenderList = new ArrayList<>();
//        this.borrowerList = new ArrayList<>();
//        this.loansForSale = new ArrayList<>();
        this.name = name;
        this.balance = balance;
    }

//    public void print(){
//        System.out.println("\r\nCustomer's name: " + name);
//        System.out.println("Current balance: " + String.format("%.2f", balance));
//        System.out.println("Customer's transactions: ");
//        for(AccountTransactionDTO transaction: transactionDTOS)
//            transaction.print();
//        System.out.println("The loans that the costumer is lending to: ");
//        for(LoanInfoDTO lenderLoan: lenderList)
//            lenderLoan.print();
//        System.out.println("-------");
//        System.out.println("The loans that the costumer is borrowing from: ");
//        for(LoanInfoDTO borrowerLoan: borrowerList)
//            borrowerLoan.print();
//        System.out.println("----------------------------------------------");
//    }

}
