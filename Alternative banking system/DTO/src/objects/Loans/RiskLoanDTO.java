package objects.Loans;

import objects.Loans.Payments.PaymentsDTO;
import java.util.List;

public class RiskLoanDTO extends ActiveLoanDTO{
    private List<PaymentsDTO> unSuccessfulPayments;

    public RiskLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, int interestPerPayment, int timePerPayment, String status, int startingActiveTime, int nextPaymentTime, List<PaymentsDTO> successfulPayments, double allInterestPayedSoFar, double allInitialPayedSoFar, double allInterestLeftToPay, double allInitialLeftToPay, List<PaymentsDTO> unSuccessfulPayments) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status, startingActiveTime, nextPaymentTime, successfulPayments, allInterestPayedSoFar, allInitialPayedSoFar, allInterestLeftToPay, allInitialLeftToPay);
        this.unSuccessfulPayments = unSuccessfulPayments;
    }
    public void print(){}
}
