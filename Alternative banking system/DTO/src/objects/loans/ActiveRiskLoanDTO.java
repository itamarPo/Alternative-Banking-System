package objects.loans;

import objects.loans.payments.PaymentsDTO;
import java.util.List;

public class ActiveRiskLoanDTO extends NewLoanDTO{
    private int startingActiveTime;
    private int nextPaymentTime;
    private List<PaymentsDTO> Payments;
    private double loanSize;
    private double allInterestPayedSoFar;
    private double allInitialPayedSoFar;
    private double allInterestLeftToPay;
    private double allInitialLeftToPay;

    public ActiveRiskLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, int interestPerPayment, int timePerPayment, String status, int startingActiveTime, int nextPaymentTime, List<PaymentsDTO> Payments, double allInterestPayedSoFar, double allInitialPayedSoFar, double allInterestLeftToPay, double allInitialLeftToPay) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status);
        this.startingActiveTime = startingActiveTime;
        this.nextPaymentTime = nextPaymentTime;
        this.Payments = Payments;
        this.allInterestPayedSoFar = allInterestPayedSoFar;
        this.allInitialPayedSoFar = allInitialPayedSoFar;
        this.allInterestLeftToPay = allInterestLeftToPay;
        this.allInitialLeftToPay = allInitialLeftToPay;
        this.loanSize = sizeNoInterest + sizeNoInterest*(interestPerPayment)/100;
    }
    @Override
    public void print(){
        super.print();
        System.out.println("Total payment including interest: " + loanSize);
        System.out.println("Activation time: " + startingActiveTime);
        System.out.println("Next payment date: " + nextPaymentTime);
        System.out.println("Payments: ");
        for(PaymentsDTO payment: Payments){
            payment.print();
        }
        System.out.println("Initial sum payed so far: " + allInitialPayedSoFar);
        System.out.println("Interest sum payed so far: " + allInterestPayedSoFar);
        System.out.println("Initial sum yet to be payed: " + allInitialLeftToPay);
        System.out.println("Interest sum yet to be payed: " + allInterestLeftToPay);
    }
}
