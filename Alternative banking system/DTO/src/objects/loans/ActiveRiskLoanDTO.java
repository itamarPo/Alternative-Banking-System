package objects.loans;

import objects.loans.payments.PaymentsDTO;
import java.util.List;
import java.util.Map;

public class ActiveRiskLoanDTO extends PendingLoanDTO{
    private int startingActiveTime;
    private int nextPaymentTime;
    private List<PaymentsDTO> Payments;
    private double loanSize;
    private double allInterestPayedSoFar;
    private double allInitialPayedSoFar;
    private double allInterestLeftToPay;
    private double allInitialLeftToPay;

    public ActiveRiskLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, double interestPerPayment, int timePerPayment, String status, Map<String, Double> listOfLenders, Double collectedSoFar, Double sumLeftToBeCollected, int startingActiveTime, int nextPaymentTime, List<PaymentsDTO> payments, double allInterestPayedSoFar, double allInitialPayedSoFar, double allInterestLeftToPay, double allInitialLeftToPay) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status, listOfLenders, collectedSoFar, sumLeftToBeCollected);
        this.startingActiveTime = startingActiveTime;
        this.nextPaymentTime = nextPaymentTime;
        this.Payments = payments;
        this.allInterestPayedSoFar = allInterestPayedSoFar;
        this.allInitialPayedSoFar = allInitialPayedSoFar;
        this.allInterestLeftToPay = allInterestLeftToPay;
        this.allInitialLeftToPay = allInitialLeftToPay;
        this.loanSize = sizeNoInterest + sizeNoInterest*(interestPerPayment)/100;
    }

    public List<PaymentsDTO> getPayments() {
        return Payments;
    }

    @Override
    public void print(){
        super.print();
        System.out.println("Total payment including interest: " + loanSize);
        System.out.println("Activation time: " + startingActiveTime);
        System.out.println("Next payment date: " + nextPaymentTime);
        System.out.println("Payments: ");
        for (PaymentsDTO payment : Payments) {
            payment.print();
        }
        System.out.println("Initial sum payed so far: " + allInitialPayedSoFar);
        System.out.println("Interest sum payed so far: " + allInterestPayedSoFar);
        System.out.println("Initial sum yet to be payed: " + allInitialLeftToPay);
        System.out.println("Interest sum yet to be payed: " + allInterestLeftToPay);
    }
}
