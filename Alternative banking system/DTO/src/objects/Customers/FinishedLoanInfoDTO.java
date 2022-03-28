package objects.Customers;

public class FinishedLoanInfoDTO extends LoanInfoDTO{
    private int startingTime;
    private int finishingTime;
    public FinishedLoanInfoDTO(double loanSize, String loanName, String loanCategory, double sizeNoInterest, int interestPerPayment, int timePerPayment, String status, int startingTime, int finishingTime) {
        super(loanSize, loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
        this.startingTime = startingTime;
        this.finishingTime = finishingTime;
    }

    @Override
    public void print() {
        super.print();
    }
}
