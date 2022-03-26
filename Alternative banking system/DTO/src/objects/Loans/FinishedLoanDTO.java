package objects.Loans;

import objects.Loans.Payments.PaymentsDTO;

import java.util.List;

public class FinishedLoanDTO extends NewLoanDTO{
    private int statingTime;
    private List<PaymentsDTO> payments;
    private int finishingTime;

    public FinishedLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, int interestPerPayment, int timePerPayment, String status, int statingTime, List<PaymentsDTO> payments, int finishingTime) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status);
        this.statingTime = statingTime;
        this.payments = payments;
        this.finishingTime = finishingTime;
    }
    public void print(){}
}
