package database.loan;

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
    private double collectedSoFar; //two implentations: pending- money collected so far by lenders. active: money payed by customer so far
    // sum that needed to make load active is (loanSizeNoInterest - collectedSoFar)



    public Loans(String borrowerName, String LOANID, String loanCategory, int timeLimitOfLoan, int InterestPerPayment, int timePerPayment, double loanSizeNoInterest) {
        this.borrowerName = borrowerName;
        this.LOANID = LOANID;
        this.loanCategory = loanCategory;
        this.loanSize = loanSizeNoInterest + loanSizeNoInterest*(InterestPerPayment /100);
        this.timeLimitOfLoan = timeLimitOfLoan;
        this.InterestPerPayment = InterestPerPayment;
        this.timePerPayment = timePerPayment;
        this.listOflenders = new HashMap<String, Double>();
        this.loanSizeNoInterest = loanSizeNoInterest;
        this.normalPay = 0;
        this.collectedSoFar = 0;
        this.status = null;
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

    //checks if load should switch to active
    public void pending() {
        if (loanSizeNoInterest - collectedSoFar == 0) {
           // status = new LoanStatus();
        }
        else {
            double sum = 0;
            for (Map.Entry entry : listOflenders.entrySet()) {
                sum = (double) entry.getValue() + sum;
            }
            collectedSoFar = sum;
        }

    }
    
    public void print(){
        System.out.println(this.LOANID);
        System.out.println(this.borrowerName);
        System.out.println(this.loanCategory);
        System.out.println(this.loanSizeNoInterest);
        System.out.println(this.timeLimitOfLoan);
        System.out.println(this.InterestPerPayment);
        System.out.println(this.timePerPayment);
        if(this.status == null)
            System.out.println("Pending");
        else
            status.print();
    }

    @Override
    public void payment() {

    }
}
