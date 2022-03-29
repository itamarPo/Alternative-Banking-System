package objects.customers.loanInfo;

public class RiskLoanInfoDTO extends LoanInfoDTO{
    //some variables which im not sure what are supposed to be.
    //TODO: understand what needs to be added and add it accordingly
    public RiskLoanInfoDTO(String loanName, String loanCategory, double sizeNoInterest, int interestPerPayment, int timePerPayment, String status) {
        super(loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
    }

    @Override
    public void print() {
        super.print();
    }
}
