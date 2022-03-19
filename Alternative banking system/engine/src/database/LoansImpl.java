package database;

import java.util.List;
import java.util.Set;

public class LoansImpl implements  Loans{
   // public enum LoansStatus{ the reason its commented it because we currently have no idea how to implement it.
        //Active , pending , risk , etc;
   // }
    private String loanName;
    private String borrowerName;
    private final int LOANID;
    private String loanCategory;
    private int startingTime;
    private int timeLimitOfLoan;
    private int timePerPayment;
    private double loanSize;
    private double loanInterest;
    private List<String> listOflenders;


    public LoansImpl() {
    }
}
