package database.loan;

import database.Engine;
import database.client.Customer;
import database.loan.status.LoanStatus;
import database.loan.status.LoanStatusInterface;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Loans implements Serializable, LoansInterface {

    //private String loanName;
    private String borrowerName;
    private final String LOANID;
    private String loanCategory;
    private double loanSize; // includinginterest
    private int timeLimitOfLoan;
    private int InterestPerPayment;
    private int timePerPayment;
    private Map<String, Double> listOflenders;
    private double loanSizeNoInterest;
    private double normalPay;
    private LoanStatus status;
    private double collectedSoFar;
    private double leftToBeCollected;




    public Loans(String borrowerName, String LOANID, String loanCategory, int timeLimitOfLoan, int InterestPerPayment, int timePerPayment, double loanSizeNoInterest) {
        this.borrowerName = borrowerName;
        this.LOANID = LOANID;
        this.loanCategory = loanCategory;
        this.loanSize = loanSizeNoInterest + loanSizeNoInterest*(InterestPerPayment /100);
        this.timeLimitOfLoan = timeLimitOfLoan;
        this.InterestPerPayment = InterestPerPayment;
        this.timePerPayment = timePerPayment;
        this.listOflenders = new HashMap<>();
        this.loanSizeNoInterest = loanSizeNoInterest;
        this.normalPay = 0;
        this.collectedSoFar = 0;
        this.leftToBeCollected = loanSizeNoInterest;
        this.status = new LoanStatus("New", null, 0,0,0,0,0,0,0);
    }

    public double getLeftToBeCollected() {
        return leftToBeCollected;
    }
    public String getLOANID() {
        return LOANID;
    }

    public double getLoanSize() {
        return loanSize;
    }

    public int getTimeLimitOfLoan() {
        return timeLimitOfLoan;
    }

    public int getInterestPerPayment() {
        return InterestPerPayment;
    }

    public int getTimePerPayment() {
        return timePerPayment;
    }

    public Map<String, Double> getListOflenders() {
        return listOflenders;
    }

    public double getLoanSizeNoInterest() {
        return loanSizeNoInterest;
    }

    public String getLoanCategory() {
        return loanCategory;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public double getCollectedSoFar() {
        return collectedSoFar;
    }

    public void setCollectedSoFar(double collectedSoFar) {
        this.collectedSoFar += collectedSoFar;
    }

    public void setLeftToBeCollected(double leftToBeCollected) {
        this.leftToBeCollected -= leftToBeCollected;
    }


    @Override
    public void payment() {

    }

    public void updateStatusBeforeActive() {
        switch (status.getStatus()){
            case "New":{
                if(leftToBeCollected != 0 && collectedSoFar != 0){
                    changeToPending();
                }
                if(leftToBeCollected == 0){
                    changeToActive();
                }
                break;
            }
            case "Pending":{
                if(leftToBeCollected == 0){
                    changeToActive();
                }
                break;
            }
        }
    }

    @Override
    public void changeToPending() {
        status.setStatus("Pending");
    }

    @Override
    public void changeToActive() {
        status.setStatus("Active");
        status.setStartingActiveTime(Engine.getTime());
        status.setNextPaymentTime(Engine.getTime() + timePerPayment);
    }
}
