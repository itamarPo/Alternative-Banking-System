package database.loan;

import database.loan.status.LoanStatus;
import database.loan.status.LoanStatusImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoansImpl implements  Loans{

    //private String loanName;
    private String borrowerName;
    private final String LOANID;
    private String loanCategory;
    private double loanSize; // includinginterest
    private int timeLimitOfLoan;
    private double loanInterest;
    private int timePerPayment;
    private Map<String, Double> listOflenders;

    private double loanSizeNoInterest;
    private double normalPay;

    private LoanStatusImpl status;
    private double collectedSoFar; //two implentations: pending- money collected so far by lenders. active: money payed by customer so far
    // sum that needed to make load active is (loanSizeNoInterest - collectedSoFar)

    public LoansImpl(String borrowerName, String LOANID, String loanCategory, int timeLimitOfLoan, double loanInterest, int timePerPayment, double loanSizeNoInterest) {
        this.borrowerName = borrowerName;
        this.LOANID = LOANID;
        this.loanCategory = loanCategory;
        this.loanSize = loanSizeNoInterest + loanSizeNoInterest*(loanInterest/100);
        this.timeLimitOfLoan = timeLimitOfLoan;
        this.loanInterest = loanInterest;
        this.timePerPayment = timePerPayment;
        this.listOflenders = new HashMap<String, Double>();
        this.loanSizeNoInterest = loanSizeNoInterest;
        this.normalPay = 0;
        this.collectedSoFar = 0;
        this.status = null;
    }

    //checks if load should switch to active
    public void pending() {
        if (loanSizeNoInterest - collectedSoFar == 0) {
            status = new LoanStatusImpl();
        }
        double sum = 0;
        for (Map.Entry entry : listOflenders.entrySet()) {
            sum = (double) entry.getValue() + sum;
        }


    }

    public void print(){
        System.out.println(this.LOANID);
        System.out.println(this.borrowerName);
        System.out.println(this.loanCategory);
        System.out.println(this.loanSizeNoInterest);
        System.out.println(this.timeLimitOfLoan);
        System.out.println(this.loanInterest);
        System.out.println(this.timePerPayment);
        if(this.status == null)
            System.out.println("Pending");
        else
            status.print();
    }
}
