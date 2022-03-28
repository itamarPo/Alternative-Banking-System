package objects.Loans;

import objects.Loans.Payments.PaymentsDTO;
import java.util.List;

public class ActiveRiskLoanDTO extends NewLoanDTO{
    private int startingActiveTime;
    private int nextPaymentTime;
    private List<PaymentsDTO> successfulPayments;
    private double allInterestPayedSoFar;
    private double allInitialPayedSoFar;
    private double allInterestLeftToPay;
    private double allInitialLeftToPay;

    public ActiveRiskLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, int interestPerPayment, int timePerPayment, String status, int startingActiveTime, int nextPaymentTime, List<PaymentsDTO> successfulPayments, double allInterestPayedSoFar, double allInitialPayedSoFar, double allInterestLeftToPay, double allInitialLeftToPay) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status);
        this.startingActiveTime = startingActiveTime;
        this.nextPaymentTime = nextPaymentTime;
        this.successfulPayments = successfulPayments;
        this.allInterestPayedSoFar = allInterestPayedSoFar;
        this.allInitialPayedSoFar = allInitialPayedSoFar;
        this.allInterestLeftToPay = allInterestLeftToPay;
        this.allInitialLeftToPay = allInitialLeftToPay;
    }
    public void print(){}
}
