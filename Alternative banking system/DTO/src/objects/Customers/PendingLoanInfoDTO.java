package objects.Customers;

public class PendingLoanInfoDTO extends LoanInfoDTO{
    private double collectedSoFar;

    public PendingLoanInfoDTO(double loanSize, String loanName, String loanCategory, double sizeNoInterest, int interestPerPayment, int timePerPayment, String status, double collectedSoFar) {
        super(loanSize, loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
        this.collectedSoFar = collectedSoFar;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("The loan requires " + (super.getSizeNoInterest() - collectedSoFar) + "funds to become active.");
    }
}
