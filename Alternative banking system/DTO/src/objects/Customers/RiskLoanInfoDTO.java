package objects.Customers;

public class RiskLoanInfoDTO extends LoanInfoDTO{
    //some variables which im not sure what are supposed to be.
    public RiskLoanInfoDTO(double loanSize, String loanName, String loanCategory, double sizeNoInterest, int interestPerPayment, int timePerPayment, String status) {
        super(loanSize, loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
    }

    @Override
    public void print() {
        super.print();
    }
}
