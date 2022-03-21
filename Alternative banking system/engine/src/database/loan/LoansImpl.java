package database.loan;

import java.util.List;
import java.util.Map;

public class LoansImpl implements  Loans{


    public enum LoansStatus{
        Active(pay = null, startingActiveTime = 0,),
        Risk(),
        Finished(),
        private List<Payment> pay;
        private int startingActiveTime;
        private int finishTime;
    }
    private LoansStatus status = LoansStatus.Active;
    status = LoansStatus.Risk(status.pay, status.startingActiveTime, status.finishTime);
    private String loanName;
    private String borrowerName;
    private final int LOANID;
    private String loanCategory;
    //private int startingTime; //(Active or Pending?)
    private int timeLimitOfLoan;
    private int timePerPayment;
    private double loanSize;
    private double normalPay;
    //private double currentPay;
    //private LoanStatus status; if(status==risk)
    private double collectedSoFar; //two implentations: pending- money collected so far by lenders. active: money payed by customer so far
    private double loanInterest;
    private Map<String, Double> listOflenders;

    public LoansImpl(String loanName, String borrowerName, int LOANID, int startingTime, int timeLimitOfLoan, int timePerPayment, double loanSize, double loanInterest, List<String> listOflenders, String... argv) {
     this.loanName = loanName;
     this.borrowerName = borrowerName;
     this.LOANID = LOANID;
     if(argv == null)
      this.loanCategory = "Uncategorized";
     else
      this.loanCategory = argv[0];
    // this.startingTime = startingTime;
     this.timeLimitOfLoan = timeLimitOfLoan;
     this.timePerPayment = timePerPayment;
     this.loanSize = loanSize;
     this.loanInterest = loanInterest;
     this.listOflenders = listOflenders;
     this.payedSoFar=0;
    }

    public void pending(){

    }
   @Override
    public String getLoanName() {
     return loanName;
    }

    @Override
    public String getBorrowerName() {
     return borrowerName;
    }

    @Override
    public int getLOANID() {
     return LOANID;
    }

    @Override
    public String getLoanCategory() {
     return loanCategory;
    }

    @Override
    public int getStartingTime() {
     return startingTime;
    }

   @Override
    public int getTimeLimitOfLoan() {
     return timeLimitOfLoan;
    }

   @Override
    public int getTimePerPayment() {
     return timePerPayment;
    }

    @Override
    public double getLoanSize() {
     return loanSize;
    }

    @Override
    public double getLoanInterest() {
     return loanInterest;
    }

    @Override
    public List<String> getListOflenders() {
     return listOflenders;
    }


}
