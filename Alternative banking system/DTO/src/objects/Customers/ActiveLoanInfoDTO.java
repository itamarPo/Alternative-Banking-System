package objects.Customers;

public class ActiveLoanInfoDTO extends LoanInfoDTO{
    private int nextTimePayment;
    private double expectedPayment;

    public ActiveLoanInfoDTO(double loanSize, String loanName, String loanCategory, double sizeNoInterest, int interestPerPayment, int timePerPayment, String status) {
        super(loanSize, loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
    }

    @Override
    public void print() {
        super.print();
    }
}
