package objects.Loans;

import java.util.Map;

public class PendingLoanDTO extends  NewLoanDTO{
    private Map<String, Double>  listOfLenders;
    private Double collectedSoFar;
    private Double sumLeftToBeCollected;

    public PendingLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, int interestPerPayment, int timePerPayment, String status, Map<String, Double> listOfLenders, Double collectedSoFar, Double sumLeftToBeCollected) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status);
        this.listOfLenders = listOfLenders;
        this.collectedSoFar = collectedSoFar;
        this.sumLeftToBeCollected = sumLeftToBeCollected;
    }
    public void print(){

    }
}
