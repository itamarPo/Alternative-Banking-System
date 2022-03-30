package objects.customers.loanInfo;

public class PendingLoanInfoDTO extends LoanInfoDTO{
    private double collectedSoFar;

    public PendingLoanInfoDTO(String loanName, String loanCategory, double sizeNoInterest, int interestPerPayment, int timePerPayment, String status, double collectedSoFar) {
        super(loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
        this.collectedSoFar = collectedSoFar;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("The loan requires " + (super.getSizeNoInterest() - collectedSoFar) + " more funds to become active.");
    }
}